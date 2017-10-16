function getURLParameter(name) {
	if(topicId)return topicId;
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}
function getURLID() {
	if(!(typeof(topicId)=="undefined"))return topicId;
	return new RegExp('\\d+').exec(window.location.pathname)[0];
}
$("#con-uri").html(getURLID());
function auto(v,k){
	if(typeof(v)=="undefined"){
		v="[{\""+k+"\":0}]";
	}
	var j=eval(v);var m=0;var matched=false;
	for(var i=0;i<j.length;i++){
		if(matched){break;}
		for(var key in j[i]){
			if(k==key){
				m=j[i][key];
				matched=true;
				break;
			}
		}
	}
	if(m<new Date().getTime())
		return true;
	return false;
}
function reLocal(v,k){
	var m=(new Date).getTime()+(1<<20);
	if(typeof(v)=="undefined"){
		return "[{\""+k+"\":"+m+"}]";
	}
	var j=eval(v);
	var h=false;
	for(var i=0;i<j.length;i++){
		if(h){break;}
		for(var key in j[i]) {
			if(k == key) {
				j[i][key]=m;
				h=true;
				break;
			}
		}
	}
	
	if(!h){
		j.push(JSON.parse("{\""+k+"\":"+m+"}"));
	}
	var s=JSON.stringify(j);
	return s;
}

function up(t){
	if(!window.localStorage || auto(localStorage.b, t)){
		$.ajax({
			type:'POST',
			url:'/reply/up',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.b=reLocal(localStorage.b,t);
					return true;
				}
			}
		});
	}
	return false;
}
function down(t){
	if(!window.localStorage || auto(localStorage.c, t)){
		$.ajax({
			type:'POST',
			url:'/reply/down',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.c=reLocal(localStorage.c,t);
					return true;
				}
			}
		});
	}
	return false;
}

var b=(function(){
	//var t=getURLParameter('t');
	var t = getURLID();
	if(window.localStorage && auto(localStorage.a,t)){
		$.ajax({
			type:'POST',
			url:'/topic/view',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.a=reLocal(localStorage.a,t);
				}
			}
		});
	}
})();
