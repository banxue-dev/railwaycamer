$(function() {
	validateRule();
});
$.validator.setDefaults({
	// 提交表单时做校验
	onsubmit: true,
	// 焦点自动定位到第一个无效元素
	focusInvalid: true,
	// 元素获取焦点时清除错误信息
	focusCleanup: true,
	//校验通过后的回调，可用来提交表单
	submitHandler : function() {
		update();
	}
});
function update() {
	var data = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/railway/person/update",
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
			loginName : {
				required : true,
				remote: {
					url: "/railway/person/check", // 校验登录名是否已经存在
					type: "post",
					dataType: "json",
					data: {
						loginName:function(){
								return $("#loginName").val();
						},
						personId:function(){
								return $("#id").val();
						}
					}
				}
			},
			password : {
				required : true
			}
		},
		messages : {
			loginName : {
				required : icon + "请输入照片上报账号"
			},
			password : {
				required : icon + "请输入密码"
			}
		}
	});
}