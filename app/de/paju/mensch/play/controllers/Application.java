package de.paju.mensch.play.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.mvc.Result;
import play.mvc.WebSocket;
import de.paju.mensch.controller.Controller.GAME_STATE;
import de.paju.mensch.play.views.html.gamegrid;
import de.paju.mensch.play.views.html.webtui;


public class Application extends play.mvc.Controller {
	
	private static String SUPER_USER = "root";
	
	private static Map<String, GameSession> sessions = new HashMap<String, GameSession>();
	
//	private static Controller game;
//	private static WebTUI tui;
////	private static GUI gui;
//	private static GameWebSocket gameSocket;
//	private static WebGUI webGUI;
	


    public static Result index() {
    	if(!sessions.containsKey(SUPER_USER)){
    		sessions.put(SUPER_USER, new GameSession());
    		sessions.get(SUPER_USER).start();
    	}
        return ok(de.paju.mensch.play.views.html.main.render("PajuMensch"));
    }
    
    public static Result init(String e){
    	if(!sessions.containsKey(SUPER_USER)){
    		return redirect(routes.Application.index());
    	}
    	sessions.get(SUPER_USER).getGame().inputPlayerCount(Integer.parseInt(e));
    	return ok();
    }
    
    public static Result doDice(){
    	if (sessions.get(SUPER_USER).getGame().getStatus() == GAME_STATE.ROLL){
    		sessions.get(SUPER_USER).getGame().doDice();
    	}
    	return ok(Integer.toString(sessions.get(SUPER_USER).getGame().getRoll()));
    }
    
    public static WebSocket<String> socket(){
    	return sessions.get(SUPER_USER).getSockets().get(0);
    }
    
    public static Result gameGrid(){
    	String message = sessions.get(SUPER_USER).getGame().getFieldCoords().toString();
    	List<String>field = null;
    	List<String>stack = null;
    	List<String>target = new ArrayList<String>();
    	try {
			stack = sessions.get(SUPER_USER).getGame().getStackCoords();
			field = sessions.get(SUPER_USER).getGame().getFieldCoords();
			for(int i = 0 ; i < sessions.get(SUPER_USER).getGame().getAnzahlMitspieler() ; ++i){
				target.addAll(sessions.get(SUPER_USER).getGame().getTargetCoords(i));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ok(gamegrid.render(message, field, stack, target, sessions.get(SUPER_USER).getGame().getAnzahlMitspieler()));
    }
    
    public static Result chooseFigure(Integer fig){
    	sessions.get(SUPER_USER).getGame().setPickFigure(fig);
    	return ok();
    }
    
//    public static Result webGui() {
//    	return ok(webgui.render());
//    }
    
    public static Result exit(){
    	sessions.remove(SUPER_USER);
    	return ok("Game restarted");
    }
    
    public static Result tui(String e, String input){
//    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(""));
    }
}
