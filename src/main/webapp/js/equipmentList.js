/**
 * 
 */

$(function() {
	showEquipment();

})

var table;
function showEquipment() {
	$("div[name='tab']").css('display', 'none');
	$("#tab1").css('display', 'block');
	var url = window.location.href;
	var id = url.substring(url.indexOf("id") + 3, url.length);

	$.ajax({
		"type" : 'post',
		"url" : "/IotCloud-background-manager/rest/equipment/getAuitById",
		"dataType" : "json",
		"data" : {
			"registerId" : id
		},
		success : function(data) {
			var info = data.data;
			if (data.result_code == 0) {
				$("#productTitle").html(info.equipmentName + "设备列表");

			} else {
				alert("获取失败");
			}
		}
	})

	$.ajax({
		type : "post",
		url : "/IotCloud-background-manager/rest/product/getDeviceList",
		datatype : "json",
		"data" : {
			"registerId" : id
		},

		success : function(data) {
			var info = data.data;
			table = $('#equipmentLi').DataTable(
					{
						"ordering" : true,
						"bProcessing" : true,
						"bPaginate" : true,
						"pagingType" : "full_numbers",
						"bFilter" : true,
						"bLengthChange" : true,
						"bInfo" : true,
						"bSort" : true,
						"retrieve" : true,
						"destroy" : true,
						"oLanguage" : {
							"sZeroRecords" : "没有数据",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "前一页",
								"sNext" : "后一页",
								"sLast" : "尾页"
							}
						},

						"columnDefs" : [
								{
									"targets" : [ 0 ],
									"render" : function(data, type, full) {
										var sex;
										if (full.states == 0) {
											sex = "开";
										} else {
											sex = "关";
										}
										return "<div>创建时间：'" + full.updatedTime
												+ "'</div>" + "<div>状态：'" + sex
												+ "'</div>";
									}
								},
								{
									"targets" : [ 1 ],
									"render" : function(data, type, full) {

										return "<div>类型：'"
												+ full.audit.brandType.typeName
												+ "'</div>" + "<div>通信方式：'"
												+ full.audit.accessMethod
												+ "'</div>";
									}
								}

						],
						data : info,
						columns : [ {
							data : ''
						}, {
							data : ""
						},

						]
					});

		}

	})
}
var tables;
function attribution() {
	$("div[name='tab']").css('display', 'none');
	$("#tab2").css('display', 'block');
	var url = window.location.href;
	var id = url.substring(url.indexOf("id") + 3, url.length);
	$
			.ajax({
				type : "post",
				url : "/IotCloud-background-manager/rest/equipment/getItemById",
				datatype : "json",
				"data" : {
					"registerId" : id
				},

				success : function(data) {
					var info = data.data;
					tables = $('#attributeList')
							.DataTable(
									{
										"ordering" : true,
										"bProcessing" : true,
										"bPaginate" : true,
										"pagingType" : "full_numbers",
										"bFilter" : true,
										"bLengthChange" : true,
										"bInfo" : true,
										"bSort" : true,
										"retrieve" : true,
										"destroy" : true,
										"oLanguage" : {
											"sZeroRecords" : "没有数据",
											"oPaginate" : {
												"sFirst" : "首页",
												"sPrevious" : "前一页",
												"sNext" : "后一页",
												"sLast" : "尾页"
											}
										},

										"columnDefs" : [
												{
													"targets" : [ 2 ],
													"render" : function(data,
															type, full) {

														if (data == 1) {
															return "可写";
														} else {
															return "只读";
														}
													}
												},
												{
													"targets" : [ 3 ],
													"render" : function(data,
															type, full) {

														if (data == 0) {
															return "有符号整型";
														}
														if (data == 1) {
															return "无符号整型";
														}
														if (data == 2) {
															return "布尔型";
														}
														if (data == 3) {
															return "字符串";
														}
														if (data == 4) {
															return "小数";
														}
														if (data == 5) {
															return "时间";
														} else {
															return "枚举";
														}
													}
												},
												{
													"targets" : [ 5 ],
													"defaultContent" : "<button name='action' title='编辑'  style='cursor:pointer;color:#009ACD; background-color:transparent;border:none;outline:none;'><img src='./images/action.jpg'></button>"
												}

										],
										data : info,
										columns : [ {
											data : 'itemDescription'
										}, {
											data : 'itemTitle'
										}, {
											data : 'itemOperation'
										}, {
											data : 'itemType'
										}, {
											data : ''
										},

										]
									});

				}

			})

	$('#attributeList tbody')
			.on(
					'click',
					"button[name='action']",
					function() {
						$("#editMinNum").val("");
						$("#editMaxNum").val("");
						$("#resolving").val("");
						$("#increment").val("");
						$("#editMinString").val("");
						$("#editMaxString").val("");
						$("#editMaxDecimal").val("");
						$("#editMinTime").val("");
						$("#editMaxTime").val("");
						$("#editEnumContent").val("");

						var data = tables.row($(this).parents('tr')).data();
						var itemId = data.itemId;
						$('#editAttribute').dialog({
							width : 700,
							height : 600
						});
						$("#editName").val(data.itemTitle);
						$("#editDescription").val(data.itemDescription);
						$("#editOpType").val(data.itemOperation);
						$("#editDataType").val(data.itemType);
						$("#editContent").val();
						$('div[name="editStatu"]').css("display", "none");

						switch (data.itemType) {
						case 0:
							$("#editInteger").css("display", "block");
							var min = data.itemContent.substring(
									data.itemContent.indexOf("min") + 5,
									data.itemContent.indexOf(","));
							var max = data.itemContent.substring(
									data.itemContent.indexOf("max") + 5,
									data.itemContent.length - 1);
							$("#editMinNum").val(min);
							$("#editMaxNum").val(max);
							$("#resolving").val(data.resolving);
							$("#increment").val(data.increment);
							break;
						case 1:
							$("#editInteger").css("display", "block");
							var min = data.itemContent.substring(
									data.itemContent.indexOf("min") + 5,
									data.itemContent.indexOf(","));
							var max = data.itemContent.substring(
									data.itemContent.indexOf("max") + 5,
									data.itemContent.length - 1);
							$("#editMinNum").val(min);
							$("#editMaxNum").val(max);
							$("#resolving").val(data.resolving);
							$("#increment").val(data.increment);
							break;
						case 3:
							$("#editString").css("display", "block");
							var min = data.itemContent.substring(
									data.itemContent.indexOf("min") + 5,
									data.itemContent.indexOf(","));
							var max = data.itemContent.substring(
									data.itemContent.indexOf("max") + 5,
									data.itemContent.length - 1);
							$("#editMinString").val(min);
							$("#editMaxString").val(max);
							break;
						case 4:
							$("#editDecimal").css("display", "block");
							var va = data.itemContent.substring(
									data.itemContent.indexOf("max") + 5,
									data.itemContent.length - 2);
							$("#editMaxDecimal").val(va);
							break;
						case 5:
							$("#editTime").css("display", "block");
							var min = data.itemContent.substring(
									data.itemContent.indexOf("min") + 6,
									data.itemContent.indexOf(",") - 1);
							var max = data.itemContent.substring(
									data.itemContent.indexOf("max") + 6,
									data.itemContent.length - 2);
							$("#editMinTime").val(min);
							$("#editMaxTime").val(max);
							if (min.length == 10) {
								$("#editTimeType").val(1);
							}
							if (min.length == 19) {
								$("#editTimeType").val(2);
							}
							if (min.length == 5) {
								$("#editTimeType").val(3);
							}
							if (min.length == 8) {
								$("#editTimeType").val(4);
							}
							break;
						case 6:
							$("#editEnum").css("display", "block");
							$("#editEnumContent").val(data.itemContent);
							break;
						}

						$("#editDataType").change(function() {
							$('div[name="editStatu"]').css("display", "none");
							var data = $(this).val();
							if (data == 0 || data == 1) {
								$("#editInteger").css("display", "block");
							}
							if (data == 3) {
								$("#editString").css("display", "block");
							}
							if (data == 4) {
								$("#editDecimal").css("display", "block");
							}
							if (data == 5) {
								$("#editTime").css("display", "block");
							}
							if (data == 6) {
								$("#editEnum").css("display", "block");
							}
						});

						$("#editTimeType")
								.change(
										function() {
											var data = $(this).val();
											if (data == 1) {
												$('#editMinTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"yyyy-MM-dd"})');
												$('#editMaxTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"yyyy-MM-dd"})');
											}
											if (data == 2) {
												$('#editMinTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"yyyy-MM-dd HH:mm:ss"})');
												$('#editMaxTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"yyyy-MM-dd HH:mm:ss"})');
											}
											if (data == 3) {
												$('#editMinTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"HH:mm"})');
												$('#editMaxTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"HH:mm"})');
											}
											if (data == 4) {
												$('#editMinTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"HH:mm:ss"})');
												$('#editMaxTime')
														.attr('onclick',
																'WdatePicker({dateFmt:"HH:mm:ss"})');
											}
										})

						$("#editAction")
								.click(
										function() {
											var title = $("#editName").val();
											var description = $(
													"#editDescription").val();
											var opType = $("#editOpType").val();
											var dataType = $("#editDataType")
													.val();
											var leng;
											var max;
											var min;
											var itemContent;

											var resolving;
											var increment;
											if (dataType == 0 || dataType == 1) {
												max = $("#editMaxNum").val();
												min = $("#editMinNum").val();

												resolving = $("#resolving")
														.val();
												increment = $("#increment")
														.val();
												var bitmax = parseInt(max)
														.toString(2);
												var bitmin = parseInt(min)
														.toString(2);
												leng = (bitmax.length >= bitmin.length) ? bitmax.length
														: bitmin.length;
												itemContent = '{"min":' + min
														+ ',"max":' + max + '}';
											}
											if (dataType == 3) {
												max = $("#editMaxString").val();
												min = $("#editMinString").val();
												var bitmax = parseInt(max)
														.toString(2);
												leng = bitmax.length;
												itemContent = '{"min":' + min
														+ ',"max":' + max + '}';
											}
											if (dataType == 5) {
												max = $("#editMaxTime").val();
												min = $("#editMinTime").val();
												leng = 56;
												itemContent = '{"min":' + '"'
														+ min + '"' + ',"max":'
														+ '"' + max + '"' + '}';
											}
											if (dataType == 2) {
												leng = 8;
												itemContent = '{0:"false",1:"true"}';
											}
											if (dataType == 4) {
												var con = $("#editMaxDecimal")
														.val();
												leng = 8;
												itemContent = "{max:'" + con
														+ "'}";
											}
											if (dataType == 6) {
												var con1 = $("#editEnumContent")
														.val();
												leng = 8;
												itemContent = con1;
											}
											var url = window.location.href;
											var id = url.substring(url
													.indexOf("id") + 3,
													url.length);

											$
													.ajax({
														"type" : 'post',
														"url" : "/IotCloud-background-manager/rest/equipment/editAttr",
														"dataType" : "json",
														"data" : {
															"length" : leng,
															"itemId" : itemId,
															"title" : title,
															"description" : description,
															"opType" : opType,
															"dataType" : dataType,
															"itemContent" : itemContent,
															"resolving" : resolving,
															"increment" : increment

														},
														success : function(data) {
															if (data.result_code == 0) {
																alert("编辑成功！");
																window.location.href = "equipmentList.html?id="
																		+ id;
															} else {
																alert("编辑失败");
															}
														}
													})
										})

					})

}

