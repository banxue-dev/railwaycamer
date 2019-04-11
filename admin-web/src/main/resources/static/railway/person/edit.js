$(function() {
	initTree({});
	validateRule();
	/*$('input[name=type]').change(function(){
		$('#stationId,#tree').val('');
		initTreeParam();
    })*/
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

//手机号码验证  
jQuery.validator.addMethod("isMobile", function(value, element) {  
	 var length = value.length;  
	 var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  
	 return this.optional(element) || (length == 11 && mobile.test(value));  
}, "请正确填写手机号码");

function initTreeParam(){
	if ($('#adminPerson').attr("checked")=='checked') {
		initTree({isBottom:0});
	}else{
		initTree({});
	}
}
function initTree(param){
	 $('#treebox').treeinit('/railway/station/listTree',{
    	bindTag:'tree',
    	urlData:param,
    	childClick: function(d){
    		var name = $(d).text();
    		var stationId = $(d).attr('data-id');
    		$('#tree').val(name);
    		$('#stationId').val(stationId);
    	}
    });
}

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
			name : {
				required : true
			},
			psersonNo : {
				required : function(e){
					var str = $(e).val();
					return str == null || str.replace(/(^\s*)|(\s*$)/g, "").length > 6;
				}
			},
			stationName : {
				required : true
			},
			phone : {
				required : true,
				minlength : 11, 
	            isMobile : true
			}
		},
		messages : {
			loginName : {
				required : icon + "请输入照片上报账号",
				remote : icon + "登录账号已经存在"
			},
			name : {
				required : icon + "姓名必填"
			},
			psersonNo : {
				required : icon + "请输入工号且不得大于6位。"
			},
			stationName : {
				required : icon + "请选择站点"
			},
			phone : {
				required : icon + "请输入手机号",
				minlength : "不能小于11个字符",  
			    isMobile : "请正确填写手机号码" 
			}
		}
	});
}