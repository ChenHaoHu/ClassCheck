var Dec = require('../../public.js');//引用封装好的加密解密js

Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:"no"
  },

  onLoad:function(){
    var islogin = wx.getStorageSync("islogin");
    var that = this;
    if (islogin) {
      that.setData({
        name: wx.getStorageSync("name")
      })
    } else {
      return false;
    }
  },

  tosign: function() {
    var userid = wx.getStorageSync("userid")
    if (userid.length == 0) {
      wx.navigateTo({
        url: '../login/login',
      })
    } else {
      wx.navigateTo({
        url: '../signs/signs',
      })
    }
  },

  toscan() {
    var isok = this.check();
    if (isok == true) {
      var that = this
      this.setData({
        visible1: false
      });
      var scandata = "";
      wx.scanCode({
        onlyFromCamera: true,
        success: (res) => {
          scandata = res.result;
          wx.showModal({
            title: '提示',
            content: res.result,
          })
           scandata = Dec.Decrypt(scandata)
          var data = scandata.split("@")
          console.log(data[0] - new Date().getTime())
          console.log(data)
          if (data[2] == "signprogram") {
            if (new Date().getTime() - data[1] < 16000) {
              var usrid = wx.getStorageSync("userid");
              wx: wx.request({
                url: api.tosign,
                data: {
                  signid: data[1],
                  usrid: usrid,
                  codetype: data[3],
                },
                header: {},
                method: 'GET',
                dataType: 'json',
                responseType: 'text',
                success: function(res) {
                  console.log(res)
                },
                fail: function(res) {},
                complete: function(res) {},
              })
            } else {
              wx.showModal({
                title: '提示',
                content: '扫描的二维码不正确或已经失效，请重新扫描',
              })
            }
          }
        }
      })
    } else {
      wx.navigateTo({
        url: '../login/login',
      })
    }
  },
  check: function() {
    var islogin = wx.getStorageSync("islogin");
    if (islogin) {
      return false;
    } else {
      return true;
    }
  }
})