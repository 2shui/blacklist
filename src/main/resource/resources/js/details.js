function auto(v,k){
	if(typeof(v)=="undefined"){
		v="[{\""+k+"\":0}]";
	}
	var j=eval(v);var m=0;
	for(var i=0;i<j.length;i++){
		for(var key in j[i]){
			if(t==key){
				m=j[i][key];
			}
		}
	}
	if(m<new Date().getTime())
		return true;
	return false;
}
function reLocal(v,k){
	var j=eval(v);
	var m=(new Date).getTime()+(1<<20);
	var h=false;
	for(var i=0;i<j.length;i++){
		for(var key in j[i]) {
			if(t == key) {
				j[i][key]=m;
				h=true;
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
					localStorage.b=reLocal(localStorage.b);
				}
			}
		});
	}
}
function down(t){
	if(!window.localStorage || auto(localStorage.c, t)){
		$.ajax({
			type:'POST',
			url:'/reply/down',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.c=reLocal(localStorage.c);
				}
			}
		});
	}
}

var b=(function(){
	var t=getURLParameter('t');
	if(window.localStorage && auto(localStorage.a,t)){
		$.ajax({
			type:'POST',
			url:'/topic/view',
			data:{'id':t},
			success:function(data){
				if("100"==data.code){
					localStorage.a=reLocal(localStorage.a);
				}
			}
		});
	}
	/*
	if(window.localStorage){
		var t=getURLParameter('t');var i=0;
		if(typeof(localStorage.a)=="undefined"){
			localStorage.a="[{\""+t+"\":0}]";
		}
		var j=eval(localStorage.a);
		for(var k=0;k<j.length;k++){
			for(var key in j[k]) {
				if(t == key) {
					i=j[k][key];
				}
			}
		}
		if(i<new Date().getTime()){
			$.ajax({
				type:'POST',
				url:'/topic/view',
				data:{'id':t},
				success:function(data){
					if("100"==data.code){
						var m=(new Date).getTime()+(1<<20);
						var h=false;
						for(var k=0;k<j.length;k++){
							for(var key in j[k]) {
								if(t == key) {
									j[k][key]=m;
									h=true;
								}
							}
						}
						
						if(!h){
							j.push(JSON.parse("{\""+t+"\":"+m+"}"));
						}
						var s=JSON.stringify(j);
						localStorage.a=s;
					}
				}
			});
		}
	}*/
})();
