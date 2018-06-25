 /*
* =====================================
* it.unibo.frontend/nodeCode/frontend/clientRest.js
* =====================================
*/

//see https://www.npmjs.com/package/node-rest-client;
'use strict';
var RestClient = require('node-rest-client').Client;
var client = new RestClient();

//https://developer.yahoo.com/weather/?guccounter=1
var location = "Bologna";
//var location = "London";
var queryAstronomy =
	"https://query.yahooapis.com/v1/public/yql?q=select astronomy from weather.forecast where woeid in (select woeid from geo.places(1) where text='" + location + "') and u='c'&format=json";
var queryItem =
	"https://query.yahooapis.com/v1/public/yql?q=select item from weather.forecast where woeid in (select woeid from geo.places(1) where text='" + location + "') and u='c'&format=json";
	

var doGet = function(query){
	client.get(query, function (data, response) {
	    // parsed response body as js object;
	    if( data.query.results == null ){
	    	console.log("SORRY: no answer");
	    	return;
	    }
// 	    console.log( JSON.stringify( data.query.results ));
//	    console.log(data.query.results.channel);
	    if( data.query.results.channel.item != null ){
	    	console.log("TEMPERATORE of " + location) ;
 	    	console.log(data.query.results.channel.item.condition.temp);
	    }
	    if( data.query.results.channel.astronomy != null ){
	    	console.log("ASTRONOMY of " + location) ;
	    	console.log(data.query.results.channel.astronomy);
	    }
	    // raw response;
	}); 
}


doGet(queryAstronomy);
doGet(queryItem);
 
 