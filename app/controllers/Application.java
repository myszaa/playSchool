package controllers;

import io.ebean.Finder;
import it.innove.play.pdf.PdfGenerator;
import model.User;
import model.UserRole;
import play.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.i18n.Lang;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.time.Duration;

public class Application extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index() {

        if(UserRole.getAllUserRole().isEmpty())
        {
            UserRole userRole = new UserRole();
            userRole.setRole("Student");
            userRole.save();

            userRole = new UserRole();
            userRole.setRole("Teacher");
            userRole.save();

            userRole = new UserRole();
            userRole.setRole("Administrator");
            userRole.save();
        }
        if(User.getAllUsers().isEmpty())
        {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.getUserRoles().add(UserRole.findByUserRole("Administrator"));
            user.setEnabled(true);
            user.save();
        }
        Logger.info("Language: " + ctx().lang().code());
        return ok(welcome.render(Http.Context.current().messages()));
    }
    
    //https://github.com/innoveit/play2-pdf
    @Inject
    public PdfGenerator pdfGenerator;
    public Result pdf()
    {
        User user = User.findByUsername(Http.Context.current().session().get("userId"));
        if(user == null){
            user = new User();
            user.setUsername("null");
        }
        return pdfGenerator.ok(userPdf.render(user), "http://localhost:9000");
    }


    public Result changeToPolish()
    {
        ctx().changeLang("pl");
        Http.CookieBuilder cb = Http.Cookie.builder("PLAY_LANG", "pl");
        cb.withMaxAge(Duration.ofDays(14));
        ctx().response().setCookie(cb.build());
        return redirect(routes.Application.index());
    }

    public Result changeToEng()
    {
        ctx().changeLang("en");
        Http.CookieBuilder cb = Http.Cookie.builder("PLAY_LANG", "en");
        cb.withMaxAge(Duration.ofDays(14));
        ctx().response().setCookie(cb.build());
        return redirect(routes.Application.index());
    }

    public Result addRole()
    {
        return ok(role.render(Http.Context.current().messages(),UserRole.getAllUserRole(),User.getAllUsers()));
    }

    public Result addRoleToUser()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        User user = User.findByUsername(form.get("username"));
        UserRole userRole = UserRole.findByUserRole(form.get("userrole"));
        Logger.info(user.getUsername() + userRole.getName());
        if(!user.getUserRoles().contains(userRole)) user.getUserRoles().add(userRole);
        user.save();
        return redirect(routes.Application.index());
   }

    public Result addSubject()
    {
        return ok(subject.render(Http.Context.current().messages(),Subject.getAllSubjects(),User.getAllUsers()));
    }
}
