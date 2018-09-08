 var app = getApp();
 var api = require('../../api.js');
 Page({
   data: {
     id: "",
     time: "",
     content: "",
     pass: ""
   },

   changinput: function(e) {
     var that = this;
     if (e.currentTarget.id == "content") {
       that.setData({
         content: e.detail.value
       });
     }
     if (e.currentTarget.id == "id") {
       that.setData({
         id: e.detail.value
       });
     }
     if (e.currentTarget.id == "time") {
       that.setData({
         time: e.detail.value
       });
     }
     if (e.currentTarget.id == "pass") {
       that.setData({
         pass: e.detail.value
       });
     }
   },
   tobuild: function() {
     wx.showLoading({
       title: '创建中',
     })
     var that = this
     if (that.data.id.length == 0 || that.data.time.length == 0 || that.data.content.length == 0 || that.data.pass.length == 0) {
       wx.hideLoading();
       wx.showModal({
         title: '提示',
         content: '请输入完整',
       })
     } else {
       wx.request({
         url: api.buildsign,
         data: {
           id: that.data.id,
           time: that.data.time,
           tips: that.data.content,
           pass: that.data.pass
         },
         success: function(res) {
           console.log(res.data)
           wx.hideLoading();
           if (res.data.data == "密匙错误") {
             wx.showToast({
               title: '密匙错误',
               icon: 'none'
             })
           } else {
             wx.hideLoading();
             wx.showModal({
               title: '提示',
               content: '创建成功',
               showCancel: false,
               success: function(e) {
                 var data = res.data.data
                 var signlist = wx.getStorageSync("signlist");
                 if (signlist == "") {
                   signlist = []
                 }
                 signlist.push("编号:" + data.signid)

                 wx.setStorageSync("signlist", signlist)
                 
                 console.log("编号:" + data.signid)
                 wx.navigateTo({
                   url: '../qrcode/qrcode?id=' + data.signid + "&intro=" + data.content + "&time=" + data.createtime,
                 })
               }
             })

           }
         }
       })
     }
   }
 });