/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 500);
/******/ })
/************************************************************************/
/******/ ({

/***/ 500:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(501);


/***/ }),

/***/ 501:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


$("#execute-form").submit(function (e) {
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
    $.post(url, data, function (responseData) {
        var response = responseData.response;
        var numSyntaxErrors = response.numSyntaxErrors;
        var syntaxErrors = response.syntaxErrors;
        var numContextualErrors = response.numContextualErrors;
        var contextualErrors = response.contextualErrors;
        var treeNodes = response.treeNodes;
        var objectCode = response.objectCode;
        var output = response.output;
        var contextualNodeOrder = response.contextualNodeOrder;
        $(".program-tree").text("");
        if (numSyntaxErrors > 0) {
            $(".program-tree").append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $(".program-tree").append("Syntax errors: <br>");
            $.each(syntaxErrors, function (index, syntaxError) {
                $(".program-tree").append(index + 1 + ": " + syntaxError);
            });
            $(".program-tree").append("<br>");
        } else {
            drawTree(treeNodes, contextualNodeOrder);
        }
    });
});

function drawTree(data, contextualNodeOrder) {
    var dataMap = data.reduce(function (map, node) {
        map[node.id] = node;
        return map;
    }, {});
    var treeData = [];
    data.forEach(function (node) {
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
    var svg = d3.select(".program-tree").append("div").classed("svg-container", true).append("svg").attr("preserveAspectRatio", "xMinYMin meet").attr("viewBox", "0 0 800 650").classed("svg-content-responsive", true);
    var g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    g.selectAll(".link").data(nodes.descendants().slice(1)).enter().append("path").attr("class", "link").attr("d", function (d) {
        return "M" + d.x + "," + d.y + "C" + d.x + "," + (d.y + d.parent.y) / 2 + " " + d.parent.x + "," + (d.y + d.parent.y) / 2 + " " + d.parent.x + "," + d.parent.y;
    });
    var node = g.selectAll(".node").data(nodes.descendants()).enter().append("g").attr("class", function (d) {
        return "node" + (d.children ? " node--internal" : " node--leaf");
    }).attr("transform", function (d) {
        return "translate(" + d.x + "," + d.y + ")";
    }).attr("id", function (d) {
        return "node-" + d.data.id;
    });
    node.append("circle").attr("r", 5);
    node.append("text").attr("dy", ".35em").attr("y", function (d) {
        return d.children ? -20 : 20;
    }).style("text-anchor", "middle").text(function (d) {
        return d.data.name;
    });

    $("#play-button").on("click", function () {
        animateTree(contextualNodeOrder);
        $("#play-button").hide();
        $("#pause-button").show();
    });
    $("#pause-button").on("click", function () {
        pause();
        $("#play-button").show();
        $("#pause-button").hide();
    });
}

var currentNodeIndex = 0;
function animateNode(node, currentNode, delayOffset) {
    d3.select("#node-" + node.id).select("circle").transition().duration(500).delay(delayOffset * 1000).style("fill", "red").on("start", function () {
        currentNodeIndex = currentNode;
        $(".typeTable tbody").text("");
        $(".explanations").text("");
        $.each(node.typeTable, function (index, tableEntry) {
            $(".typeTable tbody").append("<tr><td>" + tableEntry.scope + "</td><td>" + tableEntry.id + "</td><td>" + tableEntry.type + "</td></tr>");
        });
        $.each(node.explanations, function (index, explanation) {
            $(".explanations").append(explanation + "<br>");
        });
    }).transition().style("fill", "white");
}

function animateTree(nodeOrder) {
    for (var i = currentNodeIndex, j = 0; i < nodeOrder.length; i++, j++) {
        var node = nodeOrder[i];
        animateNode(node, i, j);
    }
}

function pause() {
    d3.selectAll("circle").transition().duration(0);
}

/***/ })

/******/ });