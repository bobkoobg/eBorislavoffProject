var $indexPageWrapper;
var $indexGalleryElementsWrapper;

function loadIndexImages(data, status) {
    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {

            var source = $("#index-gallery-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                author: dataValue.author,
                imagePath: "/api/image/" + dataValue.imagepath,
                date: dataValue.creationdate
            };
            var html = template(content);
            $indexGalleryElementsWrapper.append(html);
        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestIndexImages() {
    $.ajax({
        "url": "/api/flexiblesectionsgallery/index",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadIndexImages,
        "error": loadIndexImages
    });
}

function load() {
    console.log("index.js loaded...");

    $indexPageWrapper = $(".indexPageWrapper");
    $indexGalleryElementsWrapper = $("#index-gallery-elements-wrapper");

    requestIndexImages();

}

$(window).ready(load);
