var Test = function() {
    var url = "/api";

    this.addUser = function(username) {
        var user = { "username": username };
        var http = new XMLHttpRequest();
        http.open('POST', url + '/', false);
        http.setRequestHeader('content-type', 'application/json');
        http.send(JSON.stringify(user));
    };

    this.deleteUser = function(username) {
        var http = new XMLHttpRequest();
        http.open('DELETE', url + '/' + username, false);
        http.setRequestHeader('content-type', 'application/json');
        http.send();
    };

    this.addNote = function(username, note) {
        var http = new XMLHttpRequest();
        http.open('POST', url + '/' + username, false);
        http.setRequestHeader('content-type', 'application/json');
        http.send(JSON.stringify(note));
    };

    this.getNoteList = function(username) {
        var http = new XMLHttpRequest();
        http.open('GET', url + '/' + username, false);
        http.setRequestHeader('content-type', 'application/json');
        http.send();

        return JSON.parse(http.responseText);
    };

    this.updateNote = function(username, noteId, note) {
        var http = new XMLHttpRequest();
        http.open('PUT', url + '/' + username + '/' + noteId, false);
        http.setRequestHeader('content-type', 'application/json');
        http.send(JSON.stringify(note));
    };

    this.getHistory = function(username, noteId) {
        var http = new XMLHttpRequest();
        http.open('GET', url + '/' + username + '/' + noteId + '/history', false);
        http.setRequestHeader('content-type', 'application/json');
        http.send();

        return JSON.parse(http.responseText);
    }
};

window.addEventListener('DOMContentLoaded', function () {
    var test = new Test();
    test.deleteUser("jackie");

    test.addUser("jackie");
    test.addNote("jackie", {
        "title": "Hey",
        "content": "You are awesome"
    });

    var posts = test.getNoteList("jackie");
    var noteId = posts[0].noteId;
    test.updateNote("jackie", noteId, {
        "title": "Auch",
        "content": posts[0].note.content,
        "tags": ["tag1", "tag-1"]
    });

    var history = test.getHistory("jackie", noteId);
    var firstNote = history[history.length - 1];
    test.updateNote("jackie", noteId, firstNote);

    var htmlPost = document.getElementById("textToSend").innerHTML;
    test.addNote("jackie", {
        "content": JSON.stringify(htmlPost)
    });

    //Note with attach
    var fileDialog = document.getElementById("fileDialog");
    fileDialog.onchange = function() {
        var file = fileDialog.files[0];
        var reader = new FileReader();
        reader.onload = function (event) {
            var binaryString = event.target.result;
            var base64file = btoa(binaryString);
            test.addNote("jackie", {
               "content": "See file attached",
                "attach": [base64file]
            });
        };
        reader.readAsBinaryString(file);
    };

});