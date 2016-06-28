var $body;

function loadNavMenu(data, status) {
    if (status == "success") {
        $body.prepend(data);
        //requestNavStyling();
    } else {
        console.log("Failure in loadNavMenu Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestNavMenu() {
    $.ajax({
        "url": "/api/nav",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "success": loadNavMenu,
        "error": loadNavMenu
    });
}

function load() {
    console.log("page.js loaded...");

    $body = $(document.body);
    requestNavMenu();
}

$(window).ready(load);