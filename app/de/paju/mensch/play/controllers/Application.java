package de.paju.mensch.play.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import play.mvc.Result;
import de.paju.mensch.controller.Controller;
import de.paju.mensch.play.uis.WebTUI;
import de.paju.mensch.play.views.html.gamegrid;
import de.paju.mensch.play.views.html.webtui;


public class Application extends play.mvc.Controller {
	
	private static Controller game = new Controller();
	private static WebTUI tui = new WebTUI(game);
	


    public static Result index() {
    	game.addObserver(tui);
    	game.start();
        return ok(de.paju.mensch.play.views.html.main.render("PajuMensch"));
    }
    
    public static Result tui(String e, String input){
    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(output));
    }
    
    public static Result init(String e){
    	String output = tui.nextStep("Enter", e);
    	return ok(webtui.render(output));
    }
    
    public static Result gameGrid(){
    	String message = game.getFieldCoords().toString();
    	List<String>field = null;
    	List<String>stack = null;
    	List<String>target = new ArrayList<String>();
    	try {
			stack = game.getStackCoords();
			field = game.getFieldCoords();
			for(int i = 0 ; i < game.getAnzahlMitspieler() ; ++i){
				target.addAll(game.getTargetCoords(i));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ok(gamegrid.render(message, field, stack, target));
    }
    
//    public static Result webGui() {
//    	return ok(webgui.render());
//    }
    
    public static Result exit(){
    	game = new Controller();
    	tui = new WebTUI(game);
    	game.addObserver(tui);
    	game.start();
    	return ok("Game restarted");
    }
}
