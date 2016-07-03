var $body;

function loadFlexibleSectionContent(data, status) {

    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            var source = $("#flexible-section-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                fsid: dataValue.id,
                title: dataValue.title,
                author: dataValue.author,
                message: dataValue.message,
                date: dataValue.creationdate
            };
            var html = template(content);
            $('nav').after(html);
        });
    } else {
        console.log("Failure in loadFlexibleSectionContent Frontend");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestFlexibleSectionContent() {
    var type = window.location.href.split("/").slice(-1).pop();
    if (!type) {
        type = "index";
    }
    $.ajax({
        "url": "/api/flexiblesections/" + type,
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFlexibleSectionContent,
        "error": loadFlexibleSectionContent
    });
}

function loadFlexibleSection(data, status) {
    if (status == "success") {
        $('footer').after(data);
        requestFlexibleSectionContent();
    } else {
        console.log("Failure in loadFlexibleSection");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestFlexibleSection() {
    $.ajax({
        "url": "/api/flexibleSection",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFlexibleSection,
        "error": loadFlexibleSection
    });
}

function loadFooter(data, status) {
    if (status == "success") {
        $body.append(data);
        requestFlexibleSection();
    } else {
        console.log("Failure in loadFooter Frontend");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestFooter() {
    $.ajax({
        "url": "/api/footer",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "success": loadFooter,
        "error": loadFooter
    });
}

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
        "url": "/api/nav",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "success": loadNavMenu,
        "error": loadNavMenu
    });
}

function load() {
    $body = $(document.body);
    requestNavMenu();
    requestFooter();
}

$(window).ready(load);
