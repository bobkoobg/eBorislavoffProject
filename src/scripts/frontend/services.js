/* global Handlebars */

var $body;
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

function load() {
    $body = $(document.body);
    $servicesGalleryElementsWrapper = $("#services-gallery-elements-wrapper");
    
    requestServicesImages();
}

$(window).ready(load);
