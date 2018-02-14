package controllers;

import model.User;
import play.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;

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
        user.save();
        return ok();
    }

}