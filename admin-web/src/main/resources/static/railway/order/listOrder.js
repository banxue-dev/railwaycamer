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
            format: 'yyyy年MM月dd',
            done: function (value, date) {
                endTime.config.min = utils.doneTime(date);
            }
        });
        var endTime = laydate.render({
            elem: '#endTime',
            format: 'yyyy年MM月dd',
            done: function (value, date) {
                beginTime.config.max = utils.doneTime(date);
            }
        })

        

        var data = [
            {"a": "1", "b": "001", "c": "09-23", "d": "重庆", "e": "成都"},
            {"a": "2", "b": "001", "c": "09-23", "d": "重庆", "e": "成都"},
            {"a": "3", "b": "001", "c": "09-23", "d": "重庆", "e": "成都"},
            {"a": "4", "b": "001", "c": "09-23", "d": "重庆", "e": "成都"}
        ]
        //方法级渲染
        table.render({
            elem: '#list'
            , url: '/railway/order/list'
            , data: data
            , method: 'POST'
            , cols: [[
            	{field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'trainNo', title: '车号', align: 'center'},
                {field: 'startTime', title: '日期', align: 'center', templet: function(order){
                	if (order.startTime != null) {
                        var date = new Date(order.startTime);
                        return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
                    }
                	return '';
                }},
                {field: 'endStationName', title: '到站', align: 'center'},
                {field: 'consignor', title: '托货人', align: 'center'},
                {field: 'consignee', title: '收货人', align: 'center'},
                {field: 'productName', title: '品名', align: 'center'},
                {field: 'trainType', title: '车型', align: 'center'},
                {field: 'projectNo', title: '方案编号', align: 'center'},
                {field: 'loadingLine', title: '装车线路', align: 'center'},
                {field: 'personNames', title: '拍照人员', align: 'center'},
                {field: 'continueShot', title: '续拍', align: 'center', templet: function(order){
                	if (order.continueShot != null) {
                        return order.continueShot == 1 ? '是' : '否';
                    }
                	return '';
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
                    	stationId: function(){
                        	return $('#stationId').val();
                        },
                        beginTime: function(){
                        	return $('#beginTime').val();
                        },
                        endTime: function(){
                        	return $('#endTime').val();
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
		title : '新建拍照人员',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
	return false;
}
// 重新加载
function reLoad() {
	active.reload();
}
// 删除数据
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code === 0) {
					layer.msg("删除成功");
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})

}
// 打开编辑面
function edit(id) {
	layer.open({
		type : 2,
		title : '修改拍照人员',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
