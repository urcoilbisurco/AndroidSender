var express = require('express')
	r=require("redis"),
	ejs=require("ejs");

// Configuration
	var app = express.createServer(
	    express.bodyParser()
	  , express.static(__dirname + "/public")
	  , express.cookieParser()
	  , express.session({ secret: 'htuayreve'})
	  );


		// Configure express server

app.configure(function() {
  app.set('view engine', 'ejs');
});




app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true })); 
});

app.configure('production', function(){
  app.use(express.errorHandler()); 
});

//Socket part
var io = require('socket.io').listen(app);

	// configure socket.io for heroku
io.configure(function () { 
 	io.set("transports", ["xhr-polling"]); 
	io.set("polling duration", 10); 
});




// exports all the useful variables

exports.io=io;
exports.app=app;
exports.r= r;