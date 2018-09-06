// pages/main/index.js
var QR = require("../../utils/qrcode.js");

var aa = "";
var Dec = require('../../public.js');//引用封装好的加密解密js
Page({
  data: {
    canvasHidden: false,
    maskHidden: true,
    imagePath: '',
    placeholder: '1024,2017-9-10',
    intro:"汇编课程",
    time:"2018-9-10 12:00"
  },
 
  onLoad: function (options) {
    var that = this;
    that.setData({
      time:options.time,
      intro:options.intro
    });
    // 页面初始化 options为页面跳转所带来的参数
    var size = this.setCanvasSize();//动态设置画布大小
    var initUrl = this.data.placeholder;
    var min = new Date().getTime();
    var id = options.id;
    var con = min + "@" + id + "@signprogram" + "@" + "胡晨阳" + "@" + that.data.intro + "@" + that.data.time
   con = Dec.Encrypt(con);
    that.createQrCode(con, "mycanvas", size.w, size.h);
  aa =  setInterval(function(){
      var min = new Date().getTime();
      var name = wx.getStorageSync("name")
      var id = 1034;
    var con = min + "@" + id + "@signprogram" + "@" + name + "@" + that.data.intro + "@" + that.data.time
      con = Dec.Encrypt(con);
      that.createQrCode(con, "mycanvas", size.w, size.h);
    },6000);
  },

  //适配不同屏幕大小的canvas
  setCanvasSize: function () {
    var size = {};
    try {
      var res = wx.getSystemInfoSync();
      var scale = 750 / 686;//不同屏幕下canvas的适配比例；设计稿是750宽
      var width = res.windowWidth / scale;
      var height = width;//canvas画布为正方形
      size.w = width;
      size.h = height;
    } catch (e) {
      // Do something when catch error
      console.log("获取设备信息失败" + e);
    }
    return size;
  },
  createQrCode: function (url, canvasId, cavW, cavH) {
    QR.api.draw(url, canvasId, cavW, cavH);
    setTimeout(() => { this.canvasToTempImage(); }, 1000);
  },
  //获取临时缓存照片路径，存入data中
  canvasToTempImage: function () {
    var that = this;
    wx.canvasToTempFilePath({
      canvasId: 'mycanvas',
      success: function (res) {
        var tempFilePath = res.tempFilePath;
        console.log(tempFilePath);
        that.setData({
          imagePath: tempFilePath,
          // canvasHidden:true
        });
      },
      fail: function (res) {
        console.log(res);
      }
    });
  },
  //点击图片进行预览，长按保存分享图片
  previewImg: function (e) {
    var img = this.data.imagePath;
    console.log(img);
    wx.previewImage({
      current: img, // 当前显示图片的http链接
      urls: [img] // 需要预览的图片http链接列表
    })
  },
 back:function(){
   wx.switchTab({
     url: '../index/index',
   })
 },
 onHide:function(){
  clearInterval(aa);  
  },
   onUnload: function () {
     clearInterval(aa);  
  },
})