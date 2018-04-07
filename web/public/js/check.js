initApp = function () {
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        // User is signed in
        var displayName = user.displayName;
        user.getIdToken().then(function (accessToken) {
            if(user.displayName=='abc'){
                window.location.replace('/admin');
              }
              else{
                window.location.replace('/user');          
              }
  
        });
      } else {
        // User is signed out.
      }
    }, function (error) {
      console.log(error);
    });
  };
  
  window.addEventListener('load', function () {
    initApp()
  });