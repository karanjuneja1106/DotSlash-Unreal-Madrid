var config = {
    apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
    authDomain: "campusbuddy-d4179.firebaseapp.com",
    databaseURL: "https://campusbuddy-d4179.firebaseio.com",
    projectId: "campusbuddy-d4179",
    storageBucket: "campusbuddy-d4179.appspot.com",
    messagingSenderId: "516708520642"
  };
firebase.initializeApp(config);

initApp = function () {
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        // User is signed in
        var displayName = user.displayName;
        var title = document.querySelector('#title');
            var textArea = document.querySelector('#textArea');
            var pic = document.querySelector('#pic');
            var selectedFile;
            pic.addEventListener('change',(e)=>{
                selectedFile=e.target.files[0];
                console.log(selectedFile.name);
            })

            var userId = firebase.auth().currentUser.uid;
        var btn=document.querySelector('#submitBtn');
        btn.addEventListener('click', () => {
            let name = firebase.auth().currentUser.displayName;
            let email = firebase.auth().currentUser.email;
            let imgUrl = firebase.auth().currentUser.photoURL;
            var ref=firebase.storage().ref('/'+selectedFile.name);
            let photoUrl;
            let uploadTask=firebase.storage().ref('/'+selectedFile.name).put(selectedFile)
            uploadTask.on('state_changed',(data)=>{
                var downloadUrl=ref.getDownloadURL().then((data)=>{photoUrl=data;})
            },undefined,()=>{
                
            })
            let add={
                TITLE: title.value,
                DESC: textArea.value,
                timeStamp:new Date(),
                IMG:selectedFile.name,
                USER:email
            }
            add=JSON.stringify(add);
            firebase.database().ref('Advertisements/').push().child('ad').set(add);
            window.location.replace('/user', '_self');
        })
        
      } else {
        // User is signed out.
        window.location.replace('/','_self');
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