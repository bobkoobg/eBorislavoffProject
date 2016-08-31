/* global Handlebars, cookieName */

console.log("Hello world!!!");

var $body;
var flexibleSectionsHolder;

function goBack() {
    window.history.back()
}

function evaluateSave(data, status) {
    if (status === "success" && data != null) {
        window.location = "/emkobarona/flexibleSections";
    } else {
        console.log("ERROR!");
    }
}

function saveFlexibleSection() {
    var fsId = window.location.href.split("/").slice(-1).pop();

    var fsTitle = $(".fs-title").val();
    var fsMessage = $(".fs-message").val();

    $.ajax({
        "url": "/emkobaronaAPI/flexibleSections/save/" + fsId + "/" + getCookie(cookieName),
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            id: fsId,
            title: fsTitle,
            message: fsMessage
        }),
        "success": evaluateSave,
        "error": evaluateSave
    });
}

function loadFlexibleSections(data, status) {
    flexibleSectionsHolder.empty();

    var source = $("#flexible-section-element-template").html();
    var template = Handlebars.compile(source);
    var content = {
        id: data[0].id,
        purpose: data[0].fs_purpose,
        title: data[0].title,
        message: data[0].message,
        userId: data[0].user_id,
        creationDate: data[0].creationdate,
    };
    var html = template(content);
    flexibleSectionsHolder.append(html);
}

function requestFlexibleSections() {
    var fsId = window.location.href.split("/").slice(-1).pop();

    $.ajax({
        "url": "/emkobaronaAPI/flexibleSections/" + fsId + "/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFlexibleSections,
        "error": loadFlexibleSections
    });
}


function load() {
    console.log("flexibleSectionsSpecific.js loaded...");
    $body = $(document.body);

    flexibleSectionsHolder = $("#fs-edit-holder");

    requestFlexibleSections();

    $body.on("click", ".save-fs", saveFlexibleSection);
    $body.on("click", ".back-to-fss", goBack);
}

$(window).ready(load);