//QActorWebUI.js
var curSpeed = "low";
	
 /*
 * WEBSOCKET
 */
    var connected = false;
    console.log("QActorWebUI.js : server IP= "+document.location.host + " connected=" + connected  );    
    var sock = new WebSocket("ws://"+document.location.host, "protocolOne");      
    //var sock = new WebSocket("ws://localhost:8080", "protocolOne");
	    sock.onopen = function (event) {
	        console.log("QActorWebUI.js : I connected to server.....");
        	connected = true;   	

	         //document.getElementById("connection").value = 'CONNECTED';
	    };
	    sock.onmessage = function (event) {
	        console.log("QActorWebUI.js : "+event.data);
	        //alert( "onmessage " + event);
	        //document.getElementById("state").value = ""+event.data;
	    }
	    sock.onerror = function (connected) {
	        console.log('WebSocket Error %0',  connected);
	//        document.getElementById("state").value = ""+connected;
	    };
      
	function setSpeed(val){
		curSpeed = val;
		//document.getElementById("speed").value = curSpeed;
	}
	function send(message) {
		//document.getElementById("sending").value = ""+message;
		
		if( connected ){
			console.log('Sending message= ',  message);
			sock.send(message);
		}
		else console.log('Sorry, no connection for message= ',  message);
	};

 	//alert("loaded");
