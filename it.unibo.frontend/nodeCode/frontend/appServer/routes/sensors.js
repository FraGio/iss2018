/*
 * appServer/routes/sensors.js
 */
var express     = require('express'),
  router        = express.Router(),
  resourceModel = require('../models/model');

router.route('/').get(function (req, res, next) {
    req.type = "defaultView" ;
  	req.result = resourceModel.pi.sensors;  
    next();  
});

router.route('/pir').get(function (req, res, next) {
  req.result = resourceModel.pi.sensors.pir;
  next();
});

router.route('/temperature').get(function (req, res, next) {
	  console.log( "................" );
	  console.log( req.result );
    req.result = resourceModel.pi.sensors.temperature;
	console.log( req.result );
  console.log( "................" );
  next();
});

router.route('/temperatureProlog').get(function (req, res, next) {
  var tval =  resourceModel.pi.sensors.temperature.value ;
  console.log(tval);
  req.result = "msg( sensor, event, temperatureDev, none, "+ tval+", 0 )";
  next();
});

router.route('/humidity').get(function (req, res, next) {
  req.result = resourceModel.pi.sensors.humidity;
  next();
});

module.exports = router;