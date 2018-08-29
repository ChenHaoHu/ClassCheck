
function login() {

    var name = $("#name").val();
    var password = $("#password").val();
    console.log(name)
    var storeid = storeid;
    if(name == "admin" && password == "admin"){
        window.location.href='index.html';
    }

}