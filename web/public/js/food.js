var order={
    dosa:0,
    egg:0,
    bhel:0,
    phone:0
};
var phone=document.querySelector('#phone');
    phone.addEventListener('change',()=>{
        order.phone=phone.value;
    console.dir(phone);
    })
    

    console.log('this ran')
    var dosaA=document.querySelector('.dosaA');
    var dosaM=document.querySelector('.dosaM');
    var eggA=document.querySelector('.eggA');
    var eggM=document.querySelector('.eggM');
    var bhelA=document.querySelector('.bhelA');
    var bhelM=document.querySelector('.bhelM');
    var orderL=document.querySelector('.order');
    var dosaV=document.querySelector('.dosaV');
    var eggV=document.querySelector('.eggV');
    var bhelV=document.querySelector('.bhelV');
    var orderBtn=document.querySelector('#ordering');
    var msgDiv=document.querySelector('.msgDiv');
    var menuDiv=document.querySelector('.menuDiv');
    console.log(orderBtn);

    dosaA.addEventListener('click',()=>{
        order.dosa++;
        console.log(order);
        write();

    });
    dosaM.addEventListener('click',()=>{
        order.dosa--;
        console.log(order);
        write();
        
       
        
    });
    eggA.addEventListener('click',()=>{
        order.egg++;
        console.log(order);
        write();
        
        
        
    });
    eggM.addEventListener('click',()=>{
        order.egg--;
        console.log(order);
        write();
        
        
        
    });
    bhelA.addEventListener('click',()=>{
        order.bhel++;
        console.log(order);
        write();
        
        
        
    });
    bhelM.addEventListener('click',()=>{
        order.bhel--;
        console.log(order);
        write();
        
        
    }); 
    
    var orderString;
    console.log(order);
    function write(){
        dosaV.innerHTML=order.dosa;
        eggV.innerHTML=order.egg;
        bhelV.innerHTML=order.bhel;
        order.phone=
        orderString=JSON.stringify(order,undefined,2);
        console.log(orderString);
    }
    
    orderBtn.addEventListener('click',()=>{
        msgDiv.classList.remove('hide');
        menuDiv.classList.add('hide');
        var m=document.querySelector('#m');
        m.value=orderString;
    })
    
    document.addEventListener('load',()=>{
        msgDiv.classList.add('hide');
        menuDiv.classList.remove('hide');
    })
    
    
    
var socket=io();
var btn=document.querySelector('#btn');


            socket.emit('user ack',{
                id:socket.id,
                msg:'user connected'
            });
           btn.addEventListener('click',()=>{
               console.log('this ran');
            socket.emit('chat message', $('#m').val());
            $('#m').val('');
            return false;
          });
              socket.on('chat message',(msg)=>{
                  $('#messages').append($('<li>').text(msg));
              })




