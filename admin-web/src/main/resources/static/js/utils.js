//<input type="text" id="telephone" placeholder="电话" valid="phone"tips="请正确填写电话号码!">
//<button not='not'>提交</button>


var entity = {
	isGet : true,// 是否可以获取code了，如果还没有走出超时时间，就不允许获取
	codeInit:function(timeout,codeid){
		entity.codeTime=timeout;
		entity.codeDom=codeid;
	},
	codeDom:'test',
	codeTime : 60,
	codeText:{bef:'S后再次获取',aft:'获取验证码'},
	codeStart : function() {
		$('#'+entity.codeDom).css('background-color', '#cccccc');
		$('#'+entity.codeDom).text('(' + (entity.codeTime--) + ')'+(entity.codeText.bef || 'S后再次获取'));
	},
	codeCenter:function(){
		$('#'+entity.codeDom).text('(' + (--entity.codeTime) + ')'+(entity.codeText.bef || 'S后再次获取'));
	},
	codeEnd : function() {
		$('#'+entity.codeDom).css('background-color', '#4cd463');
		$('#'+entity.codeDom).text(entity.codeText.aft || '获取验证码');
		entity.isGet = true;
		entity.codeTime = 60;
	},
	getCode:function(url,data,callback){
		if(entity.isGet){
			entity.isGet=false;
			_ajax({
				url:url,
				data:data,
				type:'POST',
				asu:true,
				success:function(res){
					if(callback){
						callback(res);
					}else{
						if(res.code=="000000"){
							//成功
							entity.codeStart();
							var codeInv = setInterval(function() {
								if (entity.codeTime <= 1) {
									entity.codeEnd();
									window.clearInterval(codeInv);
								} else {
									entity.codeCenter();
								}
							}, 1000);
						}else{
							//失败
							entity.errTips(res.msg);
							entity.isGet = true;
							entity.codeTime = 60;
						}
					}
				}
			})
		}
	},
	/**
	 * 单元行验证通过
	 */
	codePass : function(dom) {
		if(entity.codeBorder){
			dom.parent('div').css('border', '1px solid #4cd463');
		}
	},
	/**
	 * 单元行验证不通过
	 */
	codeNPass : function(dom) {
		entity.errTips(dom.attr('tips') || dom.attr('placeholder'));
		if(entity.codeBorder){
			dom.parent('div').css('border', '1px solid red');
		}
		dom.focus();
	},
	codeBorder:false,
	err_tips_state : true,
	/**
	 * 错误提示
	 * @param msg
	 * @returns
	 */
	errTips : function(msg,_tipcolor) {
		var istips = $('#_errTips').text();
		if (istips) {
			$('#_errTips').text(msg);

		} else {
			var tipsHtml = '<div id="_errTips" style="z-index:9999;border-radius:5px;font-size:12px;top:20px;position:fixed;width:80%;left:10%;height:40px;line-height:40px;text-align:center;background-color:'+(_tipcolor||'#ec5224')+';">'
					+ msg + '</div>';
			$('body').append(tipsHtml);
		}
		if (entity.err_tips_state) {
			$('#_errTips').show();
			entity.err_tips_state = false;
			setTimeout(function() {
				$('#_errTips').hide();
				entity.err_tips_state = true;
			}, 2000);
		}

	},
	startloading : function() {
		var isload = $('#_loading').text();
		if (isload) {
			$('#_loading').show();
		} else {
			var loadHtml = '<div id="_loading" style="z-index:999999;border-radius:5px;top:0px;left:0px;font-size:12px;position:fixed;width:100%;height:100%;text-align:center;background-color:black;opacity:0.5;"><div style="margin-top:10%;color:white;">加载中..</div></div>';
			$('body').append(loadHtml);
		}
	},
	endloading : function() {
		$('#_loading').hide();
	}
}
/**
 * 验证表单
 */
