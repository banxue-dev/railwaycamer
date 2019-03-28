$(function() {
	initTree();
	initMultiselect();
	validateRule();
});
$.validator.setDefaults({
	ignore:":hidden:not(select)",
	// 提交表单时做校验
	onsubmit: true,
	// 焦点自动定位到第一个无效元素
	focusInvalid: true,
	// 元素获取焦点时清除错误信息
	focusCleanup: true,
	//校验通过后的回调，可用来提交表单
	submitHandler : function() {
		save();
	}
});

function initTree(){
	 $('#treebox').treeinit('/railway/station/listTree',{
    	bindTag:'tree,tree1',
    	childClick: function(d,tag){
    		var tagId = $(tag).attr("id");
    		if('tree' == tagId){
    			var name = $(d).text();
        		var stationId = $(d).attr('data-id');
        		$('#tree').val(name);
        		$('#startStationId').val(stationId);
    		} else {
    			var name = $(d).text();
        		var stationId = $(d).attr('data-id');
        		$('#tree1').val(name);
        		$('#endStationId').val(stationId);
    		}
    		
    	}
    });
}

// 初始化数据
function initMultiselect() {
	// 获取person数据
	$.ajax({
		cache : true,
		type : "POST",
		url : "/railway/person/list",
		data : {
			page: 1,
			limit: 999999
		}, 
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				var options = '';
				for (i=0; i< r.data.length; i++){
					var person = r.data[i];
					
					options += '<option value="'+person.id+'">'+person.name+'</option>'
				}
				
				$('#liOption').html(options);
			}

		}
	});
	
	// 初始化多选框
	$("#liOption").multiselect2side({
		selectedPosition: 'left',
		moveOptions: false,
		labelsx: '拍摄人员',
		labeldx: '可选人员'
	});
}

// 保存
function save() {
	// personIds 赋值
	var personIds = $('#liOption').val();
	if(personIds){
		$('#personIds').val(personIds.join(','));
	}
	
	var data = $('#signupForm').serialize();
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/railway/order/add",
		data : data, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			trainNo : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			/*startStationName : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			endStationName : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			consignor : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			consignee : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			productName : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			trainType : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			projectNo : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},
			loadingLine : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length == 0;
				}
			},*/
			selectPersonIds : {
				required : function(e) {
					var b = ! $("#liOption").val();
					return b;
				}
			}
		},
		messages : {
			trainNo : {
				required : icon + "请输入车厢号不能为空"
			},
			startStationName : {
				required : icon + "请选择发站点"
			},
			endStationName : {
				required : icon + "请选择到站点"
			},
			consignor : {
				required : icon + "请输入托货人不能为空"
			},
			consignee : {
				required : icon + "请输入收货人不能为空"
			},
			productName : {
				required : icon + "请输品名不能为空"
			},
			trainType : {
				required : icon + "请输入车型不能为空"
			},
			projectNo : {
				required : icon + "请输入方案编号不能为空"
			},
			loadingLine : {
				required : icon + "请输入装车路线不能为空"
			},
			selectPersonIds : {
				required : icon + "请选择拍照人员"
			}
		}
	});
}
