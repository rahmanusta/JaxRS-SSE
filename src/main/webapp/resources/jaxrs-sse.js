(function () {

    if((typeof EventSource)==="undefined")
                {
                    $("#alertbox").css("display","");
                    return;
                }

    var eventSource = new EventSource("search/hang");
    eventSource.onopen = function (e) {
        console.log("Waiting message..");
    };
    eventSource.onerror = function (e) {
        console.log("Error");
    };

    eventSource.onmessage=function (e) {
        console.log(e);
        var feedTemplate = $("#feedTemplate").html();
        var feeds = Mustache.render(feedTemplate, $.parseJSON(e.data));
        $("#feeds").html(feeds);
    };

})();