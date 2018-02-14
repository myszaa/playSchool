package controllers;

import model.User;
import play.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import service.MailService;
import views.html.*;

import javax.inject.Inject;
import java.util.UUID;

public class LoginRegisterController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result register() {
        return ok(register.render(formFactory.form(User.class)));
    }

    public Result doRegister() {
        Form<User> formClient = formFactory.form(User.class).bindFromRequest();
        User user = formClient.bindFromRequest().get();
        user.setEnabled(false);
        user.setActivationtoken(generateActivationToken());
        MailService.sendEmail(user);
        user.save();
        return ok();
    }

    public String generateActivationToken()
    {
        return UUID.randomUUID().toString();
    }

    public Result activate() {
        String code = Http.Context.current().request().getQueryString("code");
        if(code != null && code.length() > 0)
        {
            User user = User.findByActivationtoken(code);
            if (user != null && !user.isEnabled())
            {
                user.setEnabled(true);
                user.setActivationtoken("");
                user.save();
            }
        }
        return ok();
    }

}