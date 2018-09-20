var signdata = "";
var leavedata = "";
var maindata = "";

var classname = [];

window.onload = function () {




    username = getCookie('name')
    if (username != null && username != "") { 
        signid = getCookie('id')
    }
    else {
        window.location.href = "index.html"
    }

   
    getMainData();

}



function getSignData() {
    $.ajax({
        url: url.signdata,
        type: 'post',
        data: {
            signid: signid
        },
        datatype: 'json',
        success: function (result) {
            signdata = [];
            console.log(result)
            var data = result.data;
            for (let index = 0; index < data.length; index++) {
                if (data[index].type == 0) {
                    signdata.push(data[index])
                }
            }
            layui.use('table', function () {
                var table = layui.table;
                //第一个实例
                table.render({
                    elem: '#sign'
                    , height: 500
                    // ,url: 'http://localhost:8080/sign/cc/detail?signid=1013' //数据接口
                    , data: signdata
                    , page: false //开启分页
                    , toolbar: 'true'
                    , title: "签到数据"
                    // , totalRow: 'true'
                    , cols: [[ //表头
                        { field: 'stuid', title: '学号', sort: true, align: 'center' }
                        , { field: 'name', title: '姓名', align: 'center' }
                        , { field: 'college', title: '学院', align: 'center' }
                        , { field: 'class', title: '班级', sort: true, align: 'center' }
                        , { field: 'signstate', title: '状态', align: 'center' }
                        , { field: 'time', title: '时间', sort: true, align: 'center' }
                    ]]
                    , skin: 'row' //行边框风格
                    , even: true //开启隔行背景
                    , page: true //是否显示分页
                    , limits: [10, 20, 30]
                    , limit: 10
                });

            });
        }
    });
}

function getLeaveData() {
    $.ajax({
        url: url.signdata,
        type: 'post',
        data: {
            signid: signid
        },
        datatype: 'json',
        success: function (result) {
            signdata = [];
            console.log(result)
            var data = result.data;
            for (let index = 0; index < data.length; index++) {
                if (data[index].type == 1) {
                    signdata.push(data[index])
                }
            }
            layui.use('table', function () {
                var table = layui.table;
                //第一个实例
                table.render({
                    elem: '#leave'
                    , height: 500
                    // ,url: 'http://localhost:8080/sign/cc/detail?signid=1013' //数据接口
                    , data: signdata
                    , page: false //开启分页
                    , toolbar: 'true'
                    , title: "请假数据"
                    // , totalRow: 'true'
                    , cols: [[ //表头
                        { field: 'restid', title: '学号', sort: true, align: 'center' }
                        , { field: 'restname', title: '姓名', align: 'center' }
                        , { field: 'name', title: '操作人姓名', align: 'center' }
                        , { field: 'stuid', title: '操作人学号', align: 'center' }
                        , { field: 'reason', title: '理由', align: 'center' }
                        , { field: 'college', title: '学院', align: 'center' }
                        , { field: 'class', title: '班级', sort: true, align: 'center' }
                        , { field: 'signstate', title: '状态', align: 'center' }
                        , { field: 'time', title: '时间', sort: true, align: 'center' }
                    ]]
                    , skin: 'row' //行边框风格
                    , even: true //开启隔行背景
                    , page: true //是否显示分页
                    , limits: [10, 20, 30]
                    , limit: 10
                });

            });
        }
    });
}

