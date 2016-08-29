/* global Handlebars, cookieName */

var $body;
var $articleEditHolder;

function goBack() {
    window.history.back()
}

function evaluateSave(data, status) {
    console.log("data is : ", data, ", status is : ", status);
    if (status === "success" && data != null) {
        window.location = "/emkobarona/articles";
    } else {
        console.log("ERROR!");
    }
}

function saveArticle() {
    var articleId = window.location.href.split("/").slice(-1).pop();

    var articleTypeId = $(".article-type-id").val();
    var articleTitle = $(".article-title").val();
    var articleText = $(".article-text").val();

    $.ajax({
        "url": "/emkobaronaAPI/articles/save/" + articleId + "/" + getCookie(cookieName),
        "type": "POST",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            id: articleId,
            type_id: articleTypeId,
            title: articleTitle,
            text: articleText
        }),
        "success": evaluateSave,
        "error": evaluateSave
    });
}

function loadArticle(data, status) {
    $articleEditHolder.empty();
    console.log("data is : ", data, ", status is : ", status);

    var source = $("#article-edit-element-template").html();
    var template = Handlebars.compile(source);
    var content = {
        id: data[0].id,
        type_id: data[0].type_id,
        title: data[0].title,
        text: data[0].text,
        creationDate: data[0].creationDate,
        userId: data[0].userId
    };
    var html = template(content);
    $articleEditHolder.append(html);
}

function requestArticle() {
    var articleId = window.location.href.split("/").slice(-1).pop();
    console.log("Hey you", articleId);

    $.ajax({
        "url": "/emkobaronaAPI/article/" + articleId + "/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadArticle,
        "error": loadArticle
    });
}

function load() {
    console.log("articlesSpecific.js loaded...");
    $body = $(document.body);

    $articleEditHolder = $("#article-edit-holder");

    requestArticle();

    $body.on("click", ".save-article", saveArticle);
    $body.on("click", ".back-to-articles", goBack);
}

$(window).ready(load);
