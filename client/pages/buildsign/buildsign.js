var app = getApp();
var api = require('../../api.js');
Page({
  data: {
    tips:"",
    time:"",
    num:"",
    pass:""
  },
  buildsign:function(){
 
   if(this.data.pass == "123321"){
     wx.showLoading({
       title: '创建中',
     })
     var that = this;
     wx.getLocation({
       type: 'wgs84',
       success: function (res) {
         var latitude = res.latitude
         var longitude = res.longitude
         var speed = res.speed
         var accuracy = res.accuracy
         console.log(res)
         wx.request({
           url: api.buildsign,
           data: {
             id: 1001,
             time: that.data.time,
             tips: that.data.tips,
             longitude: longitude,
             latitude: latitude
           },
           success: function (e) {
             wx.hideLoading()
             console.log(e.data)
             that.setData({
               num: e.data.data.signid
             });
             wx.showModal({
               title: '提示',
               content: '此签到信息PASS码为' + e.data.data.signid,
               success: function () {
                 wx.redirectTo({
                   url: '../signdata/signdata?id=' + e.data.data.signid,
                 })
               },
               fail: function () {

               }
             })

           }
         })
       },
       fail: function (res) {
         wx.showModal({
           title: '提示',
           content: '获取地理位置信息失败,无法创建',
         })
       }
     })
   }else{
     wx.showModal({
       title: '提示',
       content: '密匙错误',
     })
   }
  },
  changinput:function(e){
    var that = this;
    console.log(e.detail.detail.value)
    console.log(e.currentTarget.id)
    if (e.currentTarget.id == "tips"){
      that.setData({
        tips: e.detail.detail.value
      });
    }
    if (e.currentTarget.id == "time") {
      that.setData({
        time: e.detail.detail.value
      });
    }
    if (e.currentTarget.id == "pass") {
      that.setData({
        pass: e.detail.detail.value
      });
    }
  }
});