var Dec = require('../../public.js'); //引用封装好的加密解密js
var api = require("../../api.js");
Page({
  data: {
    name: "no"
  },
  onLoad: function() {

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

  onShow: function() {
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

  toscan() {
    var isok = this.check();
    if (isok == true) {
      var that = this
      var scandata = "";
      wx.scanCode({
        onlyFromCamera: true,
        success: (res) => {
          scandata = res.result;
          scandata = Dec.Decrypt(scandata)
          console.log(scandata)
          var data = scandata.split("@")
          if (data[2] == "signprogram") {
            console.log(new Date().getTime())
            console.log(data[0])
            if (new Date().getTime() - data[0] < 6000) {
              console.log(data)
              wx.showModal({
                title: '详细',
                content: data[5] + "  " + data[4],
                confirmText: "确定签到",
                success: function(e) {
                  wx.showLoading({
                    title: '签到中',
                  })
                  var usrid = wx.getStorageSync("userid");
                  wx: wx.request({
                    url: api.tosign,
                    data: {
                      signid: data[1],
                      userid: usrid,
                      codetype: data[3],
                    },
                    success: function(res) {
                      wx.hideLoading()
                      console.log(res)
                      if (res.data.data != "已经签到过了") {
                        var signlist = wx.getStorageSync("signlist");
                        if (signlist == "") {
                          signlist = []
                        }
                        var flag = false;
                        for (var i = 0; i < signlist.size; i++) {
                          if (signlist.id == data[1]) {
                            flag = true;
                            break;
                          }
                        }
                        if (flag == false) {
                          signlist.push({
                            id: data[1],
                            time: data[5],
                            con: data[4]
                          })
                          wx.setStorageSync("signlist", signlist)
                        }
                        wx.showModal({
                          title: res.data.data,
                          success: function(res) {
                            wx.showModal({
                              title: '分享',
                              content: '是否愿意分享本次二维码给他人',
                              success: function(res) {
                                if (res.confirm) {
                                  wx.navigateTo({
                                    url: '../qrcode/qrcode?id=' + data[1] + "&intro=" + data[5] + "&time=" + data[4],
                                  })
                                }
                                if (res.cancel) {
                                  wx.switchTab({
                                    url: '../index/index',
                                  })
                                }
                              }
                            })
                          }
                        })
                      } else {
                        wx.showModal({
                          title: '提示',
                          content: res.data.data,
                          success: function() {
                            wx.showModal({
                              title: '分享',
                              content: '是否愿意分享本次二维码给他人',
                              success: function(res) {

                                if (res.confirm) {
                                  wx.navigateTo({
                                    url: '../qrcode/qrcode?id=' + data[1] + "&intro=" + data[5] + "&time=" + data[4],
                                  })
                                }
                                if (res.cancel) {
                                  wx.switchTab({
                                    url: '../index/index',
                                  })
                                }
                              }
                            })
                          }
                        })
                      }

                    },
                    fail: function(res) {
                      wx.hideLoading()
                      wx.showModal({
                        title: '提示',
                        content: '签到失败,请重试',
                      })
                    }
                  })
                }
              })
            } else {
              wx.showModal({
                title: '提示',
                content: '此二维码已经失效，请重新扫描',
              })
            }
          } else {
            wx.showModal({
              title: '提示',
              content: '此二维码非WE签到提供',
            })
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
      return true;
    } else {
      return false;
    }
  },


  torest: function() {
    var isok = this.check();
    if (isok == true) {
      var that = this
      var scandata = "";
      wx.scanCode({
        onlyFromCamera: true,
        success: (res) => {
          scandata = res.result;
          scandata = Dec.Decrypt(scandata)
          var data = scandata.split("@")
          wx.showModal({
            title: '提示',
            content: data[5] + "  " + data[4],
            showCancel: false,
            success: function(e) {
              console.log(data[0] - new Date().getTime())
              console.log(data)
              if (data[2] == "signprogram") {
                if (new Date().getTime() - data[0] < 6000) {
                  var signlist = wx.getStorageSync("signlist");
                  if (signlist == "") {
                    signlist = []
                  }
                  var flag = false;
                  for (var i = 0; i < signlist.size; i++) {
                    if (signlist.id == data[1]) {
                      flag = true;
                      break;
                    }
                  }
                  if (flag == false) {
                    signlist.push({
                      id: data[1],
                      time: data[5],
                      con: data[4]
                    })
                    wx.setStorageSync("signlist", signlist)
                  }
                  wx.navigateTo({
                    url: '../rest/rest?signid=' + data[1] + "&codetype=" + data[3],
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
        }
      })
    } else {
      wx.navigateTo({
        url: '../login/login',
      })
    }
  },

  todata: function() {
    var temp = wx.getStorageSync("signlist")
    console.log(temp)
    if (temp == "") {
      wx.showModal({
        title: '提示',
        content: '您还没有任何签到',
      })
    } else {
      // wx.showActionSheet({
      //   itemList: temp,
      //   success: function(res) {
      //     console.log(temp[res.tapIndex])
      //     var signid = temp[res.tapIndex].split(":")[1]
      //     wx.navigateTo({
      //       url: '../showbord/showbord?signid=' + signid,
      //     })

      //   }
      // })

      wx.navigateTo({
        url: '../signlist/signlist',
      })

    }
  },
  tobuild: function() {
    wx.navigateTo({
      url: '../buildsign/buildsign',
    })
  },
  toshare: function() {
    var temp = wx.getStorageSync("signlist")
    console.log(temp)
    if (temp == "") {
      wx.showModal({
        title: '提示',
        content: '您还没有任何签到',
      })
    } else {
      // wx.showActionSheet({
      //   itemList: temp,
      //   success: function(res) {
      //     console.log(temp[res.tapIndex])
      //     var signid = temp[res.tapIndex].split(":")[1]
      //     wx.request({
      //       url: api.findsign,
      //       data: {
      //         signid: signid
      //       },
      //       success: function(res) {
      //         console.log(res)
      //         wx.navigateTo({
      //           url: '../qrcode/qrcode?id=' + signid + "&intro=" + res.data.data.content + "&time=" + res.data.data.createtime,
      //         })
      //       }
      //     })
      //   }
      // })

      wx.navigateTo({
        url: '../signlist/signlist',
      })
    }
  }
})