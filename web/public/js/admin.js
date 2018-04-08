var socket=io();
socket.emit('admin ack',{
    id:socket.id,
    msg:'Admin conected'
});
var btn=document.querySelector('#btn');
           btn.addEventListener('click',()=>{
               console.log('this ran');

            




            socket.emit('chat message', $('#m').val());
            $('#m').val('');
            return false;
          });
              socket.on('chat message',(msg)=>{
                  $('#messages').append($('<li>').text(msg));
              })