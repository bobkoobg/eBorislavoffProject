/* global Handlebars */

var $body;
var $galleryElementsWrapper;
var $actualImage;
var $imagePath;
var $close;
var $popUp;

function closePopup() {
    $popUp.fadeOut();
}

function showPopup() {
    var $clickedElem = $(event.target);
    var $gallery = $clickedElem.closest(".gallery");

    $popUp.find(".popupImagePath").attr("src", $gallery.find(".actualImage").attr("src"));
    $popUp.find(".popupAuthor").text($gallery.find(".author").text());
    $popUp.find(".popupDate").text($gallery.find(".date").text());

    $popUp.fadeIn();
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
    $popUp = $(".specific-gallery-popupwrapper");
    $close = $(".close");

    requestGallery();

    $body.on("click", ".imagePath", showPopup);
    $body.on("click", ".close", closePopup);
}

$(window).ready(load);
