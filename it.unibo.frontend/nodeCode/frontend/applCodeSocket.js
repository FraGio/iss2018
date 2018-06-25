var express     	= require('express');
var path         	= require('path');
var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
var index           = require('./appServer/routes/index');				 
var actuatorsRoutes = require('./appServer/routes/actuators');
var sensorsRoutes   = require('./appServer/routes/sensors');

var serverWithSocket= require('./socketIofrontendServer');
var cors            = require('cors');
var converter       = require('./converter');

var app = express();

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'viewsSocket'));	 
app.set('view engine', 'jade');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use( cors() );  //npm install cors --save 
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));


//DEFINE THE ROUTES ;
app.use('/', index);	
app.use('/pi/actuators', actuatorsRoutes);
app.use('/pi/sensors', sensorsRoutes);

	
//Creates a default route for /pi;
app.get('/pi', function (req, res) {
  //for( i in req.body ){ console.info('req body field %s   ',   i ); };
  //console.info('	get /pi req URL = %s  ',   req.url  );
  res.send('This is the frontend-Pi!')
});

//REPRESENTATION;
app.use( function(req,res){
	console.info("SENDING THE ANSWER " + req.resul  );
	try{
		if( req.result != undefined)
			serverWithSocket.updateClient( JSON.stringify(req.result ) );
		res.send(req.result);
	}catch(e){console.info("SORRY ...");}
	} 
);

//app.use( converter() );



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