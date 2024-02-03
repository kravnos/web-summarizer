$(document).ready(function () {
    $("#flexSwitchCheckDefault").change(function() {
        $("body").toggleClass("bg-secondary");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $("h1").toggleClass("text-white");
        $("h2").toggleClass("text-white");
        $("h3").toggleClass("text-white");
        $("p").toggleClass("text-white");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");
    });
});