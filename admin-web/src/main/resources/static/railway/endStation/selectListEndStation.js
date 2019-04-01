var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
// 选择
function select (endStationId,endStationName){
	parent.$('#endStationId').val(endStationId);
	parent.$('#endStationName').val($.trim(endStationName));
    parent.layer.close(index); //关闭当前页面
}