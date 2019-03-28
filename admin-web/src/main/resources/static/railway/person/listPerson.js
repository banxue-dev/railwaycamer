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
            , url: '/railway/person/list'
            , method: 'POST'
            , cols: [[
                {field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'loginName', title: '登录账号', align: 'center'},
                {field: 'name', title: '姓名', align: 'center'},
                {field: 'psersonNo', title: '工号', align: 'center'},
                {field: 'stationName', title: '站点', align: 'center'},
                {field: '', title: '操作', align: 'center', toolbar: '#toolbar'}
            ]]
            , id: 'testReload'
            , page: true
        });

        var $ = layui.$
        
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
                        }   // 添加查询条件
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
        	childClick: function(d){
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
		area : [ '80%', '80%' ],
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
		area : [ '80%', '80%' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

//重置用户密码
function reset(id) {
	layer.prompt({
		title : '输入密码，并确认',
		formType : 1,
		value : ''
	}, function(pass, index) {
		layer.close(index);
		var data = {
			id : id,
			password : pass
		};
		$.ajax({
			cache : true,
			type : "POST",
			url : "/railway/person/update",
			data : data,
			async : false,
			error : function(request) {
				alert("Connection error");
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg('重置密码成功');
				} else {
					layer.msg(r.msg);
				}
			}
		});

	});
}
