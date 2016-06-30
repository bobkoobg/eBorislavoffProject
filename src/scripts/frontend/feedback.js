/* global Handlebars */

var $body;
var $feedbackGuestbookElementsWrapper;
var $feedbackClientsElementsWrapper;

function loadFeedback(data, status) {
    $feedbackGuestbookElementsWrapper.empty();
    $feedbackClientsElementsWrapper.empty();

    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            if (dataValue.imagePath) {
                console.log("Client***")
            } else {
                var source = $("#guestbook-element-template").html();
                var template = Handlebars.compile(source);
                var content = {
                    id: dataValue.id,
                    guestName: dataValue.guestName,
                    message: dataValue.message,
                    creationDate: dataValue.creationDate
                };
                var html = template(content);
                $feedbackGuestbookElementsWrapper.append(html);
            }
        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestFeedback() {
    $.ajax({
        "url": "/api/feedback",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFeedback,
        "error": loadFeedback
    });
}

function load() {
    $body = $(document.body);
    $feedbackGuestbookElementsWrapper = $("#feedback-guestbook-elements-wrapper");
    $feedbackClientsElementsWrapper = $("#feedback-clients-elements-wrapper");

    requestFeedback();
}

$(window).ready(load);
