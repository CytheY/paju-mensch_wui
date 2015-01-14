package de.paju.mensch.play.uis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.model.Figure;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.controllers.Application;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class WebGUI implements IObserver {
	
	private String gameName;
	private Controller controller;
	private Map<String, GameWebSocket> sockets;
	
	public WebGUI(String gameName, Controller controller, Map<String, GameWebSocket> sockets) {
		super();
		this.gameName = gameName;
		this.controller = controller;
		this.sockets = sockets;
	}

	@Override
	public void inputChoosePlayerCount() {
		System.out.println("inputChoosePlayerCount called");
//		statusSocket.updateStatus(controller.getStatus().toString());
	}

	@Override
	public void updateChooseFigure() {
		System.out.println("updateChooseFigure called");
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updateStatus(controller.getStatus().toString());
		}
	}

	@Override
	public void updateInput() {
		System.out.println("updateInput called");
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updateStatus(controller.getStatus().toString());
		}
	}

	@Override
	public void updateObserversRoll() {
		System.out.println("updateShowGameFrame called");
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updateStatus(controller.getStatus().toString());
		}
	}

	@Override
	public void updatePlayerStatus() {
		System.out.println("updatePlayerStatus called");
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updatePlayer(controller.getActivePlayer().getPlayerID());
		}
	}

	@Override
	public void updatePrintDice() {
		System.out.println("updatePrintDice called");
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updateDice(controller.getRoll());
		}
	}

	@Override
	public void updatePrintFigures() {
		System.out.println("updatePrintFigures called");
		Figure[] pgs = controller.getPgArray();
		List<Figure> targets = new ArrayList<Figure>();
		for (int i = 0; i < controller.getAnzahlMitspieler(); i++) {
			for (int j = 0; j < controller.getTargetFigureArray(i).length; ++j) {
				if (controller.getTargetFigureArray(i)[j] != null)
					targets.add(controller.getTargetFigureArray(i)[j]);
			}
		}
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.updateFigures(pgs, targets);
		}
	}

	@Override
	public void updateShowGameFrame() {
		System.out.println("updateShowGameFrame called");
		updatePrintFigures();
	}

}
