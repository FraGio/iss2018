var passport       = require("passport");
var session        = require("express-session");	
var LocalStrategy  = require("passport-local").Strategy;
var User           = require("./appServer/models/user");
module.exports     = function() {
  passport.serializeUser((user, cb) => {
  	cb(null, user);
  });
  passport.deserializeUser((obj, cb) => {
  	cb(null, obj);
  });
  /*passport.use("login", new LocalStrategy(function(username, password, done) {
    User.findOne({ username: username }, function(err, user) {
      if (err) { return done(err); }
      if (!user) {
        return done(null, false, { message: "Sorry, user not allowed!" });
      }
      user.checkPassword(password, function(err, isMatch) {
        if (err) { return done(err); }
        if (isMatch) {
          return done(null, user);
        } else {
          return done(null, false, { message: "Invalid password." });
        }
      });
    });
  }));*/
passport.use(new GoogleStrategy({

                clientID        : "564959440681-q8arr3albbnv1c1341f7igjl5vs8337b.apps.googleusercontent.com",
                clientSecret    : "Hsnid3veljpxDOPZWmCX2gNp",
                callbackURL     : "http://localhost:3000/auth/google/callback",
    		accessType: 'offline'

                }, (accessToken, refreshToken, profile, cb) => {
		  // Extract the minimal profile information we need from the profile object
		  // provided by Google
		  cb(null, extractProfile(profile));
		}));

};
