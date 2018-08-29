var app = getApp();
var api = require('../../api.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    hiddenmodalput: true,
    //可以通过hidden是否掩藏弹出框的属性，来指定那个弹出框  
    visible1: false,
    visible2: false,
    name: "无名氏",
    password: {
      inputvalue: "",
      value: []
    },
    content: "",
    time: "",
    tt: "",
    content2: "",
    time2: "",
    tt2: ""
  },

  inputpassword: function(e) {
    var lenth = e.detail.value.length;
    var value = e.detail.value;
    var select = e.target.id;
    var password = this.data.password;
    var that = this;
    if (lenth == 1) {
      if (password.value.length < 5) {
        password.value.push(value)
        password.inputvalue = ""
        that.setData({
          password: password,
        });
      }
    }
  },

  changepassword: function(e) {
    console.log(e)
    var id = e.target.id;
    var value = e.detail.value;
    var that = this;
    var temp = that.data.password;
    if (value.length == 1) {
      temp.value[id] = value;
      that.setData({
        password: temp,
      });
    }
    if (value.length == 0) {
      temp.value[id] = '';
      that.setData({
        password: temp,
      });
    }
  },
  handleClick1: function() {
    var that = this
    wx.request({
      url: api.findsign,
      data: {
        signid: that.data.password.value[0] + that.data.password.value[1] + that.data.password.value[2] + that.data.password.value[3] + ""
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

  sure() {
    var that = this
    this.setData({
      visible1: false
    });
    wx.showLoading({
      title: '签到中'
    })
    var usrid = wx.getStorageSync("userid");
    if (usrid) {
      wx.getLocation({
        type: 'wgs84',
        success: function(res) {
          var latitude = res.latitude
          var longitude = res.longitude
          wx.request({
            url: api.tosign,
            data: {
              signid: that.data.password.value[0] + that.data.password.value[1] + that.data.password.value[2] + that.data.password.value[3] + "",
              usrid: usrid,
              longitude: longitude,
              latitude: latitude,
            },
            success: function(res) {
              console.log(res)
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                content: res.data.data,
                success: function(res) {
                  if (res.confirm) {
                    wx.switchTab({
                      url: '../index/index',
                    })
                  } else if (res.cancel) {
                    wx.switchTab({
                      url: '../index/index',
                    })
                  }
                }
              })
            },
            fail: function() {
              wx.hideLoading()
              console.log("获取地理位置失败，无法进行")
              wx.showModal({
                title: '提示',
                content: '获取地理位置失败，无法进行',
              })
            }
          })
        }
      })
    }

  },
  sure2() {
    var that = this
    this.setData({
      visible1: false
    });
    wx.showLoading({
      title: '请假中'
    })
    var usrid = wx.getStorageSync("userid");
    if (usrid) {
      wx.getLocation({
        type: 'wgs84',
        success: function(res) {
          var latitude = res.latitude
          var longitude = res.longitude
          wx.request({
            url: api.torest,
            data: {
              signid: that.data.password.value[0] + that.data.password.value[1] + that.data.password.value[2] + that.data.password.value[3] + "",
              usrid: usrid,
              longitude: longitude,
              latitude: latitude,
              who: that.data.name
            },
            success: function(res) {
              console.log(res)
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                content: res.data.data,
                success: function(res) {
                  if (res.confirm) {
                    wx.switchTab({
                      url: '../index/index',
                    })
                  } else if (res.cancel) {
                    wx.switchTab({
                      url: '../index/index',
                    })
                  }
                }
              })
            },
            fail: function() {
              wx.hideLoading()
              console.log("获取地理位置失败，无法进行")
              wx.showModal({
                title: '提示',
                content: '获取地理位置失败，无法进行',
              })
            }
          })
        }
      })
    }

  },
  fail() {
    this.setData({
      visible1: false
    });
  },

  handleClick2: function() {
    var that = this
    that.setData({
      hiddenmodalput: false
    })

  },

  //取消按钮  
  cancel: function() {
    this.setData({
      hiddenmodalput: true
    });
  },


  //确认  
  confirm: function() {
    var that = this;
    this.setData({
      hiddenmodalput: true
    })
    wx.request({
      url: api.findsign,
      data: {
        signid: that.data.password.value[0] + that.data.password.value[1] + that.data.password.value[2] + that.data.password.value[3] + ""
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
            visible2: true,
            content2: res.data.data[0].content,
            time2: res.data.data[0].createtime,
            tt2: res.data.data[0].time,
          });
        }
      }
    })
  },


  chagename: function(e) {
    console.log(e)
    this.setData({
      name: e.detail.value
    });
  }
})