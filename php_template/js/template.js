function addNewAssetInput() {
    var count = $("#add-asset-form > div").length;
    var formBlock = $("#add-asset-form");
    var newFormRow = "  <div class=\"form-group \" style=\"border: 5px solid black\">\n" +
        "                    <div class=\"form-group\">\n" +
        "                        <label for=\"name-"+count+"\">Username: </label>\n" +
        "                        <input class=\"form-control\" placeholder=\"Enter name\" id=\"name-"+count+"\" type=\"text\" name=\"name-"+count+"\">\n" +
        "                    </div>\n" +
        "                    <div class=\"form-group\">\n" +
        "                        <label for=\"description-"+count+"\">Description: </label>\n" +
        "                        <input class=\"form-control\" placeholder=\"Enter description\" id=\"description-"+count+"\" type=\"text\" name=\"description-"+count+"\">\n" +
        "                    </div>\n" +
        "                    <div class=\"form-group\">\n" +
        "                        <label for=\"value-"+count+"\">Value: </label>\n" +
        "                        <input class=\"form-control\" placeholder=\"Enter value\" id=\"value-"+count+"\" type=\"number\" name=\"value-"+count+"\">\n" +
        "                    </div>\n" +
        "                </div>\n";

    formBlock.append(newFormRow);
}

function addEntities(userID) {
    var count = $("#add-asset-form > div").length;
    let submitArray = [];
    console.log(userID);
    for(i = 0; i < count; i++){
        console.log("name-"+i);
        var name = document.getElementById("name-"+i).value;
        var description = document.getElementById("description-"+i).value;
        var value = document.getElementById("value-"+i).value;
        if(!isEmpty(name) && !isEmpty(description) && !isEmpty(value)){
            submitArray.push(
                {
                    'name' : name,
                    'description' : description,
                    'value' : parseInt(value,10)
                }
            )
        }
    }
    if(submitArray.length>0){
        $.post({
            url: 'add.php',
            data:{'entries': submitArray,'user_id':userID},
            success:function (data,status) {
                console.log(data);
                $("#result").html('Successful add');
                window.location.reload();
            },
            error:function(xhr,textStatus, errorThrown){
                $("#result").html('Error happened');
            }
        });
    }
    console.log(submitArray);
}

function isEmpty(stringtocheck) {
    return (stringtocheck.length === 0 || !stringtocheck.trim());
};