function addAttr() {
	$('#addAttribute').dialog({
		width : 600,
		height : 400
	});

	$("#dataType").change(function() {
		$('div[name="statu"]').css("display", "none");
		var data = $(this).val();
		if (data == 0 || data == 1) {
			$("#integer").css("display", "block");
		}
		if (data == 3) {
			$("#string").css("display", "block");
		}
		if (data == 4) {
			$("#decimal").css("display", "block");
		}
		if (data == 5) {
			$("#time").css("display", "block");
		}
		if (data == 6) {
			$("#enum").css("display", "block");
		}
	});

	$("#timeType").change(
			function() {
				var data = $(this).val();
				if (data == 1) {
					$('#minTime').attr('onclick',
							'WdatePicker({dateFmt:"yyyy-MM-dd"})');
					$('#maxTime').attr('onclick',
							'WdatePicker({dateFmt:"yyyy-MM-dd"})');
				}
				if (data == 2) {
					$('#minTime').attr('onclick',
							'WdatePicker({dateFmt:"yyyy-MM-dd HH:mm:ss"})');
					$('#maxTime').attr('onclick',
							'WdatePicker({dateFmt:"yyyy-MM-dd HH:mm:ss"})');
				}
				if (data == 3) {
					$('#minTime').attr('onclick',
							'WdatePicker({dateFmt:"HH:mm"})');
					$('#maxTime').attr('onclick',
							'WdatePicker({dateFmt:"HH:mm"})');
				}
				if (data == 4) {
					$('#minTime').attr('onclick',
							'WdatePicker({dateFmt:"HH:mm:ss"})');
					$('#maxTime').attr('onclick',
							'WdatePicker({dateFmt:"HH:mm:ss"})');
				}
			})

	$("#confirm")
			.click(
					function() {
						var title = $("#name").val();
						var description = $("#description").val();
						var opType = $("#opType").val();
						var dataType = $("#dataType").val();
						var leng;
						var max;
						var min;
						var itemContent;
						if (dataType == 0 || dataType == 1) {
							max = $("#maxNum").val();
							min = $("#minNum").val();
							var bitmax = parseInt(max).toString(2);
							var bitmin = parseInt(min).toString(2);
							leng = (bitmax.length >= bitmin.length) ? bitmax.length
									: bitmin.length;
							itemContent = '{"min":' + min + ',"max":' + max
									+ '}';
						}
						if (dataType == 3) {
							max = $("#maxString").val();
							min = $("#minString").val();
							var bitmax = parseInt(max).toString(2);
							leng = bitmax.length;
							itemContent = '{"min":' + min + ',"max":' + max
									+ '}';
						}
						if (dataType == 5) {
							max = $("#maxTime").val();
							min = $("#minTime").val();
							leng = 56;
							itemContent = '{"min":' + '"' + min + '"'
									+ ',"max":' + '"' + max + '"' + '}';
						}
						if (dataType == 2) {
							leng = 8;
							itemContent = '{0:"false",1:"true"}';
						}
						if (dataType == 4) {
							var con = $("#maxDecimal").val();
							leng = 8;
							itemContent = "{max:'" + con + "'}";
						}
						if (dataType == 6) {
							var con1 = $("#enumContent").val();
							leng = 8;
							itemContent = con1;
						}
						var url = window.location.href;
						var id = url.substring(url.indexOf("id") + 3,
								url.length);

						$
								.ajax({
									"type" : 'post',
									"url" : "/IotCloud-background-manager/rest/equipment/addAttr",
									"dataType" : "json",
									"data" : {
										"length" : leng,
										"registerId" : id,
										"title" : title,
										"description" : description,
										"opType" : opType,
										"dataType" : dataType,
										"itemContent" : itemContent,

									},
									success : function(data) {
										if (data.result_code == 0) {
											alert("添加成功！");
											window.location.href = "equipmentList.html?id="
													+ id;
										} else {
											alert("添加失败");
										}
									}
								})
					})
}

function licenseBox() {
	$('#licenseBox').dialog({
		width : 300,
		height : 200
	});
	var url = window.location.href;
	var id = url.substring(url.indexOf("id") + 3, url.length);
	$("#addLicense").click(function() {
		var sn = $("#productSn").val();
		$.ajax({
			"type" : 'post',
			"url" : "/IotCloud-background-manager/rest/protocol/createLicense",
			"dataType" : "text",
			"data" : {
				"deviceSn" : sn,
				"registerId" : id
			},
			success : function(data) {
				$("#licenseText").css("display", "block");
				$("#licenseText").val(data);
			}
		})
	})
}