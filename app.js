
/**
 * Module dependencies.
 */

var config= require('./config.js'),
	app=config.app,
	io=config.io,
	r=config.r;
	if (process.env.REDISTOGO_URL) {
		var rtg   = require("url").parse(process.env.REDISTOGO_URL);
		var redis = r.createClient(rtg.port, rtg.hostname);
		redis.auth(rtg.auth.split(":")[1]);
	} else {
	  var redis =r.createClient();
	}
	redis.del("activeUsers");
	io.sockets.on('connection', function(socket){
		console.log("ho ricevuto una connessione");
	  	socket.on('getAuth', function(data, fn){
			socket.auth=data;
			redis.sismember("activeUsers", data, function(err,res){
				console.log("this user is already in activeUser array? " + !!res);
				if(res){
					console.log("yes is it!");
				}else{
					
					console.log("no it isn't!");
					console.log(socket.id)
					console.log("PRIMO");
					socket.join(data);
					console.log("SECONDO");
				
					//importante per trovare una soluzione!!!!!	
					//onsole.log(socket.join(data).namespace.manager.roomClients);
					console.log("auth ricevuto:"+data);
					//store redis activeUsers the auth;
					redis.sadd("activeUsers", socket.auth);
				}
				console.log("rooms:");
				console.log(io.sockets.manager.rooms);
				var key="m:"+socket.auth;
				redis.lrange(key, 0 , -1, function(err, urls){
					console.log(urls)
					redis.del(key, function(err, res){});
					fn(urls);
				});
			});
		});
		
		socket.on('disconnect', function () {
			console.log("un client si è disconnesso:" + socket.auth);
		    socket.leave(socket.auth);
			//remove activeUsers socket.auth;
			redis.srem("activeUsers", socket.auth, function(err, res){});
			
		});
	});


// Routes

app.get('/', function(req, res){
  res.render('index', { title: 'Express' })
});




//new version, with auth support for multi user!

app.post('/api/message', function(req, res){
	//change the activeuser type in set
	console.log("ricevuto messaggio da " + req.body.auth + " e l'url è "+ req.body.url);
	redis.sismember("activeUsers", req.body.auth, function(err,res){
		console.log("is activeuser: " + !!res);
		if(res){
			console.log(req.body.auth + " è già collegato");
			io.sockets.in(req.body.auth).emit('message', { type:"url", url: req.body.url});
		}else{
		
			console.log(req.body.auth + " non è collegato, registro il dato");
			key="m:"+req.body.auth;
			//store in redis db and wait that the client connects;
			redis.rpush(key, req.body.url);
		}
	});
	res.redirect("back");
		
});

app.post('/api/auth', function(req,res){
	//first, check that the email is not already used:
	key="users:"+ req.body.email;
	redis.get(key, function(error, auth){
		if(auth){
			if(auth==req.body.auth){
				//user already created, but auth is right and the user is the same
				console.log("user "+ req.body.email +" is logged in (it was already registered)");
				//res.send("ok, user logged in");
			} else{
				console.log("esiste già: " + req.body.email + "-" + auth);
				//res.send("the email '#{email} already exists with another password, #{forgot my password link?}");
			}
		} else {
			// get email and token and store in two separate redis elements:
			// i create also a list of email, just in case i need it for comunicate something
			redis.rpush('users', req.body.email);
			redis.set(key, req.body.auth)
			// it don't need to create the list for the urls, will be created the first time a url is pushed
			//res.send("ok, user created");
		}		
	});
	res.redirect("back");
});



app.listen(process.env.PORT || 3000);
console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
