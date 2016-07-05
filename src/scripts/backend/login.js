var hashedPassword;
var $password;
var $username;
var clientRN;
var serverRN;
var $loginStatus;
var cookieName = "eborislavoff-user-sessionid";

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}

function setCookie(cname, cvalue, minutes) {
    var d = new Date();
    d.setTime(d.getTime() + (minutes * 60 * 1000)); //days*24*60*60*1000
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
    //window.location = "/musicLadder";
    $loginStatus.html("BETA TESTING - SUCCESS LOGIN.");
}

function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function evaluateLoginServerResponse(data, status) {
    if (status === "success" && data != null) {
        setCookie(cookieName, data.sessionId, 30);
        window.location = "/emkobarona";
    } else {
        $loginStatus.html(status + " - Incorrect login information.");
    }
}
function sendLoginInformation(data, status) {
    clientRN = data.message;
    if (status == "success" && data.responseCode == 200) {
        var password = (serverRN + "").concat(hashedPassword.concat((clientRN + "")));

        $.ajax({
            "url": "/emkobaronaAPI/login",
            "type": "POST",
            "headers": {"Content-Type": "application/json"},
            "data": JSON.stringify({
                'username': $username.val(),
                'password': password
            }),
            "success": evaluateLoginServerResponse,
            "error": evaluateLoginServerResponse
        });
    } else {
        console.log("Display error where you provide status and responseCode");
    }
}

function sendClientIdentifier(data, status) {
    serverRN = data.message;

    if (status == "success" && data.responseCode == 201) {
        clientRN = Math.floor((Math.random() * 10) + 1);

        $.ajax({
            "url": "/emkobaronaAPI/clientId",
            "type": "POST",
            "headers": {"Content-Type": "application/json"},
            "data": JSON.stringify({
                "message": clientRN
            }),
            "success": sendLoginInformation
        });
    } else {
        console.log("Display error where you provide status and responseCode");
    }
}

function requestServerIdentifier() {
    hashedPassword = sha256($password.val());

    $.ajax({
        "url": "/emkobaronaAPI/loginServerId",
        "type": "PUT",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": sendClientIdentifier
    });
}

function basicCheck(e) {
    e.preventDefault();
    var username = $username.val();
    var password = $password.val();

    if (username.length <= 5) {
        alert("username.length <= 5 : " + username.length);
        return false;
    }
    if (password.length <= 8) {
        alert("password.length <= 8 : " + password.length);
        return false;
    }
    var matches = password.match(/\d+/g);

    if (matches === null) {
        alert('password does not contain number(s)');
        return false;
    }

    requestServerIdentifier();
}

function loadComponents() {
    $username = $("#login-form").find("input[name='username']");
    $password = $("#login-form").find("input[name='password']");
    $loginStatus = $(".login-status");

    $("#login-form-button").removeAttr("disabled");
    $('#login-form').submit(basicCheck);
}

function evaluateServerCookieResponse(data, status) {
    $loginStatus = $(".login-status");
    if (status == "success" && data.responseCode == 200) {
        window.location = "/emkobarona";
    } else {
        $loginStatus.html(status + " : " + data.responseJSON.message);
        deleteCookie(cookieName);
        loadComponents();
    }
}

function evaluateUserCookie(cookie) {
    $.ajax({
        "url": "/emkobaronaAPI/session",
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify(cookie),
        "success": evaluateServerCookieResponse,
        "error": evaluateServerCookieResponse
    });
}

function load() {
    console.log("index.js loaded...");

    var cookie = getCookie(cookieName);
    if (cookie) {
        evaluateUserCookie(cookie);
    } else {
        loadComponents();
    }
}

$(window).ready(load);
