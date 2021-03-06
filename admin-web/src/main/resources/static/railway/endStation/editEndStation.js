$(function() {
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

// 保存
function save() {
	var data = $('#signupForm').serialize();
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/railway/station/update",
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
		},
		messages : {
			trainNo : {
				required : icon + "请输入车厢号不能为空"
			},
		}
	});
}
