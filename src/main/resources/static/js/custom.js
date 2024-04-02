/*
    when DOM is ready
*/
$(document).ready(function() {
    const regex = /^(https?):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    const sleep = 250;
    const longSleep = 750;
    const messageTimer = 6666;
    const minLength = 200;
    const maxLength = 5000;
    let timeout;
    let isDark = sessionStorage.getItem("isDark") || "false";           // sessionStorage must be a string, cannot be boolean false
    let isLoggedIn = sessionStorage.getItem("isLoggedIn") || "false";
    let isProUser = sessionStorage.getItem("isProUser") || "false";

    /*
        Dark Mode
    */
    if (isDark == "true") {
        $("body").attr("data-bs-theme", "dark");

        $("#flexSwitchCheckDefault").each(function() {
            if (!$(this).is(":checked")) {
                this.checked = true;
            }
        });
    }

    $("#flexSwitchCheckDefault").on("change", function() {
        if ($(this).is(":checked")) {
            $("body").attr("data-bs-theme", "dark");
            sessionStorage.setItem("isDark", "true");
        } else {
            $("body").removeAttr("data-bs-theme");
            sessionStorage.setItem("isDark", "false");
        }
    });

    /*
        Main
    */
    $("#input-main").on("input keyup", function(event) {
        let val = $("#input-main").val();
        let ai = $(".ai").last();

        clearTimeout(timeout);
        timeout = setTimeout(function() { // throttle input events
            if ((val.length) && ($("#loader").css("display") == "none") && ((!ai.length) || (ai.css("display") == "none"))) {
                $("#feedback-length").text(val.length + "/" + maxLength).removeClass("opacity-0");

                if ((val.toLowerCase().startsWith("http") == true) || (val.toLowerCase().indexOf("www") >= 0)) {
                    if (regex.test(val)) {
                        $("#button-summary").removeClass("disabled").removeAttr("aria-disabled");
                        $(".invalid-feedback").fadeOut(sleep);
                    } else {
                        $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                        $(".invalid-feedback").text("Please enter a valid URL").fadeIn(sleep);
                    }
                } else {
                    if (val.length >= minLength) {
                        $("#button-summary").removeClass("disabled").removeAttr("aria-disabled");
                        $(".invalid-feedback").fadeOut(sleep);
                    } else {
                        $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                        $(".invalid-feedback").text("Please enter a minimum of " + minLength + " characters").fadeIn(sleep);
                    }
                }
            } else {
                $("#button-summary").addClass("disabled").attr("aria-disabled", "true");
                $(".invalid-feedback").fadeOut(sleep);

                $("#feedback-length").text("0/" + maxLength).addClass("opacity-0");
            }

            if ((event.key == "Enter") && (!$("#button-summary").hasClass("disabled"))) {
                $("#button-summary").trigger("click");
            }
        }, sleep);
    });

    $("#button-summary").on("htmx:beforeRequest", function() {
        $(this).addClass("disabled").attr("aria-disabled", "true");
        $("#button-summary-text").addClass("opacity-0");
        $("#button-summary-spinner").removeClass("d-none").removeAttr("aria-hidden");
        $("#input-main").val(null).focus();
        $("#feedback-length").text("0/" + maxLength).addClass("opacity-0");
        $(".invalid-feedback").hide();

        $("#main").addClass("opacity-0");
        $("#intro").hide();
        $("#loader").show();
    });

    $("#button-summary").on("htmx:afterRequest", function(event) {
        let div = $(".text-output").last();

        if (event.detail.successful == true) {
            const typeSpeed = 15;
            let summary = div.html();
            let scroller = $(".scroll-custom");
            let height = 0;

            for (let i = 0, l = summary.length; i <= l; i++) {
                setTimeout(function() {
                    if (i == summary.length) {
                        $(".ai").hide();
                        $("#button-summary-text").removeClass("opacity-0");
                        $("#button-summary-spinner").addClass("d-none").attr("aria-hidden", "true");
                        $("#input-main").trigger("input");
                    }
                    if ((i > 0) && (height < div.height())) {
                        height = div.height();
                        scroller.scrollTop(scroller[0].scrollHeight);
                    }

                    div.html(summary.slice(0, i));
                }, typeSpeed * i);
            }
        } else {
            div.text("Error. Request from server failed.");
            $("#main").trigger("htmx:afterSettle");
        }
    });

    $("#main").on("htmx:afterSettle", function() {
        $("#loader").fadeOut(sleep, function() {
            let scroller = $(".scroll-custom");
            scroller.scrollTop(scroller[0].scrollHeight);
            $("#main").removeClass("opacity-0");
        });
    });

    /*
        Modals
    */
    $("#wrapper-login").on("input keydown", "input", function(event) {
        if (event.key == "Enter") {
            $("#wrapper-login .btn-primary.btn-request").trigger("click");
        } else {
            $(this).parent().addClass("was-validated");
        }
    });

    $("#wrapper-login").on("htmx:beforeRequest", ".link-request", function() {
        $("#wrapper-login .link-request").addClass("disabled").attr("aria-disabled", "true");

        $("#wrapper-login").addClass("opacity-0");
        $("#modal-login-loader").show();
    });

    $("#wrapper-login").on("htmx:beforeRequest", ".btn-request", function(event) {
        let isValid = true;
        let successMessage;
        let errorMessage;

        $("#wrapper-login input").each(function() {
            let element = $(this);

            if ((element.prop("required")) || ((element.attr("type")) && (element.attr("type") != "text"))) {
                isValid = this.checkValidity();

                if (!isValid) {
                    element.focus();

                    errorMessage = "<span class='bi bi-exclamation-triangle-fill'></span> ";
                    errorMessage += $("label[for='" + element.attr('id') + "']").text() + " error. " + this.validationMessage;

                    return false;
                }
            }
        });

        if ((isValid) || (this.id == "button-password")) {
            $("#wrapper-login .btn-request").addClass("disabled").attr("aria-disabled", "true");
            $("#wrapper-login .button-text, #wrapper-login .modal-body").addClass("opacity-0");
            $("#wrapper-login .button-spinner").removeClass("d-none").removeAttr("aria-hidden");
            $("#modal-login-loader").show();
        } else {
            let element = $("#modal-message");

            if (element.length > 0) {
                element.removeClass("success, d-none").addClass("error").html(errorMessage);
            } else {
                $("#modal-message-success, #modal-message-error").remove();
                $("#wrapper-message").html("<p id='modal-message' class='px-4 py-2 d-none'></p>");
                $("#modal-message").removeClass("success, d-none").addClass("error").html(errorMessage);
            }

            clearTimeout(timeout);
            $("#wrapper-message").css({"display": "none"}).fadeIn(longSleep, function() {
                timeout = setTimeout(function() {
                    $("#wrapper-message").fadeOut(longSleep);
                }, messageTimer);
            });

            $("#wrapper-login .field-set").addClass("was-validated");

            event.preventDefault();
            event.stopPropagation();
        }
    });

    $("#wrapper-login").on("htmx:afterRequest", function(event) {
        let successMessage;
        let errorMessage;

        if (event.detail.successful == true) {
            let inputs = $("#wrapper-login input");
            inputs.first().focus();

            $(inputs.get().reverse()).each(function() {
                let element = $(this);

                if (element.val()) {
                    element.parent().addClass("was-validated");
                } else if (element.prop("required")) {
                    element.focus();
                }
            });
        } else {
            errorMessage = "<span class='bi bi-exclamation-triangle-fill'></span> ";
            errorMessage += "Error. Request from server failed.";

            $("#modal-message").removeClass("success, d-none").addClass("error").html(errorMessage);
            $("#wrapper-login").trigger("htmx:afterSettle");
        }
    });

    $("#wrapper-login").on("htmx:afterSettle", function() {
        $("#modal-login-loader").fadeOut(sleep, function() {
            $("#wrapper-login, #wrapper-login .modal-body, #wrapper-login .button-text").removeClass("opacity-0");
            $("#wrapper-login .link-request, #wrapper-login .btn-request").removeClass("disabled").removeAttr("aria-disabled");
            $("#wrapper-login .button-spinner").addClass("d-none").attr("aria-hidden", "true");

            clearTimeout(timeout);
            $("#wrapper-message").css({"display": "none"}).fadeIn(longSleep, function() {
                timeout = setTimeout(function() {
                    $("#wrapper-message").fadeOut(longSleep);
                }, messageTimer);
            });
        });
    });

    /*
        Session Data
    */
    $("body").on("htmx:beforeRequest", ".nav-request, #link-account", function(event) {
        $("body").attr("data-ws-path", $(this).data("ws-path"));
    });

    $("#wrapper-login").on("htmx:afterRequest", function(event) {
        if (event.detail.successful == true) {
            $("#wrapper-login .btn-primary.btn-request, #wrapper-login .link-request").each(function() {
                let login = $(this).data("ws-login");
                let pro = $(this).data("ws-pro");

                if (login != null) {
                    $("body").attr("data-ws-login", login);
                    sessionStorage.setItem("isLoggedIn", login);
                }
                if (pro != null) {
                    $("body").attr("data-ws-pro", pro);
                    sessionStorage.setItem("isProUser", pro);
                }
            });

            updateNavbar();
        }
    });

    /*
        Navbar
    */
    updateNavbar(); // initial state

    $("#wrapper-login").on("click", "#button-logout", function() {
        $("body").attr("data-ws-login", "false");
        sessionStorage.setItem("isLoggedIn", "false");
        setTimeout("redirectHome()", longSleep);
        updateNavbar();
    });

    $("#wrapper-login").on("htmx:beforeRequest", "#button-cancel", function() {
        $("body").attr("data-ws-pro", "false");
        sessionStorage.setItem("isProUser", "false");
        updateNavbar();
    });

    /*
        CSRF Security Tokens
    */
    $("body").on("htmx:configRequest", function(event) {
        event.detail.headers["accept"] = "text/html-partial";

        if (event.detail.verb != "get") {
            event.detail.headers[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");
        }
    });
});

/*
    when ALL content is loaded
*/
$(window).on("load", function() {
    $("#loader").fadeOut(750, function() {
        $("#wrapper-page").removeClass("opacity-0");
    });
});

/*
    Helpers
*/
function getPath() {
    return $("body").attr("data-ws-path");
}

function getIsLoggedIn() {
    return sessionStorage.getItem("isLoggedIn");
}

function getIsProUser() {
    return sessionStorage.getItem("isProUser");
}

function redirectHome() {
    window.location.href = "/";
}

function updateNavbar() {
    if (getIsLoggedIn() == "true") {
        $("#nav-login").text("Account");

        if (getIsProUser() == "true") {
            $("#nav-pro .bi").removeClass("bi-lock-fill").addClass("bi-unlock-fill");
        } else {
            $("#nav-pro .bi").removeClass("bi-unlock-fill").addClass("bi-lock-fill");
        }
    } else {
        $("#nav-login").text("Login");
        $("#nav-pro .bi").removeClass("bi-unlock-fill").addClass("bi-lock-fill");
    }
}