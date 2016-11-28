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
	var m=(new Date).getTime()+(1<<16);
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

var b=(function(){
	var t=getURLParameter();
	if(window.localStorage && auto(localStorage.ba,t)){
		$.ajax({
			type:'get',
			dataType:'jsonp',
			url:'http://www.itblacklist.cn/article/view',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.ba=reLocal(localStorage.ba,t);
				}
			}
		});
	}
})();
