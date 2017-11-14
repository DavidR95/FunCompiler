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
/******/ 	return __webpack_require__(__webpack_require__.s = 498);
/******/ })
/************************************************************************/
/******/ ({

/***/ 498:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(499);


/***/ }),

/***/ 499:
/***/ (function(module, exports) {

$('#execute-form').submit(function (e) {
    // Get the form that was submitted
    var $form = $(this);
    // Stop the form submitting normally (i.e., don't route to action parameter)
    e.preventDefault();
    // Get the intended controller route
    var url = $form.attr('action');
    // Get csrf token from page meta-data
    var AUTH_TOKEN = $('meta[name="csrf-token"]').attr('content');
    // Serialise the form inputs, add csrf token
    var data = $form.serialize() + "&_token=" + AUTH_TOKEN;
    // Post to the controller
    $.post(url, data, function (response) {
        var response = response['response'];
        var numSyntaxErrors = response['numSyntaxErrors'];
        var syntaxErrors = response['syntaxErrors'];
        var numContextualErrors = response['numContextualErrors'];
        var contextualErrors = response['contextualErrors'];
        var astData = response['astData'];
        var objectCode = response['objectCode'];
        var output = response['output'];
        $('.program-tree').text("");
        if (numSyntaxErrors > 0) {
            $('.program-tree').append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $('.program-tree').append("Syntax errors: <br>");
            $.each(syntaxErrors, function (index, syntaxError) {
                $('.program-tree').append(index + 1 + ": " + syntaxError);
            });
            $('.program-tree').append("<br>");
        } else if (numContextualErrors > 0) {
            $('.program-tree').append("Number of contextual errors: " + numContextualErrors + "<br>");
            $('.program-tree').append("Contextual errors: <br>");
            $.each(contextualErrors, function (index, contextualError) {
                $('.program-tree').append(index + 1 + ": " + contextualError);
            });
            $('.program-tree').append("<br>");
        } else {
            drawTree(astData);
        }
        $('.object-code').text("");
        $.each(objectCode, function (index, instruction) {
            $('.object-code').append(instruction + "<br>");
        });
        $('.output').text(output);
    });
});

function drawTree(data) {
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

    var margin = { top: 35, right: 10, bottom: 35, left: 10 };
    var width = 770 - margin.left - margin.right;
    var height = 800 - margin.top - margin.bottom;
    var treemap = d3.tree().size([width, height]);
    var nodes = d3.hierarchy(treeData[0]);
    nodes = treemap(nodes);
    var svg = d3.select(".program-tree").append("div").classed("svg-container", true).append("svg").attr("preserveAspectRatio", "xMinYMin meet").attr("viewBox", "0 0 770 800").classed("svg-content-responsive", true);
    var g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    var link = g.selectAll(".link").data(nodes.descendants().slice(1)).enter().append("path").attr("class", "link").attr("d", function (d) {
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

    animateTree(data);
}

function animateTree(data) {
    var counter = 0;
    $.each(data, function () {
        d3.select("#node-" + this.id).select("circle").transition().duration(500).delay(500 * counter).style("fill", "red");
        counter++;
    });
}

/***/ })

/******/ });