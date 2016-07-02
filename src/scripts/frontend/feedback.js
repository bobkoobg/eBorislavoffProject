/* global Handlebars */

var $body;
var $feedbackGuestbookElementsWrapper;
var $feedbackClientsElementsWrapper;
var $backButton;
var $forwardButton;
var $carouselHolder;
var switching;
var $pauseButton;
var $restartButton;
var $firstButton;
var $lastButton;
var $formName;
var $formMessage;
var $contactStatus;
var $popUp;
var $close;

function loadFeedback(data, status) {

    if (status == "success") {
        var source;
        var template;
        var content;
        var html;
        var carouselId = 1;
        var elemStyle;
        $.each(data, function (dataKey, dataValue) {
            if (!($("[data-clientmid='" + dataValue.id + "']").length > 0
                    || $("[data-guestmid='" + dataValue.id + "']").length > 0)) {
                if (dataValue.imagePath) {
                    if (carouselId == 1) {
                        elemStyle = "display: block;";
                    } else {
                        elemStyle = "display: none;";
                    }

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
                        ip: dataValue.ip,
                        creationDate: dataValue.creationDate
                    };
                    html = template(content);

                    $feedbackGuestbookElementsWrapper.append(html);
                }
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
    var direction = event.data.direction;
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

function stopSwitching() {
    clearInterval(switching);
}

function startSwitching() {
    switching = window.setInterval(function () {
        $forwardButton.trigger("click");
    }, 3000);
}

function goToEdge(event) {
    var direction = event.data.direction;
    var elements = document.getElementsByClassName("hideable");

    var visibleID = getVisible(elements);

    elements[visibleID].style.display = "none"; //hides the currently visible item
    if (!direction) {
        elements[0].style.display = "block"; //shows the first item
    } else {
        elements[elements.length - 1].style.display = "block"; //shows the last item
    }
}

function closePopup() {
    $popUp.fadeOut();

    $formName.val("");
    $formMessage.val("");
}

function evaluateFeedbackSubmittion(data, status) {
    console.log("data is : " + data + ", status is : " + status);

    if (status == "success") {
        $(".clientName").text(data.guestName);
        $(".clientMessage").text(data.message);
        $(".clientDate").text(data.creationDate);
        $(".clientIP").text(data.ip);

        $popUp.fadeIn();
    } else {
        $formMessage.text("Internal error, status : " + status + ". Please, try again later.");
    }
}

function submitFeedback() {
    console.log("Ready to make request");

    $.ajax({
        "url": "/api/submitFeedback",
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            'guestName': $formName.val(),
            'message': $formMessage.val(),
            'secret': "secret2"
        }),
        "success": evaluateFeedbackSubmittion,
        "error": evaluateFeedbackSubmittion
    });
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function basicCheck() {
    var name = $formName.val();
    var message = $formMessage.val();

    if (!name) {
        alert("Please write your name in the form.");
        return false;
    }
    if (message.length <= 5) {
        alert("Please write a meaningful message for your form. (More than 5 symbols)");
        return false;
    }

    submitFeedback();
}

function breakSubmitRedirect() {
    basicCheck();
    return false;
}

function load() {
    $body = $(document.body);
    $feedbackGuestbookElementsWrapper = $("#feedback-guestbook-elements-wrapper");
    $feedbackClientsElementsWrapper = $("#feedback-clients-elements-wrapper");
    $carouselHolder = $(".carousel-holder");

    requestFeedback();
    //Request feedback every 30sec. to get if there is something new
    window.setInterval(function () {
        requestFeedback();
    }, 30000);

    $backButton = $(".backButton");
    $forwardButton = $(".forwardButton");
    $pauseButton = $(".pauseButton");
    $restartButton = $(".restartButton");
    $firstButton = $(".firstButton");
    $lastButton = $(".lastButton");

    $backButton.click({direction: false}, toggleSlide);
    $forwardButton.click({direction: true}, toggleSlide);

    $pauseButton.click(stopSwitching);
    $restartButton.click(startSwitching);

    $firstButton.click({direction: false}, goToEdge);
    $lastButton.click({direction: true}, goToEdge);

    startSwitching();

    $formName = $("#feedback-form").find("input[name='name']");
    $formMessage = $(".formMessage");
    $contactStatus = $(".feedback-form-status");
    $popUp = $(".submittion-confirmation-popupwrapper");
    $close = $(".close");

    $("#feedback-form-button").removeAttr("disabled");
    $('#feedback-form').submit(breakSubmitRedirect);
    $body.on("click", ".close", closePopup);

}

$(window).ready(load);
