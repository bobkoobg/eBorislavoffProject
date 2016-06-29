/* global Handlebars */

var $body;
var $newsElementWrapper;

function loadNews(data, status) {

    if (status == "success") {
        $newsElementWrapper.empty();

        var source = $("#news-element-template").html();
        var template = Handlebars.compile(source);
        var content = {
            id: data.id,
            title: data.title,
            author: data.author,
            text: data.text,
            date: data.creationDate
        };
        var html = template(content);
        $newsElementWrapper.append(html);

        var backLinkHtml = "<a class=\"readmore\" href=\"/news\">...back to news</a>"
        $newsElementWrapper.before(backLinkHtml);
        $newsElementWrapper.after(backLinkHtml);
    } else {
        window.location = "/notfound";
    }
}

function requestNews() {
    var index = window.location.href.split("/").slice(-1).pop();
    $.ajax({
        "url": "/api/news/" + index,
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadNews,
        "error": loadNews
    });
}

function load() {
    $body = $(document.body);
    $newsElementWrapper = $("#newsElementWrapper");
    console.log(window.location.href);
    requestNews();
}

$(window).ready(load);