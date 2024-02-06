$(document).ready(function () {
    var bDark = sessionStorage.getItem('bDark') || '';

    if (bDark == 'true') {
        $("body, .modal-content").addClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").addClass("text-white");
        $("nav").addClass("bg-dark");
        $("button").addClass("btn-dark");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");
        $("#flexSwitchCheckDefault").each(function() { this.checked = true; });
    }

    $("#flexSwitchCheckDefault").change(function() {
        $("body, .modal-content").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").toggleClass("text-white");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");

        if ($("#flexSwitchCheckDefault").is(':checked')) {
            sessionStorage.setItem('bDark','true');
        } else {
            sessionStorage.removeItem('bDark');
        }
    });
});