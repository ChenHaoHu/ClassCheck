var api = require('../../api.js');
Page({

  data: {
    classdata: [],
    signid:""
  },
   onLoad: function (options) {
     var signid = options.signid
     this.setData({
       signid:signid
     })
    var that = this;
    wx.connectSocket({
      url: api.signdata,
    })
    wx.onSocketOpen(function (res) {
      console.log('WebSocket连接已打开！')
      console.log(signid)
      wx.sendSocketMessage({
        data: signid
      })
      wx.onSocketMessage(function (res) {
        console.log('收到服务器内容：' + res.data)
        if (res.data.length != 15) {
          that.setData({
            classdata: JSON.parse(res.data).classnames
          })
        }
      })
    })

    wx.onSocketClose(function (res) {
      console.log('WebSocket 已关闭！')
    })

  },
  onHide: function () {
    wx.closeSocket()
  },

  onUnload: function () {
    wx.closeSocket()
  },
  lookmore: function (e) {
    console.log(e)
    wx.navigateTo({
      url: '../signdata/signdata?class=' + e.currentTarget.id+"&signid="+this.data.signid,
    })
  }
})