var prefix = '/railway/person'; // 路径前缀
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
        	table = layui.table,
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
            , url: '/railway/person/list'
            , method: 'POST'
            , data: data
            , cols: [[
                {field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'name', title: '姓名', align: 'center'},
                {field: 'stationName', title: '站点', align: 'center'},
                {field: 'account', title: '照片上报账号', align: 'center'},
                {field: '', title: '操作', align: 'center', toolbar: '#toolbar'}
            ]]
            , id: 'testReload'
            , page: true
        });

        var $ = layui.$
        
        active = {
            reload: function () {
                var demoReload = $('#demoReload');
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        id: null   // 添加查询条件
                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
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
