var express = require("express");
var passport = require("passport");

var router = express.Router();

router.get('/auth/google', passport.authenticate('google', { scope: ['email', 'profile'] }));


router.get('/auth/google/callback',

  function(req, res) { res.render("access");}
  //passport.authenticate('google')
);

module.exports = router;
