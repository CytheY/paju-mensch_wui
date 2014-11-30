function getGameGrid(){
	$.ajax({
		url: "gameGrid",
		context: document.body
	}).done(function(data) {
		$("#gameGrid").css('display', 'block');
		$("#gameGrid").html(data);
	});
}

function startGame(){
	var s = prompt('Anzahl der Spieler: ', '4');
	if(s != null){
		console.log(s);
		$.ajax({
			url: "tui?e=Enter&i="+s,
			context: document.body
		}).done(getGameGrid);
	}
	$("#status").html("Spiel mit " + s + " Spielern gestartet!")
}

function exit(){
	$.ajax({
		url: "exit",
		context: document.body
	}).done(function(data) {
		$("#status").html(data);
		$("#gameGrid").css('display', 'none');
	});
}