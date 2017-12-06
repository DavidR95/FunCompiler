$("#generation-button").on("click", function() {
    $(".right-contextual-container").hide();
    $(".right-generation-container").css("display", "table");
});

$("#contextual-button").on("click", function() {
    $(".right-contextual-container").css("display", "table");
    $(".right-generation-container").hide();
});
