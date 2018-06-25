 /*
* =====================================
* getData.js
* =====================================
*/

'use strict';
var request = require('request');

var url = 'http://localhost:3000/pi/actuators/leds/1';

request.get({
	    url: url,
	    json: true,
	    headers: {'User-Agent': 'request'}
 	}, (err, res, data) => {
    if (err) {
      console.log('Error:', err);
    } else if (res.statusCode !== 200) {
      console.log('Status:', res.statusCode);
    } else {
      // data is already parsed as JSON:
      console.log(data);
      //console.log(data.html_url);
    }
});

request.put({
    url: url,
    json: true,
    headers: {'User-Agent': 'request'}
	}, (err, res, data) => {
if (err) {
  console.log('Error:', err);
} else if (res.statusCode !== 200) {
  console.log('Status:', res.statusCode);
} else {
  // data is already parsed as JSON:
  console.log(data);
  //console.log(data.html_url);
}
});


/*
var http = require('http');

var options = {
  host: 'http://localhost',
  port: 8484,
  path: '/pi//actuators/leds/1'
};

 
var options = {
  host: 'www.google.com',
  port: 80,
  path: '/index.html'
};
 

http.get(options, function(res) {
  console.log("Got response: " + res.statusCode);
}).on('error', function(e) {
  console.log("Got error: " + e.message);
});
 
 
/*
var querystring = require('querystring');
var request     = require('request');

var form = {
    username: 'usr',
    password: 'pwd',
    opaque: 'opaque',
    logintype: '1'
};

var formData = querystring.stringify(form);
var contentLength = formData.length;

request({
    headers: {
      'Content-Length': contentLength,
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    uri: 'http://myUrl',
    body: formData,
    method: 'POST'
  }, function (err, res, body) {
    //it works!
  });

*/
/*
const 
	request = require('request'),
     url = 'http://devices.webofthings.io/pi/sensors/temperature/value/'

request(url, (error, response, body)=> {
  if (!error && response.statusCode === 200) {
    const fbResponse = JSON.parse(body)
    console.log("Got a response: ", fbResponse.picture)
  } else {
    console.log("Got an error: ", error, ", status code: ", response.statusCode)
  }
})
*/
 
/* 
var http = require('http');
//var url  = 'http://devices.webofthings.io/pi/sensors/temperature/value/';
var url  = 'http://http://localhost:8484/pi/actuators/leds/1';

console.log("Start url=" + url);  
  
http.get(url, function(res){
    var body = ''; 

    res.on('data', function(chunk){
    	console.log("on "+chunk);
        body += chunk;
    });

    res.on('end', function(){
        //var fbResponse = JSON.parse(body);
        //console.log("Got a response: ", fbResponse.picture);
        console.log(body);
    });
}).on('error', function(e){
      console.log("Got an error: ", e);
});

*/
/*

console.log("Start");

var options = {
  host: 'http://localhost',
  port: 8484,
  path: '/pi//actuators/leds/1'
};



var x = http.request(options,function(res){
    console.log("Connected");
    res.on('data',function(data){
        console.log(data);
    });
});

x.end();

*/

//============================================
process.on('exit', function(code){
	console.log("Exiting code= " + code );  
});

//See https://coderwall.com/p/4yis4w/node-js-uncaught-exceptions
process.on('uncaughtException', function (err) {
	console.error('got uncaught exception:', err.message);
	process.exit(1);		//MANDATORY!!!
});



/*
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
  
 
 
function httpGetAsync(theUrl, callback){
    var xmlHttp = new XMLHttpRequest();
    
    xmlHttp.onreadystatechange = function() { 
    	console.log("onreadystatechange status=" + xmlHttp.status + " readyState=" + xmlHttp.readyState + " " + xmlHttp.responseText);
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
} 
 
function showValue( data ){
	console.log(data);
} 

console.log("START");
httpGetAsync( "http://devices.webofthings.io/pi/sensors/temperature", showValue );
 
*/

 
/*
var http = require('http');

//http://devices.webofthings.io/pi/sensors/temperature
//The url we want is: 'www.random.org/integers/?num=1&min=1&max=10&col=1&base=10&format=plain&rnd=new'
var options = {
  host: 'http://localhost:8484/pi//actuators/leds/1/',
  path: ''
};

callback = function(response) {
  var str = '';

  //another chunk of data has been received, so append it to `str`
  response.on('data', function (chunk) {
  	console.log(chunk);
    str += chunk;
  }); 

  //the whole response has been recieved, so we just print it out here
  response.on('end', function () {
    console.log(str);
  });
}

 

http.request(options, callback).end(); 
*/
 
 /*
request('http://stackabuse.com', function(err, res, body) {  
    console.log(body);
});
*/

/*
//var serverUrl = 'http://localhost:8484/pi/actuators/leds/1';
var serverUrl = 'http://localhost:8484/pi/sensors/temperature';

request.get({
    url:     serverUrl,
    //json:    true,
    headers: {
    	"Content-Type" : 'application/prolog',
    	'User-Agent': 'request'
    }
  }, (err, res, data) => {
    if (err) {
      console.log('Error:', err);
    } else if (res.statusCode !== 200) {
      console.log('Status:', res.statusCode);
    } else {
      // data is already parsed as JSON:
      console.log(data);
     }
});
*/
 /*
var https = require("http");

var options = {
  host: 'localhost',
  port: '8484',
  path: '/pi/sensors/temperature',
  headers: { "Content-Type" :"application/prolog" },
  method: 'GET'  
};

var req = http.request(options, function(res) {
  console.log('STATUS: ' + res.statusCode);
  console.log('HEADERS: ' + JSON.stringify(res.headers));
  
  res.setEncoding('utf8');
  res.on('data', function (chunk) {
    console.log( chunk );   // output the return raw data
  });
});

req.on('error', function(e) {
  console.log('problem with request: ' + e.message);
});


*/
 