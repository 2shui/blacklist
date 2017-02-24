var stompClient = null;
	var socket = new SockJS('/tweet');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		stompClient.subscribe('/user/topic/getting', function (greeting) {
			if(''!=JSON.parse(greeting.body).msg) {
				writeToHtml(JSON.parse(greeting.body).user +':'+ JSON.parse(greeting.body).msg);
			}
		});
		stompClient.subscribe('/topic/resay', function (greeting) {
			if(''!=JSON.parse(greeting.body).msg) {
				writeToHtml(JSON.parse(greeting.body).user +':'+ JSON.parse(greeting.body).msg);
			}
		});
	  });
	
	var _roll = {
		mLeft:function(i){
			var left = parseInt(Math.random()*20, 10);
			if(i<0){
				$('#roll0').attr('style','margin-left:'+(left<20?left:-left)+'%;');
				left = parseInt(Math.random()*50, 10);
				$('#roll1').attr('style','margin-left:'+(left<20?left:-left)+'%;');
				left = parseInt(Math.random()*50, 10);
				$('#roll2').attr('style','margin-left:'+(left<20?left:-left)+'%;');
				left = parseInt(Math.random()*50, 10);
				$('#roll3').attr('style','margin-left:'+(left<20?left:-left)+'%;');
				left = parseInt(Math.random()*50, 10);
				$('#roll4').attr('style','margin-left:'+(left<20?left:-left)+'%;');
			}else{
				$('#roll'+i).attr('style','margin-left:'+(left<20?left:-left)+'%;');
			}
			
		}
	};
	  
	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
			stompClient = null;
		}
	}
	function sendMsg(msg) {
		var _u = getCookie('u');
		if(null==_u)
			_u = $.md5(navigator.appVersion+new Date().getTime());
		setCookie('u', _u, 1000*60*5);
		if(socket.readyState != 1){
			socket.close();
			socket = new SockJS('/tweet');
			stompClient = Stomp.over(socket);
			stompClient.connect({}, function (frame) {
			stompClient.subscribe('/user/topic/getting', function (greeting) {
				if(''!=JSON.parse(greeting.body).msg) {
					writeToHtml(JSON.parse(greeting.body).user +':'); writeToHtml(JSON.parse(greeting.body).msg);
				}
			});
			stompClient.subscribe('/topic/resay', function (greeting) {
				if(''!=JSON.parse(greeting.body).msg) {
					writeToHtml(JSON.parse(greeting.body).user +':'); writeToHtml(JSON.parse(greeting.body).msg);
				}
			});
		  });
		  setTimeout(function(){
			// user say and show msg
			stompClient.send("/app/say", {}, JSON.stringify({'msg': msg,'uid':_u}));sleep(50);
			// user get xiaoi
			stompClient.send("/app/get", {}, JSON.stringify({'msg': msg,'uid':_u}));
		  },50*3);
		}else {
			// user say and show msg
			stompClient.send("/app/say", {}, JSON.stringify({'msg': msg,'uid':_u}));sleep(50);
			// user get xiaoi
			stompClient.send("/app/get", {}, JSON.stringify({'msg': msg,'uid':_u}));
		}
	}
	  
	var _irid=0;
	function onRoll(i) {_irid = i;}
	function writeToHtml(msg) {
		var rid = parseInt(Math.random()*6-1, 10);
		for(;;)if(rid==_irid)rid = parseInt(Math.random()*5-1, 10);else break;
		$('#roll'+rid).html(msg);
		var left = parseInt(Math.random()*20, 10);
		$('#roll'+rid).attr('style', 'margin-left:'+left+'%;');
	}
	  function sleep(d){for(var t=Date.now();Date.now()-t<=d;);}
	  
	  function Compute(v='',s='default') {
	       var d = document.getElementById('dvCompute');
	       d.setAttribute('class', s);
	       d.innerHTML = v;
	       return { w: d.offsetWidth, h: d.offsetHeight };
	   }
	  var w=null;
	  function iset(e) {
		document.getElementById('input').focus();
		if(null==w)
			$("#input").attr('style','width:0px;');
	}
	
	function resize() {
		w = Compute($("#input").val());
		$("#input").attr('style','width:'+(w.w+3)+'px;');
	}
	function check() {
		var msg = $('#input').val();
		sendMsg(msg);
		$('#input').val('');
		resize();
		return false;
	}