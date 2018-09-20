 var ip = "https://welnpu.hcyang.top";
//var ip = "http://localhost:8080";
var wss = "wss://welnpu.hcyang.top";
//var wss = "ws://localhost:8080";
var api = {
  login: ip + "/stu/add",
  buildsign: ip + "/sign/build",
  findsign: ip + "/sign/find",
  tosign: ip + "/sign/r/sign",
  signdata: wss + "/websocket",
  torest: ip + "/sign/r/rest",
  detail: ip +"/sign/detail"
};


module.exports = api;