/* ==========================================================================
 * codeAnimation.js
 *
 * Build and draw the AST using the D3 library.
 *
 * Define the logic used to traverse/animate the resulting AST.
 * ========================================================================== */

// Import the D3 node module
var d3 = require('d3');
// Import CodeTemplates module
var CodeTemplates = require('./codeTemplates.js');

var CodeAnimation = module.exports = {
    // Draw the AST gives the tree nodes
    drawTree: function(data) {
        var treeData = mapData(data);
        var marginLeft = 10;
        var marginTop = 35
        var width = 800 - (marginLeft * 2);
        var height = 650 - (marginTop * 2);
        var treemap = d3.tree().size([width, height]);
        var nodes = d3.hierarchy(treeData[0]);
        nodes = treemap(nodes);
        var svg = d3.select(".program-tree-container")
            .html("")
            .append("div")
            .classed("svg-container", true)
            .append("svg")
            .attr("preserveAspectRatio", "none")
            .attr("viewBox", "0 0 800 650")
        var g = svg.append("g")
            .attr("transform", "translate(" + marginLeft + "," + marginTop + ")");
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
            }).attr("data-node-name", function(d) {
                return d.data.nodeName;
            }).attr("data-node-value", function(d) {
                return d.data.nodeValue;
            });
        node.append("text")
            .attr("dy", ".35em")
            .style("text-anchor", "middle")
            .style("font-size", "0.75em")
            .text(function(d) {
                var name = d.data.nodeValue;
                if (name.length <= 5)
                    return name;
                else
                    return name.substring(0, 5) + "...";
            });
        node.each(function(){
            var node = d3.select(this);
            var bBox = node.select("text").node().getBBox();
            node.insert("rect", ":first-child")
                .attr("x", bBox.x - 3)
                .attr("y", bBox.y - 3)
                .attr("height", bBox.height + 6)
                .attr("width", bBox.width + 6);
        });
    },
    initialise: function(executionType, executionNodeOrder) {
        pause();
        previousNode = null;
        currentNodeIndex = -1;
        showGenerationAnimation = (executionType === "cg") ? true : false;
        if (showGenerationAnimation) {
            $(".controls-container span").html("Code Generation");
            $("#display-contextual-container").hide();
            $("#display-generation-container").show();
        } else {
            $(".controls-container span").html("Contextual Analysis");
            $("#display-generation-container").hide();
            $("#display-contextual-container").show();
        }
        nodeOrder = executionNodeOrder
    },
    highlightFirstNode: function() {
        var node = nodeOrder[currentNodeIndex+1];
        animateNode(node, true, 0);
    }
}

var nodeOrder;
var currentNodeIndex;
var is_playing;
var showGenerationAnimation;
var previousNode;

$("#play-button").on("click", function() {
    play();
});
$("#pause-button").on("click", function() {
    pause();
});
$("#forward-button").on("click", function() {
    forward();
});
$("#reverse-button").on("click", function() {
    reverse();
});

// Modifies the array index to be the ID of the node
function mapData(data) {
    var dataMap = data.reduce(function(map, node) {
        map[node.id] = node;
        return map;
    }, {});
    var treeData = buildTree(data, dataMap);
    return treeData;
}

// Converts the flat data structure into a parent-child tree
function buildTree(data, dataMap) {
    var treeData = [];
    data.forEach(function(node) {
        var parent = dataMap[node.parent_id];
        if (parent) {
            (parent.children || (parent.children = [])).push(node);
        } else {
            treeData.push(node);
        }
    });
    return treeData;
}

function animateNode(node, isPlayingForward, delayOffset) {
    if (showGenerationAnimation) {
        var explanationsText = $(".generation-explanations ul");
        var objectCodeText = $(".object-code ul");
        var tableBody = $(".address-table tbody");
        var tableWrapper = $(".address-table").parent();
        var codeTemplateText = $(".code-template ul");
    } else {
        var explanationsText = $(".contextual-explanations ul");
        var tableBody = $(".type-table tbody");
        var tableWrapper = $(".type-table").parent();
    }
    d3.select("#node-" + node.id).select("rect").transition()
        .duration(0).delay(delayOffset * 1000).style("fill", "#035a80")
        .on("start", function() {
            $(this).next("text").css({"font-weight": "900"});
            if (previousNode != null && previousNode !== this) {
                $(previousNode).css("fill", "#3e4153");
                $(previousNode).next("text").css({"fill": "white", "font-weight": "normal"});
            }

            isPlayingForward ? currentNodeIndex++ : currentNodeIndex--;

            var nodeName = $("#node-"+node.id).data("node-name");

            $(".data-heading-container span").html(nodeName);

            var tableEntries = "";
            $.each(node.table, function(index, tableEntry) {
                tableEntries += "<tr><td>" + tableEntry.scope +
                                "</td><td>" + tableEntry.id +
                                "</td><td>" + tableEntry.type_address +
                                "</td></tr>";
            });
            tableBody.html(tableEntries);
            tableWrapper.scrollTop(tableWrapper.prop("scrollHeight"));

            var explanations = "";
            $.each(node.explanations, function(index, explanation) {
                explanations += "<li>> " + explanation + "</li>";
            });
            explanationsText.html(explanations);
            explanationsText.scrollTop(explanationsText.prop("scrollHeight"));

            if (showGenerationAnimation) {
                var objectCodeInstructions = "";
                $.each(node.objectCode, function(index, objectCode) {
                    objectCodeInstructions += "<li>" + objectCode + "</li>";
                });
                objectCodeText.html(objectCodeInstructions);
                var codeTemplateInstructions = "";
                var codeTemplate = CodeTemplates.getTemplate(nodeName);
                $.each(codeTemplate, function(index, codeTemplateInstruction) {
                    codeTemplateInstructions += "<li>> " + codeTemplateInstruction + "</li>";
                })
                codeTemplateText.html(codeTemplateInstructions);
            }
        }).on("end", function() {
            previousNode = this;
            if (hasAnimationFinished() && is_playing) {
                is_playing = false;
                enablePlayButton();
            }
        });
}

function animateTree() {
    for (var i = currentNodeIndex, j = 0; i < nodeOrder.length-1; i++, j++) {
        var node = nodeOrder[i+1];
        animateNode(node, true, j);
    }
}

function play() {
    is_playing = true;
    enablePauseButton();
    if (hasAnimationFinished())
        currentNodeIndex = -1;
    animateTree();
}

function pause() {
    is_playing = false;
    enablePlayButton();
    d3.selectAll("rect").interrupt();
}

function forward() {
    if (is_playing)
        pause();
    if (!hasAnimationFinished()) {
        var node = nodeOrder[currentNodeIndex+1];
        animateNode(node, true, 0);
    }
}

function reverse() {
    if (is_playing)
        pause();
    if (hasAnimationStarted()) {
        var node = nodeOrder[currentNodeIndex-1];
        animateNode(node, false, 0);
    }
}

function enablePlayButton() {
    $("#play-button").show();
    $("#pause-button").hide();
}

function enablePauseButton() {
    $("#play-button").hide();
    $("#pause-button").show();
}

function hasAnimationFinished() {
    return currentNodeIndex === nodeOrder.length-1;
}

function hasAnimationStarted() {
    return currentNodeIndex > 0;
}
