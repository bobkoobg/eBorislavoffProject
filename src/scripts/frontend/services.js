/* global Handlebars */

var $body;
var $servicesElementWrapper;
var $servicesGalleryElementsWrapper;

function loadServicesImages(data, status) {
    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {

            var source = $("#services-gallery-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                author: dataValue.author,
                imagePath: "/api/image/" + dataValue.imagepath,
                date: dataValue.creationdate
            };
            var html = template(content);
            $servicesGalleryElementsWrapper.append(html);
        });
    } else {
    console.log("Failure in loadNews Frontend Index ");
    console.log("data is : " + data + ", status is : " + status);
    }
}

function loadServices(data, status) {

    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            var source = $("#services-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                title: dataValue.title,
                author: dataValue.author,
                message: dataValue.message,
                date: dataValue.creationdate
            };
            var html = template(content);
            $servicesElementWrapper.append(html);
        });
    } else {
        console.log("Failure in Biography Frontend Page");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestServicesImages() {
    $.ajax({
        "url": "/api/flexiblesectionsgallery/services",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadServicesImages,
        "error": loadServicesImages
    });
}

function requestServices() {
    $.ajax({
        "url": "/api/flexiblesections/services",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadServices,
        "error": loadServices
    });
}

function load() {
    $body = $(document.body);
    $servicesElementWrapper = $("#services-element-wrapper");
    $servicesGalleryElementsWrapper = $("#services-gallery-elements-wrapper");

    requestServices();
    requestServicesImages();
}

$(window).ready(load);
