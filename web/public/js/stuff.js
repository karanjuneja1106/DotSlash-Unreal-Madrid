

var config = {
  apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
  authDomain: "campusbuddy-d4179.firebaseapp.com",
  databaseURL: "https://campusbuddy-d4179.firebaseio.com",
  projectId: "campusbuddy-d4179",
  storageBucket: "campusbuddy-d4179.appspot.com",
  messagingSenderId: "516708520642"
};
firebase.initializeApp(config);
database=firebase.database();
var storageRef = firebase.storage().ref();
var leftst=document.querySelector('.here');
initApp = function () {
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        // User is signed in
        var displayName = user.displayName;
        var btn=document.querySelector('#stuffBtn');
        var imgP,st=[],imgUrl=[];
        btn.addEventListener('click',()=>{
          window.open('/makeStuff','_self');
        })
        database.ref('Advertisements/').on('value',(data)=>{
          store=data.val();
          for(x in store){
            for(y in store[x]){
              var z=JSON.parse(store[x][y]);
                    st.push(z);
                    console.log(st);
            }
          }
          st.forEach((img)=>{
            let i=0;
            console.log(img.IMG);
            storageRef.child(img.IMG).getDownloadURL().then(function(url) {
            imgUrl.push(url);
            var template=`
      <div class="ui special cards">
      <div class="card">
          <div class="blurring dimmable image">
              <div class="ui dimmer">
                  <div class="content">
                      <div class="center">
                          <div class="ui inverted button">Add Friend</div>
                      </div>
                  </div>
              </div>
              <img src="${imgUrl[i]}">
          </div>
          <div class="content">
              <a class="header">${img.TITLE}</a>
              <div class="meta">
                  <span class="date">${img.DESC}</span>
              </div>
          </div>
          <div class="extra content">
              <a>
                  <i class="users icon"></i>
                  2 Interested
              </a>
          </div>
      </div>
  </div>`;
      leftst.insertAdjacentElement('afterend','<div> cool</div>')
      console.log('this ran');
            i++;
          })
        },(i)=>{
          
        });});

        
   


       
      } else {
        // User is signed out.
        window.location.replace('/')
      }
    }, function (error) {
      console.log(error);
    });
  };
  $('.special.cards .image').dimmer({
    on: 'hover'
  });
  
  window.addEventListener('load', function () {
    initApp()
  });