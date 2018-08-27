Page({

  /**
   * 页面的初始数据
   */
  data: {
    password: {
      inputvalue: "",
      value: []
    },
  },

  inputpassword: function (e) {
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

  changepassword: function (e) {
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})