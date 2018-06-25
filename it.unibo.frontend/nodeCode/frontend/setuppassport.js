var passport       = require("passport");
var LocalStrategy  = require("passport-local").Strategy;
var User           = require("./appServer/models/user");
module.exports     = function() {
  passport.serializeUser(function(user, done) {
    done(null, user._id);
  });
  passport.deserializeUser(function(id, done) {
    User.findById(id, function(err, user) {
      done(err, user);
    });
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
passport.use("login", new GoogleStrategy({

                clientID        : "564959440681-q8arr3albbnv1c1341f7igjl5vs8337b.apps.googleusercontent.com",
                clientSecret    : "Hsnid3veljpxDOPZWmCX2gNp",
                callbackURL     : "/access",

                },
                function(token, refreshToken, profile, done) {

                    // make the code asynchronous
                    // User.findOne won't fire until we have all our data back from Google
                    process.nextTick(function() {
			console.log("here");
                        // try to find the user based on their google id
                        User.findOne({ 'google.id' : profile.id }, function(err, user) {

                            if (err)
                                return done(err);

                            if (user) {

                                // if a user is found, log them in
                                return done(null, user);
                            } else {

                                // if the user isnt in our database, create a new user
                                var newUser          = new User();

                                // set all of the relevant information
                                newUser.google.id    = profile.id;
                                newUser.google.token = token;
                                newUser.google.name  = profile.displayName;
                                newUser.google.email = profile.emails[0].value; // pull the first email

                                // save the user
                                newUser.save(function(err) {
                                    if (err)
                                        throw err;
                                    return done(null, newUser);
                                });
                            }
                        });
                    });

                })); 
};