function getMainData() {
    $.ajax({
        url: url.classdata,
        type: 'post',
        data: {
            signid: signid
        },
        datatype: 'json',
        success: function (result) {
            // console.log(result.data)

            maindata = result.data

            var str = "";

            classname = [];

            for (var i = 0; i < maindata.length; i++) {

                var item = maindata[i];

                classname.push(item.class.data);

                var con =
                    '<div class="layui-card" style="width:40%; float:left;margin-left: 30rpx;">'
                    + '<div class="layui-card-header layui-btn">' + item.class.data + '</div>'
                    + '<div class="layui-card-body"> '
                    + '<div class="item"> '
                    + '<div>总注册人数:' + item.class.number + '人</div></div> '
                    + '<div class="item"> '
                    + '<div>本次签到人数:' + item.data.sign + '人</div></div> '
                    + '<div class="item"> '
                    + '<div>未签到人数:' + (parseInt(item.class.number) - parseInt(item.data.sign)) + '人</div></div> '
                    + '<div class="item"> '
                    + '<div>本次请假人数:' + item.data.rest + '人</div></div> '
                    + '</div> '
                    + '</div> ';

                // console.log(con)
                str = str + con;
            }
            $("#maindata").html(str)
        }
    });


}

function getDetail() {
    $.ajax({
        url: url.detail,
        type: 'post',
        data: {
            signid: signid
        },
        datatype: 'json',
        success: function (result) {
            var data = result.data;
            var aa = data.content.split(" ");
            console.log(aa)
            $("#starttime").attr("value", data.createtime);
            $("#time").attr("value", data.time);
            $("#title").attr("value", aa[0]);
            $("#point").attr("value", aa[1]);
            $("#tips").attr("value", aa[2]);
        }
    });
}


function getOther() {
    var selects = "";
    for (let index = 0; index < classname.length; index++) {
        var select = '<option value="' + index + '" >' + classname[index] + '</option>';
        selects = selects + select;
    }
    $("#selects").html(selects);
    $.ajax({
        url: url.other,
        type: 'post',
        data: {
            signid: signid,
            classname: classname[0]
        },
        datatype: 'json',
        success: function (result) {
            console.log(result)
            var result = result.data;

            layui.use('table', function () {
                var table = layui.table;
                //第一个实例
                table.render({
                    elem: '#other'
                    , height: 500
                    // ,url: 'http://localhost:8080/sign/cc/detail?signid=1013' //数据接口
                    , data: result
                    , page: false //开启分页
                    , toolbar: 'true'
                    , title: classname[0] + "未签到数据"
                    //  // , totalRow: 'true'
                    , cols: [[ //表头
                        { field: 'stuid', title: '学号', sort: true, align: 'center' }
                        , { field: 'name', title: '姓名', align: 'center' }
                        , { field: 'college', title: '学院', align: 'center' }
                        , { field: 'classname', title: '班级', sort: true, align: 'center' }
                        , { field: 'creattime', title: '注册时间', align: 'center' }
                    ]]
                    , skin: 'row' //行边框风格
                    , even: true //开启隔行背景
                    , page: true //是否显示分页
                    , limits: [10, 20, 30]
                    , limit: 10
                });

            });

        }
    });
}

function changeOther(e) {
    var index = $('#selects option:selected').val();
    console.log($('#selects option:selected').val());

    console.log(classname)
    console.log(classname[parseInt(index)])

    $.ajax({
        url: url.other,
        type: 'post',
        data: {
            signid: signid,
            classname: classname[index]
        },
        datatype: 'json',
        success: function (result) {
            console.log(result)
            var result = result.data;

            layui.use('table', function () {
                var table = layui.table;
                //第一个实例
                table.render({
                    elem: '#other'
                    , height: 500
                    , data: result
                    , page: false //开启分页
                    , toolbar: 'true'
                    , title: classname[index] + "未签到数据"
                    , cols: [[ //表头
                        { field: 'stuid', title: '学号', sort: true, align: 'center' }
                        , { field: 'name', title: '姓名', align: 'center' }
                        , { field: 'college', title: '学院', align: 'center' }
                        , { field: 'classname', title: '班级', sort: true, align: 'center' }
                        , { field: 'creattime', title: '注册时间', align: 'center' }
                    ]]
                    , skin: 'row' //行边框风格
                    , even: true //开启隔行背景
                    , page: true //是否显示分页
                    , limits: [10, 20, 30]
                    , limit: 10
                });

            });

        }
    });
}

