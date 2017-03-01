//域名设置
function Domain_Name(){
    var Domain_Name = 'http://localhost:8082';
    return Domain_Name;
}
//邮箱认证，id为input框的id，btn为提交按钮id，若验证失败，提交按钮不可点击，验证成功可以点击
function testMail(id,btn)
 {
  var temp = document.getElementById(id);
  var temp1 = document.getElementById(btn);
  //对电子邮件的验证
  var myreg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  if(!myreg.test(temp.value))
  {
    alert('提示:请输入有效的E_mail！');
    temp1.disabled=true;
   return 2;
  }else {
  	return 1;
  }
 }
//自定义提示框，结合对应的css使用
 function alertBox_exit(){
 	$(".alertBox-bg").css('display', 'none');
 	$(".alertBox").css('display', 'none');
 }
 function alertBox_open(){
 	$(".alertBox-bg").css('display', 'block');
 	$(".alertBox").css('display', 'block');
 }
//本地验证码
 function yanzheng(num){
    switch (nb) {
        case 1:
            if (num == 4683) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 2:
            if (num == 4985) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 3:
            if (num == 7765) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 4:
            if (num == 9181) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 5:
            if (num == 1107) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 6:
            if (num == 5161) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 7:
            if (num == 4422) {
                return 1;
            }else{
                return 2;
            }
            break;
        case 8:
            if (num == 1227) {
                return 1;
            }else{
                return 2;
            }
            break;
        default:
            return 2;
            break;
    }
}
//验证手机号格式
function reg_phone(phone){
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(16[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
        if (!myreg.test(phone)) {
            return false;
        }else{
            return true;
        }
}
//验证邮箱格式
function reg_email(email){
    var myreg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!myreg.test(email)){
        return false;
    }else {
        return true;
    }
}
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}