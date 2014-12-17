package de.paju.mensch.play.uis;

import java.util.ArrayList;
import java.util.List;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.model.Figure;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.controllers.Application;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class WebGUI implements IObserver {
	
	private Controller controller;
	private GameWebSocket gameWebSocket;
	
	public WebGUI(Controller controller, GameWebSocket statusSocket) {
		super();
		this.controller = controller;
		this.gameWebSocket = statusSocket;
	}

	@Override
	public void inputChoosePlayerCount() {
		System.out.println("inputChoosePlayerCount called");
//		statusSocket.updateStatus(controller.getStatus().toString());
	}

	@Override
	public void updateChooseFigure() {
		System.out.println("updateChooseFigure called");
		gameWebSocket.updateStatus(controller.getStatus().toString());
	}

	@Override
	public void updateInput() {
		System.out.println("updateInput called");
		gameWebSocket.updateStatus(controller.getStatus().toString());
	}

	@Override
	public void updateObserversRoll() {
		System.out.println("updateShowGameFrame called");
		gameWebSocket.updateStatus(controller.getStatus().toString());
	}

	@Override
	public void updatePlayerStatus() {
		System.out.println("updatePlayerStatus called");
		gameWebSocket.updatePlayer(controller.getActivePlayer().getPlayerID());
	}

	@Override
	public void updatePrintDice() {
		System.out.println("updatePrintDice called");
		gameWebSocket.updateDice(controller.getRoll());
	}

	@Override
	public void updatePrintFigures() {
//		Figure[] pgs = controller.getPgArray();
//		if(pgs != null){
//			gameWebSocket.updateFigures(pgs);
//		}
	}

	@Override
	public void updateShowGameFrame() {
		System.out.println("updateShowGameFrame called");
		List<String> targets = new ArrayList<String>();
		for (int i = 0 ; i < controller.getAnzahlMitspieler() ; i++) {
			for(int j = 0 ; j < controller.getTargetFigureArray(i).length ; ++j){
				if(controller.getTargetFigureArray(i)[j] != null)
					targets.add("{ \"id\":" + controller.getTargetFigureArray(i)[j].getFigureID() +", \"playerID\": " + controller.getTargetFigureArray(i)[j].getPlayerID() + ", \"targetPos\": " + j  + "}");
			}
		}
		gameWebSocket.showGameFrame(Application.gameGrid().toString(), controller.getPgArray(), targets);
	}

}
