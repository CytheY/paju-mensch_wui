package de.paju.mensch.play.uis;

import java.util.HashMap;
import java.util.Map;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.model.Figure;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class WebGUI implements IObserver {
	
	private Controller controller;
	private Map<String, GameWebSocket> sockets;
	
	public WebGUI(Controller controller, Map<String, GameWebSocket> sockets) {
		super();
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
		Map<Figure, Integer> targets = new HashMap<Figure, Integer>();
		for (int i = 0; i < controller.getAnzahlMitspieler(); i++) {
			for (int j = 0; j < controller.getTargetFigureArray(i).length; ++j) {
				if (controller.getTargetFigureArray(i)[j] != null)
					targets.put(controller.getTargetFigureArray(i)[j], j);
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
