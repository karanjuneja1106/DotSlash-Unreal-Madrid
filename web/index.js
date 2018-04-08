const express=require('express');
const app=express();
const http=require('http').Server(app);
var server = app.listen(3000);
const io=require('socket.io').listen(server);
const port=process.env.PORT || 3000;
require('./globals'); 
// require('./socketserver')(io,app);

app.set('view engine','hbs');
app.use(express.static(__dirname+'/public'));
app.use('/bower_components',express.static(__dirname+'/bower_components'));


io.on('connection', function(socket){
    console.log('a user connected');
    socket.on('chat message',(msg)=>{
        io.emit('chat message',msg);
    })
    socket.on('disconnect', function(){
      console.log('user disconnected');
    });
  });

app.get('/',(req,res)=>{
    res.render('home.hbs');
});
app.get('/sign_in',(req,res)=>{
    res.render('sign_in.hbs');
});
app.get('/user',(req,res)=>{
    res.render('user.hbs');
});
app.get('/admin',(req,res)=>{
    res.render('admin.hbs');
});
app.get('/check',(req,res)=>{
    res.render('check.hbs');
});
app.get('/userFood',(req,res)=>{
    res.render('userFood.hbs');
});
app.get('/stuff',(req,res)=>{
    res.render('stuff.hbs');
});
app.get('/makeStuff',(req,res)=>{
    res.render('makeStuff.hbs');
});
app.get('/post',(req,res)=>{
    res.render('post.hbs');
});
