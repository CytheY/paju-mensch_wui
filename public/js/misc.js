function getGameGrid(){
	$.ajax({
		url: "gameGrid?game="+sessionStorage.game,
		context: document.body,
		async: false
	}).done(function(data) {
		$("#gameGrid").css('display', 'block');
		$("#gameGrid").html(data);
		$("#lobby").css('display', 'none');
	});
}

function startGame(){
	var s = prompt('Spielname: ', 'Spiel');
	var player = prompt('Spielernamer: ', 'Spielername');
	if(s != null && player != null){
		$.ajax({
			url: "init?game="+s+"&player="+player,
			context: document.body,
		}).done(function(data){
			console.log(data);
			if(data.indexOf("Spiel existiert bereits!") < 0){
				sessionStorage.game = s;
				sessionStorage.player = player;
				getWebSocket();
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
		sessionStorage.clear();
		socket.close();
		$("#gameGrid").css('display', 'none');
	});
}

function doDice(){
	$.ajax({
		url: "doDice?game="+sessionStorage.game,
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
		url: "fig?game="+sessionStorage.game + "&figure="+s,
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
		$("#lobby").css('display', 'block');
		$("#lobby").html(data);
	});
}

function join(game){
	sessionStorage.player = prompt('Spielernamer: ', 'Spielername');
	sessionStorage.game = game;
	getWebSocket();
}

function begin(){
	$.ajax({
		url: "begin?game="+sessionStorage.game,
		context: document.body,
	});
	$("#gameGrid").css('display', 'none');
	$("#lobby").css('display', 'block');
}