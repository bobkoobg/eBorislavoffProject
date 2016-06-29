/* global Handlebars */

var $body;
var $exercisesElementsWrapper;

function loadNews(data, status) {
    console.log("data is : " + data + ", status is : " + status);
    if (status == "success") {
        $exercisesElementsWrapper.empty();
        $.each(data, function (dataKey, dataValue) {
            var extendedText = dataValue.text.length > 255 ? 1 : 0;

            var source = $("#exercises-element-template").html();
            var template = Handlebars.compile(source);
            var content = {
                id: dataValue.id,
                title: dataValue.title,
                author: dataValue.author,
                text: Boolean(extendedText) ? dataValue.text.substring(0, 255) + " ..." : dataValue.text,
                date: dataValue.creationDate
            };
            var html = template(content);
            $exercisesElementsWrapper.append(html);

            if (Boolean(extendedText)) {
                source = $("#exercises-element-readmore-template").html();
                template = Handlebars.compile(source);

                content = {
                    path: "/exercises/" + dataValue.id
                };
                html = template(content);
                $("[data-exercisesid=" + dataValue.id + "] .text").after(html);
            }

        });
    } else {
        console.log("Failure in loadNews Frontend Index ");
        console.log("data is : " + data + ", status is : " + status);
    }
}

function requestExercises() {
    $.ajax({
        "url": "/api/exercises",
        "type": "GET",
        "headers": {"Content-Type": "application/json"},
        "data": {},
        "success": loadNews,
        "error": loadNews
    });
}

function load() {
    $body = $(document.body);
    $exercisesElementsWrapper = $("#exercisesElementsWrapper");

    requestExercises();
}

$(window).ready(load);