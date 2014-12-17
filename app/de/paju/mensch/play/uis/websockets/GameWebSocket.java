package de.paju.mensch.play.uis.websockets;

import java.util.List;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import de.paju.mensch.controller.Controller;
import de.paju.mensch.model.Figure;

public class GameWebSocket extends WebSocket<String> {
	
	private Out<String> out;
	private In<String> in;

	@Override
	public void onReady(play.mvc.WebSocket.In<String> in,
			play.mvc.WebSocket.Out<String> out) {
		this.out = out;
		this.in = in;
		in.onMessage(new Callback<String>(){
			@Override
			public void invoke(String event) throws Throwable {
				System.out.println("StatusWebSocket recevied event: " + event);
			}
		});
		in.onClose(new Callback0(){
			@Override
			public void invoke() throws Throwable {
				System.out.println("StatusWebSocket closed!");
			}
		});
	}
	
	public void updateStatus(String status){
		out.write("{\"status\":\"" + status + "\"}");
	}
	
	public void showGameFrame(String gameFrame, Figure[] figures, List<String> targets){
		System.out.println("send gameframe");
		StringBuilder message = new StringBuilder();
		message.append("{\"gamegrid\":" + true );
		if(figures != null){
			boolean found = false;
			message.append(",\"figures\": [");
			for (Figure figure : figures) {
				if(figure != null && figure.getWeglaenge() <= Controller.MAX_GEFAHRENE_WEG_LAENGE){
					message.append("{ \"id\":" + figure.getFigureID() +", \"playerID\": " + figure.getPlayerID() + ", \"position\": " + figure.getFigurePos() + "},");
					found = true;
				}
			}
			if(found)
				message.deleteCharAt(message.length()-1);
			message.append("]");
		}
		if(targets != null && !targets.isEmpty()){
			boolean found = false;
			message.append(",\"targets\": [");
			for (String target : targets) {
					message.append(target);
					message.append(",");
					found = true;
			}
			if(found)
				message.deleteCharAt(message.length()-1);
			message.append("]");
		}
		message.append("}");
		out.write(message.toString());
	}
	
	public void updateDice(int i){
		System.out.println("send dice");
		out.write("{\"dice\":\"" + i + "\"}");
	}
	
	public void updatePlayer(int i){
		System.out.println("send player");
		out.write("{\"player\":\"" + i + "\"}");
	}
	
	public void updateFigures(Figure[] figures){
		System.out.println("send figures");
		StringBuilder message = new StringBuilder();
		message.append("{\"figures\": [");
		for (Figure figure : figures) {
			if(figure != null)
				message.append("{ \"id\":" + figure.getFigureID() +", \"playerID\": " + figure.getPlayerID() + ", \"position\": " + figure.getFigurePos() + "},");
		}
		message.deleteCharAt(message.length()-1);
		message.append("]}");
		System.out.println(message.toString());
		out.write(message.toString());
	}
}
