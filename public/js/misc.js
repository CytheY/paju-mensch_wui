$(document).ready(refreshLobby);

function getGameGrid(){
	$.ajax({
		url: "gameGrid?game="+sessionStorage.game,
		context: document.body
	}).done(function(data) {
		$("#gameGridContainer").html(data);
		$("#dice").click(doDice);
	});
}

function startGame(){
	if(sessionStorage.game)
		return;
	var s = prompt('Spielname: ', 'Spiel');
	if(s != null){
		$.ajax({
			url: "init?game="+s+"&player="+sessionStorage.player,
			context: document.body,
			async: false
		}).done(function(data){
			if(data.indexOf("Spiel existiert bereits!") < 0){
				sessionStorage.game = s;
				getWebSocket();
				getChatSocket();
				$('#buttonNew').css('display', 'none');
				$('#buttonJoin').css('display', 'none');
				$('#buttonStart').css('display', 'block');
				$('#buttonExit').css('display', 'block');
				return s;
			}
			else
				alert("Ein Spiel mit dem Namen \"" + s + "\" exitiert bereits!");
		});
	}
	//$("#status").html("Spiel mit " + s + " Spielern gestartet!")
}

function exit(){
	$.ajax({
		url: "exit?game="+sessionStorage.game + "&player="+sessionStorage.player,
		context: document.body
	}).done(function(data) {
		var player = sessionStorage.player;
		sessionStorage.clear();
		sessionStorage.player = player;
		socket.close();
		$("#gameGridContainer").html('');
		$("#status").html('');
		scope.messages = [];
		scope.$apply();
		$("#dice").css('display', 'none');
		$('#chatContainer').css('display', 'none');
		$('#chatInputContainer').css('display', 'none');
		$('#buttonNew').css('display', 'block');
		$('#buttonJoin').css('display', 'block');
		$('#buttonExit').css('display', 'none');
		$('#buttonStart').css('display', 'none');
		$('#lobbyContainer').css('display', 'block');
		$('#infoWrapper').css('display', 'block');
	});
}

function doDice(){
	$.ajax({
		url: "doDice?game="+sessionStorage.game+ "&player="+sessionStorage.player,
		context: document.body
	});
}

function activateDice(){
	$("#dice").css('opacity','1');
	$("#dice").click(doDice);
}

function deactivateDice(){
	$("#dice").css('opacity','0.5');
	$("#dice").unbind("click");
}

function clearDice(){
	$("#dice").html("");
}

function chooseFig(s){
	$.ajax({
		url: "fig?game="+sessionStorage.game + "&figure="+s + "&player="+sessionStorage.player,
		context: document.body,
	});
}

function updateFigures(i){
	$('#gameCell_'+i).css('background-color', 'black');
}

function refreshLobby(){
	$.ajax({
		url: "lobby",
		context: document.body,
		async: false
	}).done(function(data) {
		$("#lobby").html(data);
	});
}

function join(game){
	if(sessionStorage.game)
		return;
	sessionStorage.game = game;
	getWebSocket();
	getChatSocket();
	$('#buttonNew').css('display', 'none');
	$('#buttonJoin').css('display', 'none');
	$('#buttonExit').css('display', 'block');
	$('#buttonStart').css('display', 'block');
}

function begin(){
	$.ajax({
		url: "begin?game="+sessionStorage.game,
		context: document.body,
	}).done(function(){
		$('#buttonStart').css('display', 'none');
		$('#lobbyContainer').css('display', 'none');
	});
}