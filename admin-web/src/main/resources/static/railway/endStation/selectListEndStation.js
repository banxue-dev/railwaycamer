var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
// 选择
function select (endStationId,endStationName,code,pys){
	var iddom=parent.$('#endStationId');
	
	var namedom=parent.$('#endStationName');
	var codedom=parent.$('#stationCode');
	var pydom=parent.$('#staPys');
	if(iddom){
		iddom.val(endStationId);
	}
	if(namedom){
		namedom.val($.trim(endStationName));
	}
	if(codedom){
		codedom.val($.trim(code));
	}
	if(pydom){
		pydom.val($.trim(pys))
	}
    parent.layer.close(index); //关闭当前页面
}