$(document).ready(function() { // when DOM is ready
    const regex = /^(https?):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    let bDark = sessionStorage.getItem("bDark");

    if (bDark == "true") {
        $("body, .modal-content, .form-check-input").addClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p, #feedback-length").addClass("text-white");
        $("nav").addClass("bg-dark");
        $("button").addClass("btn-dark");
        $(".form-check-input").addClass("border-secondary");
        $(".form-check-label").toggleClass("bi-moon-fill").toggleClass("bi-brightness-high-fill");
        $("#loader, #wand, .htmx-indicator").removeClass("text-primary");
        $("#flexSwitchCheckDefault").each(function() { this.checked = true; });
    }

    $("#flexSwitchCheckDefault").on("change", function() {
        $("body, .modal-content, .form-check-input").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p, #feedback-length").toggleClass("text-white");
        $("nav").toggleClass("bg-dark");
        $("button").toggleClass("btn-dark");
        $(".form-check-input").toggleClass("border-secondary");
        $(".form-check-label").toggleClass("bi-moon-fill").toggleClass("bi-brightness-high-fill");
        $("#loader, #wand, .htmx-indicator").toggleClass("text-primary");

        if ($("#flexSwitchCheckDefault").is(":checked")) {
            sessionStorage.setItem("bDark", "true");
        } else {
            sessionStorage.removeItem("bDark");
        }
    });

    $("#input-main").on("input keyup", function(event) {
        let value = $(this).val();

        if (value.length > 0) {
            $("#feedback-length").text(value.length + "/5000").removeClass("opacity-0");

            if ((value.toLowerCase().startsWith("http") == true) || (value.toLowerCase().indexOf('www') >= 0) || (value.toLowerCase().indexOf('.c') >= 0)) {
                if (regex.test(value)) {
                    $("#summary-button").removeClass("disabled").removeAttr("aria-disabled");
                    $(".invalid-feedback").fadeOut(250);
                } else {
                    $("#summary-button").addClass("disabled").attr("aria-disabled", "true");
                    $(".invalid-feedback").text("Please enter a valid URL").fadeIn(250);
                }
            } else {
                if (value.length >= 200) {
                    $("#summary-button").removeClass("disabled").removeAttr("aria-disabled");
                    $(".invalid-feedback").fadeOut(250);
                } else {
                    $("#summary-button").addClass("disabled").attr("aria-disabled", "true");
                    $(".invalid-feedback").text("Please provide a minimum of 200 characters").fadeIn(250);
                }
            }
        } else {
            $("#summary-button").addClass("disabled").attr("aria-disabled", "true");
            $(".invalid-feedback").fadeOut(250);

            $("#feedback-length").text("0/5000").addClass("opacity-0");
        }

        if ((!$("#summary-button").hasClass("disabled")) && (event.key == "Enter")) {
            event.preventDefault();

            $("#summary-button").trigger("click");
        }
    });

    $("#summary-button").on("htmx:beforeRequest", function() {
        $(this).addClass("disabled").attr("aria-disabled", "true");
        $("#input-main").val(null).focus();
        $("#feedback-length").text($("#input-main").val().length + "/5000").addClass("opacity-0");
        $(".invalid-feedback").hide();

        $("#summary-wrapper").addClass("opacity-0");
        $("#intro").hide();
        $("#loader").show();
    });

    $("#summary-wrapper").on("htmx:afterSettle", function() {
        $("#loader").fadeOut(250, function() {
            $(".scroll-custom").scrollTop($(".scroll-custom")[0].scrollHeight);
            $("#summary-wrapper").removeClass("opacity-0");
        });
    });
});

$(window).on("load", function() { // when ALL content is loaded
    $("#loader").fadeOut(750, function() {
        $("#wrapper").removeClass("opacity-0");
    });
});