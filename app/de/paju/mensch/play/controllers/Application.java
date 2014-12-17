package de.paju.mensch.play.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import play.mvc.Result;
import play.mvc.WebSocket;
import de.paju.mensch.controller.Controller;
import de.paju.mensch.controller.Controller.GAME_STATE;
import de.paju.mensch.play.uis.WebGUI;
import de.paju.mensch.play.uis.WebTUI;
import de.paju.mensch.play.uis.websockets.GameWebSocket;
import de.paju.mensch.play.views.html.gamegrid;
import de.paju.mensch.play.views.html.webtui;
import de.paju.mensch.view.GUI;


public class Application extends play.mvc.Controller {
	
	private static Controller game;
	private static WebTUI tui;
//	private static GUI gui;
	private static GameWebSocket gameSocket;
	private static WebGUI webGUI;
	


    public static Result index() {
    	game = new Controller();
    	tui = new WebTUI(game);
//    	gui = new GUI(game);
    	gameSocket = new GameWebSocket();
    	webGUI = new WebGUI(game, gameSocket);
        return ok(de.paju.mensch.play.views.html.main.render("PajuMensch"));
    }
    
    public static Result init(String e){
    	if(game == null)
    		game = new Controller();
    	if(tui == null)
    		tui = new WebTUI(game);
    	if(gameSocket == null)
    		gameSocket = new GameWebSocket();
    	if(webGUI == null)
    		webGUI = new WebGUI(game, gameSocket);
//    	if(gui == null)
//    		gui = new GUI(game);
    	game.addObserver(tui);
    	game.addObserver(webGUI);
//    	game.addObserver(gui);
    	game.start();
//    	if(game.getStatus() == GAME_STATE.CHOOSE_PLAYER_COUNT){
    		game.inputPlayerCount(Integer.parseInt(e));
//    	}
    	return ok(game.getStatus().toString());
    }
    
    public static Result doDice(){
    	if (game.getStatus() == GAME_STATE.ROLL){
    		game.doDice();
    	}
    	return ok(Integer.toString(game.getRoll()));
    }
    
    public static WebSocket<String> socket(){
    	return gameSocket;
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
    	return ok(gamegrid.render(message, field, stack, target, game.getAnzahlMitspieler()));
    }
    
    public static Result chooseFigure(Integer fig){
    	game.setPickFigure(fig);
    	return ok();
    }
    
//    public static Result webGui() {
//    	return ok(webgui.render());
//    }
    
    public static Result exit(){
    	game = null;
    	tui = null;
    	webGUI = null;
    	return ok("Game restarted");
    }
    
    public static Result tui(String e, String input){
    	String output = tui.nextStep(e, input);
    	return ok(webtui.render(output));
    }
}
