// pages/signlist/signlist.js
Page({

  data: {
  data :[]
  },

  onLoad: function (options) {
    var data = wx.getStorageSync("signlist");
    console.log(data)
    this.setData({
      data:data
    });
  },
})