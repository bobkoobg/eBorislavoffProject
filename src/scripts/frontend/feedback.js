/* global Handlebars */

var $body;
var $feedbackGuestbookElementsWrapper;
var $feedbackClientsElementsWrapper;
var $backButton;
var $forwardButton;
var $carouselHolder;

function loadFeedback(data, status) {
    //$feedbackGuestbookElementsWrapper.empty();
    //$feedbackClientsElementsWrapper.empty();

    if (status == "success") {
        var source;
        var template;
        var content;
        var html;
        var carouselId = 1;
        var elemStyle;
        $.each(data, function (dataKey, dataValue) {
            if (dataValue.imagePath) {
                if (carouselId == 1) {
                    elemStyle = "display: block;";
                }

                console.log("Client***");
                source = $("#clients-element-template").html();
                template = Handlebars.compile(source);
                content = {
                    carouselId: carouselId,
                    elemStyle: elemStyle,
                    id: dataValue.id,
                    imagePath: "/api/image/" + dataValue.imagePath,
                    guestName: dataValue.guestName,
                    message: dataValue.message,
                    creationDate: dataValue.creationDate
                };
                html = template(content);

                //$feedbackClientsElementsWrapper.append(html);

                $carouselHolder.append(html);
                carouselId++;
                elemStyle = "";
            } else {

                source = $("#guestbook-element-template").html();
                template = Handlebars.compile(source);
                content = {
                    id: dataValue.id,
                    guestName: dataValue.guestName,
                    message: dataValue.message,
                    creationDate: dataValue.creationDate
                };
                html = template(content);

                $feedbackGuestbookElementsWrapper.append(html);
            }
        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestFeedback() {
    $.ajax({
        "url": "/api/feedback",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadFeedback,
        "error": loadFeedback
    });
}

function prev(num, arrayLength) {
    if (num == 0) {
        return arrayLength - 1; //If you want to go before 1st elem, go to last in the list
    }
    else {
        return num - 1;
    }
}
function next(num, arrayLength) {
    if (num == arrayLength - 1) {
        return 0; //If you want to go after last elem, go to the 1st elem in the list
    }
    else {
        return num + 1;
    }
}

function getVisible(elements) {
    var visibleID = -1;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].style.display == "block") {
            visibleID = i;
        }
    }
    return visibleID;
}

// direction = boolean value: true or false. If true, go to NEXT slide; otherwise go to PREV slide
function toggleSlide(event) {
    console.log("Hello : " + event.data.status);
    var direction = event.data.status;
    var elements = document.getElementsByClassName("hideable"); // gets all the "slides" in our slideshow 
    var visibleID = getVisible(elements); // Find the element that's currently displayed
    elements[visibleID].style.display = "none"; // hide the currently visible element

    var makeVisible;
    if (direction) { //if right/true (next)
        makeVisible = next(visibleID, elements.length); // get the next slide
    } else { //if left/false (previous)
        makeVisible = prev(visibleID, elements.length); // get the previous slide
    }

    elements[makeVisible].style.display = "block"; // show the previous or next elem
}

function load() {
    $body = $(document.body);
    $feedbackGuestbookElementsWrapper = $("#feedback-guestbook-elements-wrapper");
    $feedbackClientsElementsWrapper = $("#feedback-clients-elements-wrapper");
    $carouselHolder = $(".carousel-holder");

    requestFeedback();

    $backButton = $(".backButton");
    $forwardButton = $(".forwardButton");

    $backButton.click({status: false}, toggleSlide);
    $forwardButton.click({status: true}, toggleSlide);

}

$(window).ready(load);
