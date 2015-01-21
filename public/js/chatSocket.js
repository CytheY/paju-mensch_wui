var chatSocket;
var messagesBuf = [];

function getChatSocket(){
	chatSocket = new WebSocket('ws:shielded-sierra-6483.herokuapp.com/chatSocket?game='+sessionStorage.game + "&player="+sessionStorage.player);
	chatSocket.onmessage = function(message){
		console.log(message);
		messagesBuf.push(jQuery.parseJSON(message.data));
		scope.messages = messagesBuf;
		scope.$apply();
		document.getElementById('chat').scrollTop = document.getElementById('chat').scrollHeight;
	};
	$('#chatInputContainer').css('display', 'block');
}