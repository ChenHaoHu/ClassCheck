var app = getApp();
var api = require('../../api.js');
Page({
  data: {
    data: [],
    classname:"",
    signid:"",
    signnum:10,
    restnum:10
  },

  onLoad: function(options) {
    console.log(options)
    var signid = options.signid
    var classname = options.class;
    this.setData({
      signid: signid,
      classname: classname
    });
    var that = this;
    wx.connectSocket({
      url: api.signdata,
    })
    wx.onSocketOpen(function(res) {
      console.log('WebSocket连接已打开！')
      console.log(signid)
      wx.sendSocketMessage({
        data: signid
      })
      wx.onSocketMessage(function(res) {
        console.log('收到服务器内容：' + res.data)
        if (res.data.length != 15) {
          var temp = JSON.parse(res.data).data;
          var data = [];
          for(var i = 0; i < temp.length; i++){
            if (temp[i].class == classname){
              data.push(temp[i]);
            }
          }
          that.setData({
            data: data
          })
        }
      })
    })

    wx.onSocketClose(function(res) {
      console.log('WebSocket 已关闭！')
    })

  },
  onHide: function() {
    wx.closeSocket()
  },

  onUnload: function() {
    wx.closeSocket()
  }
})