package de.paju.mensch.play.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.controller.Controller.GAME_STATE;
import de.paju.mensch.observer.IObserver;
import de.paju.mensch.play.chat.ChatRoom;
import de.paju.mensch.play.chat.ChatWebSocket;
import de.paju.mensch.play.uis.WebGUI;

public class GameSession {
	
	private String gameName;
	private Controller game;
	private List<IObserver> observers;
	private Map<String, GameWebSocket> sockets;
	private Map<String, Integer> players;
	private ChatRoom chat;
	
	public GameSession(String gameName) {
		super();
		this.gameName = gameName;
		observers = new ArrayList<IObserver>();
		sockets = new HashMap<String, GameWebSocket>();
		players = new HashMap<String, Integer>();
		chat = new ChatRoom();
		game = new Controller();
		observers.add(new WebGUI(game, sockets, players));
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
		chat.leaveChatRoom(player);
		players.remove(player);
		return sockets.size();
	}
	
	public int getPlayerId(String player){
		return players.get(player);
	}

	public Controller getGame() {
		return game;
	}

	public ChatWebSocket enterChatRoom(String player, int i) {
		return chat.enterChatRoom(player, i);
	}

	public Set<String> getAllPlayer() {
		return players.keySet();
	}

	public GAME_STATE getStatus() {
		return game.getStatus();
	}

	public GameWebSocket getGameWebSocket(String player) {
		return sockets.get(player);
	}

	public ChatWebSocket getChatSocket(String player) {
		return chat.getChatSocket(player);
	}
}