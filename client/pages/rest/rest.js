var api = require("../../api.js");
Page({
  data: {
    username:"none",
    num:"0",
    reason:"",
    reststuname:"",
    reststuid:"",
    signid:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    var username = wx.getStorageSync("name");
    this.setData({
      username: username,
      signid: options.signid
    });
  },

  inputtextarea:function(e){
      console.log(e.detail.value)
  this.setData({
    reason: e.detail.value,
    num:e.detail.value.length
  });
  },
  inputname:function(e){
    console.log(e.detail.value)
    this.setData({
      reststuname: e.detail.value,
    });
  },
  inputid: function (e) {
    console.log(e.detail.value)
    this.setData({
      reststuid: e.detail.value,
    });
  },
  torest:function(){
    var  that = this
    wx.showModal({
      title: '提示',
      content: '确定请假？',
      success:function(res){
        if (res.confirm){
            wx.request({
              url: api.torest,
              data:{
                userid:wx.getStorageSync("userid"),
                reason: that.data.reason,
                reststuname: that.data.reststuname,
                reststuid: that.data.reststuid,
                signid: that.data.signid
              },
              success:function(e){
                console.log(e)
              }
            })
        }else{
      
        }


      }
    })
  }
})