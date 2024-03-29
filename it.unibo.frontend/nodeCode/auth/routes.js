var express  = require("express");
var passport = require("passport");

var User     = require("./models/user");
var router   = express.Router();

function ensureAuthenticated(req, res, next) {
  if (req.isAuthenticated()) {
    next();
  } else {
    req.flash("info", "You must be logged in to see this page.");
    res.redirect("/login");
  }
}


router.use(function(req, res, next) {
  res.locals.currentUser = req.user;
  res.locals.errors      = req.flash("error");
  res.locals.infos       = req.flash("info");
  next();
});

router.get("/", function(req, res, next) {
	res.render("login");	//AN (1)
//  User.find()
//  .sort({ createdAt: "descending" })
//  .exec(function(err, users) {
//    if (err) { return next(err); }
//    res.render("index", { users: users });
//  });
});
router.get("/login", function(req, res) {
	  res.render("login");
	});

router.post("/login", passport.authenticate('login', { scope : ['profile', 'email'] }));

router.get("/access", ensureAuthenticated, function(req, res, next) {	//AN (3)
	//console.log("auth routes access ");
	res.render("access");		//AN (4)
});


router.get("/logout", function(req, res) {
  req.logout();
  res.redirect("/");
});

router.get("/signup", function(req, res) {
  res.render("signup");
});

router.post("/signup", function(req, res, next) {

  var username = req.body.username;
  var password = req.body.password;

  User.findOne({ username: username }, function(err, user) {

    if (err) { return next(err); }
    if (user) {
      req.flash("error", "User already exists");
      return res.redirect("/signup");
    }

    var newUser = new User({
      username: username,
      password: password
    });
    newUser.save(next);

  });
}, passport.authenticate(passport.authenticate('google', { scope : ['profile', 'email'] }));

// =====================================
// GOOGLE ROUTES =======================
// =====================================
// send to google to do the authentication
// profile gets us their basic information including their name
// email gets their emails
router.get('/auth/google', function(){console.log("AAAAA")}/*passport.authenticate('google', { scope : ['profile', 'email'] })*/);

// the callback after google has authenticated the user
/*router.get('/auth/google/callback',
    passport.authenticate('google', {
            successRedirect : '/',
            failureRedirect : '/signup'
    }));*/

router.get("/users/:username", function(req, res, next) {
  User.findOne({ username: req.params.username }, function(err, user) {
    if (err) { return next(err); }
    if (!user) { return next(404); }
    res.render("profile", { user: user });
  });
});

router.get("/edit", ensureAuthenticated, function(req, res) {
  res.render("edit");
});

router.post("/edit", ensureAuthenticated, function(req, res, next) {
  req.user.displayName = req.body.displayname;
  req.user.bio = req.body.bio;
  req.user.save(function(err) {
    if (err) {
      next(err);
      return;
    }
    req.flash("info", "Profile updated!");
    res.redirect("/edit");
  });
});

module.exports = router;
