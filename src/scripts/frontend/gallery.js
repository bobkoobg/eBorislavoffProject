/* global Handlebars */

var $body;
var $galleryElementsWrapper;
var $actualImage;
var $imagePath;

function popInImage() {
    var $clickedElem = $(event.target);
    var elemId = $clickedElem.closest(".gallery").attr("data-galleryid");
    $actualImage = $(".actualImage");
    console.log($actualImage);
    console.log(elemId);

//    var source = $("#specific-gallery-element-template").html();
//    var template = Handlebars.compile(source);
//    var content = {
//        id: dataValue.id,
//        author: dataValue.author,
//        imagePath: "/api/image/" + dataValue.imagePath,
//        date: dataValue.creationDate
//    };
//    var html = template(content);
//    $body.append(html);
}

function loadGallery(data, status) {

    if (status == "success") {
        $galleryElementsWrapper.empty();
        $.each(data, function (dataKey, dataValue) {

            var source = $("#gallery-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                author: dataValue.author,
                imagePath: "/api/image/" + dataValue.imagePath,
                date: dataValue.creationDate
            };
            var html = template(content);
            $galleryElementsWrapper.append(html);
        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestGallery() {
    $.ajax({
        "url": "/api/gallery",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadGallery,
        "error": loadGallery
    });
}

function load() {
    $body = $(document.body);
    $galleryElementsWrapper = $("#galleryElementsWrapper");

    requestGallery();

    $body.on("click", ".imagePath", popInImage);
}

$(window).ready(load);
