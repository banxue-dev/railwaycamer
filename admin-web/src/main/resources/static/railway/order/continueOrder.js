var prefix = '/railway/order'; // 路径前缀
var active;
var checkedOrderId; // 选中的任务id

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
    		    },
		    	//计算表头时间+1天
    			getNextDay: function(d){
		    	        d = new Date(d);
		    	        d = +d + 1000*60*60*24;
		    	        d = new Date(d);
		    	        //格式化
		    	        return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		    	    },
		    	//计算表头时间-1天
    		    getPreDay: function(d){
		    	        d = new Date(d);
		    	        d = +d - 1000*60*60*24;
		    	        d = new Date(d);
		    	        //格式化
		    	        return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
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
            , where: {
            	beginTime: function(){
                	return utils.getPreDay(new Date().getTime()) + ' 00:00:00';
                },
                endTime: function(){
                	return utils.getPreDay(new Date().getTime()) + ' 23:59:59';
                }
            }
            , method: 'get'
            , cols: [[
            	{field: '', title: '选择', align: 'center'
            		,templet: function(d){
            			//console.log(d.id);
            	        return '<input type="radio" name="sex" value='+d.id+' title=" " lay-skin=" ">'
            	      }	
            	},
            	{field: 'id', title: 'ID', align: 'center', hide: true},
                {field: 'trainNo', title: '车号', align: 'center'},
                {field: 'endStationName', title: '到站', align: 'center'},
                {field: 'consignor', title: '托货人', align: 'center'},
                {field: 'consignee', title: '收货人', align: 'center'},
                {field: 'productName', title: '品名', align: 'center'},
                {field: 'trainType', title: '车型', align: 'center'},
                {field: 'loadingLine', title: '装车线路', align: 'center'}
            ]]
            , id: 'testReload'
            , page: true
            , done: function() {
            	// 表格初始完成
            	$('[name=sex]:input').each(function(){
            		$(this).next().children('i').on('click',function(){
            			checkedOrderId = $(this).parent().prev().val();
            			console.log(checkedOrderId);
            			})
            	});
			}
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
                    	endStationId: function(){
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
		title : '新建任务调度',
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
		title : '复制',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
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
		area : [ '800px', '520px' ],
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
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + 1 // iframe的url
	});
}

// 下一步
function next() {
	
	// 检查是否选择了行
	if(!checkedOrderId){
		layer.msg('请选择一个车号继续任务');
		return;
	}
	
	// 关闭继续任务窗口
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
	
	parent.layer.open({
		type : 2,
		title : '继续任务',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/copy/' + checkedOrderId // iframe的url
	});
}
