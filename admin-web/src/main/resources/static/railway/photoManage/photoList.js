var prefix = '/railway/photomanage'; // 路径前缀
var active;
$(function () {
    load();
});

// 加载表格
function load() {
    layui.use(['table', 'laydate','layer'], function () {
        var table = layui.table,
            $ = layui.jquery,
            laydate = layui.laydate;
        var utils = {
            doneTime: function (date) {
                return {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date
                }
            }
        }
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

        //方法级渲染
        table.render({
            elem: '#list'
            , url: prefix + '/list'
            , method: 'get'
            , cols: [[
                {field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'trainNo', title: '车号', align: 'center'},
                {
                    field: 'createTime', title: '日期', align: 'center', templet: function (order) {
                        if (order.createTime != null) {
                            var date = new Date(order.createTime);
                            return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
                        }
                        return '';
                    }
                },
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
                        var a = '<a class="layui-btn layui-btn-primary layui-btn-xs '+s_view_h+'" target="iframeCamera" onclick="viewPhoto('+item.id+')">查看照片</a>';
                        return a;
                    }
                }
            ]],
            id: 'testReload',
            page: true
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
                        endStationId: function () {
                            return $('#stationId').val();
                        },
                        beginTime: function () {
                            return $('#beginTime').val().replace('年', '-').replace('月', '-').replace('日', '');
                        },
                        endTime: function () {
                            return $('#endTime').val().replace('年', '-').replace('月', '-').replace('日', '');
                        },
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
    active.reload();
}

function viewPhoto(id) {
    window.location.href = "/railway/photomanage/viewPhoto/"+id
}