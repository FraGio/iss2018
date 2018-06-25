var express     	= require('express');
var path         	= require('path');
var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
//var index           = require('./appServer/routes/index');				 
//var actuatorsRoutes = require('./appServer/routes/actuators');
//var sensorsRoutes   = require('./appServer/routes/sensors');
//
//var session         = require("express-session");	//npm install express-session --save
//var authRoutes      = require('./appServer/routes/auth');
//var serverWithSocket= require('./authfrontendServer');
////var parseurl        = require('parseurl');

var basicAuth = require('basic-auth');	//npm install basic-auth --save

var app = express();

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'views'));	 
app.set('view engine', 'jade');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

 
// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
 

console.log("__dirname=" + __dirname );	//
app.use(express.static( __dirname+"/appServer/templateLogReg/index.html" ));

app.use(function(req, res, next) {
    var user = basicAuth(req);
console.log(user);
    if (user === undefined || user['name'] !== 'aladdin' || user['pass'] !== 'opensesame') {
        res.writeHead(401, 'Access invalid for user', {'Content-Type' : 'text/plain'});
        res.end('Invalid credentials');
    } else {
    	console.log("OKKKKKKKKKKKKKKKKKKKKK");
        next();
    }
});
 

//REPRESENTATION;
app.use( function(req,res){
	console.info("SENDING THE ANSWER " + req.result   );
	try{
		res.send(req.result);
	}catch(e){console.info("SORRY ...");}
	} 
);
//app.use(converter());



// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page;
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;