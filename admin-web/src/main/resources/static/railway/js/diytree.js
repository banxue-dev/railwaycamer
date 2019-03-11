!function(win) {
	var doc = win.document
	/*
	 * 进行菜单的展开与折叠
	 */
	$(".treebox .parent>label").click(
			function() {

				var d = $(this).siblings('ul').css('display');
				if (d != 'none') {
					$(this).siblings('ul').slideUp('slow', 'easeOutQuad');
					$(this).children("i").removeClass('down')
					return false;
				}
				$(this).addClass('current') // 给当前元素添加"current"样式
				.find('i').addClass('down') // 小箭头向下样式
				.parent().next().slideDown('slow', 'easeOutQuad') // 下一个元素显示
				.parent().siblings().children('label').removeClass('current')// 父元素的兄弟元素的子元素去除"current"样式
				.find('i').removeClass('down').parent().next().slideUp('slow',
						'easeOutQuad');// 隐藏
				return false; // 阻止默认时间
			});
	/*
	 * 子节点点击事件
	 */
	$('.child>li>a').click(function() {

		alert($(this).text());
	});

	var colors = [ {
		color : "white",
		backend : "#004069"
	}, {
		color : "white",
		backend : "#3E8DD0"
	}, {
		color : "white",
		backend : "#81DAEA"
	}, {
		color : "black",
		backend : "#E4E4E4"
	} ];
	/*
	 * 这个min表示从多少开始 这个dz表示每次增加多少
	 */
	var min = 10;
	var dz = 15;
	/*
	 * 缩进
	 */
	$('.top').each(
			function(i) {
				var _this = this;
				var index = 0;
				$(_this).children('label').css('padding-left', min);
				$(_this).children('label').css('color', colors[index].color);
				$(_this).children('label').css('background-color',
						colors[index].backend);
				setPadding(_this, min, index);
			})
	/*
	 * 递归
	 */
	function setPadding(dom, min, index) {
		var child = $(dom).children('.child');
		var parents = child.children('.parent');
		if (parents.length > 0) {
			// 包含了父节点
			min += dz;
			index++;
			parents.children('label').css('padding-left', min);
			parents.children('label').css('color', colors[index].color);
			parents.children('label').css('background-color',
					colors[index].backend);
			parents.each(function() {
				setPadding(this, min, index);
			})
		}
	}
	$('.child').each(
			function(i) {
				var _this = this;
				var par = $(_this).siblings('label').css('padding-left')
						.replace(/px/, '') * 1;
				$(_this).children('li').children('a').css('padding-left',
						par += dz);
			})

	/*
	 * 显示tree
	 */
	$('#tree').click(
			function() {
				var top = $(this).offset().top;
				var left = $(this).offset().left;
				var height = $(this).css('height').replace(/px/, '') * 1;
				var width = $(this).css('width').replace(/px/, '') * 1;
				$('.treebox').css('position', 'absolute').css('top',
						top + height + 5).css('left', left).css('width', width)
						.show();
			})
	$('html').bind('click', function(event) {
		// IE支持 event.srcElement ， FF支持 event.target
		var evt = event.srcElement ? event.srcElement : event.target;
		if (evt.id == 'tree' || evt.id == 'treebox')
			return; // 如果是元素本身，则返回
		else {
			$('#treebox').hide(); // 如不是则隐藏元素
		}
	});

}(window);