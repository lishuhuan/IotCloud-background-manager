/**
 * 显示用户的产品和在线设备	
 * 李树桓 
 * 2016-04-27
 */


$(function() {
	showProduct();
	
})

function showProduct(){
	$('#equipment').empty();
	$.ajax({
		"type" : 'post',
		"url" : "/IotCloud-background-manager/rest/equipment/getProductByUser",
		"dataType" : "json",

		success : function(data) {
			if(data.result_code==0){
				$.each(data.data, function(i, branch) {
					$("#equipment").append(
							
							"<font onclick=displayEquipment('"+branch.brand.brandId+"') style='cursor: pointer;color: blue;'>"+branch.equipmentName+"</font><br>");
					
					/*if(i==0){
						$("#equipment").append(
								
								"<p>"+branch.equipmentName+"</p>"+"<input type='button' value='增加数据点' onclick='addAttr("+branch.registerId+")'/>");
					}*/
				
				
				});
            }else{
                alert("操作失败");
            }
		}
	})
}


function displayEquipment(e){
	window.location.href="equipmentList.html?id="+e;
	
	
	/*$('#equipment').empty();
	$("#equipment").append(
			
			"<p>"+t+"</p>"+"<input type='button' value='增加数据点' onclick='addAttr("+e+")'/>");*/
}

function addProduct(){
	
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
					var option = "<option value='" + p.id + "'>" + p.typeName + "</option>";
					$("#productType").append(option);
				});
			}
		})
		
	
}


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
	var iotType=$("#iotType").val();
	var connectType=$("#connectType").val();
	if (company == "") {
		alert("公司名不能为空，请核对!");
		document.getElementById("company").focus();
		return false;
	} if (model == "select") {
		alert("型号不能为空，请核对!");
		document.getElementById("model").focus();
		return false;
	}else if (productType == 0) {
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
				"function":functiondes,
				"connectType":connectType,
				"iotType":iotType
				

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
