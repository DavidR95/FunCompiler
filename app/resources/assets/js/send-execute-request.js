"use strict";

var currentNodeIndex;
var is_playing;

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
        var contextualNodeOrder = response.contextualNodeOrder;
        $(".program-tree-container").text("");
        if (numSyntaxErrors > 0) {
            $(".program-tree-container").append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $(".program-tree-container").append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $(".program-tree-container").append((index + 1) + ": " + syntaxError);
            });
            $(".program-tree-container").append("<br>");
        } else {
            drawTree(treeNodes, contextualNodeOrder);
        }
    }).fail(function(responseData) {
        alert(responseData.responseJSON.errors.program);
    });
});

function drawTree(data, contextualNodeOrder) {
    currentNodeIndex = -1;
    is_playing = false;

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
    var width = 800 - margin.left - margin.right;
    var height = 650 - margin.top - margin.bottom;
    var treemap = d3.tree().size([width, height]);
    var nodes = d3.hierarchy(treeData[0]);
    nodes = treemap(nodes);
    var svg = d3.select(".program-tree-container")
        .append("div")
        .classed("svg-container", true)
        .append("svg")
        .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("viewBox", "0 0 800 650")
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
        }).attr("data-name", function(d) {
            return d.data.name;
        });
    node.append("rect")
        .attr("x", -25)
        .attr("y", -12.5)
        .attr("width", 50)
        .attr("height", 25);
    node.append("text")
        .attr("dy", ".35em")
        .style("text-anchor", "middle")
        .text(function(d) {
            var name = d.data.name;
            if (name.length <= 5)
                return name;
            else
                return name.substring(0, 5) + "...";
        });

    $("#play-button").on("click", function() {
        play(contextualNodeOrder);
    });
    $("#pause-button").on("click", function() {
        pause(contextualNodeOrder);
    });
    $("#forward-button").on("click", function() {
        forward(contextualNodeOrder);
    });
    $("#reverse-button").on("click", function() {
        reverse(contextualNodeOrder);
    });
}

function animateNode(node, currentNode, delayOffset, numNodes) {
    d3.select("#node-" + node.id).select("rect").transition()
        .duration(500).delay(delayOffset * 1000).style("fill", "yellow")
        .on("start", function() {
            currentNodeIndex = currentNode;
            $(".type-table tbody").text("");
            $(".explanations").html("<p>Explanations</p>");
            $.each(node.typeTable, function(index, tableEntry) {
                $(".type-table tbody").append("<tr><td>" + tableEntry.scope +
                                             "</td><td>" + tableEntry.id +
                                             "</td><td>" + tableEntry.type +
                                             "</td></tr>");
            });
            $(".explanations").append("<b>Node: " + $("#node-"+node.id).data("name") + "</b><br>");
            $.each(node.explanations, function(index, explanation) {
                $(".explanations").append(explanation + "<br>");
            });
        }).on("end", function() {
            if (currentNode === numNodes-1)
                is_playing = false;
        }).transition().style("fill", "white");
}

function animateTree(nodeOrder) {
    currentNodeIndex = (currentNodeIndex == -1 ? 0 : currentNodeIndex);
    for (var i = currentNodeIndex, j = 0; i < nodeOrder.length; i++, j++) {
        var node = nodeOrder[i];
        animateNode(node, i, j, nodeOrder.length);
    }
}

function play(nodeOrder) {
    is_playing = true;
    $("#play-button").hide();
    $("#pause-button").show();
    animateTree(nodeOrder);
}

function pause(nodeOrder) {
    var node = nodeOrder[currentNodeIndex];
    is_playing = false;
    $("#play-button").show();
    $("#pause-button").hide();
    d3.selectAll("rect").interrupt();
    d3.select("#node-" + node.id).select("rect")
        .transition().style("fill", "yellow");
}

function forward(nodeOrder) {
    if (is_playing)
        pause(nodeOrder);
    var node = nodeOrder[currentNodeIndex+1];
    animateNode(node, currentNodeIndex+1, 0, nodeOrder.length);
}

function reverse(nodeOrder) {
    if (is_playing)
        pause(nodeOrder);
    var node = nodeOrder[currentNodeIndex-1];
    animateNode(node, currentNodeIndex-1, 0, nodeOrder.length);
}
