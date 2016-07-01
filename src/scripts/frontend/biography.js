/* global Handlebars */

var $body;
var $biographyElementWrapper;
var $biographyGalleryElementsWrapper;

function loadBiographyImages(data, status) {
    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {

            var source = $("#biography-gallery-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                author: dataValue.author,
                imagePath: "/api/image/" + dataValue.imagepath,
                date: dataValue.creationdate
            };
            var html = template(content);
            $biographyGalleryElementsWrapper.append(html);
        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function loadBiography(data, status) {

    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            var source = $("#biography-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                title: dataValue.title,
                author: dataValue.author,
                message: dataValue.message,
                date: dataValue.creationdate
            };
            var html = template(content);
            $biographyElementWrapper.append(html);
        });
    } else {
        console.log("Failure in Biography Frontend Page");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestBiographyImages() {
    $.ajax({
        "url": "/api/flexiblesectionsgallery/biography",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadBiographyImages,
        "error": loadBiographyImages
    });
}

function requestBiography() {
    $.ajax({
        "url": "/api/flexiblesections/biography",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadBiography,
        "error": loadBiography
    });
}

function load() {
    $body = $(document.body);
    $biographyElementWrapper = $("#biography-element-wrapper");
    $biographyGalleryElementsWrapper = $("#biography-gallery-elements-wrapper");

    requestBiography();
    requestBiographyImages();
}

$(window).ready(load);
