var btn= document.querySelector('#signUp');

btn.addEventListener('click',()=>{
    window.open('/sign_in','_self')
}) 

var config = {
    apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
    authDomain: "campusbuddy-d4179.firebaseapp.com",
    databaseURL: "https://campusbuddy-d4179.firebaseio.com",
    projectId: "campusbuddy-d4179",
    storageBucket: "campusbuddy-d4179.appspot.com",
    messagingSenderId: "516708520642"
};
firebase.initializeApp(config);
var list=document.querySelector('.ten.wide.column');
var database = firebase.database();
initApp = function () {
    firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
            // User is signed in
            window.location.replace('/user');
            }
         else {
            // User is signed out.
            var st=[];
            database.ref('users/').on('value',(data)=>{
                store=data.val();
                var template;
                for(x in store){
                  for(y in store[x]){
                    var z=JSON.parse(store[x][y].blog);
                    console.log(z);
                    st.push(z);
                }}
                st.sort((a,b)=>{
                    return a.timeStamp-b.timeStamp;
                })
                st.forEach(z=>{
                    template=`<div class="ui card" style="width:60vw;">
                    <div class="content">
                      <i class="right floated like icon"></i>
                      <i class="right floated star icon"></i>
                      <div class="header">${z.title}</div>
                      <div class="description">
                        <p>${z.text}</p>
                      </div>
                    </div>
                    <div class="extra content">
                      <span class="left floated like">
                        <i class="like icon"></i>
                        Like
                      </span>
                      <span class="right floated star">
                        <i class="star icon"></i>
                        Favorite
                      </span>
                    </div>
                  </div>`;
                    list.insertAdjacentHTML('afterbegin',template);
                }) 
              });
              
              
        }
    }, function (error) {
        console.log(error);
    });
};

window.addEventListener('load', function () {
    initApp()
});