var app = getApp();
var api = require('../../api.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    hiddenmodalput: true,
    focus: true,
    visible1: false,
    password:"",
    content: "",
    time: "",
    tt: "",
  },
  
  tosign: function() {
    var that = this
    wx.request({
      url: api.findsign,
      data: {
        signid: that.data.password
      },
      success: function(res) {
        console.log(res)
        if (res.data.data == "null") {
          wx.showModal({
            title: '提示',
            content: '查询为空',
          })
        } else {
          that.setData({
            visible1: true,
            content: res.data.data[0].content,
            time: res.data.data[0].createtime,
            tt: res.data.data[0].time,
          });
        }
      }
    })
  },

  toscan() {
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
       var data = scandata.split("@")
       if(data[3] == "signprogram")
        {
          if(data[1] - new Date().getTime() < 6000){
            var usrid = wx.getStorageSync("userid");
            wx: wx.request({
              url: api.tosign,
              data: {
                signid: data[2],


              },
              header: {},
              method: 'GET',
              dataType: 'json',
              responseType: 'text',
              success: function (res) { },
              fail: function (res) { },
              complete: function (res) { },
            })
          }else{
            wx.showModal({
              title: '提示',
              content: '扫描的二维码不正确或已经失效，请重新扫描',
            })
          }
        }
      }
    })
   
  },

  failscan() {
    this.setData({
      visible1: false
    });
  },

  torest: function() {
    var that = this
    that.setData({
      hiddenmodalput: false
    })

  },

  inputpassword:function(e){
    console.log(e.detail.value)
    this.setData({
      password: e.detail.value
    });
  },

  showbord:function(){
    this.setData({
      focus:true
    })
  }
  
})