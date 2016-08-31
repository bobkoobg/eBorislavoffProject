/* global Handlebars */

var $body;
var $flexSectHolder;

function loadFlexibleSections(data, status) {
    if (status === "success") {
        $.each(data, function (dataKey, dataValue) {
            var extendedText = dataValue.message.length > 255 ? 1 : 0;

            var source = $("#flexible-section-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                purpose: dataValue.fs_purpose,
                title: dataValue.title,
                message: Boolean(extendedText)
                        ? dataValue.message.substring(0, 255) + " ..."
                        : dataValue.message,
                userId: dataValue.user_id,
                creationDate: dataValue.creationdate,
                path: "flexibleSections/edit/" + dataValue.id
            };
            var html = template(content);
            $flexSectHolder.append(html);
        });
    } else {
        console.log("Failure in loadFlexibleSections Backend");
        console.log("data is : ", data, ", status is : ", status);
    }
}

function requestFlexibleSections() {

    $.ajax({
        "url": "/emkobaronaAPI/flexibleSections/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFlexibleSections,
        "error": loadFlexibleSections
    });
}

function load() {
    console.log("flexibleSections.js loaded...");

    $body = $(document.body);
    $flexSectHolder = $("#fs-holder");

    requestFlexibleSections();

}

$(window).ready(load);
