package de.paju.mensch.play.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.uis.WebGUI;
import de.paju.mensch.play.uis.websockets.GameWebSocket;

public class GameSession {
	
	private String gameName;
	private Controller game;
	private List<IObserver> observers;
	private Map<String, GameWebSocket> sockets;
	
	public GameSession(String gameName) {
		super();
		this.gameName = gameName;
		observers = new ArrayList<IObserver>();
		sockets = new HashMap<String, GameWebSocket>();
		game = new Controller();
		observers.add(new WebGUI(this.gameName, game, sockets));
	}
	
	public void start(){
		for (IObserver iObserver : observers) {
			game.addObserver(iObserver);
		}
		game.start();
	}
	
	public GameWebSocket createGameWebSocket(String player){
		GameWebSocket sock = new GameWebSocket();
		sockets.put(player, sock);
		return sock;
	}
	
	public void begin() {
		game.inputPlayerCount(sockets.size());
	}
	
	public int removePlayer(String player) {
		GameWebSocket sock = sockets.remove(player);
		sock.close();
		return sockets.size();
	}

	public Controller getGame() {
		return game;
	}
}