/* global Handlebars, cookieName */

var $body;
var $guestbookCreateEditHolder;
var processType;

function goBack() {
    window.history.back()
}

function evaluateSave(data, status) {
    if (status === "success" && data != null) {
        window.location = "/emkobarona/guestbook";
    } else {
        console.log("ERROR!");
    }
}

function createGuestbook() {
    console.log("You are trying to save dude");

    var gbName = $(".guestbook-name").val();
    var gbMessage = $(".guestbook-message").val();
    var gbImagePath = $(".guestbook-image-path").val();

    $.ajax({
        "url": "/emkobaronaAPI/guestbook/create/" + getCookie(cookieName),
        "type": "PUT",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            guestName: gbName,
            message: gbMessage,
            imagePath: gbImagePath
        }),
        "success": evaluateSave,
        "error": evaluateSave
    });
}

function updateGuestbook() {
    var guestbookId = window.location.href.split("/").slice(-1).pop();

    var gbName = $(".guestbook-name").val();
    var gbMessage = $(".guestbook-message").val();
    var gbImagePath = $(".guestbook-image-path").val();

    $.ajax({
        "url": "/emkobaronaAPI/guestbook/save/" + guestbookId + "/" + getCookie(cookieName),
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            guestName: gbName,
            message: gbMessage,
            imagePath: gbImagePath
        }),
        "success": evaluateSave,
        "error": evaluateSave
    });
}

function saveGuestbookLogic() {
    if ("edit" === processType) {
        updateGuestbook();
    } else if ("create" === processType) {
        createGuestbook();
    }
}

function loadGuestbook(data, status) {
    $guestbookCreateEditHolder.empty();

    var source = $("#guestbook-create-edit-element-template").html();
    var template = Handlebars.compile(source);
    var content = {
        gbid: data[0].id,
        guestName: data[0].guestName,
        message: data[0].message,
        imagePath: data[0].imagePath,
        ip: data[0].ip,
        creationDate: data[0].creationDate
    };

    var html = template(content);
    $guestbookCreateEditHolder.append(html);
}

function requestGuestbook() {
    var guestbookId = window.location.href.split("/").slice(-1).pop();

    $.ajax({
        "url": "/emkobaronaAPI/guestbook/" + guestbookId + "/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadGuestbook,
        "error": loadGuestbook
    });
}

function load() {
    console.log("articlesSpecific.js loaded...");
    $body = $(document.body);

    $guestbookCreateEditHolder = $("#guestbook-create-edit-holder");

    processType = window.location.href.split("/")[5];

    if ("edit" === processType) {
        requestGuestbook();
    } else if ("create" === processType) {
        loadGuestbook([{
                gbid: "...will be automatically generated",
                guestName: "",
                message: "",
                imagePath: "",
                ip: "...will be automatically generated",
                creationDate: "...will be automatically generated"
            }], "success");
    }

    $body.on("click", ".save-guestbook", saveGuestbookLogic);
    $body.on("click", ".back-to-guestbook", goBack);

}

$(window).ready(load);
