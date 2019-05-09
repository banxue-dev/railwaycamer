
//http://localhost:8888/railway/station/listTree
function strchange(str){
	if(str==null || str=='null' || str=='' || str==undefined){
		return '待补充';
	}
	return str;
}
function diytree(){
		var treeinit=function(url,params){
			var treedom=$(this);
			var listree={
				thisTag:'',
				ttreedom:treedom,
				defaultpara:'',
				init:function(treedom,url,params){
					this.inithtml(treedom,url,params);
				},
				initdata:function(uri,callback){
					if(url){
						//准备ajax
						$.ajax({
							url:uri,
							type:'post',
							success:function(res){
								if(res.code=='0'){
									callback(res.data);
								}
							}
						})
					}else{
						callback(testdata)
					}
					
				},
				simpleevent(){
					$(".newchild .parent>label").unbind('click')
					/*
					* 进行菜单的展开与折叠
					*/
					$(".newchild .parent>label").unbind('click').bind('click', function(){
						var d=$(this).siblings('ul').css('display');
						if(d!='none'){
							$(this).siblings('ul').slideUp('slow','easeOutQuad');
							$(this).children("i").removeClass('down')
							return false;
						}
						$(this).addClass('current')   //给当前元素添加"current"样式
						.find('i').addClass('down')   //小箭头向下样式
						.parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
						.parent().siblings().children('label').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
						.find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');//隐藏
						 return false; //阻止默认事件
					});
					/*
					* 子节点点击事件
					*/
					$('.child>li>.bottom_').unbind('click').bind('click',function(){
						var _this=this;
						var order_id=$(_this).attr('data-id');
						var pname=$(_this).attr('data-pname');
						var ptime=$(_this).attr('data-ptime');
						var ptype=$(_this).attr('data-trainType');
						var tline=$(_this).attr('data-loadingLine');
						var trinno=$(_this).attr('data-trinno');
						var endStationName=$(_this).attr('data-endStationName');
						$('#trinno').text('车号：'+strchange(trinno));
						$('#pname').text('品名：'+strchange(pname));
						$('#ptime').text('拍摄日期：'+strchange(ptime));
						$('#ptype').text('车型：'+strchange(ptype));
						$('#tline').text('装车线路：'+strchange(tline));
						$('#endStationName').text('终点站：'+strchange(endStationName));
						//去获取图片。
						$.ajax({
							url:'/railway/photomanage/listPicturebyr',
							type:'post',
							data:{orderId:order_id},
							success:function(data){
								if(data.code==0){
									if(data.data.length<1){
										$('#notdata').text('暂无图片数据，请检查。');
									}
									pictureboxs.picdatas=data.data;
									getPage(1);
								}
							}
						})
						
					});
				},loadPhoto:function(tdom){
					var thistdom=$(tdom);
					var id=thistdom.attr('data-id');
					/*
					判断是否有了子节点
					*/
					var childdom=thistdom.siblings('[parent-id='+id+']').html();
					if(childdom){
						
						return false;
					}
					$.ajax({
						url:'/railway/station/listTimeTree',
						type:'post',
						data:{stationId:id,nowsize:0,pagesize:1,isFirst:0},
						success:function(data){
							if(data.code==0){
								if(data.data==null || data.data.length<1){
									var html='';
									html+='<ul parent-id="'+id+'" class="child newchild">';
									html+='<li><a class="loadmore" href="javascript:;"  >加载更多...</a></li>';
									html+='</ul>';
									thistdom.after(html);
								}else{
									var html='';
									html+='<ul parent-id="'+id+'" class="child newchild">';
									html+=listree.looptimehtml(data.data);
									html+='<li><a class="loadmore" href="javascript:;"  >加载更多...</a></li>';
									html+='</ul>';
									thistdom.after(html);
								}
								/*
								* 子节点点击事件
								*/
								$('.child>li>.loadmore').click(function(){
									//nowsize=现在这个兄弟有多少条
									var _this=this;
									var loadmore=$(_this);
									var nowsize=loadmore.parent().siblings('li').length;
//									nowsize=nowsize<1?1:nowsize;
									var psize=5;
									$.ajax({
										url:'/railway/station/listTimeTree',
										type:'post',
										data:{stationId:id,nowsize:nowsize,pagesize:psize,isFirst:1},
										success:function(data){
											if(data.code==0){
												if(data.data==null || data.data.length<1){
													var html='';
													html+='<li><a data-id="-1"   href="javascript:;" >没有更多了</a></li>';
													loadmore.parent().after(html);
													loadmore.remove();
												}else{
													var html='';
													html+=listree.looptimehtml(data.data);
													loadmore.parent().before(html);
													
													if(data.data.length<psize){
														var html1='';
														html1+='<li><a data-id="-1"   href="javascript:;" >没有更多了</a></li>';
														loadmore.parent().after(html1);
														loadmore.remove();
													}
												}
												listree.initcss({min:10,dz:15});
												listree.simpleevent();
											}
										}
									})
									
								});

								$('.newchild').slideUp('slow','easeOutQuad');
								thistdom.siblings('ul').slideDown('slow','easeOutQuad');
								var sb=thistdom.siblings('ul').children('li').children('label')
								thistdom.siblings('ul').children('li').children('.loadmore').css('color',sb.css('color')).css('background-color',sb.css('background-color'));
								
								listree.initcss({min:10,dz:15});
								listree.simpleevent();
							}
						}
					})
					return false;
				},
				looptimehtml:function(tdata){
					var html='';
					for(var i=0;i<tdata.length;i++){
						var tar=tdata[i];
						if(tar.hasChildren){
							html+='<li class="parent">';
							html+='<label   ><em class=" "></em>'+tar.text+'<i></i></label>';
							html+='<ul class="child">';
							html+=listree.looptimehtml(tar.children);
							html+='</ul>';
							html+='</li>';
						}else{
							if(tar.attributes.trinno=='暂无数据'){
								html+='<li><a data-id="'+tar.id+'"  class="" href="javascript:;" >'+tar.text+'</a></li>';
							}else{	
								html+='<li><a data-id="'+tar.id+'" data-endStationName="'+tar.attributes.endStationName+'" data-trinno="'+tar.attributes.trinno+'" data-pname="'+tar.attributes.productName+'" data-ptime="'+tar.attributes.ptime+'" data-trainType="'+tar.attributes.trainType+'" data-loadingLine="'+tar.attributes.loadingLine+'"  class="bottom_" href="javascript:;" >'+tar.text+'</a></li>';
							}
						}
					
					}
					return html;
				},
				loophtml:function (tdata){
					var html='';
					for(var i=0;i<tdata.length;i++){
						var tar=tdata[i];
						if(tar.hasChildren){
							html+='<li class="parent '+(tar.parentId==0?'top':'')+'">';
							html+='<label  class="oneClick" ><em class=" "></em>'+tar.text+'<i></i></label>';
							html+='<ul class="child">';
							html+=listree.loophtml(tar.children);
							html+='</ul>';
							html+='</li>';
						}else{
							//html+='<li><a href="javascript:;" data-id="'+tar.attributes.id+'" >'+tar.text+'</a></li>';
							html+='<li class="parent '+(tar.parentId==0?'top':'')+'">';
							html+='<label data-id="'+tar.id+'" data-code="'+tar.text+'" class="childlabel oneClick" ><em class=" "></em>'+tar.text+'<i></i></label>';
							html+='</li>';
						}
					
					}
					return html;
				},
				inithtml:function(treedom,url,params){
					/*
					*	递归的循环方法 
					*/
					listree.initdata(url,function(tdata){
						//开始生成
						var tree=treedom;
						tree.addClass('treebox').hide();
						var html='<ul class="menu">';
						html+=listree.loophtml(tdata);
						html+='</ul>';
						tree.html(html);
						listree.initevent(treedom,params);
						
					});
					
				},
				initcss:function(defpara){

					var colors=[
						{color:"white",backend:"#004069"},
						{color:"white",backend:"#3E8DD0"},
						{color:"white",backend:"#81DAEA"},
						{color:"black",backend:"#E4E4E4"},
						{color:"red",backend:"green"},
						{color:"white",backend:"black"}
					];
					/*
					这个min表示从多少开始
					这个dz表示每次增加多少
					*/
					var min=defpara.min;
					var dz=defpara.dz;
					/*
					* 缩进
					*/
					$('.top').each(function(i){
						var _this=this;
						var index=0;
						$('.menu>li>a').css('padding-left',min);
						$(_this).children('label').css('padding-left',min);
						$(_this).children('label').css('color',colors[index].color);
						$(_this).children('label').css('background-color',colors[index].backend);
						setPadding(_this,min,index);
					})
					/*
						递归
					*/
					function setPadding(dom,min,index){
						var child=$(dom).children('.child');
						var parents=child.children('.parent');
						if(parents.length>0){
							//包含了父节点
							min+=dz;
							index++;
							parents.children('label').css('padding-left',min);
							parents.children('label').css('color',colors[index].color);
							parents.children('label').css('background-color',colors[index].backend);
							child.children('li').children('a').css('padding-left',min);
							child.children('li').children('a').css('color',colors[index].color);
							child.children('li').children('a').css('background-color',colors[index].backend);
							
							parents.each(function(){
								setPadding(this,min,index);
							})
						}
					}
					$('.child').each(function(i){
						var _this=this;
						var pardom=$(_this).siblings('label');
						var par=pardom.css('padding-left').replace(/px/,'')*1;
						var childlabeldom=pardom.children('li').children('label');
						$(_this).children('li').children('a').css('padding-left',par+=dz);
						//.css('color',childlabeldom.css('color')).css('background-color',childlabeldom.css('background-color'))
					})
				},
				initevent:function(treedom,params){
					var defpara={
						isBind:true,
						bindTag:'tree',//要绑定到那个元素下,以让其被点击时显示这个树
						min:10,//上级与下级直接的间距起点
						dz:15,//上级与下级直接的间距递增量
						childClick:function(atag,tag){
							//atag表示当前那个子节点，tag表示当前是由那个bingtag引出的
							alert($(atag).text());
						}
					}
					$.extend(defpara,params);
					
					/*
					* 进行菜单的展开与折叠
					*/
					$(".oneClick").click(function(){
						var d=$(this).siblings('ul').css('display');
						if(d!='none'){
							$(this).siblings('ul').slideUp('slow','easeOutQuad');
							$(this).children("i").removeClass('down')
							return false;
						}
						$(this).addClass('current')   //给当前元素添加"current"样式
						.find('i').addClass('down')   //小箭头向下样式
						.parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
						.parent().siblings().children('label').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
						.find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');//隐藏
						 return false; //阻止默认事件
					});
					
					/*
					* 子节点点击事件
					*/
					$('.childlabel').click(function(){
						listree.loadPhoto(this);
					});
					
					
					listree.initcss(defpara);
					/*
						显示tree,如果有绑定的标签，就给标签加上点击事件
					*/
					if(defpara.bindTag){
						var bindtags=defpara.bindTag.split(',');
						for(var i=0;i<bindtags.length;i++){
							var thistage=bindtags[i];
							$('#'+thistage).click(function (){
//							var btag=$('#'+defpara.bindTag);
								var _this=this;
								var btag=$(_this);
								listree.thisTag=_this;
								var top = btag.offset().top;
								var left = btag.offset().left;
								var height = btag.css('height').replace(/px/, '') * 1;
								var width = btag.css('width').replace(/px/, '') * 1;
								treedom.css('position', 'absolute').css('top',
										top + height + 5).css('left', left).css('width', width)
										.show();
							})
						}
						$('html').bind('click', function(event) {
							// IE支持 event.srcElement ， FF支持 event.target    
							var evt = event.srcElement ? event.srcElement : event.target;    
							if(defpara.bindTag.indexOf(evt.id) >=0  || evt.id==treedom.attr('id') ) {
								return;
							} else {
								treedom.hide(); // 如不是则隐藏元素
							}   
						});
					}else{
						treedom.show(); 
					}
				}
				
			}
			listree.init(treedom,url,params);
		}
		
		$.fn.extend({treeinit:treeinit});
	};