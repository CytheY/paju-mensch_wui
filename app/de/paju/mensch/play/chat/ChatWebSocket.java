package de.paju.mensch.play.chat;

import play.libs.F.Callback;
import play.mvc.WebSocket;

public class ChatWebSocket extends WebSocket<String> {
	
	private ChatRoom chat;
	
	private Out<String> out;
	
	public ChatWebSocket(ChatRoom chat) {
		this.chat = chat;
	}

	@Override
	public void onReady(play.mvc.WebSocket.In<String> in, play.mvc.WebSocket.Out<String> out) {
		this.out = out;
		in.onMessage(new Callback<String>(){
			@Override
			public void invoke(String event) throws Throwable {
				chat.sendMessage(event);
			}
		});
	}

	public void sendMessage(String message) {
		out.write(message);
	}

}
