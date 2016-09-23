var b=(function(){
	if(window.localStorage){
//		localStorage.clear();
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
	}
})();
