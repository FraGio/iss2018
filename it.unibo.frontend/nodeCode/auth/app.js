var bodyParser = require("body-parser");
var cookieParser = require("cookie-parser");
var express  = require("express");
var flash    = require("connect-flash"); //npm install connect-flash --save
var mongoose = require("mongoose");
var passport = require("passport");
var path     = require("path");
var session  = require("express-session");

var setUpPassport = require("./setuppassport");
var routes        = require("./routes");

var app = express();
try{
	//mongoose.connect("mongodb://localhost:27017/test");
	mongoose.connect("mongodb://0.0.0.0:27017/test");
}catch( e ){
	console.log("SORRY mongoose ... " + e) ;
}
setUpPassport();

app.set("port", process.env.PORT || 8080);

app.set("views", path.join(__dirname, "views"));
app.set("view engine", "ejs");

//app.use(express.static(path.join(__dirname, "public")));
app.use(express.static(path.join(__dirname, 'jsCode')))


app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(session({
  secret: "LUp$Dg?,I#i&owP3=9su+OB%`JgL4muLF5YJ~{;t",
  resave: true,
  saveUninitialized: true
}));

app.use(flash());

app.use(passport.initialize());
app.use(passport.session());

app.use(routes);

app.listen(app.get("port"), function() {
  console.log("Server started on port " + app.get("port"));
});
