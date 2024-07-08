function manualCarouselNavigation(slideIndex) {
    $('#big-images').carousel(slideIndex);
}

$(document).ready(function () {
    $('#big-images').carousel({
        interval: false
    });
});