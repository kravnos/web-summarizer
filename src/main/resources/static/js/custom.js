$(document).ready(function () {
    $("#flexSwitchCheckDefault").change(function() {
        $("body, .modal-content").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").toggleClass("text-white");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");
    });
});