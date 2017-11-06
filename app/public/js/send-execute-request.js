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
    var margin = { top: 20, right: 90, bottom: 30, left: 90 };
    var width = 960 - margin.left - margin.right;
    var height = 500 - margin.top - margin.bottom;
    var svg = d3.select(".program-tree").append("svg").attr("width", width + margin.right + margin.left).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    var i = 0;
    var duration = 750;
    var root;
    var treemap = d3.tree().size([height, width]);
    root = d3.hierarchy(treeData[0], function (d) {
        return d.children;
    });
    root.x0 = height / 2;
    root.y0 = 0;
    root.children.forEach(collapse);
    update(root);

    function collapse(d) {
        if (d.children) {
            d._children = d.children;
            d._children.forEach(collapse);
            d.children = null;
        }
    }

    function update(source) {
        var treeData = treemap(root);
        var nodes = treeData.descendants(),
            links = treeData.descendants().slice(1);
        nodes.forEach(function (d) {
            d.y = d.depth * 180;
        });
        var node = svg.selectAll('g.node').data(nodes, function (d) {
            return d.id || (d.id = ++i);
        });
        var nodeEnter = node.enter().append('g').attr('class', 'node').attr("transform", function (d) {
            return "translate(" + source.y0 + "," + source.x0 + ")";
        }).on('click', click);
        nodeEnter.append('circle').attr('class', 'node').attr('r', 1e-6).style("fill", function (d) {
            return d._children ? "lightsteelblue" : "#fff";
        });
        nodeEnter.append('text').attr("dy", ".35em").attr("x", function (d) {
            return d.children || d._children ? -13 : 13;
        }).attr("text-anchor", function (d) {
            return d.children || d._children ? "end" : "start";
        }).text(function (d) {
            return d.data.name;
        });
        var nodeUpdate = nodeEnter.merge(node);
        nodeUpdate.transition().duration(duration).attr("transform", function (d) {
            return "translate(" + d.y + "," + d.x + ")";
        });
        nodeUpdate.select('circle.node').attr('r', 10).style("fill", function (d) {
            return d._children ? "lightsteelblue" : "#fff";
        }).attr('cursor', 'pointer');
        var nodeExit = node.exit().transition().duration(duration).attr("transform", function (d) {
            return "translate(" + source.y + "," + source.x + ")";
        }).remove();
        nodeExit.select('circle').attr('r', 1e-6);
        nodeExit.select('text').style('fill-opacity', 1e-6);
        var link = svg.selectAll('path.link').data(links, function (d) {
            return d.id;
        });
        var linkEnter = link.enter().insert('path', "g").attr("class", "link").attr('d', function (d) {
            var o = { x: source.x0, y: source.y0
            };
            return diagonal(o, o);
        });
        var linkUpdate = linkEnter.merge(link);
        linkUpdate.transition().duration(duration).attr('d', function (d) {
            return diagonal(d, d.parent);
        });
        var linkExit = link.exit().transition().duration(duration).attr('d', function (d) {
            var o = { x: source.x, y: source.y };
            return diagonal(o, o);
        }).remove();
        nodes.forEach(function (d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });
        function diagonal(s, d) {
            path = 'M ' + s.y + ' ' + s.x + '\n            C ' + (s.y + d.y) / 2 + ' ' + s.x + ',\n            ' + (s.y + d.y) / 2 + ' ' + d.x + ',\n            ' + d.y + ' ' + d.x;
            return path;
        }
        function click(d) {
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else {
                d.children = d._children;
                d._children = null;
            }
            update(d);
        }
    }
}

/***/ })

/******/ });