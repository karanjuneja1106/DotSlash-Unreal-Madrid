var config = {
    apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
    authDomain: "campusbuddy-d4179.firebaseapp.com",
    databaseURL: "https://campusbuddy-d4179.firebaseio.com",
    projectId: "campusbuddy-d4179",
    storageBucket: "campusbuddy-d4179.appspot.com",
    messagingSenderId: "516708520642"
};
firebase.initializeApp(config);
var socket=io();
var btn=document.querySelector('#btn');
            console.log(btn);
           btn.addEventListener('click',()=>{
               console.log('this ran');
            socket.emit('chat message', $('#m').val());
            $('#m').val('');
            return false;
          });
              socket.on('chat message',()=>{
                  $('#messages').append($('<li>').text(msg));
              })
var database = firebase.database();