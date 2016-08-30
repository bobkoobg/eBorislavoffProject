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
                creationDate: dataValue.creationDate
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
        "url": "/api/feedback",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadGuestbook,
        "error": loadGuestbook
    });
}

function load() {
    console.log("guestbook.js loaded...");

    $body = $(document.body);
    $guestbookTable = $(".guestbook-table-body");

    requestGuestbook();
}

$(window).ready(load);
