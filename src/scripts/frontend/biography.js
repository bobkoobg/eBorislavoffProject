/* global Handlebars */

var $body;
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

function load() {
    $body = $(document.body);
    $biographyGalleryElementsWrapper = $("#biography-gallery-elements-wrapper");

    requestBiographyImages();
}

$(window).ready(load);
