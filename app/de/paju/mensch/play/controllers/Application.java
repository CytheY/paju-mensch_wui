package de.paju.mensch.play.controllers;

import play.mvc.Result;
import de.paju.mensch.controller.Controller;
import de.paju.mensch.play.uis.WebTUI;
import de.paju.mensch.play.views.html.gamegrid;
import de.paju.mensch.play.views.html.webgui;
import de.paju.mensch.play.views.html.webtui;


public class Application extends play.mvc.Controller {
	
	private static Controller game = new Controller();
	private static WebTUI tui = new WebTUI(game);
	


    public static Result index() {
    	game.addObserver(tui);
    	game.start();
        return ok();
    }
    
    public static Result tui(String e, String input){
    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(output));
    }
    
    public static Result gameGrid(){
    	String string = game.getFieldCoords().toString();
    	
    	return ok(gamegrid.render(string));
    }
    
    public static Result webGui() {
    	return ok(webgui.render());
    }
}
