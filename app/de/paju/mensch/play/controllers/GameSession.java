package de.paju.mensch.play.controllers;

import java.util.ArrayList;
import java.util.List;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.uis.WebGUI;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class GameSession {
	
	private String gameName;
	private Controller game;
	private List<IObserver> observers;
	private List<GameWebSocket> sockets;
	
	public GameSession(String gameName) {
		super();
		this.gameName = gameName;
		observers = new ArrayList<IObserver>();
		sockets = new ArrayList<GameWebSocket>();
		game = new Controller();
		observers.add(new WebGUI(this.gameName, game, sockets));
	}
	
	public void start(){
		for (IObserver iObserver : observers) {
			game.addObserver(iObserver);
		}
		game.start();
	}
	
	public GameWebSocket createGameWebSocket(){
		GameWebSocket sock = new GameWebSocket();
		sockets.add(sock);
		return sock;
	}
	
	public void begin() {
		game.inputPlayerCount(sockets.size());
	}

	public Controller getGame() {
		return game;
	}
}