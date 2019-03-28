var prefix = "/railway/station";
$(function() {
    validateRule();
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
        url : prefix + "/update",
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
            },
            staPys : {
            	required : icon + "请输入站点的首字母拼音"
            }
        }
    })
}