/* global Handlebars */

var $body;
var $exercisesElementWrapper;

function loadNews(data, status) {
    console.log("data is : " + data + ", status is : " + status);

    if (status == "success") {
        $exercisesElementWrapper.empty();

        var source = $("#exercises-element-template").html();
        var template = Handlebars.compile(source);
        var content = {
            id: data.id,
            title: data.title,
            author: data.author,
            text: data.text,
            date: data.creationDate
        };
        var html = template(content);
        $exercisesElementWrapper.append(html);

        var backLinkHtml = "<a class=\"readmore\" href=\"/exercises\">...back to exercises</a>"
        $exercisesElementWrapper.before(backLinkHtml);
        $exercisesElementWrapper.after(backLinkHtml);
    } else {
        window.location = "/notfound";
    }
}

function requestNews() {
    var index = window.location.href.split("/").slice(-1).pop();
    $.ajax({
        "url": "/api/exercises/" + index,
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadNews,
        "error": loadNews
    });
}

function load() {
    $body = $(document.body);
    $exercisesElementWrapper = $("#exercisesElementWrapper");
    console.log(window.location.href);
    requestNews();
}

$(window).ready(load);
