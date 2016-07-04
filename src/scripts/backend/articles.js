console.log("Hello Articles");

var $logout;
var cookieName = "eborislavoff-user-sessionid";

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}

function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function logOut() {
    deleteCookie(cookieName);
    window.location = "/emkobarona/";
}

function loadComponents() {
    $logout = $(".logout");
    $logout.on("click", logOut);
}

function evaluateServerCookieResponse(object, status) {
    if (status === "success" && object != true) {
        loadComponents();
    } else {
        window.location = "/emkobarona/";
    }
}

function evaluateUserCookie(cookie) {
    $.ajax({
        "url": "/api/session",
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify(cookie),
        "success": evaluateServerCookieResponse,
        "error": evaluateServerCookieResponse
    });
}

function load() {
    console.log("articles.js loaded...");

    var cookie = getCookie(cookieName);
    if (cookie) {
        evaluateUserCookie(cookie);
    } else {
        window.location = "/emkobarona/";
    }


}

$(window).ready(load);
