/**
 * 
 */
var table;
$(function() {
	$("#tab1").css('display','block'); 
	$.ajax({
		type: "post",
		url: "/v1/product/getAuditList",
		datatype: "json",
/* 		beforeSend : function(){
			$('#loadingalert').dialog({
				width:300
			});
		},
		complete : function(){

			$('#loadingalert').dialog('close');
			$("#alert_message").innerHTML="数据加载中请稍候。。。"
		}, */
		success: function (data) {
			 var info = data.data;
			 table = $('#auditlist').DataTable({
				  "ordering":true,
				  "bProcessing": true,
				  "bPaginate": true,
				  "pagingType": "full_numbers",
				  "bFilter": true,
				  "bLengthChange": true,
				  "bInfo": true,
				  "bSort": true,
				  "retrieve":true,
				  "destroy": true,
				  "oLanguage": {
					  "sZeroRecords": "没有数据",
					  "oPaginate": {
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "尾页"
					}
				},

				"columnDefs": [
					{
					"targets": [4],
					"defaultContent": "<button name='shenhe' style='cursor:pointer;color:#009ACD; margin-left:20px; background-color:transparent;border:none;outline:none;'>等待审核</button>" 
				},
					{ "bSearchable": false, "aTargets": [1,4]}
				],
				data:info,
				columns: [
					{data: 'company'},
					{data: "brandType.typeName"},
					{data: 'equipmentName'},
					{data: 'brandSeries.brandSeriesName'},
				]
			});
			

		}

	})
	
	$('#auditlist tbody').on( 'click', "button[name='shenhe']", function (){
		var data=table.row( $(this).parents('tr') ).data();
		$('#auit').dialog({
	    	width:600,
	    	height:400
	    });
		$("#registerId").html(data.registerId);
		$("#company").html(data.company);
		$("#type").html(data.brandType.typeName);
		$("#productName").html(data.equipmentName);
		$("#model").html(data.brandSeries.brandSeriesName);
		$("#assess").html(data.accessMethod);
		$("#net").html(data.netMethod);
		$("#ram").html(data.ramSize);
		$("#rom").html(data.romSize);
		$("#oprate").html(data.operateSystem);
		$("#description").html(data.functionDescribe);
		$("#auditTime").html(data.auditTime);
		
			
		$("#close").click(function(){
			$('#auit').dialog('close');
		})
	})
	
	
	
})

function acceptIt(){
	var id=$("#registerId").html();
	$.ajax({
		"type" : 'post',
		"url" : "/v1/product/throughApply",
		"dataType" : "json",
		"data" : {
			"registerId" : id
			

		},
		success : function(data) {
			if(data.result_code==0){
                alert("申請通過！");
                window.location.href="admin.html";
            }else{
                alert("申請失败");
            }
		}
	})
}

function rejectIt(){
	var id=$("#registerId").html();
	$.ajax({
		"type" : 'post',
		"url" : "/v1/product/rejectApply",
		"dataType" : "json",
		"data" : {
			"registerId" : id,
			

		},
		success : function(data) {
			if(data.result_code==0){
                alert("操作成功！");
                window.location.href="admin.html";
            }else{
                alert("操作失败");
            }
		}
	})
}

function displayTab1(){
	$("div[name='tab']").css('display','none'); 
	$("#tab1").css('display','block'); 
}

var tables
function displayTab2(){
	$("div[name='tab']").css('display','none'); 
	$("#tab2").css('display','block'); 
	$.ajax({
		type: "post",
		url: "/v1/product/getAuditByState",
		datatype: "json",
		success: function (data) {
			 var info = data.data;
			 tables = $('#auditAccess').DataTable({
				  "ordering":true,
				  "bProcessing": true,
				  "bPaginate": true,
				  "pagingType": "full_numbers",
				  "bFilter": true,
				  "bLengthChange": true,
				  "bInfo": true,
				  "bSort": true,
				  "retrieve":true,
				  "destroy": true,
				  "oLanguage": {
					  "sZeroRecords": "没有数据",
					  "oPaginate": {
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "尾页"
					}
				},

				"columnDefs": [
					/*{
					"targets": [4],
					"defaultContent": "<button name='shenhe' style='cursor:pointer;color:#009ACD; margin-left:20px; background-color:transparent;border:none;outline:none;'>等待审核</button>" 
				},*/
					{ "bSearchable": false, "aTargets": [1,4]}
				],
				data:info,
				columns: [
					{data: 'company'},
					{data: "brandType.typeName"},
					{data: 'equipmentName'},
					{data: 'brandSeries.brandSeriesName'},
					{data: 'accessMethod'},
					{data: 'netMethod'},
					{data: 'operateSystem'},
					{data: 'ramSize'},
					{data: 'romSize'},
					{data: 'functionDescribe'},
				]
			});
			

		}

	})
}

