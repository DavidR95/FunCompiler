$('#execute-form').submit(function(e) {
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
    $.post(url, data, function(response) {
        var response = response['response'];
        var numSyntaxErrors = response['numSyntaxErrors'];
        var syntaxErrors = response['syntaxErrors'];
        var numContextualErrors = response['numContextualErrors'];
        var contextualErrors = response['contextualErrors'];
        var astData = response['astData'];
        var objectCode = response['objectCode'];
        var output = response['output']
        $('.program-tree').text("");
        if (numSyntaxErrors > 0) {
            $('.program-tree').append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $('.program-tree').append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $('.program-tree').append((index+1) + ": " + syntaxError);
            });
            $('.program-tree').append("<br>");
        } else if (numContextualErrors > 0) {
            $('.program-tree').append("Number of contextual errors: " + numContextualErrors + "<br>");
            $('.program-tree').append("Contextual errors: <br>");
            $.each(contextualErrors, function(index, contextualError) {
                $('.program-tree').append((index+1) + ": " + contextualError);
            });
            $('.program-tree').append("<br>");
        } else {
            drawTree(astData);
        }
        $('.object-code').text("");
        $.each(objectCode, function(index, instruction) {
            $('.object-code').append(instruction + "<br>");
        });
        $('.output').text(output);
    });
});

function drawTree(data) {
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

    // set the dimensions and margins of the diagram
    var margin = {top: 40, right: 90, bottom: 50, left: 90};
    var width = 660 - margin.left - margin.right;
    var height = 500 - margin.top - margin.bottom;

    // declares a tree layout and assigns the size
    var treemap = d3.tree().size([width, height]);

    // assigns the data to a hierarchy using parent-child relationships
    var nodes = d3.hierarchy(treeData[0]);

    // maps the node data to the tree layout
    nodes = treemap(nodes);

    // append the svg obgect to the body of the page
    // appends a 'group' element to 'svg'
    // moves the 'group' element to the top left margin
    var svg = d3.select(".program-tree").append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom);
    var g = svg.append("g")
          .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    // adds the links between the nodes
    var link = g.selectAll(".link")
        .data( nodes.descendants().slice(1)).enter().append("path")
        .attr("class", "link").attr("d", function(d) {
            return "M" + d.x + "," + d.y
                + "C" + d.x + "," + (d.y + d.parent.y) / 2
                + " " + d.parent.x + "," +  (d.y + d.parent.y) / 2
                + " " + d.parent.x + "," + d.parent.y;
            });

    // adds each node as a group
    var node = g.selectAll(".node")
        .data(nodes.descendants())
        .enter().append("g")
        .attr("class", function(d) {
            return "node" + (d.children ? " node--internal" : " node--leaf");
        }).attr("transform", function(d) {
          return "translate(" + d.x + "," + d.y + ")";
        });

    // adds the circle to the node
    node.append("circle").attr("r", 10);

    // adds the text to the node
    node.append("text")
      .attr("dy", ".35em")
      .attr("y", function(d) { return d.children ? -20 : 20; })
      .style("text-anchor", "middle")
      .text(function(d) { return d.data.name; });
}
