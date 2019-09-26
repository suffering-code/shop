$(function(){
    $.ajax({
        url:"http://localhost:8084/sso/checkLogin",
        dataType:"JSONP",
        jsonpCallback:"checkLogin",
        success:function(data){
            if(data){
                data = JSON.parse(data);
                $("#loginState").html("欢迎【"+data.username+"】登录码农网 <a href='http://localhost:8084/sso/logout'>注销</a>");
            }else{
                $("#loginState").html("<a href='http://localhost:8084/toRegister'>注册</a>/<a onclick='toLogin()' href='javascript:void(0)'>登录</a>");
            }
        }
    });
})

function toLogin(){

    // 1.先获获取url地址
    var url = location.href;

    url = encodeURIComponent(url)

    // 2.跳转到的登录页面
    location.href = "http://localhost:8084/toLogin?returnUrl="+url; // get


}