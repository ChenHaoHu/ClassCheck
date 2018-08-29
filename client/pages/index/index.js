Page({

  /**
   * 页面的初始数据
   */
  data: {
  },
  
  tosign: function () {
    var userid =  wx.getStorageSync("userid")
    if (userid.length==0){
      wx.navigateTo({
        url: '../login/login',
      })
    }else{
      wx.navigateTo({
        url: '../signs/signs',
      })
    }
  },
  buildsign: function () {
    wx.navigateTo({
      url: '../buildsign/buildsign',
    })
  },
})
