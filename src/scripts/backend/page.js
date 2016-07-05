var $body;

function loadNavMenu(data, status) {
    if (status == "success") {
        $body.prepend(data);
    } else {
        console.log("Failure in loadNavMenu Frontend");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestNavMenu() {
    $.ajax({
        "url": "/emkobaronaAPI/nav",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "success": loadNavMenu,
        "error": loadNavMenu
    });
}

function load() {
    $body = $(document.body);
    requestNavMenu();
}

$(window).ready(load);
