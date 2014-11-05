package controllers;

import observer.IObserver;
import play.mvc.Controller;
import play.mvc.Result;
import uis.WebTUI;
import views.html.index;
import views.html.webtui;

public class Application extends Controller {
	
	private static controller.Controller game = new controller.Controller();
	private static WebTUI tui = new WebTUI(game);

    public static Result index() {
    	game.addObserver(tui);
    	game.start();
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result nextStep(String e, String input){
    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(output));
    }

}
