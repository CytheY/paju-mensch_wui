package de.paju.mensch.play.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import play.mvc.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ChatRoom {
	
	private Map<String, ChatWebSocket> chatroom;
	private Map<String, Integer> players;
	
	public ChatRoom(){
		chatroom = new HashMap<String, ChatWebSocket>();
		players = new HashMap<String, Integer>();
	}
	
	public WebSocket<String> enterChatRoom(String player, int i){
		ChatWebSocket socket = new ChatWebSocket(this);
		chatroom.put(player, socket);
		players.put(player, i);
		return socket;
	}
	
	public void leaveChatRoom(String player){
		chatroom.remove(player);
	}

	public void sendMessage(String event){
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode message = (ObjectNode) mapper.readTree(event);
			message.put("playerId", players.get(message.get("player").asText()));
			for (ChatWebSocket socket : chatroom.values()) {
				socket.sendMessage(message.toString());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
