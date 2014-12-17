function getGameGrid(){
	$.ajax({
		url: "gameGrid",
		context: document.body,
		async: false
	}).done(function(data) {
		$("#gameGrid").css('display', 'block');
		$("#gameGrid").html(data);
	});
}

function startGame(){
	var s = prompt('Anzahl der Spieler: ', '4');
	if(s != null){
		$.ajax({
			url: "init?e="+s,
			context: document.body,
		}).done(function(){
			getGameGrid();
		});
	}
	//$("#status").html("Spiel mit " + s + " Spielern gestartet!")
}

function exit(){
	$.ajax({
		url: "exit",
		context: document.body
	}).done(function(data) {
		$("#gameGrid").css('display', 'none');
	});
}

function doDice(){
	$.ajax({
		url: "doDice",
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
		url: "fig?e="+s,
		context: document.body,
	});
}

function updateFigures(i){
	$('#gameCell_'+i).css('background-color', 'black');
}