package controllers;

import it.innove.play.pdf.PdfGenerator;
import model.User;
import play.*;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;

public class Application extends Controller {

    public Result index() {
        return ok(welcome.render());
    }

    @Inject
    public PdfGenerator pdfGenerator;
    public Result pdf()
    {
        User user = User.findByUsername(Http.Context.current().session().get("userId"));
        return pdfGenerator.ok(userPdf.render(user), "http://localhost:9000");
    }

}
