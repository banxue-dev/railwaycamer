var prefix = '/railway/order'; // 路径前缀
var active;

$(function() {
	load();
});

// 加载表格
function load() {
	layui.use(['table', 'laydate'], function () {
    	var utils = {
    		    doneTime: function (date) {
    		        return {
    		            year: date.year,
    		            month: date.month - 1,
    		            date: date.date
    		        }
    		    }
    		}
        var table = layui.table,
            $ = layui.jquery,
            laydate = layui.laydate;

        var beginTime = laydate.render({
            elem: '#beginTime',
            format: 'yyyy-MM-dd',
            done: function (value, date) {
                endTime.config.min = utils.doneTime(date);
            }
        });
        var endTime = laydate.render({
            elem: '#endTime',
            format: 'yyyy-MM-dd',
            done: function (value, date) {
                beginTime.config.max = utils.doneTime(date);
            }
        })

        //方法级渲染
        table.render({
            elem: '#list'
            , url: '/railway/order/listData'
            , method: 'get'
            , cols: [[
            	{field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'trainNo', title: '车号', align: 'center'},
                {field: 'createTime', title: '日期', align: 'center',width:180},
				{field: 'startStationName', title: '发站', align: 'center'},
                {field: 'endStationName', title: '到站', align: 'center'},
                {field: 'consignor', title: '托货人', align: 'center'},
                {field: 'consignee', title: '收货人', align: 'center'},
                {field: 'productName', title: '品名', align: 'center'},
                {field: 'trainType', title: '车型', align: 'center'},
                {field: 'projectNo', title: '方案编号', align: 'center'},
                {field: 'loadingLine', title: '装车线路', align: 'center'},
                {field: 'personNames', title: '拍照人员', align: 'center'},
                {field: 'continueShot', title: '续拍', align: 'center', templet: function(order){
                        return order.continueShot == 0 ? '否' : '是';
                }},
                {field: '', title: '操作', align: 'center', toolbar: '#toolbar'}
            ]]
            , id: 'testReload'
            , page: true
        });

        var $ = layui.$;
        
        active = {
            reload: function () {
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                    	startStationId: function(){
                        	return $('#stationId').val();
                        },
                        beginTime: function(){
                        	var beginTime = $('#beginTime').val();
                        	if(beginTime){
                        		beginTime += ' 00:00:00';
                        	}
                        	return beginTime;
                        },
                        endTime: function(){
                        	var endTime = $('#endTime').val();
                        	if(endTime){
                        		endTime += ' 23:59:59';
                        	}
                        	return endTime;
                        },
                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        
        $('#treebox').treeinit('/railway/station/listTree',{
        	bindTag:'tree',
        	childClick: function(d,tag){
        		var name = $(d).text();
        		var stationId = $(d).attr('data-id');
        		$('#tree').val(name);
        		$('#stationId').val(stationId);
        	}
        });
        
    });
	
}

//打开添加页面
function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '新建任务调度',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '800px' ],
		content : prefix + '/add' // iframe的url
	});
	return false;
}
// 重新加载
function reLoad() {
	active.reload();
}
// 删除数据
function copy(id) {
//	layer.confirm('确定要复制选中的记录？', {
//		btn : [ '确定', '取消' ]
//	}, function() {
//		$.ajax({
//			url : prefix + "/copy",
//			type : "post",
//			data : {
//				'id' : id
//			},
//			success : function(r) {
//				if (r.code === 0) {
//					layer.msg("复制成功");
//					reLoad();
//				} else {
//					layer.msg(r.msg);
//				}
//			}
//		});
//	})
	
	layer.open({
		type : 2,
		title : '继续任务',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '800px' ],
		content : prefix + '/copy/' + id // iframe的url
	});

}
// 打开编辑面
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '800px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

// 继续任务
function continueOrder() {
	layer.open({
		type : 2,
		title : '继续任务',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '820px', '800px' ],
		content : prefix + '/continue' // iframe的url
	});
}
