var app = getApp();
var api = require('../../api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    data: []
  },

  onLoad: function(options) {
    console.log(options)
    var signid = options.id
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
          that.setData({
            data: JSON.parse(res.data)
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