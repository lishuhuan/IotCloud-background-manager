/**
 * lishuhuan
 * 2016年3月25日
 * 登录页面的登录功能和注册
 */

function login() {
	var username = $("#inputUser").val();
	var password = $("#inputPassword").val();
	if (username == "") {
		alert("用户名不能为空，请核对!");
		document.getElementById("inputUser").focus();
		return false;
	} else if (password == "") {
		alert("密码不能为空，请核对!");
		document.getElementById("inputPassword").focus();
		return false;
	} else {
		$.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/user/login",
			"dataType" : "json",
			"data" : {

				"username" : username,
				"password" : password

			},
			success : function(data) {
				if(data.result_code==0){
					//user_level = resp.result_code;
				    var date=new Date(); 
	                /*date.setTime(date.getTime()+10*60*1000); //设置date为当前时间+？分，记得修改，调试时候会很烦
	                document.cookie="query=user_level; expires="+date.toGMTString(); //将date赋值给expires  
*/				    window.location.href="homePage.html";



				}else if(data.result_code==6){
					
						alert("密码错误");
					
				}else if(data.result_code==19){
					 alert("用户不存在");
				}
			}
		})
	}
}

function register() {
	var username = $("#username").val();
	var password = $("#password").val();
	var checkPassword = $("#checkPassword").val();
	var phoneNumber=$("#phoneNumber").val();
	var verifyCode=$("#verifyCode").val();
	var email=$("#email").val();
	if (username == "") {
		alert("用户名不能为空，请核对!");
		document.getElementById("username").focus();
		return false;
	} else if (password == "") {
		alert("密码不能为空，请核对!");
		document.getElementById("password").focus();
		return false;
	} else if(checkPassword!=password){
		alert("两次密码输入不一致，请重新输入!");
		return false;
	} else if(phoneNumber==""){
		alert("手机号不能为空，请核对!");
		document.getElementById("phoneNumber").focus();
		return false;
	} else if (verifyCode == "") {
		alert("验证码不能为空，请核对!");
		document.getElementById("verifyCode").focus();
		return false;
	}else {
		$.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/user/register",
			"dataType" : "json",
			"data" : {
				"username" : username,
				"password" : password,
				"phoneNumber":phoneNumber,
				"email":email,
				"verifyCode":verifyCode

			},
			success : function(data) {
				if(data.result_code==0){
	                alert("添加成功");
	                window.location.href="login.html";
	            }else{
	                alert("添加失败");
	            }
			}
		})
	}
}

$(function() {
	$('#dialog_link').click(function () {
	    $('#dialog_simple').dialog({
	    	width:700,
	    	height:600
	    });
	    return false;
	});
})


function  message(){
	var phoneNumber=$("#username").val();
	if(phoneNumber==""){
		alert("手机号不能为空，请核对!");
		document.getElementById("username").focus();
		return false;
	}else {
		$.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/user/message",
			"dataType" : "json",
			"data" : {
				"phoneNumber":phoneNumber

			},
			success : function(data) {
				if(data.result_code==0){
	                showtime();
	            }else{
	                alert("添加失败");
	            }
			}
		})
	}
}


function showtime(){
	var oBtn = document.getElementById('message');
	oBtn.disabled = 'true';
	for(i=1;i<=60;i++) { 
		 window.setTimeout("update(" + i + ")", i*1000 ); 
     } 
}

function update(num){
	if(num==60){
		document.getElementById("message").value="重新发送";
		$("#message").attr("disabled", false);
	}
	else{
		var time=60-num;
		document.getElementById("message").value=time+"秒后重新发送";
	}
}
