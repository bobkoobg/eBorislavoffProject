/* global Handlebars */

var $body;
var $newsWrapper;

function loadNews(data, status) {
    console.log("Failure in loadNews Frontend Index ");
    console.log("data is : " + data + ", status is : " + status);

    $newsWrapper.empty();
    $.each(data, function (dataKey, dataValue) {

        var source = $("#news-element-template").html();
        var template = Handlebars.compile(source);
        var content = {
            id: dataValue.id,
            title: dataValue.title,
            author: dataValue.userId + " < find user by id ..",
            text: dataValue.text,
            date: dataValue.creationDate
        };
        var html = template(content);
        $newsWrapper.append(html);

    });
}

function requestNews() {
    $.ajax({
        "url": "/api/news",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadNews,
        "error": loadNews
    });
}

function load() {
    console.log("news.js loaded...");

    $body = $(document.body);
    $newsWrapper = $("#newsWrapper");

    //displaySongs("stuff");

    requestNews();
}

$(window).ready(load);