var d3 = require('d3');

var Tree = module.exports = {
    nodeOrder: null,
    drawTree: function(data) {
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
    },
    setNodeOrder: function(nodeOrder) {
        Tree.nodeOrder = nodeOrder;
    },
    setUpSwitchListeners: function(contextualNodeOrder, generationNodeOrder) {
        $("#generation-button").on("click", function() {
            $(".right-contextual-container").hide();
            $(".right-generation-container").css("display", "table");
            showGenerationAnimation = true;
            Tree.nodeOrder = generationNodeOrder
            currentNodeIndex = 0;
        });

        $("#contextual-button").on("click", function() {
            $(".right-contextual-container").css("display", "table");
            $(".right-generation-container").hide();
            showGenerationAnimation = false;
            Tree.nodeOrder = contextualNodeOrder
            currentNodeIndex = 0;
        });

    },
    setUpPlaybackListeners: function() {
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
    }
}

var currentNodeIndex;
var is_playing;
var showGenerationAnimation;

function animateNode(node, currentNode, delayOffset, numNodes) {
    if (showGenerationAnimation) {
        var explanations = $(".generation-explanations");
        var table = $(".address-table tbody");
        var codeTemplate = $(".code-template");
    } else {
        var explanations = $(".contextual-explanations");
        var table = $(".type-table tbody");
    }
    d3.select("#node-" + node.id).select("rect").transition()
        .duration(500).delay(delayOffset * 1000).style("fill", "yellow")
        .on("start", function() {
            currentNodeIndex = currentNode;
            table.text("");
            explanations.html("<p>Explanations</p>");
            if (showGenerationAnimation)
                codeTemplate.html("<p>Code Template</p>");
            $.each(node.table, function(index, tableEntry) {
                table.append("<tr><td>" + tableEntry.scope +
                                             "</td><td>" + tableEntry.id +
                                             "</td><td>" + tableEntry.type_address +
                                             "</td></tr>");
            });
            explanations.append("<b>Node: " + $("#node-"+node.id).data("name") + "</b><br>");
            $.each(node.explanations, function(index, explanation) {
                explanations.append(explanation + "<br>");
            });
            if (showGenerationAnimation) {
                $.each(node.codeTemplate, function(index, codeTemplateString) {
                    codeTemplate.append(codeTemplateString + "<br>");
                });
            }
        }).on("end", function() {
            if (currentNode === numNodes-1)
                is_playing = false;
        }).transition().style("fill", "white");
}

function animateTree() {
    currentNodeIndex = (currentNodeIndex == -1 ? 0 : currentNodeIndex);
    for (var i = currentNodeIndex, j = 0; i < Tree.nodeOrder.length; i++, j++) {
        var node = Tree.nodeOrder[i];
        animateNode(node, i, j, Tree.nodeOrder.length);
    }
}

function play() {
    is_playing = true;
    $("#play-button").hide();
    $("#pause-button").show();
    animateTree();
}

function pause() {
    var node = Tree.nodeOrder[currentNodeIndex];
    is_playing = false;
    $("#play-button").show();
    $("#pause-button").hide();
    d3.selectAll("rect").interrupt();
    d3.select("#node-" + node.id).select("rect")
        .transition().style("fill", "yellow");
}

function forward() {
    if (is_playing)
        pause();
    var node = Tree.nodeOrder[currentNodeIndex+1];
    animateNode(node, currentNodeIndex+1, 0, Tree.nodeOrder.length);
}

function reverse() {
    if (is_playing)
        pause();
    var node = Tree.nodeOrder[currentNodeIndex-1];
    animateNode(node, currentNodeIndex-1, 0, Tree.nodeOrder.length);
}
