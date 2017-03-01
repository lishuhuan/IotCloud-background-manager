/**
 * 
 */

$(function() {
	$('#product').click(function () {
	    $('#productInfo').dialog({
	    	width:700,
	    	height:600
	    });
	    $.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/product/getTypeList",
			"dataType" : "json",
			
			success : function(data) {
				var typeList=data.data;
				$.each(typeList,function(k,p){
					var option = "<option value='" + p.typeId + "'>" + p.typeName + "</option>";
					$("#productType").append(option);
				});
			}
		})
	});
	
	
})


function product(){
	var company = $("#company").val();
	var productType = $("#productType").val();
	var productName = $("#productName").val();
	var model = $("#model").val();
	var access = $("#access").val();
	var network=$("#network").val();
	var os=$("#os").val();
	var ram=$("#ram").val();
	var rom=$("#rom").val();
	var functiondes=$("#function").val();
	
	if (company == "") {
		alert("公司名不能为空，请核对!");
		document.getElementById("company").focus();
		return false;
	} else if (productType == 0) {
		alert("产品类型不能为空，请核对!");
		document.getElementById("productType").focus();
		return false;
	} else if(access==""){
		alert("接入方式不能为空，请核对!");
		document.getElementById("access").focus();
		return false;
	} else if (network == "") {
		alert("联网方式不能为空，请核对!");
		document.getElementById("network").focus();
		return false;
	}
	else if (os == "") {
		alert("操作系统不能为空，请核对!");
		document.getElementById("os").focus();
		return false;
	}
	else if (ram == "") {
		alert("ram大小不能为空，请核对!");
		document.getElementById("ram").focus();
		return false;
	}
	else if (rom == "") {
		alert("rom大小不能为空，请核对!");
		document.getElementById("rom").focus();
		return false;
	}
	else {
		$.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/product/audit",
			"dataType" : "json",
			"data" : {
				"company" : company,
				"productType" : productType,
				"productName":productName,
				"model":model,
				"access":access,
				"network":network,
				"os":os,
				"ram":ram,
				"rom":rom,
				"function":functiondes
				

			},
			success : function(data) {
				if(data.result_code==0){
	                alert("注册成功，请等待审核");
	                window.location.href="homePage.html";
	            }else{
	                alert("添加失败");
	            }
			}
		})
	}

}