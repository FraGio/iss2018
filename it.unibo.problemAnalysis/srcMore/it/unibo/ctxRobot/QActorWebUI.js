//QActorWebUI.js
var curSpeed = "low";
	
    console.log("QActorWebUI.js : server IP= "+document.location.host);
 /*
 * WEBSOCKET
 */
    var sock = new WebSocket("ws://"+document.location.host, "protocolOne");
    sock.onopen = function (event) {
         //console.log("QActorWebUI.js : I am connected to server.....");
         document.getElementById("connection").value = 'CONNECTED';
    };
    sock.onmessage = function (event) {
        //console.log("QActorWebUI.js : "+event.data);
        //alert( "onmessage " + event);
        document.getElementById("state").value = ""+event.data;
    }
    sock.onerror = function (error) {
        //console.log('WebSocket Error %0',  error);
        document.getElementById("state").value = ""+error;
    };
    
	function setSpeed(val){
		curSpeed = val;
		document.getElementById("speed").value = curSpeed;
	}
	function send(message) {
		var eventstr = "msg(robotCmd,event,js,none,robotCmd("+message +"),1)";
  		//alert( eventstr );
 		mqttUtils.publish( eventstr );
	};
/*	
	document.onkeydown = function(event) {
//	alert("event="+event);
		    event = event || window.event;
		    switch (event.keyCode || event.which) {
		        case 65:
		            send('a-High');
		            break;
		        case 68:
		            send('d-High');
		            break;
		        case 87:
		            send('w-High');
		            break;
		        case 83:
		            send('s-High');
		            break;
		        default:
		            //send('h-High');
		    }
	};
*/
 	//alert("loaded");
 	
const mqtt   = require ('mqtt');
const topic  = "unibo/qasys";
//var client = mqtt.connect('mqtt://iot.eclipse.org');
var client   = mqtt.connect('tcp://localhost:1883');
//var client   = mqtt.connect('tcp://192.168.43.229:1883');

console.log("mqtt client= " + client );

client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('client has subscribed successfully ');
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
  console.log("mqtt RECEIVES:"+ message.toString()); //if toString is not given, the message comes as buffer
});

exports.publish = function( msg ){
	//console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}
