var cookieName = "eborislavoff-user-sessionid";
var $correctSession;
var $status;

function loadComponents() {
    $correctSession.text("Your session is correct : " + getCookie(cookieName));
    $status.text("You are an administrator!");
}

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


function evaluateServerCookieResponse(data, status) {
    $loginStatus = $(".login-status");
    if (status == "success" && data.responseCode == 200) {
        loadComponents();
    } else {
        $loginStatus.html(status + " : " + data.responseJSON.message);
        deleteCookie(cookieName);
        window.location = "/emkobarona/login";
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

    $correctSession = $(".correct-session");
    $status = $(".status");

    var cookie = getCookie(cookieName);
    if (cookie) {
        evaluateUserCookie(cookie);
    } else {
        window.location = "/emkobarona/login";
    }
}

$(window).ready(load);

