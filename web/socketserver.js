'use strict';

require('./globals');
 
module.exports=(io,app)=>{

    let windowID;
    io.on('connection',(socket)=>{
        // userRoom.push(socket);
        // windowID=socket; 
        // console.log("user connected");
        // socket.emit('ackUser',{
        //     id:socket.id,
        //     msg:"User connected"
        // });
        // socket.on('privateRoom', (user) => {
        //   let unfilledRooms = messageRoom.filter((room) => {
        //     if (!room.isFilled) {
        //       console.log(room.roomID);
        //       return room;
        //     }
        //   });
        //   console.log(`Unfilled Rooms: ${JSON.stringify(unfilledRooms[0])}`);
        //   try {
        //     // join the existing room.
        //     let socket=adminRoom[0];
        //     socket.join(unfilledRooms[0].roomID);
        //     let index = rooms.indexOf(unfilledRooms[0]);
        //     rooms[index].isFilled = true;
        //     unfilledRooms[0].isFilled = true;
        //     socket.emit('private ack', { "message": "Added to privateRoom", "roomID": unfilledRooms[0].roomID });
        //     socket.roomID = unfilledRooms[0].roomID;
        //     io.sockets.in(socket.roomID).emit('toast', { "message": "You are connected. Place your Order!"})
        //     console.log(`Joined existing room: ${unfilledRooms[0].roomID}`);
        //     console.log(`--------------------------------------------`);
        //   }
        //   catch(e) {
        //     // dont have unfilled rooms. Thus creating a new user.
        //     let uID = uniqueID();
        //     console.log(`Created new room: ${uID}`);
        //     unfilledRooms.push({ "roomID": uID, "isFilled": false });
        //     socket.join(uID);
        //     socket.roomID = uID;
        //     console.log(`Socket joined in room with id: ${uID}`);
        //     socket.emit('private ack', { "message": "Added to privateRoom", "roomID": uID });
        //     // console.log(`Current status of rooms: ${rooms}`);
        //     console.log(`--------------------------------------------`);
        //   }
        // });
        // socket.on('sendMessage', (data) => {
        //   let timeStamp = moment().format('LT');
        //   io.sockets.in(data.room).emit('newMessage', { "message": data , "senderId": windowID.id, "timeStamp": timeStamp});
        // });
        
            console.log('a user connected');
            socket.on('chat message',(msg)=>{
              io.emit('chat message',msg);
          })
    });
    // io.of('/admin').on('connection',(socket)=>{
    //     adminRoom.push(socket);

    //     socket.emit('ackAdmin',{
    //         id:socket.id,
    //         msg:"Admin connected"
    //     });
    // });

}