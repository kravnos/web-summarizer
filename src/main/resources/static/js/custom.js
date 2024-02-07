$(document).ready(function() { // when DOM is ready
    var bDark = sessionStorage.getItem('bDark') || '';

    if (bDark == 'true') {
        $("body, .modal-content, .form-check-input").addClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").addClass("text-white");
        $("nav").addClass("bg-dark");
        $("button").addClass("btn-dark");
        $(".form-check-input").addClass("border-secondary");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");
        $("#flexSwitchCheckDefault").each(function() { this.checked = true; });
    }

    $("#flexSwitchCheckDefault").change(function() {
        $("body, .modal-content, .form-check-input").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").toggleClass("text-white");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $(".form-check-input").toggleClass("border-secondary");
        $(".dkmode-check-label").toggleClass("bi-moon-fill");
        $(".dkmode-check-label").toggleClass("bi-brightness-high-fill");

        if ($("#flexSwitchCheckDefault").is(':checked')) {
            sessionStorage.setItem('bDark','true');
        } else {
            sessionStorage.removeItem('bDark');
        }
    });
});

$(window).on("load", function() { // when ALL content is loaded
    $("#loader").fadeOut(750).queue(function() {
        $("#wrapper").removeClass("opacity-0");
    });
});