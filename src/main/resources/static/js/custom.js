var regex = /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;

$(document).ready(function() { // when DOM is ready
    var bDark = sessionStorage.getItem("bDark") || "";

    if (bDark == "true") {
        $("body, .modal-content, .form-check-input").addClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").addClass("text-white");
        $("nav").addClass("bg-dark");
        $("button").addClass("btn-dark");
        $(".form-check-input").addClass("border-secondary");
        $(".form-check-label").toggleClass("bi-moon-fill").toggleClass("bi-brightness-high-fill");
        $("#loader, #wand, .htmx-indicator").removeClass("text-primary");
        $("#flexSwitchCheckDefault").each(function() { this.checked = true; });
    }

    $("#flexSwitchCheckDefault").on("change", function() {
        $("body, .modal-content, .form-check-input").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p").toggleClass("text-white");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $(".form-check-input").toggleClass("border-secondary");
        $(".form-check-label").toggleClass("bi-moon-fill").toggleClass("bi-brightness-high-fill");
        $("#loader, #wand, .htmx-indicator").toggleClass("text-primary");

        if ($("#flexSwitchCheckDefault").is(":checked")) {
            sessionStorage.setItem("bDark","true");
        } else {
            sessionStorage.removeItem("bDark");
        }
    });

    $("#url").on("input keyup", function(event) {
        if (regex.test($(this).val())) {
            $("#summary-button").removeClass("disabled").removeAttr("aria-disabled");
            $(".invalid-feedback").hide();
        } else {
            $("#summary-button").addClass("disabled").attr("aria-disabled", "true");
            $(".invalid-feedback").show();
        }

        if ((!$("#summary-button").hasClass("disabled")) && (event.key == "Enter")) {
            event.preventDefault();
            $("#summary-button").click();
        }
    });

    $("#summary-button").on("click", function() {
        $(this).addClass("disabled").attr("aria-disabled", "true");
        $("#url").val("");
        $("#intro").fadeOut(750);
        $(".scroll-custom").stop(true, true).delay(750).queue(function() {
            $(this).scrollTop($(this)[0].scrollHeight).dequeue();
        });
    });

});

$(window).on("load", function() { // when ALL content is loaded
    $("#loader").fadeOut(750).queue(function() {
        $("#wrapper").removeClass("opacity-0").dequeue();
    });
});