/* global Handlebars */

var $body;
var $guestbookTable;

function loadGuestbook(data, status) {

    if (status === "success") {
        var source;
        var template;
        var content;
        var html;
        $.each(data, function (dataKey, dataValue) {

            source = $("#guestbook-element-template").html();
            template = Handlebars.compile(source);
            content = {
                gbid: dataValue.id,
                guestName: dataValue.guestName,
                message: dataValue.message,
                imagePath: dataValue.imagePath == null ? "N/A" : "/api/image/" + dataValue.imagePath,
                ip: dataValue.ip,
                creationDate: dataValue.creationDate,
                path: "guestbook/edit/" + dataValue.id
            };
            html = template(content);

            $guestbookTable.append(html);
        });
    } else {
        console.log("Failure in loadGuestbook Backend");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestGuestbook() {
    $.ajax({
        "url": "/emkobaronaAPI/guestbook/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadGuestbook,
        "error": loadGuestbook
    });
}

function reloadGuestbook(data, status) {
    if (status == "success") {
        window.location.href = "/emkobarona/guestbook";
    } else {
        console.log("Failure in deleteGuestbook Backend");
        console.log("data is : ", data, ", status is : ", status);
    }
}

function deleteGuestbook() {
    var guestbookId = $(this).parent().attr("data-gbid");
    var result = confirm("Do you really want to delete this article? (Article ID : " + guestbookId + " )");
    if (result) {
        $.ajax({
            "url": "/emkobaronaAPI/guestbook/delete/" + guestbookId + "/" + getCookie(cookieName),
            "type": "DELETE",
            "headers": {"Content-Type": "application/json"},
            "data": {},
            "success": reloadGuestbook,
            "error": reloadGuestbook
        });
    }
}

function load() {
    console.log("guestbook.js loaded...");

    $body = $(document.body);
    $guestbookTable = $(".guestbook-table-body");

    requestGuestbook();

    $body.on("click", ".delete-guestbook", deleteGuestbook);
}

$(window).ready(load);
