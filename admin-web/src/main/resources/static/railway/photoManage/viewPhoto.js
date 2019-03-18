var prefix = '/railway/photomanage'; // 路径前缀
var prefixPic = '/railway/file'; // 路径前缀
$(function () {
    load();
    photoList();
    initShowBig();
});

function selCheckBox() {
    var ids = [];
    $('input[name="photo"]:checked').each(function () {
        ids.push($(this).val());
    });
    if (ids.length > 0) {
        layer.confirm('确定要删除选中的记录？', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                url: prefix + "/deletePic",
                type: "post",
                data: {
                    "ids" : ids
                },
                success: function (r) {
                    if (r.code === 0) {
                        layer.msg("删除成功", {icon: 6});
                        load();
                    } else {
                        layer.msg(r.msg);
                    }
                }
            });
        })
    } else {
        layer.msg("请至少选择一张图片进行删除", {icon: 5});
    }
}
//导出
function exportPic() {
    window.location.href = prefix + "/exportPic/"+$("#id").val();
}

//翻页
function photoList() {
    //获取图片
    $.ajax({
        cache: true,
        type: "get",
        url: prefix + "/listPicture",
        data: {
            orderId: $("#id").val()
        },
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            var ul = $(".listul");
            ul.empty();
            for (var j = 0; j < data.length; j++) {
                var e = "<li>\n" +
                    "                <a href=\"javascript:;\"><img src='" + prefixPic + "/getThumPhoto/" + data[j].id + "'/></a>\n" +
                    "                <label style=\"float: left;cursor:pointer;\"><a class='showbig' data-caption='第"+(j+1)+"张' data-magnify='gallery2' data-group='g2'\n" +
                    "                                                 data-src='" + prefixPic + "/getPhoto/" + data[j].id + "'>查看</a></label>\n" +
                    "                    <label style=\"float: right;cursor:pointer;\"><input type=\"checkbox\" value='" + data[j].id + "' name=\"photo\" lay-skin=\"primary\">选择</label>\n" +
                    "            </li>";
                ul.append(e);
            }
        }
    });
}

function initShowBig() {
    var width = $(window).width();
    var height = $(window).height();
    $('.showbig').Magnify({
        Toolbar: ['prev', 'next', 'rotateLeft', 'rotateRight', 'zoomIn', 'zoomOut', 'actualSize'],
        keyboard: true,
        draggable: true,
        movable: true,
        modalSize: [width, height],
        beforeOpen: function (obj, data) {
        },
        opened: function (obj, data) {
        },
        beforeClose: function (obj, data) {
        },
        closed: function (obj, data) {
        },
        beforeChange: function (obj, data) {
        },
        changed: function (obj, data) {
        }
    });
}

// 加载表格
function load() {
    layui.use(['table'], function () {
        var table = layui.table, $ = layui.jquery;
        //方法级渲染
        table.render({
            elem: '#list'
            , url: prefix + '/list'
            , method: 'get'
            , cols: [[
                {field: '', title: '序号', align: 'center', type: 'numbers'},
                {field: 'trainNo', title: '车号', align: 'center'},
                {
                    field: 'createTime', title: '日期', align: 'center',width:180
                },
                {field: 'endStationName', title: '到站', align: 'center'},
                {field: 'consignor', title: '托货人', align: 'center'},
                {field: 'consignee', title: '收货人', align: 'center'},
                {field: 'productName', title: '品名', align: 'center'},
                {field: 'trainType', title: '车型', align: 'center'},
                {field: 'projectNo', title: '方案编号', align: 'center'},
                {field: 'loadingLine', title: '装车线路', align: 'center'},
                {field: 'photoNumber', title: '照片数量', align: 'center'}
            ]],
            id: 'testReload',
            where: {id: $("#id").val()}
        });
    });
}