var rejectTable;
function displayTab3(){
	$("div[name='tab']").css('display','none'); 
	$("#tab3").css('display','block'); 
	$.ajax({
		type: "post",
		url: "/v1/product/getAuditReject",
		datatype: "json",
		success: function (data) {
			 var info = data.data;
			 rejectTable = $('#auditNotAccess').DataTable({
				  "ordering":true,
				  "bProcessing": true,
				  "bPaginate": true,
				  "pagingType": "full_numbers",
				  "bFilter": true,
				  "bLengthChange": true,
				  "bInfo": true,
				  "bSort": true,
				  "retrieve":true,
				  "destroy": true,
				  "oLanguage": {
					  "sZeroRecords": "没有数据",
					  "oPaginate": {
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "尾页"
					}
				},

				"columnDefs": [
					/*{
					"targets": [4],
					"defaultContent": "<button name='shenhe' style='cursor:pointer;color:#009ACD; margin-left:20px; background-color:transparent;border:none;outline:none;'>等待审核</button>" 
				},*/
					{ "bSearchable": false, "aTargets": [1,4]}
				],
				data:info,
				columns: [
					{data: 'company'},
					{data: "brandType.typeName"},
					{data: 'equipmentName'},
					{data: 'brandSeries.brandSeriesName'},
					{data: 'accessMethod'},
					{data: 'netMethod'},
					{data: 'operateSystem'},
					{data: 'ramSize'},
					{data: 'romSize'},
					{data: 'functionDescribe'},
				]
			});
			

		}

	})
}

function displayTab4(){
	$("div[name='tab']").css('display','none'); 
	$("#tab4").css('display','block'); 
	$("#productType option:gt(0)").remove();
	$.ajax({
		"type" : 'post',
		"url" : "/v1/product/getAuditByState",
		"dataType" : "json",

		success : function(data) {
			var product=data.data;
			$.each(product,function(k,p){
				var option = "<option value='" + p.registerId + "'>" + p.equipmentName + "</option>";
				$("#productType").append(option);
			});
		}
	})
}

var deviceTable;
function findDevice(){
	var product=$("#productType").val();
	if(product==0){
		return false;
	}
	else{
		$.ajax({
			type: "post",
			url: "/v1/product/getDeviceList",
			datatype: "json",
			data : {
				"registerId" : product,
				

			},
			success: function (data) {
				 var info = data.data;
				 $('#devicelist').dataTable().fnClearTable();
				 $('#devicelist').dataTable().fnDestroy();
				 deviceTable = $('#devicelist').DataTable({
					  "ordering":true,
					  "bProcessing": true,
					  "bPaginate": true,
					  "pagingType": "full_numbers",
					  "bFilter": true,
					  "bLengthChange": true,
					  "bInfo": true,
					  "bSort": true,
					  "retrieve":true,
					  "destroy": true,
					  "oLanguage": {
						  "sZeroRecords": "没有数据",
						  "oPaginate": {
							"sFirst": "首页",
							"sPrevious": "前一页",
							"sNext": "后一页",
							"sLast": "尾页"
						}
					},

					"columnDefs": [{
						"targets" : [ 4 ],
						"render" : function(data, type, full) {
							if (data == 0) {
								return "<td>开</td>";
							}
							 else {
								return "<td>关</td>";
							}
						}

					}, 
					               
						/*{
						"targets": [4],
						"defaultContent": "<button name='shenhe' style='cursor:pointer;color:#009ACD; margin-left:20px; background-color:transparent;border:none;outline:none;'>等待审核</button>" 
					},*/
						{ "bSearchable": false, "aTargets": [1,4]}
					],
					data:info,
					columns: [
						{data: 'deviceId'},
						{data: "audit.equipmentName"},
						{data: 'updatedTime'},
						{data: 'lastLogin'},
						{data: 'states'}

					]
				});
				

			}

		})
	}
}