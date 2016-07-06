/* global Handlebars */

var $body;
var $articlesHolder;
var $articleTypesHolder;
var $articleEdit;
var $articleDelete;


function loadArticleTypes(data, status) {
    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            var source = $("#articletype-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                articletypename: dataValue.articletypename
            };
            var html = template(content);
            $articleTypesHolder.append(html);
        });
    } else {
        console.log("Failure in loadArticles Backend");
        console.log("data is : ", data, ", status is : ", status);
    }
}

function requestArticleTypes() {
    $.ajax({
        "url": "/emkobaronaAPI/articletypes/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadArticleTypes,
        "error": loadArticleTypes
    });
}

function loadArticles(data, status) {
    if (status == "success") {
        $.each(data, function (dataKey, dataValue) {
            var extendedText = dataValue.text.length > 255 ? 1 : 0;

            var source = $("#article-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                type_id: dataValue.type_id,
                title: dataValue.title,
                text: Boolean(extendedText) ? dataValue.text.substring(0, 25) + " ..." : dataValue.text,
                creationDate: dataValue.creationDate,
                userId: dataValue.userId
            };
            var html = template(content);
            $articlesHolder.append(html);
        });
    } else {
        console.log("Failure in loadArticles Backend");
        console.log("data is : ", data, ", status is : ", status);
    }
}

function requestArticles() {

    //http://stackoverflow.com/questions/10298899/how-to-send-data-in-request-body-with-a-get-when-using-jquery-ajax
    $.ajax({
        "url": "/emkobaronaAPI/articles/" + getCookie(cookieName),
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadArticles,
        "error": loadArticles
    });
}

function editArticleFunc() {
    console.log("hello!!!");
}

function load() {
    $body = $(document.body);
    console.log("articles.js loaded...");

    $articlesHolder = $("#articles-holder");
    $articleTypesHolder = $("#articletypes-holder");
    $articleEdit = $(".edit-article");
    $articleDelete = $(".delete-article");

    requestArticles();
    requestArticleTypes();
    
    $body.on("click", ".edit-article", editArticleFunc);
}

$(window).ready(load);
