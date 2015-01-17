package de.paju.mensch.play.lobby;

import java.util.Map;

import play.libs.F.Callback;
import play.libs.Json;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.paju.mensch.play.controllers.GameSession;

public class LobbyWebSocket extends WebSocket<String> {
	
	private Out<String> out;

	@Override
	public void onReady(play.mvc.WebSocket.In<String> in,
			play.mvc.WebSocket.Out<String> out) {
		this.out = out;
		in.onMessage(new Callback<String>(){
			@Override
			public void invoke(String event) throws Throwable {
				//TODO
			}
		});
	}
	
	public void refreshLobby(Map<String, GameSession> allGames){
		ObjectNode message = Json.newObject();
		ArrayNode sessions = message.putArray("sessions");
		for (String gameName : allGames.keySet()) {
			ObjectNode session = Json.newObject();
			session.put("session", gameName);
			ArrayNode players = session.putArray("players");
			for (String playerName : allGames.get(gameName).getAllPlayer()) {
				ObjectNode player = Json.newObject();
				player.put("name", playerName);
				players.add(player);
			}
			session.put("status", allGames.get(gameName).getStatus().toString());
			sessions.add(session);
		}
		out.write(sessions.toString());
	}
}