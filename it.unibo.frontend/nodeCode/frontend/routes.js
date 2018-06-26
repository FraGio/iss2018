var express = require("express");
var passport = require("passport");

var router = express.Router();

router.get('/auth/google', passport.authenticate('google', { scope: ['email', 'profile'] }));


router.get('/auth/google/callback',

  // Finish OAuth 2 flow using Passport.js
  function(req, res) { res.render("access");}
  //passport.authenticate('google')
);

module.exports = router;
