var prefix = "/railway/station";
$(document).ready(function () {
    load();
});

var loadTable = function () {
    $('#exampleTable').bootstrapTreeTable({
        id: 'id',
        code: 'id',
        parentCode: 'parentId',
        type: "GET", // 请求数据的ajax类型
        url: prefix + '/list', // 请求数据的ajax的url
        ajaxParams: {sort: 'id','type':'1'}, // 请求数据的ajax的data属性
        expandColumn: '1',// 在哪一列上面显示展开按钮
        striped: true, // 是否各行渐变色
        bordered: true, // 是否显示边框
        expandAll: false, // 是否全部展开
        // toolbar : '#exampleToolbar',
        columns: [
            {
                title: '站点编号',
                field: 'id',
                visible: false,
                align: 'center',
                valign: 'center',
                width: '10%'
            },
            {
                title: '站点名称',
                field: 'name',
                valign: 'center'
            },
            {
            	title: '站点编码',
            	field: 'stationCode',
            	valign: 'center'
            },
            {
                title: '状态',
                field: 'delState',
                valign: 'center',
                width: '5%',
                formatter:function (item,index) {
                    if (item.delState == 0) {
                        return '<span class="label label-success">启用</span>';
                    }
                    if (item.delState == 1) {
                        return '<span class="label label-danger">禁用</span>';
                    }
                }
            },
            {
                title: '操作',
                align: 'center',
                valign: 'center',
                width: '15%',
                formatter: function (item, index) {
                    var e = '<a class="btn btn-primary btn-sm '
                        + s_edit_h
                        + '" href="javascript:;" mce_href="javascript:;" title="编辑站点" onclick="edit(\''
                        + item.id
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    /*
                     * =1表示是底层节点，
                     */
                    var p='';
                    if(item.isBottom!=1){
                    	p = '<a class="btn btn-primary btn-sm '
                    		+ s_add_h
                    		+ '" href="javascript:;" mce_href="javascript:;" title="添加下级站点" onclick="add(\''
                    		+ item.id
                    		+ '\')"><i class="fa fa-plus"></i></a> ';
                    }
                    var d = '<a class="btn btn-warning btn-sm '
                        + s_remove_h
                        + '" href="javascript:;" title="删除站点"   onclick="removeStation(\''
                        + item.id
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    return p + d + e;
                }
            }]
    });
}

function load() {
    loadTable();
}

function add(pId) {
    layer.open({
        type: 2,
        title: '增加站点',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
		area : [ '80%', '60%' ],
        content: prefix + '/add/' + pId // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '修改站点',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
		area : [ '80%', '60%' ],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function removeStation(id) {
	
    layer.confirm('确定要删除选中的站点？', {
        btn: ['确定', '取消']
    }, function () {
    	 $.ajax({
             url: prefix + "/remove",
             type: "post",
             data: {
                 'id': id
             },
             success: function (data) {
                 if (data.code == 0) {
                     layer.msg(data.msg);
                     load();
                 } else {
                     layer.msg(data.msg);
                 }
             }
         });
    })
}