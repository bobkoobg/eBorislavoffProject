/* global Handlebars, cookieName */

var $body;
var $guestbookCreateEditHolder;
var processType;

function goBack() {
    window.history.back()
}

function evaluateSave(data, status) {
    if (status === "success" && data != null) {
        window.location = "/emkobarona/articles";
    } else {
        console.log("ERROR!");
    }
}

function createArticle() {
    console.log("You are trying to save dude");

//    var articleTypeId = $(".article-type-id").val();
//    var articleTitle = $(".article-title").val();
//    var articleText = $(".article-text").val();
//
//    $.ajax({
//        "url": "/emkobaronaAPI/articles/create/" + getCookie(cookieName),
//        "type": "PUT",
//        "headers": {"Content-Type": "application/json"},
//        "data": JSON.stringify({
//            id: 0,
//            type_id: articleTypeId,
//            title: articleTitle,
//            text: articleText
//        }),
//        "success": evaluateSave,
//        "error": evaluateSave
//    });
}

//function updateArticle() {
//    var articleId = window.location.href.split("/").slice(-1).pop();
//
//    var articleTypeId = $(".article-type-id").val();
//    var articleTitle = $(".article-title").val();
//    var articleText = $(".article-text").val();
//
//    $.ajax({
//        "url": "/emkobaronaAPI/articles/save/" + articleId + "/" + getCookie(cookieName),
//        "type": "POST",
//        "headers": {"Content-Type": "application/json"},
//        "data": JSON.stringify({
//            id: articleId,
//            type_id: articleTypeId,
//            title: articleTitle,
//            text: articleText
//        }),
//        "success": evaluateSave,
//        "error": evaluateSave
//    });
//}

function saveArticleLogic() {
    if ("edit" === processType) {
        //updateArticle();
    } else if ("create" === processType) {
        console.log("Save article");
        createArticle();
    }
}

function loadArticle(data, status) {
    $guestbookCreateEditHolder.empty();

    var source = $("#guestbook-create-edit-element-template").html();
    var template = Handlebars.compile(source);
    var content = {
        gbid: data[0].gbid,
        guestName: data[0].guestName,
        message: data[0].message,
        imagePath: data[0].imagePath,
        ip: data[0].ip,
        creationDate: data[0].creationDate
    };

    var html = template(content);
    $guestbookCreateEditHolder.append(html);
}

//function requestArticle() {
//    var articleId = window.location.href.split("/").slice(-1).pop();
//
//    $.ajax({
//        "url": "/emkobaronaAPI/article/" + articleId + "/" + getCookie(cookieName),
//        "type": "GET",
//        "headers": {"Content-Type": "application/json"},
//        "data": {},
//        "success": loadArticle,
//        "error": loadArticle
//    });
//}

function load() {
    console.log("articlesSpecific.js loaded...");
    $body = $(document.body);

    $guestbookCreateEditHolder = $("#guestbook-create-edit-holder");

    processType = window.location.href.split("/")[5];

    if ("edit" === processType) {
        //requestArticle();
    } else if ("create" === processType) {
        loadArticle([{
                gbid: "...will be automatically generated",
                guestName: "",
                message: "",
                imagePath: "",
                ip: "...will be automatically generated",
                creationDate: "...will be automatically generated"
            }], "success");
    }

    $body.on("click", ".save-guestbook", saveArticleLogic);
    $body.on("click", ".back-to-guestbook", goBack);
}

$(window).ready(load);
