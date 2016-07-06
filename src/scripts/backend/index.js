var pages = [
    {'name': 'Articles', 'href': '/emkobarona/articles.html', 'color': '#ee4035'},
    {'name': 'Gallery', 'href': '/emkobarona/gallery.html', 'color': '#f37736'},
    {'name': 'Tickets', 'href': '/emkobarona/ticket.html', 'color': '#fdf498'},
    {'name': 'Guestbook', 'href': '/emkobarona/guestbook.html', 'color': '#7bc043'},
    {'name': 'Flexible Sections', 'href': '/emkobarona/flexibleSections.html', 'color': '#0392cf'},
    {'name': 'Users', 'href': '/emkobarona/users.html', 'color': '#000000'}
];
var $indexNav;

function loadIndexNav() {
    for (i = 0; i < pages.length; i++) {
        console.log("> ", pages[i]);
        var source = $("#index-nav-element-template").html();
        var template = Handlebars.compile(source);
        var content = {
            name: pages[i].name,
            color: pages[i].color,
            href: pages[i].href
        };
        var html = template(content);
        $indexNav.append(html);
    }
}

function load() {
    console.log("index.js loaded...");

    $indexNav = $("#index-nav");

    loadIndexNav();
}

$(window).ready(load);
