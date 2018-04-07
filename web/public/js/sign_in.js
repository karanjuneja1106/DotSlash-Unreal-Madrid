var config = {
    apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
    authDomain: "campusbuddy-d4179.firebaseapp.com",
    databaseURL: "https://campusbuddy-d4179.firebaseio.com",
    projectId: "campusbuddy-d4179",
    storageBucket: "campusbuddy-d4179.appspot.com",
    messagingSenderId: "516708520642"
  };
firebase.initializeApp(config);
var uiConfig = {
    signInSuccessUrl: '/check',
    signInOptions: [
      firebase.auth.GoogleAuthProvider.PROVIDER_ID,
      firebase.auth.EmailAuthProvider.PROVIDER_ID,
    ],
  };

firebase.auth().onAuthStateChanged(function (user) {
    if (user) {
      // User is signed in
      if(user.displayName=='abc'){
        window.location.replace('/admin');
      }
      else{
        window.location.replace('/user');          
      }
      
    } else {
      // User is signed out.
    }
  }, function (error) {
    console.log(error);
  });
  
  var ui = new firebaseui.auth.AuthUI(firebase.auth());
  ui.start('#firebaseui-auth-container', uiConfig);