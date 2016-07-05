var $username;
var $password;
var $passwordRepeated;
var $email;
var $emailRepeated;
var $registrationStatus;
var hashedPassword;
var clientRN;
var serverRN;
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

function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function evaluateRegistrationServerResponse(object, status) {
    if (status === "success" && object != null) {
        window.location = '/emkobarona';
    } else {
        $registrationStatus.html(status + " - Incorrect login information.");
    }
}

function sendRegistrationInformation(data, status) {

    if (status == "success" && data.responseCode == 200) {
        var password = (serverRN + "").concat(hashedPassword.concat((clientRN + "")));
        $.ajax({
            "url": "/emkobaronaAPI/register",
            "type": "PUT",
            "headers": {"Content-Type": "application/json"},
            "data": JSON.stringify({
                'username': $username.val(),
                'password': password,
                'email': $email.val(),
            }),
            "success": evaluateRegistrationServerResponse,
            "error": evaluateRegistrationServerResponse
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
            "success": sendRegistrationInformation
        });
    } else {
        console.log("Display error where you provide status and responseCode");
    }

}

function requestServerIdentifier() {
    hashedPassword = sha256($password.val());
    $.ajax({
        "url": "/emkobaronaAPI/registerServerId",
        "type": "PUT",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": sendClientIdentifier
    });
}

function validateEmail(email) {
    var re = new RegExp("/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/");
    return re.test(email);
}

function basicCheck(e) {
    e.preventDefault();

    var username = $username.val();
    var password = $password.val();
    var passwordRepeated = $passwordRepeated.val();
    var email = $email.val();
    var emailRepeated = $emailRepeated.val();
    if (username.length <= 5) {
        alert("username.length <= 5 : " + username.length);
        return false;
    }
    if (password !== passwordRepeated) {
        alert("password fields are not matching");
        return false;
    }
    if (password.length <= 8 || passwordRepeated.length <= 8) {
        alert("password.length <= 8 : " + password.length);
        return false;
    }
    if (validateEmail(email) && validateEmail(emailRepeated)) {
        alert("The email(s) are not correct");
        return false;
    }
    if (email !== emailRepeated) {
        alert("email fields are not matching");
        return false;
    }
    var matches = password.match(/\d+/g);
    var matchesRepeated = passwordRepeated.match(/\d+/g);
    if (matches === null || matchesRepeated === null) {
        alert('password does not contain number(s)');
        return false;
    }

    requestServerIdentifier();
}


function loadComponents() {
    $username = $("#register-form").find("input[name='username']");
    $password = $("#register-form").find("input[name='password']");
    $passwordRepeated = $("#register-form").find("input[name='password-repeated']");
    $email = $("#register-form").find("input[name='email']");
    $emailRepeated = $("#register-form").find("input[name='email-repeated']");
    $registrationStatus = $(".registration-status");
    $("#register-form-button").removeAttr("disabled");
    $("#register-form").submit(basicCheck);
}

function evaluateServerCookieResponse(data, status) {
    $registrationStatus = $(".registration-status");
    if (status == "success" && data.responseCode == 200) {
        $registrationStatus.html("You currently have a session.");
        window.location = '/emkobarona';
    } else {
        $registrationStatus.html(status + " : " + data.responseJSON.message);
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
    console.log("register.js loaded...");
    var cookie = getCookie(cookieName);
    if (cookie) {
        evaluateUserCookie(cookie);
    } else {
        loadComponents();
    }
}

$(window).ready(load);
