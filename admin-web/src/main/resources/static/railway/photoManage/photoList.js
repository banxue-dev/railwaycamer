var prefix = '/railway/photomanage'; // 路径前缀
var active;
$(function () {
    load();
});

// 加载表格
function load() {
    layui.use(['table', 'laydate', 'layer'], function () {
        var table = layui.table,
            $ = layui.$,
            laydate = layui.laydate;
        var utils = {
            doneTime: function (date) {
                return {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date
                }
            }
        };
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
        });

        //方法级渲染
        table.render({
            elem: '#list'
            , url: prefix + '/list'
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
                {field: 'photoNumber', title: '照片数量', align: 'center'},
                {
                    field: '', title: '操作', align: 'center', templet: function (item) {
                    	var a='';
                    	if(item.photoNumber!=0){
                    		 a = '<a class="layui-btn layui-btn-primary layui-btn-xs ' + s_view_h + '" target="iframeCamera" onclick="viewPhoto(' + item.id + ')">查看照片</a>';
                    	}
                        var b = '<a class="layui-btn layui-btn-primary layui-btn-xs ' + s_edit_h + '" onclick="edit(' + item.id + ')">编辑</a>';
                        return a + b;
                    }
                }
            ]],
            id: 'testReload',
            page: true
        });

        active = {
            load: function () {
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        startStationId: function () {
                            return $('#stationId').val();
                        },
	                    endStationId:function(){
	                    	 return $('#endStationId').val();
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
                        trainNo:function () {
                            return $('#trainNo').val();
                        }
                    }
                });
            }
        };


        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        $('#treebox').treeinit('/railway/station/listTree', {
            bindTag: 'tree',
            childClick: function (d, tag) {
                var name = $(d).text();
                var stationId = $(d).attr('data-id');
                $('#tree').val(name);
                $('#stationId').val(stationId);
            }
        });

    });

}

// 重新加载
function reLoad() {
    active.load();
}

function viewPhoto(id) {
    window.location.href = "/railway/photomanage/viewPhoto/" + id
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
/*显示到站页面*/
function showEndStation(){
	// iframe层
	layer.open({
		type : 2,
		title : '请选择到站点',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : '/railway/station/endStation/selectList' // iframe的url
	});
	return false;
}
