package controllers;

import model.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import service.MailService;
import service.RecaptchaService;
import views.html.*;
import views.html.helper.form;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;

public class LoginRegisterController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result register() {
        return ok(register.render(formFactory.form(User.class)));
    }

    public Result login() {
        return ok(login.render());
    }

    public Result doLogin() {
        DynamicForm form = formFactory.form().bindFromRequest();
        User user = User.findByAutenticationData(form.get("username"), form.get("password"));
        if(user != null)
        {
            Http.Context.current().session().put("userId", user.getUsername());
            return redirect(routes.Application.index()+ Http.Context.current().session().get("userId"));
        }
        return ok();
    }

    public Result logout()
    {
        Http.Context.current().session().clear();
        return redirect(routes.Application.index()+ Http.Context.current().session().get("userId"));
    }

    public Result doRegister() {
        Form<User> formClient = formFactory.form(User.class).bindFromRequest();
        User user = formClient.bindFromRequest().get();
        try {
            DynamicForm form = formFactory.form().bindFromRequest();
            if (RecaptchaService.verify(form.get("g-recaptcha-response"))) {
                user.setEnabled(false);
                user.setActivationtoken(generateActivationToken());
                MailService.sendEmail(user);
                user.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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