var validateFrom = function(FormId) {
	var inps = $('#'+FormId+' :input');
	var params = {};
	var res = true;
	var thDom;
	for (var i = 0; i < inps.size(); i++) {
		var thDom = $(inps[i]);
		var vthens = thDom.attr('valid');
		var thenVal = thDom.val();
		//如果有not这个属性，表示此值不参与提交
		if (thDom.attr('not') != 'not') {
			var thId = thDom.attr('id');
			if(thDom.attr('type')=='checkbox'){
				if(thDom.prop("checked")){
					params[thId]=0;
				}else{
					params[thId]=1;
				}
			}else{
				params[thId] = thenVal;
			}
		}
		if(!vthens){
			//没有valid属性，直接跳过。
			continue;
		}
		vthens=vthens.split(',');
		for(var j=0;j<vthens.length;j++){
			var vthen=vthens[j];
			if (vthen == 'required') {
				if (thenVal == '') {
					res = false;
					break;
				}
				entity.codePass(thDom);
			}else if (vthen == 'phone') {
				var phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
				if (!phoneReg.test(thenVal)) {
					res = false;
					break;
				}
				entity.codePass(thDom);
			}else if (vthen == 'ID') {
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				if (reg.test(thenVal) === false) {
					res = false;
					break;
				}
				entity.codePass(thDom);
			}//如果valid=att，则表示要与他的某个属性值做比较,这里使用v-value
			else if (vthen == 'att') {
				if (thenVal == thDom.attr('v-value')) {
					res = false;
					break;
				}
				entity.codePass(thDom);
			}else if(vthen=='len'){
				var mx=thDom.attr('max')*1;
				var mn=thDom.attr('min')*1;
				if(thenVal.length<min || thenVal.length>max){
					res = false;
					break;
				}
			}

		}

		if (!res) {
			entity.codeNPass(thDom);
			break;
		}
	}
	params['res'] = res;
	return params;
}
/**
 * 为表单赋值
 */
var setFormValue = function(FormId,params) {
	var inps = $('#'+FormId+' :input');
	var data = params;
	var thDom;
	for (var i = 0; i < inps.size(); i++) {
		var thDom = $(inps[i]);
		var vthens = thDom.attr('id');
		if(thDom.attr('type')=='checkbox'){
			var isChecked=params[vthens]==0?true:false;
			thDom.attr("checked", isChecked);
		}else{
			thDom.val(params[vthens]);
		}
	}
}
$(function() {
	/**
	 * 增加当ajax提交时的加载框
	 */
	$(document).ajaxStart(function () {
        //正在加载
        entity.startloading();
    });
    $(document).ajaxStop(function () {
       //删除加载
       entity.endloading();
    });
})
var _ajax=function(parm){
	$.ajax({
		url:parm.url,
		data:parm.data || {},
		type:parm.type || 'POST',
		xhrFields: {
	           withCredentials: true
	    },
	    crossDomain: true,
		dataType:parm.dataType || 'json',
		success:function(data){
			if(parm.asu){
				//这个参数表示要直接使用suc方法
				parm.success(data);
				return;
			}
			if(data.code=="000000"){
				if(parm.success){
					parm.success(data.data);
				}else{
					entity.errTips(data.msg || '请求成功。','green');
				}
			}else{
				entity.errTips(data.msg || '请求异常。');
			}
		},error:function(data){
			if(parm.error){
				parm.error();
			}else{
				console.log(JSON.stringify(data))
				entity.errTips('请求错误。');
				alert(JSON.stringify(data));
			}
		}
	})
}

function test123123(window) //函数：获取尺寸
{
	var c_height=window.screen.height ;
	$('body').css('height',c_height);
	var winWidth = 0;
	var winHeight = 0;
	//获取窗口宽度
	if (window.innerWidth)
		winWidth = window.innerWidth;
	else if ((document.body) && (document.body.clientWidth))
		winWidth = document.body.clientWidth;
	//获取窗口高度
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	//通过深入Document内部对body进行检测，获取窗口大小
	if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)
	{
		winHeight = document.documentElement.clientHeight;
		winWidth = document.documentElement.clientWidth;
	}
	console.log(winHeight+"---"+winWidth);
	$('body').css('height',winHeight-20);
	$('body').css('width',winWidth);
}