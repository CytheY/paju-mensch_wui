var chatSocket;
var messagesBuf = [];

function getChatSocket(){
	chatSocket = new WebSocket('ws:' + window.location.host + '/chatSocket?game='+sessionStorage.game + "&player="+sessionStorage.player);
	chatSocket.onmessage = function(message){
		console.log(message);
		messagesBuf.push(jQuery.parseJSON(message.data));
		scope.messages = messagesBuf;
		scope.$apply();
		document.getElementById('chat').scrollTop = document.getElementById('chat').scrollHeight;
	};
	$('#chatInputContainer').css('display', 'block');
}

//function sendMessage(){
//	var message = $('#chatInput').val();
//	if (message == '') return;
//		$('#chatInput').val('');
//	var jMessage = new Object();
//	jMessage.player = sessionStorage.player;
//	jMessage.message = message;
//	chatSocket.send(JSON.stringify(jMessage));
//}