package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	
    	controller.Controller con = new controller.Controller();
        return ok(index.render("Your new application is ready."));
    }

}
