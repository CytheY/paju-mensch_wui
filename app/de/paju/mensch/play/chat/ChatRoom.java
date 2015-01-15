package de.paju.mensch.play.chat;

import java.util.HashMap;
import java.util.Map;

import play.mvc.WebSocket;

public class ChatRoom {
	
	private Map<String, ChatWebSocket> chatroom;
	
	public ChatRoom(){
		chatroom = new HashMap<String, ChatWebSocket>();
	}
	
	public WebSocket<String> enterChatRoom(String player){
		ChatWebSocket socket = new ChatWebSocket(this);
		chatroom.put(player, socket);
		return socket;
	}
	
	public void leaveChatRoom(String player){
		chatroom.remove(player);
	}

	public void sendMessage(String event){
		for (ChatWebSocket socket : chatroom.values()) {
			socket.sendMessage(event);
		}
	}
}
