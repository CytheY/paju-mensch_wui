package de.paju.mensch.play.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.uis.WebGUI;

public class GameSession {
	
	private String gameName;
	private Controller game;
	private List<IObserver> observers;
	private Map<String, GameWebSocket> sockets;
	private Map<String, Integer> players;
	
	public GameSession(String gameName) {
		super();
		this.gameName = gameName;
		observers = new ArrayList<IObserver>();
		sockets = new HashMap<String, GameWebSocket>();
		players = new HashMap<String, Integer>();
		game = new Controller();
		observers.add(new WebGUI(game, sockets));
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
		players.put(player, players.size());
		return sock;
	}
	
	public void begin() {
		game.inputPlayerCount(sockets.size());
		for (GameWebSocket gameWebSocket : sockets.values()) {
			gameWebSocket.showGameFrame(Application.gameGrid(gameName).toString());
		}
	}
	
	public int removePlayer(String player) {
		GameWebSocket sock = sockets.remove(player);
		sock.close();
		players.remove(player);
		return sockets.size();
	}
	
	public int getPlayerId(String player){
		return players.get(player);
	}

	public Controller getGame() {
		return game;
	}
}