/* global Handlebars */

var $body;
var $newsElementsWrapper;

function loadNews(data, status) {

    if (status == "success") {
        $newsElementsWrapper.empty();
        $.each(data, function (dataKey, dataValue) {
            var extendedText = dataValue.text.length > 255 ? 1 : 0;

            var source = $("#news-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                title: dataValue.title,
                author: dataValue.author,
                text: Boolean(extendedText) ? dataValue.text.substring(0, 255) + " ..." : dataValue.text,
                date: dataValue.creationDate
            };
            var html = template(content);
            $newsElementsWrapper.append(html);

            if (Boolean(extendedText)) {
                source = $("#news-element-readmore-template").html();
                template = Handlebars.compile(source);

                content = {
                    path: "/news/" + dataValue.id
                };
                html = template(content);
                $("[data-newsid=" + dataValue.id + "] .text").after(html);
            }

        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
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
    $body = $(document.body);
    $newsElementsWrapper = $("#newsElementsWrapper");

    requestNews();
}

$(window).ready(load);