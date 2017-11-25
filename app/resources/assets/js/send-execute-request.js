"use strict";

$("#execute-form").submit(function(e) {
    // Get the form that was submitted
    var $form = $(this);
    // Stop the form submitting normally (i.e., don't route to action parameter)
    e.preventDefault();
    // Get the intended controller route
    var url = $form.attr("action");
    // Get csrf token from page meta-data
    var AUTH_TOKEN = $("meta[name='csrf-token']").attr("content");
    // Serialise the form inputs, add csrf token
    var data = $form.serialize() + "&_token=" + AUTH_TOKEN;
    // Post to the controller
    $.post(url, data, function(responseData) {
        var response = responseData.response;
        var numSyntaxErrors = response.numSyntaxErrors;
        var syntaxErrors = response.syntaxErrors;
        var numContextualErrors = response.numContextualErrors;
        var contextualErrors = response.contextualErrors;
        var treeNodes = response.treeNodes;
        var objectCode = response.objectCode;
        var output = response.output;
        var contextualAnimationOrder = response.contextualAnimationOrder;
        $(".program-tree").text("");
        if (numSyntaxErrors > 0) {
            $(".program-tree").append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $(".program-tree").append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $(".program-tree").append((index + 1) + ": " + syntaxError);
            });
            $(".program-tree").append("<br>");
        } else if (numContextualErrors > 0) {
            $(".program-tree").append("Number of contextual errors: " + numContextualErrors + "<br>");
            $(".program-tree").append("Contextual errors: <br>");
            $.each(contextualErrors, function(index, contextualError) {
                $(".program-tree").append((index + 1) + ": " + contextualError);
            });
            $(".program-tree").append("<br>");
        } else {
            drawTree(treeNodes, contextualAnimationOrder);
        }
        $(".object-code").text("");
        $.each(objectCode, function(index, instruction) {
            $(".object-code").append(instruction + "<br>");
        });
        $(".output").text(output);
    });
});

function drawTree(data, contextualAnimationOrder) {
    var dataMap = data.reduce(function(map, node) {
        map[node.id] = node;
        return map;
    }, {});
    var treeData = [];
    data.forEach(function(node) {
        var parent = dataMap[node.parent_id];
        if (parent) {
            (parent.children || (parent.children = [])).push(node);
        } else {
            treeData.push(node);
        }
    });

    var margin = {
        top: 35,
        right: 10,
        bottom: 35,
        left: 10
    };
    var width = 770 - margin.left - margin.right;
    var height = 800 - margin.top - margin.bottom;
    var treemap = d3.tree().size([width, height]);
    var nodes = d3.hierarchy(treeData[0]);
    nodes = treemap(nodes);
    var svg = d3.select(".program-tree")
        .append("div")
        .classed("svg-container", true)
        .append("svg")
        .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("viewBox", "0 0 770 800")
        .classed("svg-content-responsive", true);
    var g = svg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    g.selectAll(".link")
        .data(nodes.descendants().slice(1)).enter().append("path")
        .attr("class", "link").attr("d", function(d) {
            return "M" + d.x + "," + d.y +
                "C" + d.x + "," + (d.y + d.parent.y) / 2 +
                " " + d.parent.x + "," + (d.y + d.parent.y) / 2 +
                " " + d.parent.x + "," + d.parent.y;
        });
    var node = g.selectAll(".node")
        .data(nodes.descendants())
        .enter().append("g")
        .attr("class", function(d) {
            return "node" + (d.children ? " node--internal" : " node--leaf");
        }).attr("transform", function(d) {
            return "translate(" + d.x + "," + d.y + ")";
        }).attr("id", function(d) {
            return "node-" + d.data.id;
        });
    node.append("circle").attr("r", 5);
    node.append("text")
        .attr("dy", ".35em")
        .attr("y", function(d) {
            return d.children ? -20 : 20;
        })
        .style("text-anchor", "middle")
        .text(function(d) {
            return d.data.name;
        });

    $("#play-button").on("click", function() {
        animateTree(contextualAnimationOrder);
    });
}

function animateTree(animationOrder) {
    $.each(animationOrder, function(index, value) {
        d3.select("#node-" + value.id).select("circle").transition()
            .duration(500).delay(500 * index).style("fill", "red")
            .on("start", function() {
                $.each(value.explanations, function(index, value) {
                    console.log(value);
                })
            }).transition().style("fill", "white");
    });
}
