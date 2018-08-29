var ip = "https://welnpu.hcyang.top";
var wss = "wss://welnpu.hcyang.top";
var api = {

  login: ip + "/stu/add",
  buildsign: ip + "/sign/build",
  findsign: ip + "/sign/find",
  tosign: ip + "/sign/sign",
  torest: ip + "/sign/rest",
  signdata: wss + "/websocket",
};


module.exports = api;