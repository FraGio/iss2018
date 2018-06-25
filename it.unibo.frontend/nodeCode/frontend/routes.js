var express = require("express");
var passport = require("passport");

var router = express.Router();

router.get('/auth/google', passport.authenticate('login', { scope : ['profile', 'email'] }));

module.exports = router;
