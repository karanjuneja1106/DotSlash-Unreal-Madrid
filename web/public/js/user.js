var config = {
  apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
  authDomain: "campusbuddy-d4179.firebaseapp.com",
  databaseURL: "https://campusbuddy-d4179.firebaseio.com",
  projectId: "campusbuddy-d4179",
  storageBucket: "campusbuddy-d4179.appspot.com",
  messagingSenderId: "516708520642"
};
firebase.initializeApp(config);

var postBtn = document.querySelector('#postBtn');
console.log(postBtn)
var foodBtn = document.querySelector('#foodBtn');
var stuffBtn = document.querySelector('#stuffBtn');

var database = firebase.database();
initApp = function () {
  firebase.auth().onAuthStateChanged(function (user) {
    if (user) {
      // User is signed in
      var title = document.querySelector('#title');
      var textArea = document.querySelector('#textArea');
      var submitBtn = document.querySelector('#submitBtn');
      var list = document.querySelector('.eight.wide.column');
      var userId = firebase.auth().currentUser.uid;
      var store;
      database.ref('users/' + userId).orderByChild('timeStamp').on('value', (data) => {
        store = data.val();
        var template;
        for (x in store) {
          for (y in store[x]) {
            z = store[x][y];
            z = JSON.parse(z);
            console.log(z);

            template = `<div class="ui card" style="width:60vw;">
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
            list.insertAdjacentHTML('afterbegin', template);
          }
        }
      });
      var food = document.querySelector('#food');
      food.addEventListener('click', () => {
        let name = firebase.auth().currentUser.displayName;
        let imgUrl = firebase.auth().currentUser.photoURL;
        let blog = {
          name: name,
          profile_picture: imgUrl,
          talk:true,
          timeStamp: new Date().getTime()
        }
        firebase.database().ref('users/' + userId).push().child('message').set(blog);
      })


      submitBtn.addEventListener('click', () => {
        let name = firebase.auth().currentUser.displayName;
        let email = firebase.auth().currentUser.email;
        let imgUrl = firebase.auth().currentUser.photoURL;
        let blog = {
          name: name,
          title: title.value,
          text: textArea.value,
          profile_picture: imgUrl,
          timeStamp: new Date().getTime()
        };
        blog = JSON.stringify(blog);
        firebase.database().ref('users/' + userId).push().child('blog').set(blog);
        window.open('/user', '_self');
      })
    } else {
      // User is signed out.
      window.location.replace('/');
    }
  }, function (error) {
    console.log(error);
  });
};

window.addEventListener('load', function () {
  initApp()
});


var logOut = document.querySelector('#logOut');

logOut.addEventListener('click', () => {
  firebase.auth().signOut().then(() => {
    window.location.replace('/');
  });
});