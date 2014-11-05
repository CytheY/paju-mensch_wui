package uis;

import java.io.File;

import view.TextGUI;
import controller.Controller;
import controller.Controller.GAME_STATE;

public class WebTUI extends TextGUI {
	
	private Controller controller;
	private String tmpLine;

	public WebTUI(Controller controller) {
		super(controller);
		this.controller = controller;
		tmpLine = "";
	}
	
	@Override
	public void println(String sg){
		System.out.println(sg);
		if(sg != null && !sg.equals("null"))
			tmpLine += sg + "\n";
	}
	
	@Override
	public void print(String sg){
		System.out.print(sg);
		if(sg != null && !sg.equals("null"))
			tmpLine += sg;
	}
	
	public String nextStep(String e, String input){
		if (controller.getStatus() == GAME_STATE.ROLL){
			if (e.equals("Würfel")) {
				controller.doDice();
				//print("Es wurde gewuerfelt:" + controller.getRoll());
			}
		}
		
		if (controller.getStatus() == GAME_STATE.CHOOSE_FIG) {
			if(e.equals("Figur0")) {
				controller.setPickFigure(Controller.NULL);
			}
			if (e.equals("Figur1")) {
				controller.setPickFigure(Controller.EINS);
			}
			if (e.equals("Figur2")) {
				controller.setPickFigure(Controller.ZWEI);
			}
			if (e.equals("Figur3")) {
				controller.setPickFigure(Controller.DREI);
			}
		}
		if(controller.getStatus()== GAME_STATE.CHOOSE_PLAYER_COUNT){
			if(e.equals("Enter")) {
				controller.inputPlayerCount(Integer.parseInt(input));
			}
		}
		return tmpLine;
	}

}
