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
            }).attr("data-node-name", function(d) {
                return d.data.nodeName;
            }).attr("data-node-value", function(d) {
                return d.data.nodeValue;
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
                var name = d.data.nodeValue;
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
            currentNodeIndex = -1;
        });

        $("#contextual-button").on("click", function() {
            $(".right-contextual-container").css("display", "table");
            $(".right-generation-container").hide();
            showGenerationAnimation = false;
            Tree.nodeOrder = contextualNodeOrder
            currentNodeIndex = -1;
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
var previousNode = null;

function animateNode(node, isPlayingForward, delayOffset) {
    if (showGenerationAnimation) {
        var explanationsText = $(".generation-explanations p");
        var tableBody = $(".address-table tbody");
        var codeTemplateImage = $(".code-template img");
    } else {
        var explanationsText = $(".contextual-explanations p");
        var tableBody = $(".type-table tbody");
    }
    d3.select("#node-" + node.id).select("rect").transition()
        .delay(delayOffset * 1000).style("fill", "#3e4153")
        .on("start", function() {
            $(this).next("text").css({"fill": "white", "font-weight": "900"});
            if (previousNode != null && previousNode !== this) {
                $(previousNode).css("fill", "white");
                $(previousNode).next("text").css({"fill": "#3e4153", "font-weight": "normal"});
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

            var explanations = "";
            $.each(node.explanations, function(index, explanation) {
                explanations += explanation + "<br>";
            });
            explanationsText.html(explanations);

            if (showGenerationAnimation) {
                var codeTemplateURL = "images/" + nodeName + ".png";
                codeTemplateImage.attr("src", codeTemplateURL);
            }
        }).on("end", function() {
            previousNode = this;
            if (hasAnimationFinished() && is_playing) {
                is_playing = false;
                togglePlayButton();
            }
        });
}

function animateTree() {
    for (var i = currentNodeIndex, j = 0; i < Tree.nodeOrder.length-1; i++, j++) {
        var node = Tree.nodeOrder[i+1];
        animateNode(node, true, j);
    }
}

function play() {
    is_playing = true;
    togglePlayButton();
    if (isAnimationFinished())
        currentNodeIndex = -1;
    animateTree();
}

function pause() {
    is_playing = false;
    togglePlayButton();
    d3.selectAll("rect").interrupt();
}

function forward() {
    if (is_playing)
        pause();
    if (!hasAnimationFinished()) {
        var node = Tree.nodeOrder[currentNodeIndex+1];
        animateNode(node, true, 0);
    }
}

function reverse() {
    if (is_playing)
        pause();
    if (hasAnimationStarted()) {
        var node = Tree.nodeOrder[currentNodeIndex-1];
        animateNode(node, false, 0);
    }
}

function togglePlayButton() {
    $("#play-button").toggle();
    $("#pause-button").toggle();
}

function hasAnimationFinished() {
    return currentNodeIndex === Tree.nodeOrder.length-1;
}

function hasAnimationStarted() {
    return currentNodeIndex > 0;
}
