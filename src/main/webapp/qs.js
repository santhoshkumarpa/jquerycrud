
// function loadData() {

//     $.ajax({

//         url: "http://localhost:9090/country",
//         type: 'GET',
//         headers: {
//             "Accept": "application/json",
//             "Content-Type": "application/json"
//         },
//         success: function (data) {
//             $('#countries').append("<table class='table'><tr><th width='50></th>", +"<th width='50'>name</th></tr></table>");
//             var tr;
//             for (var i = 0; i < data.length; i++) {
//                 tr = $('<tr/>');
//                 tr.append("<td >" + data[i].id + "</td>");
//                 tr.append("<td>" + data[i].name + "</td>");
//                 var d = JSON.stringify(data[i]);
//                 //alert(d);
//                 tr.append("<td><button onclick='edit(" + d + ")' >Edit</button> <button onclick=remove(" + d + ") >Delete</button></td>");
//                 $('table').append(tr);
//             }

//         },
//         error: function () {
//             alert("fail.....get");

//         }
//  });
// }
var mode = "";
function loadData() {
    //    alert('loadData!');
    $.ajax({
        url: "http://localhost:9090/country",
        type: 'GET',

        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {
            // alert(JSON.stringify(data));
            //  alert(data.length);
            $("#countries").append("<table class='table' ><tr> <th width='50'>Id</th>"
                + "<th width='50'>name</th> </tr></table>");
            var tr;
            for (var i = 0; i < data.length; i++) {
                tr = $('<tr/>');
                tr.append("<td >" + data[i].id + "</td>");
                tr.append("<td>" + data[i].name + "</td>");
                var d = JSON.stringify(data[i]);
                //alert(d);
                tr.append("<td><button id='edit' onclick='edit(" + d + ")' >Edit</button> <button id='delete' onclick=remove(" + d + ") >Delete</button></td>");
                $('table').append(tr);
            }
        },
        error: function () {
            alert('fail....post');

        }
    });
}

$(document).ready(function () {

    // Some code to be executed...
    // alert("Hello World!");
    loadData();

   
    $('#edit').click(function () {

        location.reload();
    });
});
function saveorupdate() {


    if (mode == "edit") {

        update();
    }
    else {
        save();
    }
}
function update() {

    mode = "";

    var updateCountry = {
        "id": $('#id').val(),
        "name": $('#name').val()
    };
    $.ajax({
        type: "PUT",
        url: "http://localhost:9090/country",
        data: JSON.stringify(updateCountry),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            alert("success");
            console.log("success");

        },
        failure: function (errMsg) {
            console.log(errMsg);
            alert(errMsg);
        }

    });

}
function save() {
    // alert("post");
    var newCountry = {

        "id": $('#id').val(),
        "name": $('#name').val()
    };

    $.ajax({
        type: "POST",
        url: "http://localhost:9090/country",
        data: JSON.stringify(newCountry),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(result){
          alert("saved");
          },
        failure: function (errMsg) {
            console.log(errMsg);
            alert(errMsg);
        }
    });
    
}

function edit(data) {
    mode = "edit";
    if (mode == "edit") {
        console.log(data);
        $('#btn').text("UPDATE");

    }
    $('#id').val(data.id);
    $('#name').val(data.name);

}
function remove(data) {

    var removeCountry = {

        "id": $('#id').val(),
        "name": $('#name').val()
    };
    console.log(removeCountry);
    $.ajax({
        type: "DELETE",
        url: "http://localhost:9090/country",
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (result) {
            alert("success");
            $('countries').html(result);

        }

    });
}

