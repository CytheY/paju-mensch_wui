package de.paju.mensch.play.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.libs.F;
import play.libs.openid.OpenID;
import play.mvc.Result;
import play.mvc.WebSocket;
import de.paju.mensch.controller.Controller.GAME_STATE;
import de.paju.mensch.play.views.html.gamegrid;
import de.paju.mensch.play.views.html.login;
import de.paju.mensch.play.views.html.webtui;

public class Application extends play.mvc.Controller {

	private static String SUPER_USER = "root";
	private static String SUPER_PASSWORD = "fu";

	private static Map<String, GameSession> sessions = new HashMap<String, GameSession>();

	// private static Controller game;
	// private static WebTUI tui;
	// // private static GUI gui;
	// private static GameWebSocket gameSocket;
	// private static WebGUI webGUI;

	public static Result index() {
//		if (!sessions.containsKey(SUPER_USER)) {
//			sessions.put(SUPER_USER, new GameSession());
//			sessions.get(SUPER_USER).start();
//		}
		return ok(de.paju.mensch.play.views.html.main.render("PajuMensch"));
	}

	public static Result init(String game) {
		if(sessions.containsKey(game)){
			return ok("Spiel existiert bereits!");
		}
		sessions.put(game, new GameSession(game));
		sessions.get(game).start();
		return ok();
	}

	public static Result doDice(String game) {
		if (sessions.get(game).getGame().getStatus() == GAME_STATE.ROLL) {
			sessions.get(game).getGame().doDice();
		}
		return ok(Integer
				.toString(sessions.get(game).getGame().getRoll()));
	}

	public static WebSocket<String> socket(String game) {
		return sessions.get(game).createGameWebSocket();
	}

	public static Result gameGrid(String game) {
		String message = sessions.get(game).getGame().getFieldCoords()
				.toString();
		List<String> field = null;
		List<String> stack = null;
		List<String> target = new ArrayList<String>();
		try {
			stack = sessions.get(game).getGame().getStackCoords();
			field = sessions.get(game).getGame().getFieldCoords();
			for (int i = 0; i < sessions.get(game).getGame()
					.getAnzahlMitspieler(); ++i) {
				target.addAll(sessions.get(game).getGame()
						.getTargetCoords(i));
			}

		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return ok(gamegrid.render(message, field, stack, target,
				sessions.get(game).getGame().getAnzahlMitspieler()));
	}

	public static Result chooseFigure(String game, Integer fig) {
		sessions.get(game).getGame().setPickFigure(fig);
		return ok();
	}

	// public static Result webGui() {
	// return ok(webgui.render());
	// }
	
	public static Result getLobby(){
		return ok(de.paju.mensch.play.views.html.lobby.render(sessions.keySet()));
	}
	
	public static Result begin(String game){
		sessions.get(game).begin();
		return ok();
	}

	public static Result exit(String game) {
		//TODO Aufräumen, aber nur für den aufrufendnen, da fehlt also noch so eine Art User id...., eventuell die GameSocket Liste als map umsetzen...
		sessions.remove(game);
		return ok("Game restarted");
	}

	public static Result tui(String game, String input) {
		// String output = tui.nextStep(e, input);
		return ok(webtui.render(""));
	}

	// Form login handler
	public static Result login() {
		return ok(login.render(Form.form(Login.class)));
	}

	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			return redirect(routes.Application.index());
		}
	}

	public static Result auth() {
		String providerUrl = "https://www.google.com/accounts/o8/id";
		String returnToUrl = routes.Application.verify().absoluteURL(request());

		Map<String, String> attributes = new HashMap<>();
		attributes.put("Email", "http://schema.openid.net/contact/email");
		attributes.put("FirstName", "http://schema.openid.net/namePerson/first");
		attributes.put("LastName", "http://schema.openid.net/namePerson/last");
		
		F.Promise<String> redirectUrl = OpenID.redirectURL(providerUrl,
				returnToUrl, attributes);
		return redirect(redirectUrl.get(10000));
	}

	public Result verify() {
		F.Promise<OpenID.UserInfo> userInfoPromise = OpenID.verifiedId();
		OpenID.UserInfo userInfo = userInfoPromise.get(10000);
		session().clear();
		session("email", userInfo.attributes.get("Email"));
		return redirect(routes.Application.index());
	}

	/*--------Login Object---------*/
	public static class Login {

		public String user;
		public String password;

		public String validate() {
			if (!(this.user.equals(SUPER_USER))
					|| !(this.password.equals(SUPER_PASSWORD))) {
				return "Invalid user or password!";
			}
			return null;
		}

	}
}
