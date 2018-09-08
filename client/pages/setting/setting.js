// pages/setting/setting.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  userdata:function(){
    var name = wx.getStorageSync("name")
    var userid = wx.getStorageSync("userid")
    wx.showModal({
      title: '个人信息',
      content: name,
    })
  },
  usedata:function(){
    wx.showModal({
      title: '使用说明',
      content: '自己意会',
    })
  },
  resp: function () {
    wx.showModal({
      title: '免责声明',
      content: '我不负责',
    })
  },
  money:function(){
    wx.showModal({
      title: '打赏',
      content: '还在与第三方协商',
    })
  },
  about: function () {
    wx.showModal({
      title: '关于我们',
      content: '由指尖改变世界编写,版权所有 v2.0.1',
    })
  }
})