package de.paju.mensch.play.controllers;

import java.util.ArrayList;
import java.util.List;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.uis.WebGUI;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class GameSession {
	private Controller game;
	private List<IObserver> observers;
	private List<GameWebSocket> sockets;
	
	public GameSession() {
		super();
		observers = new ArrayList<IObserver>();
		sockets = new ArrayList<GameWebSocket>();
		game = new Controller();
		GameWebSocket sock = new GameWebSocket();
		sockets.add(sock);
		observers.add(new WebGUI(game, sock));
	}
	
	public void start(){
		for (IObserver iObserver : observers) {
			game.addObserver(iObserver);
		}
		game.start();
	}

	public Controller getGame() {
		return game;
	}

	public void setGame(Controller game) {
		this.game = game;
	}

	public List<IObserver> getObservers() {
		return observers;
	}

	public void setObservers(List<IObserver> observers) {
		this.observers = observers;
	}

	public List<GameWebSocket> getSockets() {
		return sockets;
	}
}
