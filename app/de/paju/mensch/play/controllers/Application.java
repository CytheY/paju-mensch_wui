package de.paju.mensch.play.controllers;

import de.paju.mensch.play.uis.WebTUI;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.controller.Controller;
import play.mvc.Result;
import de.paju.mensch.play.views.html.index;
import de.paju.mensch.play.views.html.webtui;

public class Application extends play.mvc.Controller {
	
	private static Controller game = new Controller();
	private static WebTUI tui = new WebTUI(game);

    public static Result index() {
    	game.addObserver(tui);
    	game.start();
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result tui(String e, String input){
    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(output));
    }

}
