function updateDark() {
    let bDark = sessionStorage.getItem("bDark");

    if (bDark == "true") {
        $("body, .modal-content, .form-check-input").addClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p:not(.modal-message), input, label, .ai, .text-account, .text-chat, #feedback-length").addClass("text-white");
        $("nav, input").addClass("bg-dark");
        $(".chat").addClass("bg-dark").removeClass("bg-white");
        $(".date").addClass("text-white-50").removeClass("text-black-50");
        $("button").addClass("btn-dark");
        $(".modal input").addClass("border-0");
        $(".form-check-input").addClass("border-secondary");
        $(".form-check-label").addClass("bi-moon-fill").removeClass("bi-brightness-high-fill");
        $(".links img").removeClass("invert");
        $("#loader, #modal-loader, #wand, .htmx-indicator").removeClass("text-primary");
        $("#flexSwitchCheckDefault").each(function() {
            if (!$(this).is(':checked')) {
                this.checked = true;
            }
         });
    }
}

$(document).ready(function() { // when DOM is ready
    const csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
    const regex = /^(https?):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    let timeout;

    updateDark(); // set dark mode initial state

    $("#flexSwitchCheckDefault").on("change", function() {
        $("body, .modal-content, .form-check-input").toggleClass("bg-secondary");
        $("h1, h2, h3, h4, h5, p:not(.error):not(.success), input, label, .ai, .text-account, .text-chat, #feedback-length").toggleClass("text-white");
        $("nav, input").toggleClass("bg-dark");
        $(".chat").toggleClass("bg-dark").toggleClass("bg-white");
        $(".date").toggleClass("text-white-50").toggleClass("text-black-50");
        $("button").toggleClass("btn-dark");
        $(".modal input").toggleClass("border-0");
        $(".form-check-input").toggleClass("border-secondary");
        $(".form-check-label").toggleClass("bi-moon-fill").toggleClass("bi-brightness-high-fill");
        $(".links img").toggleClass("invert");
        $("#loader, #modal-loader, #wand, .htmx-indicator").toggleClass("text-primary");

        if ($("#flexSwitchCheckDefault").is(":checked")) {
            sessionStorage.setItem("bDark", "true");
        } else {
            sessionStorage.removeItem("bDark");
        }
    });

    $("#input-main").on("input keyup", function(event) {
        /*if (event.key == "Enter") {
            event.preventDefault();
        }*/

        let value = $("#input-main").val();
        let ai = $(".ai").last();

        clearTimeout(timeout);
        timeout = setTimeout(function() { // throttle input events
            if ((value.length) && ((!ai.length) || (ai.css("display") == "none"))) {
                $("#feedback-length").text(value.length + "/5000").removeClass("opacity-0");

                if ((value.toLowerCase().startsWith("http") == true) || (value.toLowerCase().indexOf("www") >= 0)) {
                    if (regex.test(value)) {
                        $("#button-summary").removeClass("disabled").removeAttr("aria-disabled");
                        $(".invalid-feedback").fadeOut(250);
                    } else {
                        $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                        $(".invalid-feedback").text("Please enter a valid URL").fadeIn(250);
                    }
                } else {
                    if (value.length >= 200) {
                        $("#button-summary").removeClass("disabled").removeAttr("aria-disabled");
                        $(".invalid-feedback").fadeOut(250);
                    } else {
                        $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                        $(".invalid-feedback").text("Please enter a minimum of 200 characters").fadeIn(250);
                    }
                }
            } else {
                $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                $(".invalid-feedback").fadeOut(250);

                $("#feedback-length").text("0/5000").addClass("opacity-0");
            }

            if ((event.key == "Enter") && (!$("#button-summary").hasClass("disabled"))) {
                $("#button-summary").trigger("click");
            }
        }, 250);
    });

    $("#button-summary").on("htmx:beforeRequest", function() {
        $(this).addClass("disabled").attr("aria-disabled", "true");
        $("#button-summary-text").addClass("opacity-0");
        $("#button-summary-spinner").removeClass("d-none").removeAttr("aria-hidden");
        $("#input-main").val(null).focus();
        $("#feedback-length").text("0/5000").addClass("opacity-0");
        $(".invalid-feedback").hide();

        $("#main").addClass("opacity-0");
        $("#intro").hide();
        $("#loader").show();
    });

    $("#button-summary").on("htmx:afterRequest", function(event) {
        if (event.detail.successful == true) {
            let div = $(".text-output").last();
            let summary = div.html();
            let scroller = $(".scroll-custom");
            let height = 0;

            for (let i = 0, l = summary.length; i <= l; i++) {
                setTimeout(function() {
                    if (i == summary.length) {
                        $(".ai").last().hide();
                        $("#button-summary-spinner").addClass("d-none").attr("aria-hidden", "true");
                        $("#button-summary-text").removeClass("opacity-0");
                        $("#input-main").trigger("input");
                    }
                    if ((i > 0) && (height < div.height())) {
                        height = div.height();
                        scroller.scrollTop(scroller[0].scrollHeight);
                    }
                    /*if (!(i % 100)) {
                        scroller.scrollTop(scroller[0].scrollHeight);
                    }*/
                    div.html(summary.slice(0, i)); //(Math.random() * 50 | 0))
                }, 15 * i);
            }
        } else {
            $(".text-output").last().text("Request from server failed.");
        }
    });

    $("#main").on("htmx:afterSettle", function() {
        $("#loader").fadeOut(250, function() {
            let scroller = $(".scroll-custom");
            scroller.scrollTop(scroller[0].scrollHeight);
            $("#main").removeClass("opacity-0");
        });
    });

    $("#wrapper-login").on("input keydown", "input", function(event) {
        if (event.key == "Enter") {
            $("#button-login, #button-register, #button-account").trigger("click");
        } else {
            $(this).parent().addClass("was-validated");
        }
    });

    $("#wrapper-login").on("htmx:beforeRequest", "#link-login, #link-register", function() {
        $("#link-login, #link-register").addClass("disabled").attr("aria-disabled", "true");

        $("#wrapper-login").addClass("opacity-0");
        $("#modal-loader").show();
    });

    $("#wrapper-login").on("htmx:beforeRequest", "#button-login, #button-register, #button-account", function(event) {
        let isValid = true;
        let successMessage;
        let errorMessage;

        $("#wrapper-login input").each(function() {
            if (($(this).prop("required")) || (($(this).attr("type")) && ($(this).attr("type") != "text"))) {
                isValid = this.checkValidity();

                if (!isValid) {
                    errorMessage = "<span class='bi bi-exclamation-triangle-fill'></span> ";
                    errorMessage += $("label[for='" + $(this).attr('id') + "']").text() + " error. " + this.validationMessage;

                    return false;
                }
            }
        });

        if (isValid) {
            $("#button-login, #button-register, #button-account").addClass("disabled").attr("aria-disabled", "true");
            $("#button-login-text, #button-register-text, #button-account-text").addClass("opacity-0");
            $("#button-login-spinner, #button-register-spinner, #button-account-spinner").removeClass("d-none").removeAttr("aria-hidden");

            $(".modal-body").addClass("opacity-0");
            $("#modal-loader").show();
        } else {
            $(".modal-message").removeClass("success, d-none").addClass("error").html(errorMessage);
            $(".field-set").addClass("was-validated");

            event.preventDefault();
            event.stopPropagation();
        }
    });

    $("#wrapper-login").on("htmx:afterSettle", function() {
        let message = $(".modal-message");
        let text = message.text();

        if (text) {
            if (text.indexOf("success") !== -1) {
                message.addClass("success").removeClass("error, d-none");
            } else if (text.indexOf("error") !== -1) {
                message.removeClass("success, d-none").addClass("error");
            }
        }

        $("#wrapper-login input").each(function() {
            if ($(this).val()) {
                $(this).parent().addClass("was-validated");
            }
        });

        $("#modal-loader").fadeOut(250, function() {
            $("#wrapper-login, .modal-body").removeClass("opacity-0");
            $("#link-login, #link-register, #link-account, #button-login, #button-register, #button-account").removeClass("disabled").removeAttr("aria-disabled");
            $("#wrapper-login, #button-login-text, #button-register-text, #button-account-text, .modal-body").removeClass("opacity-0");
            $("#button-login-spinner, #button-register-spinner, #button-account-spinner").addClass("d-none").attr("aria-hidden", "true");
        });
    });

    $("body").on("htmx:afterSettle", function() {
        updateDark();
    });

    $("body").on("htmx:configRequest", function(event) {
        event.detail.headers["accept"] = "text/html-partial";

        if (event.detail.verb !== "get") {
          event.detail.headers[csrfHeaderName] = $("meta[name='_csrf']").attr("content");
        }
    });
});

$(window).on("load", function() { // when ALL content is loaded
    $("#loader").fadeOut(750, function() {
        $("#wrapper").removeClass("opacity-0");
    });
});