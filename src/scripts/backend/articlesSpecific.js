/* global Handlebars, cookieName */

var $body;
var $articleEditHolder;
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
    
    var articleTypeId = $(".article-type-id").val();
    var articleTitle = $(".article-title").val();
    var articleText = $(".article-text").val();

    $.ajax({
        "url": "/emkobaronaAPI/articles/create/" + getCookie(cookieName),
        "type": "PUT",
        "headers": {"Content-Type": "application/json"},
        "data": JSON.stringify({
            id: 0,
            type_id: articleTypeId,
            title: articleTitle,
            text: articleText
        }),
        "success": evaluateSave,
        "error": evaluateSave
    });
}

function updateArticle() {
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

function saveArticleLogic() {
    if ("edit" === processType) {
        updateArticle();
    } else if ("create" === processType) {
        createArticle();
    }
}

function loadArticle(data, status) {
    $articleEditHolder.empty();

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

    processType = window.location.href.split("/")[5];

    if ("edit" === processType) {
        requestArticle();
    } else if ("create" === processType) {
        loadArticle([{
                id: "...will be automatically generated",
                type_id: "",
                title: "",
                text: "",
                creationDate: "...will be automatically generated",
                userId: "...will become your personal user id"
            }], "success");
    }

    $body.on("click", ".save-article", saveArticleLogic);
    $body.on("click", ".back-to-articles", goBack);
}

$(window).ready(load);
