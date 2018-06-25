/*
 *  
 */
var express  = require('express');
var router   = express.Router();
var User     = require('../models/user');
var parseurl = require('parseurl');


//var redis   = require('redis');	//npm install redis --save
//var client = redis.createClient( 6379, '127.0.0.1');

router.use(function (req, res, next) {
	  if (!req.session.views) {
	    req.session.views = {}
	  }	 
	  // get the url pathname
	  var pathname = parseurl(req).pathname
	  console.log( "%%%%%%% applAuth pathname=" + pathname + 
			  " req.req.session.userId=" + req.session.userId );  
	  // count the views
	  req.session.views[pathname] = (req.session.views[pathname] || 0) + 1 ;	 
	  next();
})
	
// GET route for reading data ;
router.get('/', function (req, res, next) {
  //__dirname = C:\Didattica2018Work\iss2018Lab\it.unibo.frontend\nodeCode\frontend\appServer\routes		  
   var path = require('path');
   console.log("auth __dirname= " +  __dirname + " path="  + path  );  
   console.log("auth      path= " + path.join( __dirname , '../templateLogReg/index.html') );  
   return res.sendFile(path.join(__dirname , '../templateLogReg/index.html'));
});


//POST route for updating data ;
router.post('/', function (req, res, next) {
  // confirm that user typed same password twice ;
	 //console.log("POST " + req.body.email + " " + req.body.username );
  if (req.body.password !== req.body.passwordConf) {
    var err = new Error('Passwords do not match.');
    err.status = 400;
    res.send("passwords dont match");
    return next(err);
  }
  if (req.body.email &&
    req.body.username &&
    req.body.password &&
    req.body.passwordConf) {
	console.log("register " + req.body.email + " " + req.body.username + " " + req.body.password);
    var userData = {
      email: req.body.email,
      username: req.body.username,
      password: req.body.password,
      passwordConf: req.body.passwordConf,
    }

    User.create(userData, function (error, user) {
      if (error) {
        return next(error);
      } else {
        req.session.userId = user._id; 	 	 		
        return res.redirect('/profile');
      }
    });

  } else if (req.body.logemail && req.body.logpassword) {
		 console.log("logging " 
				 + req.body.logemail + " " + req.body.logpassword + " " + req.session.userId );
//    User.authenticate(req.body.logemail, req.body.logpassword, function (error, user) {
//      if (error || !user) {
//        var err = new Error('Wrong email or password.');
//        err.status = 401;
//        return next(err);
//      } else {
//        req.session.userId = user._id;
//        console.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + req.session.userId );
//        return res.redirect('/profile');
//      }
//    });
		 	//res.send("Welcome with a page todo");
		 req.session.userId = "default";
		 return res.redirect('/index');
		 	//return res.sendFile(path.join(__dirname , 'index'));
  } else {
    var err = new Error('All fields required.');
    err.status = 400;
    return next(err);
  }
})

// GET route after registering ;
router.get('/profile', function (req, res, next) {
	console.log("profileeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee " + req.session.userId );
  User.findById(req.session.userId)
    .exec(function (error, user) {
      if (error) {
        return next(error);
      } else {
        if (user === null) {
          var err = new Error('Not authorized! Go back!');
          err.status = 400;
          return next(err);
        } else {
          return res.send('<h1>Name: </h1>' + user.username + '<h2>Mail: </h2>' + user.email + '<br><a type="button" href="/logout">Logout</a>')
        }
      }
    });
});

// GET for logout logout ;
router.get('/logout', function (req, res, next) {
  if (req.session) {
    // delete session object
    req.session.destroy(function (err) {
      if (err) {
        return next(err);
      } else {
        return res.redirect('/');
      }
    });
  }
});

module.exports = router;