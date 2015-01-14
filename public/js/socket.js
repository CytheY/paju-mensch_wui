var socket;

function getWebSocket(){
	socket = new WebSocket('ws:' + window.location.host + '/socket?game='+sessionStorage.game + "&player="+sessionStorage.player);
	socket.onmessage = function(message){
		console.log(message);
		var json = $.parseJSON(message.data);
		if(json.status){
			switch(json.status){
				case "ROLL":
					$("#status").html("Bitte Würfeln!");
					activateDice();
					break;
				case "CHOOSE_FIG":
					$("#status").html("Bitte Figure auswählen!");
					deactivateDice();
					break;
				case "GAME_STOP":
					$("#status").html("Spiel beendet!");
					deactivateDice();
					break;
			}
		}
			
		if(json.gamegrid)
			getGameGrid();
		if(json.dice)
			$("#dice").html(json.dice);
		if(json.player != null){
			$("#player").html(json.player);
			$("#dice").removeClass("player0");
			$("#dice").removeClass("player1");
			$("#dice").removeClass("player2");
			$("#dice").removeClass("player3");
			$("#dice").addClass("player"+json.player);
			clearDice();
		}
		if(json.figures){
			$(".gameCell").removeClass("player0");
			$(".gameCell").removeClass("player1");
			$(".gameCell").removeClass("player2");
			$(".gameCell").removeClass("player3");
			$(".gameCell").off('click');
			$(".stackCell").removeClass("empty");
			$(json.figures).each(function(){
				var id = this.id;
				$('#gameCell_'+this.position).addClass('player'+this.playerID);
				$('.stackCell.player'+this.playerID+'.figure'+this.id).addClass('empty');
				$('#gameCell_'+this.position).click(function(){
					chooseFig(id);
					clearDice();
				});
			});
		}
		if(json.targets){
			$(json.targets).each(function(){
				var index = this.playerID+this.position;
				$('#targetCell_'+index).removeClass('empty');
				$('.stackCell.player'+this.playerID+'.figure'+this.id).addClass('empty');
			});
		}
	};
}