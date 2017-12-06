$("#generation-button").on("click", function() {
    $(".right-contextual-container").hide();
    $(".right-generation-container").show();
});

$("#contextual-button").on("click", function() {
    $(".right-contextual-container").show();
    $(".right-generation-container").hide();
});
