package de.paju.mensch.play.uis.websockets;

import java.util.List;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.paju.mensch.controller.Controller;
import de.paju.mensch.model.Figure;

public class GameWebSocket extends WebSocket<String> {
	
	private Out<String> out;

	@Override
	public void onReady(play.mvc.WebSocket.In<String> in,
			play.mvc.WebSocket.Out<String> out) {
		this.out = out;
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
		ObjectNode result = Json.newObject();
		result.put("gamegrid", true);
		if(figures != null){
			ArrayNode jsonFigures = result.putArray("figures");
			for (Figure figure : figures) {
				if(figure != null && figure.getWeglaenge() <= Controller.MAX_GEFAHRENE_WEG_LAENGE){
					ObjectNode jsonFigure = Json.newObject();
					jsonFigure.put("id", figure.getFigureID());
					jsonFigure.put("playerID", figure.getPlayerID());
					jsonFigure.put("position", figure.getFigurePos());
					jsonFigures.add(jsonFigure);
				}
			}
		}
		if(targets != null && !targets.isEmpty()){
			ArrayNode jsonTargets = result.putArray("targets");
			for (String target : targets) {
				jsonTargets.add(target);
			}
		}
		out.write(result.toString());
	}
	
	public void updateDice(int i){
		System.out.println("send dice");
		ObjectNode result = Json.newObject();
		result.put("dice", i);
		out.write(result.toString());
	}
	
	public void updatePlayer(int i){
		System.out.println("send player");
		ObjectNode result = Json.newObject();
		result.put("player", i);
		out.write(result.toString());
	}
	
	public void updateFigures(Figure[] figures, List<Figure> targets){
		System.out.println("send figures");
		ObjectNode result = Json.newObject();
		ArrayNode jsonFigures = result.putArray("figures");
		for (Figure figure : figures) {
			if(figure != null && figure.getWeglaenge() <= Controller.MAX_GEFAHRENE_WEG_LAENGE){
				ObjectNode jsonFigure = Json.newObject();
				jsonFigure.put("id", figure.getFigureID());
				jsonFigure.put("playerID", figure.getPlayerID());
				jsonFigure.put("position", figure.getFigurePos());
				jsonFigures.add(jsonFigure);
			}
		}
		ArrayNode jsonTargets = result.putArray("targets");
		for (Figure target : targets) {
			if(target != null){
				ObjectNode jsonTarget = Json.newObject();
				jsonTarget.put("id", target.getFigureID());
				jsonTarget.put("playerID", target.getPlayerID());
				jsonTarget.put("position", target.getFigurePos());
				jsonTargets.add(jsonTarget);
			}
		}
		out.write(result.toString());
		
	}
	
	public void close(){
		out.close();
	}
}
