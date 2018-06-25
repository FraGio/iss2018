/*
 * frontend/plugins/internal/DHT22sensorPlugin.js
 */
var 
  resources = require('./../../appServer/models/model'),
  utils     = require('./../../utils.js');
var interval, sensor;
var model       = resources.pi.sensors;
var pluginName  = 'Temperature & Humidity';
var localParams = {'simulate': true, 'frequency': 5000};

exports.start = function (params) {
  localParams = params;
  if (params.simulate) {
    simulate();
  } else {
    connectHardware();
  }
};
exports.stop = function () {
  if (localParams.simulate) {
    clearInterval(interval);
  } else {
    sensor.unexport();
  }
  console.info('%s plugin stopped!', pluginName);
};

function connectHardware() {
 var sensorDriver = require('node-dht-sensor');
  var sensor = {
    initialize: function () {
      return sensorDriver.initialize(22, model.temperature.gpio);  
    },
    read: function () {
      var readout = sensorDriver.read();  
      model.temperature.value = parseFloat(readout.temperature.toFixed(2));
      model.humidity.value    = parseFloat(readout.humidity.toFixed(2));  
      showValue();
      setTimeout(function () {
        sensor.read(); //#D
      }, localParams.frequency);
    }
  };
  if (sensor.initialize()) {
    console.info('Hardware %s sensor started!', pluginName);
    sensor.read();
  } else {    console.warn('Failed to initialize sensor!'); }
};

function simulate() {
  interval = setInterval(function () {
    model.temperature.value = utils.randomInt(0, 40);
    model.humidity.value    = utils.randomInt(0, 100);
    showValue();
  }, localParams.frequency);
  console.info('Simulated %s sensor started!', pluginName);
};

function showValue() {
  console.info('Temperature: %s C, humidity %s \%',
  model.temperature.value, model.humidity.value);
  emitInfo(model.temperature.value);
};

/*
 * Emit the new led value according to the blsMVC model
 */
var mqttUtils     = require('./../../uniboSupports/mqttUtils'); 	 

var emitInfo = function( value ){
 	var eventstr = "msg(inputCtrlEvent,event,js,none,inputEvent(temperature, t1, " +value + "),1)"
 		console.log("	DHT22Plugin emits> "+ eventstr);
 		mqttUtils.publish( eventstr );
}