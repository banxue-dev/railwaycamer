var prefix = '/railway/station/endStation'; // 路径前缀
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

        //方法级渲染
        table.render({
            elem: '#list',
            where:{
                //请求的参数，写在这里
            }
            , url: prefix + '/listData'
            , method: 'get'
            , cols: [[
            	{field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'id', title: 'id', align: 'center', hide: true},
                {field: 'name', title: '站点名称', align: 'center',width:180},
                {field: 'stationCode', title: '站点编码', align: 'center',width:180},
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
                    	keyword: function(){
                    		if($('#keyword')){
                    			return $('#keyword').val().toLocaleUpperCase();
                    		}
                    	},
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
		title : '新建到站',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '60%', '75%' ],
		content : prefix + '/add' // iframe的url
	});
	return false;
}
// 重新加载
function reLoad() {
	active.reload();
}
// 打开编辑面
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

