/*
 * frontend/robotFrontendServer.js 
 */
var appl            = require('./applCodeRobot');   //previously was applCode;
var resourceModel   = require('./appServer/models/robot');
var http            = require('http');
var io              ; 	//Upgrade ro socketIo;

var createServer = function (port  ) {
  initPlugins();  
  server = http.createServer( appl );   
  io     = require('socket.io').listen(server); //Upgrade fro socketio;  
  server.on('listening', onListening);
  server.on('error', onError);
  setInterval( showResourceState, 1000 ); //show the robot state;
  server.listen( port ); 
};

//viene fatto automaticamente ogni secondo e manda attraverso il meccanismo di socketIO le informazioni
//che ha appena lette dal modello , implementato rapporto observable (modello del robot), il quale verrà cambiato da qualcuno, ed observator.
//Se qualcuno cambia questo stato, ogni secondo vengono "propagati"  i cambiamenti  
function showResourceState(){
	var now = new Date() ;
	var info = "ROBOT state="+resourceModel.robot.state+"\n"+
//		resourceModel.robot.sonar1.name+"="+resourceModel.robot.sonar1.value+"\n"+
//		resourceModel.robot.sonar2.name+"="+resourceModel.robot.sonar2.value+"\n"+
//		resourceModel.robot.sonarRobot.name+"="+resourceModel.robot.sonarRobot.value+"\n"+ //;
		"time=" + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
	io.sockets.send( info );
}

function initPlugins() {}

createServer(3000);

function onListening() {
	  var addr = server.address();
	  var bind = typeof addr === 'string'
	    ? 'pipe ' + addr
	    : 'port ' + addr.port;
	  console.log('Listening on ' + bind);
}
function onError(error) {
	if (error.syscall !== 'listen') {
	    throw error;
	}
	 var bind = typeof port === 'string'
		    ? 'Pipe ' + port
		    : 'Port ' + port;
		  // handle specific listen errors with friendly messages;
		  switch (error.code) {
		    case 'EACCES':
		      console.error(bind + ' requires elevated privileges');
		      process.exit(1);
		      break;
		    case 'EADDRINUSE':
		      console.error(bind + ' is already in use');
		      process.exit(1);
		      break;
		    default:
		      throw error;
		  }
}
//Handle CRTL-C;
process.on('SIGINT', function () {
//  ledsPlugin.stop();
//  dhtPlugin.stop();
  console.log('frontendServer Bye, bye!');
  process.exit();
});
process.on('exit', function(code){
	console.log("Exiting code= " + code );
});
process.on('uncaughtException', function (err) {
 	console.error('robotFrontendServer uncaught exception:', err.message);
  	process.exit(1);		//MANDATORY!!!;
});

module.exports.updateClient = function (msg) { 
    console.log(msg);
    io.sockets.send(msg);
};

