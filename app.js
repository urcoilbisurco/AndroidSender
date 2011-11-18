
/**
 * Module dependencies.
 */

var config= require('./config.js'),
	app=config.app,
	io=config.io,
	r=config.r;

	io.sockets.on('connection', function (socket) {
	  
	});


// Routes

app.get('/', function(req, res){
  res.render('index', { title: 'Express' })
});



app.post('/send', function(req, res){
	console.log(req.body.url)
	io.sockets.emit('message', { type: "url" , url: req.body.url });
	console.log("messaggio inviato");
	
	res.redirect("back");
});
app.listen(process.env.PORT || 3000);
console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
