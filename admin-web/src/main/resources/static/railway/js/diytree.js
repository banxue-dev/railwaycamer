!function(win){
		var treeinit=function(url,params){
			var treedom=$(this);
			var listree={
				thisTag:'',
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
						var deaddata=[{
								"attributes": {
									"icon": "fa fa-file-pdf-o",
									"url": ""
								},
								"checked": false,
								"children": [],
								"hasChildren": false,
								"hasParent": false,
								"id": "105",
								"parentId": "0",
								"text": "照片查询"
							}]
						callback(deaddata)
					}
					
				},
				loophtml:function (tdata){
					var html='';
					for(var i=0;i<tdata.length;i++){
						var tar=tdata[i];
						if(tar.hasChildren){
							html+='<li class="parent '+(tar.parentId==0?'top':'')+'">';
							html+='<label class="haschild" data-id="'+tar.id+'"><em class=" "></em>'+tar.text+'<i></i></label>';
							html+='<ul parent-id="'+tar.parentId+'" class="child">';
							html+=listree.loophtml(tar.children);
							html+='</ul>';
							html+='</li>';
						}else{
							html+='<li><a class="bottoma" href="javascript:;" data-id="'+tar.id+'" >'+tar.text+'</a></li>';
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
						{color:"white",backend:"#003C71"},
						{color:"white",backend:"#398CD3"},
						{color:"white",backend:"#81DAEA"},
						{color:"black",backend:"#E4E4E4"}
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
						$(_this).children('label').css('padding-left',min);
						$('.menu>li>a').css('padding-left',min);
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
							parents.each(function(){
								setPadding(this,min,index);
							})
						}
					}
					$('.bottoma').each(function(i){
						var _this=this;
						var par=$(_this).siblings('label').css('padding-left').replace(/px/,'')*1;
						$(_this).children('li').children('a').css('padding-left',par+=dz);
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
					$(".haschild").click(function(){
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
					$('.child>li>a').click(function(){
					
						defpara.childClick(this,listree.thisTag);
						treedom.hide();
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
								var btag=$(this);
								listree.thisTag=this;
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
							if(!evt.id){
								treedom.hide(); // 如不是则隐藏元素
							}
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
	}(window);