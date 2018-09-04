//login.js
//获取应用实例
var app = getApp();
var api = require('../../api.js');
Page({
  data: {
    remind: '加载中',
    help_status: false,
    userid_focus: false,
    passwd_focus: false,
    userid: '',
    passwd: '',
    angle: 0
  },
  onReady: function () {
    var _this = this;
    setTimeout(function () {
      _this.setData({
        remind: ''
      });
    }, 1000);
    wx.onAccelerometerChange(function (res) {
      var angle = -(res.x * 30).toFixed(1);
      if (angle > 14) { angle = 14; }
      else if (angle < -14) { angle = -14; }
      if (_this.data.angle !== angle) {
        _this.setData({
          angle: angle
        });
      }
    });
  },
  bind: function () {
    var _this = this;
    if (!_this.data.userid || !_this.data.passwd) {
      wx.showModal({
        title: '提醒',
        content: "账号及密码不能为空",
      })
      return false;
    }
    wx.showLoading({
      title: '绑定中',
    })
    wx.request({
      url: api.login,
      data: {
        name: _this.data.userid,
        password: _this.data.passwd
      },
      success: function (res) {
        wx.hideLoading()
        console.log(res)
        if (res.data.data.name!= undefined){
         wx.showModal({
           title: '绑定成功',
           content: '欢迎' + res.data.data.name + "同学",
           success(ee){
             if(ee.confirm){
               wx.setStorageSync("userid", res.data.data.userid)
               wx.setStorageSync("name", res.data.data.name)
               wx.setStorageSync("islogin", true)
               wx.switchTab({
                 url: '../index/index',
               })
             }
             if(ee.cancel){
               wx.setStorageSync("userid", res.data.data.userid)
               wx.setStorageSync("name", res.data.data.name)
               wx.setStorageSync("islogin", true)
               wx.switchTab({
                 url: '../index/index',
               })
             }
           }
         })
       }else{
          wx.showModal({
            title: '提示',
            content: res.data.data,
          })
       }
      },
      fail: function (res) {
        wx.hideLoading();
        wx.showModal({
          title: '提示',
          content: "连接错误，绑定失败",
        })
      }
    });
  },
  useridInput: function (e) {
    this.setData({
      userid: e.detail.value
    });
    if (e.detail.value.length >= 10) {
      wx.hideKeyboard();
    }
  },
  passwdInput: function (e) {
    this.setData({
      passwd: e.detail.value
    });
  },
  inputFocus: function (e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': true
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': true
      });
    }
  },
  inputBlur: function (e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': false
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': false
      });
    }
  },
  tapHelp: function (e) {
    if (e.target.id == 'help') {
      this.hideHelp();
    }
  },
  showHelp: function (e) {
    this.setData({
      'help_status': true
    });
  },
  hideHelp: function (e) {
    this.setData({
      'help_status': false
    });
  }
});