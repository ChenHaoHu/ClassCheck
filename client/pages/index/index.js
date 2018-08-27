Page({

  /**
   * 页面的初始数据
   */
  data: {
  },
  
  handleClick: function () {
    var userid =  wx.getStorageSync("userid")
    if (userid.length==0){
      wx.navigateTo({
        url: '../login/login',
      })
    }
  },
})
