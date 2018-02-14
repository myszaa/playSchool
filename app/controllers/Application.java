package controllers;

import it.innove.play.pdf.PdfGenerator;
import model.User;
import play.*;
import play.i18n.Lang;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.time.Duration;

public class Application extends Controller {

    public Result index() {
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

}
