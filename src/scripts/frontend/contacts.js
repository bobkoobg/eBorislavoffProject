var $body;
var $formName;
var $formEmail;
var $formTitle;
var $formMessage;
var $contactStatus;
var $popUp;
var $close;
var googleReCAPTCHA;

function closePopup() {
    $popUp.fadeOut();

    $formName.val("");
    $formEmail.val("");
    $formTitle.val("");
    $formMessage.val("");

    window.location = "/";
}

function evaluateContactSubmittion(data, status) {

    if (status == "success") {
        $(".clientName").text(data.sender_name);
        $(".clientEmail").text(data.sender_email);
        $(".clientTitle").text(data.title);
        $(".clientMessage").text(data.message);
        $(".clientDate").text(data.creationDate);
        $(".clientStatus").text(data.status);

        $popUp.fadeIn();
        $formMessage.fadeOut();
    } else {
        $formMessage.text("Internal error, status : " + status + ". Please, try again later.");
        $formMessage.fadeIn();
    }
}

function submitContact() {

    $.ajax({
        "url": "/api/submitContact",
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            'sender_name': $formName.val(),
            'sender_email': $formEmail.val(),
            'title': $formTitle.val(),
            'message': $formMessage.val(),
            'secret': googleReCAPTCHA
        }),
        "success": evaluateContactSubmittion,
        "error": evaluateContactSubmittion
    });
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function basicCheck(e) {
    e.preventDefault();
    googleReCAPTCHA = grecaptcha.getResponse();

    var name = $formName.val();
    var email = $formEmail.val();
    var title = $formTitle.val();
    var message = $formMessage.val();

    if (!name) {
        alert("Please write your name in the form.");
        return false;
    }
    if (!email || !validateEmail(email)) {
        alert("Please write a correct e-mail address.");
        return false;
    }
    if (title.length <= 5) {
        alert("Please write a meaningful title for your form. (More than 5 symbols)");
        return false;
    }
    if (message.length <= 10) {
        alert("Please write a meaningful message for your form. (More than 10 symbols)");
        return false;
    }

    submitContact();
}

function load() {
    $body = $(document.body);
    $formName = $("#contact-form").find("input[name='name']");
    $formEmail = $("#contact-form").find("input[name='email']");
    $formTitle = $("#contact-form").find("input[name='title']");
    $formMessage = $(".formMessage");
    $contactStatus = $(".contact-form-status");
    $popUp = $(".submittion-confirmation-popupwrapper");
    $close = $(".close");

    $("#contact-form-button").removeAttr("disabled");
    $('#contact-form').submit(basicCheck);
    $body.on("click", ".close", closePopup);

}

$(window).ready(load);
