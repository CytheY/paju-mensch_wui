var chatSocket;

function getChatSocket(){
	chatSocket = new WebSocket('ws:' + window.location.host + '/chatSocket?game='+sessionStorage.game + "&player="+sessionStorage.player);
	chatSocket.onmessage = function(message){
		console.log(message);
	};
}

function sendMessage(message){
	chatSocket.send(message);
}