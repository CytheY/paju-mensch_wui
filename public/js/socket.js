var socket;

function getWebSocket(){
	socket = new WebSocket('ws:' + window.location.host + '/socket?game='+sessionStorage.game + "&player="+sessionStorage.player);
	socket.onmessage = function(message){
		console.log(message);
		var json = $.parseJSON(message.data);
		if(json.status){
			$("#status").css('display', 'none');
			if(sessionStorage.player == json.activePlayer){
				$("#status").css('display', 'block');
				deactivateDice();
			}
			switch(json.status){
				case "ROLL":
					$("#status").html("Bitte Würfeln!");
					activateDice();
					$('#buttonStart').css('display', 'none');
					$('#lobbyContainer').css('display', 'none');
					break;
				case "CHOOSE_FIG":
					$("#status").html("Bitte Figure auswählen!");
					deactivateDice();
					$('#buttonStart').css('display', 'none');
					$('#lobbyContainer').css('display', 'none');
					break;
				case "GAME_STOP":
					$("#status").html("Spiel beendet!");
					deactivateDice();
					$('#buttonStart').css('display', 'none');
					$('#lobbyContainer').css('display', 'none');
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
				var index = this.playerID*4+this.position;
				$('#targetCell_'+index).removeClass('empty');
				$('.stackCell.player'+this.playerID+'.figure'+this.id).addClass('empty');
			});
		}
	};
}