var prefix = "/railway/station";
$(function() {
    validateRule();
    $('input[name=isBottom]').change(function(){
		if ($('#showCheckList').attr("checked")=='checked') {
	       $('#endStationName').attr('readonly','readonly').val('').attr('placeholder','点击选择站点');
	       $('#stationCode').attr('readonly','readonly').attr('placeholder','点击选择站点').val('');
	       $('#staPys').attr('readonly','readonly').attr('placeholder','点击选择站点').val('');
	       $('#endStationName,#stationCode,#staPys').bind('click',function(){
	    	   layer.open({
	    			type : 2,
	    			title : '请选择到站点',
	    			maxmin : true,
	    			shadeClose : false, // 点击遮罩关闭层
	    			area : [ '80%', '80%' ],
	    			content : '/railway/station/endStation/selectList' // iframe的url
	    		});
	       })
	    }else{
	    	 $('#endStationName').removeAttr('readonly').attr('placeholder','请输入站点名称');
	         $('#stationCode').removeAttr('readonly').attr('placeholder','请输入站点编码');
	         $('#staPys').removeAttr('readonly').attr('placeholder','请输入检索拼音');
	         $('#endStationName,#stationCode,#staPys').unbind("click");
	    }
    })
});
$.validator.setDefaults({
    submitHandler : function() {
        submit();
    }
});
function submit() {
    $.ajax({
        cache : true,
        type : "POST",
        url : prefix + "/save",
        data : $('#submitForm').serialize(),
        async : false,
        error : function(request) {
            laryer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.load();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                layer.alert(data.msg)
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#submitForm").validate({
        rules : {
            name : {
                required : true
            },
            staPys : {
		    	required : true
		    }
        },
        messages : {
            name : {
                required : icon + "请输入站点名称"
            },staPys : {
            	required : icon + "请输入站点的首字母拼音"
            }
        }
    })
}