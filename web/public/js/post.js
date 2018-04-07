var config = {
    apiKey: "AIzaSyBDOISEh81sFLFOVx4wuY-Bj3_Y35EZxoU",
    authDomain: "campusbuddy-d4179.firebaseapp.com",
    databaseURL: "https://campusbuddy-d4179.firebaseio.com",
    projectId: "campusbuddy-d4179",
    storageBucket: "campusbuddy-d4179.appspot.com",
    messagingSenderId: "516708520642"
};
firebase.initializeApp(config);

var database = firebase.database();
initApp = function () {
    firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
            // User is signed in
            var title = document.querySelector('#title');
            var textArea = document.querySelector('#textArea');
            var submitBtn = document.querySelector('#submitBtn');
            var userId = firebase.auth().currentUser.uid;

            submitBtn.addEventListener('click', () => {
                let name = firebase.auth().currentUser.displayName;
                let email = firebase.auth().currentUser.email;
                let imgUrl = firebase.auth().currentUser.photoURL;
                let blog={
                    name: name,
                    title: title.value,
                    text: textArea.value,
                    profile_picture: imgUrl,
                    timeStamp:new Date().getTime()
                };
                blog=JSON.stringify(blog);
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