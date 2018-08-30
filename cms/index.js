
//渲染最新菜单列表

window.onload = function () {



    var data = [

    ]

    onloaddata(data);

};


var createtime = 0;
var time = 0;

onloaddata = function (data) {
    //  $("#list").html(" ")
    var str = "";

    var class1_success_sign = 0;
    var class1_fail_sign = 0;
    var class1_success_rest = 0;
    var class1_fail_rest = 0;
    var class2_success_sign = 0;
    var class2_fail_sign = 0;
    var class2_success_rest = 0;
    var class2_fail_rest = 0;
    var class3_success_sign = 0;
    var class3_fail_sign = 0;
    var class3_success_rest = 0;
    var class3_fail_rest = 0;

    var classname = "未知";

    // console.log(data)
    for (let index = 0; index < data.length; index++) {
    //     var signtime = parseInt( data[index].time.split(":")[1])*60+parseInt( data[index].time.split(":")[2]);
    //     console.log(parseInt( data[index].time.split(":")[1]))
    //     console.log(parseInt( data[index].time.split(":")[2]))
    //    console.log("注册时间："+signtime)
    //    console.log("创建时间："+createtime)
    //    console.log("有效时间："+time)
console.log(data[index].stuid.slice(7,8))
        if(data[index].stuid.slice(7,8)==1){
            if(data[index].signstate == "签到成功"){
                class1_success_sign ++;
            }
            if(data[index].signstate == "签到超时"){
                class1_fail_sign ++;
            }
            if(data[index].signstate == "请假成功"){
                class1_success_rest ++;
            }
            if(data[index].signstate == "请假超时"){
                class1_fail_rest ++;
            }
            classname = "一班"
        }
        
        if(data[index].stuid.slice(7,8)==2){
            if(data[index].signstate == "签到成功"){
                class2_success_sign ++;
            }
            if(data[index].signstate == "签到超时"){
                class2_fail_sign ++;
            }
            if(data[index].signstate == "请假成功"){
                class2_success_rest ++;
            }
            if(data[index].signstate == "请假超时"){
                class2_fail_rest ++;
            }
            classname = "二班"
        }
        
        if(data[index].stuid.slice(7,8)==3){
            if(data[index].signstate == "签到成功"){
                class3_success_sign ++;
            }
            if(data[index].signstate == "签到超时"){
                class3_fail_sign ++;
            }
            if(data[index].signstate == "请假成功"){
                class3_success_rest ++;
            }
            if(data[index].signstate == "请假超时"){
                class3_fail_rest ++;
            }
            classname = "三班"
        }


            var con = " <tr><td>" + data[index].name +
            "</td><td >" + data[index].stuid + "</td><td>" + data[index].distance + "m</td>"
            + "<td>" + data[index].signstate + "</td>"
            + "<td>" + classname + "</td>"
            + "<td>" + (data.length - index) + "</td>"
            + " <td><span class='badge badge-info'>" + data[index].time + "</span></td></tr>"
       
        str = str + con;
        // $("#list").append(con)
    }
    $("#list").html(str)

    $("#class1_success_sign").text(class1_success_sign)
    $("#class1_fail_sign").text(class1_fail_sign)
    $("#class1_success_rest").text(class1_success_rest)
    $("#class1_fail_rest").text(class1_fail_rest)

    $("#class2_success_sign").text(class2_success_sign)
    $("#class2_fail_sign").text(class2_fail_sign)
    $("#class2_success_rest").text(class2_success_rest)
    $("#class2_fail_rest").text(class2_fail_rest)

    $("#class3_success_sign").text(class3_success_sign)
    $("#class3_fail_sign").text(class3_fail_sign)
    $("#class3_success_rest").text(class3_success_rest)
    $("#class3_fail_rest").text(class3_fail_rest)

}



start = function () {
    var message = $("#input").val();
    // 获取数据
    $.ajax({
        url: "https://welnpu.hcyang.top/sign/find?signid=" + message,
        async: true,
        success: function (res) {
            console.log(res.data[0])
            var text = "备注：" + res.data[0].content + "<br/>创建时间：" + res.data[0].createtime + "<br/>限时：" + res.data[0].time + "分钟"
            $("#intro").html(text);

            var createtime = new Date(res.data[0].createtime)
           
            var tt = 60*parseInt(res.data[0].time) - (new Date().getTime()/1000 - createtime.getTime()/1000)
            
            console.log(tt)
            if(tt  > 0){
                maxtime = tt;
            }else{
                maxtime = 0;
            }
          
            timer = setInterval("CountDown()", 1000);    

            // console.log(res.data[0].createtime.split(":")[1])
            // console.log(res.data[0].createtime.split(":")[2])
            // createtime = parseInt(res.data[0].createtime.split(":")[1])*60+parseInt(res.data[0].createtime.split(":")[2]);
            // console.log(createtime)
            // time = 60*parseInt(res.data[0].time);
        }
    });
    console.log(message)
    send(message);
}


var flag = true;
input = function (e) {
    if (flag == true) {
        flag = false;
    } else {
        window.location.reload();
    }

}

var maxtime = 60*60
function CountDown() {
    if (maxtime >= 0) {
        minutes = Math.floor(maxtime / 60);
        seconds = Math.floor(maxtime % 60);
        msg = "距离结束还有" + minutes + "分" + seconds + "秒";
        document.all["timer"].innerHTML = msg;
        if (maxtime == 5 * 60)alert("还剩5分钟");
            --maxtime;
    } else{
        clearInterval(timer);
        alert("时间到，结束!");
        websocket.onclose();
    }
}
   