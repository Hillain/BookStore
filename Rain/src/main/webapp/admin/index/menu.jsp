<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>图书分类管理</title>
		<link rel="stylesheet" type="text/css" href="../../static/admin/layui/css/layui.css" />
		<link rel="stylesheet" type="text/css" href="../../static/admin/css/admin.css" />
	</head>
	<body>
		<div class="page-content-wrap">
					<form class="layui-form" action="">
						<div class="layui-form-item">
							<div class="layui-inline tool-btn">
								<button class="layui-btn layui-btn-small layui-btn-normal addBtn hidden-xs" data-url="menu-add.html"><i class="layui-icon">&#xe654;</i></button>
								<button class="layui-btn layui-btn-small layui-btn-warm listOrderBtn hidden-xs" onclick="order();"><i class="iconfont">&#xe656;</i></button>
							</div>
						</div>
					</form>
					<div class="layui-form" id="table-list">
						<table class="layui-table" lay-skin="line">
							<colgroup>
								<col width="50">
								<col class="hidden-xs" width="50">
								<!-- <col class="hidden-xs" width="100"> -->
								<col class="hidden-xs" width="100">
								<col>
								<col width="80">
								<col width="130">
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
									<th class="hidden-xs">ID</th>
									<!-- <th class="hidden-xs">排序</th> -->
									<th class="hidden-xs">应用</th>
									<th>分类名称</th>
									<th>创建者</th>
									<th>状态</th>
									<th style="width: 200px;">描述</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${ order == 1}">
								<!--ssssssssssss-->									
								<c:forEach items="${category }" var="cate" varStatus="s">
									<tr id='node-${s.count}' class="parent collapsed">
										<td><input type="checkbox" name="" lay-skin="primary" data-id="1"></td>
										<td class="hidden-xs">${cate.id }</td>
										<!-- <td class="hidden-xs"><input type="text" name="title" autocomplete="off" class="layui-input" value="0" data-id="1"></td> -->
										<td class="hidden-xs" id="appName">若水图书</td>
										<td id="cateName">${cate.lx }<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td id="who">超级管理员<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td>
											<c:if test="${cate.state==1}">
												<button class="layui-btn layui-btn-mini layui-btn-normal table-list-status">
													显示
												</button>
											</c:if> 
											<c:if test="${cate.state==0}">
												<button class="layui-btn layui-btn-mini layui-btn-warm table-list-status">
													隐藏
												</button>
											</c:if> 
										</td>
										<td id="descr">${cate.descr }<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td>
											<div class="layui-inline">
												<button class="layui-btn layui-btn-mini layui-btn-normal  edit-btn" data-id="1" data-url="menu-edit.jsp" onclick="return getCate(${cate.id });"><i class="layui-icon">&#xe642;</i></button>
												<button class="layui-btn layui-btn-mini layui-btn-danger del-btn" data-id="1" onclick="return delCate(${cate.id });"><i class="layui-icon">&#xe640;</i></button>
											</div>
										</td>
									</tr>
									</c:forEach>
								</c:if>
								<c:if test="${ order == 0}">
								<!-- dddddddddddd -->
									<c:forEach items="${category }" var="cate" varStatus="s">
									<tr id='node-${s.count}' class="parent collapsed">
										<td><input type="checkbox" name="" lay-skin="primary" data-id="1"></td>
										<td class="hidden-xs">${cate.id }</td>
										<!-- <td class="hidden-xs"><input type="text" name="title" autocomplete="off" class="layui-input" value="0" data-id="1"></td> -->
										<td class="hidden-xs" id="appName">若水图书</td>
										<td id="cateName">${cate.lx }<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td id="who">超级管理员<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td><c:if test="${cate.state==1}">
												<button class="layui-btn layui-btn-mini layui-btn-normal table-list-status">
													显示
												</button>
											</c:if> 
											<c:if test="${cate.state==0}">
												<button class="layui-btn layui-btn-mini layui-btn-warm table-list-status">
													隐藏
												</button>
											</c:if> 
										</td>
										<td id="descr">${cate.descr }<!-- <a class="layui-btn layui-btn-mini layui-btn-normal showSubBtn" data-id='1'>+</a> --></td>
										<td>
											<div class="layui-inline">
												<button class="layui-btn layui-btn-mini layui-btn-normal  edit-btn" data-id="1" data-url="menu-edit.jsp" onclick="return getCate(${cate.id });"><i class="layui-icon">&#xe642;</i></button>
												<button class="layui-btn layui-btn-mini layui-btn-danger del-btn" data-id="1" onclick="return delCate(${cate.id });"><i class="layui-icon">&#xe640;</i></button>
											</div>
										</td>
									</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
		</div>
		<script src="../../static/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../static/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../ui/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../adminjs/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			function delCate(id){
				//alert(id);
				/* var data = sessionStorage.getItem('order');
				if(data == 0){
					sessionStorage.setItem('order', 1);
					alert(0);
				}else{
					sessionStorage.setItem('order', 0);
					alert(1);
				} */
				$.ajax({
		            async: true,
		            url: "/Rain/admin/cate_del?id="+id,
		            type: "get",
		            success: function (data) {
		            	
		            }
		        });
			}
			function getCate(id){
				//alert(id);
				/* var data = sessionStorage.getItem('order');
				if(data == 0){
					sessionStorage.setItem('order', 1);
					alert(0);
				}else{
					sessionStorage.setItem('order', 0);
					alert(1);
				} */
				$.ajax({
		            async: true,
		            url: "/Rain/admin/cate_get?id="+id,
		            type: "get",
		            success: function (data) {
		            }
		        });
			}
			function order(){
				/* var data = sessionStorage.getItem('order');
				if(data == 0){
					sessionStorage.setItem('order', 1);
					alert(0);
				}else{
					sessionStorage.setItem('order', 0);
					alert(1);
				} */
				$.ajax({
		            async: true,
		            url: "/Rain/admin/cate_reverse",
		            type: "post",
		            success: function (data) {
		            }
		        });
			}
			layui.use(['jquery'], function() {
				//修改状态
				$('#table-list').on('click', '.table-list-status', function() {
					var That = $(this);
					var status = That.attr('data-status');
					var id = That.parent().attr('data-id');
					if(status == 1) {
						That.removeClass('layui-btn-normal').addClass('layui-btn-warm').html('隐藏').attr('data-status', 2);
					} else if(status == 2) {
						That.removeClass('layui-btn-warm').addClass('layui-btn-normal').html('显示').attr('data-status', 1);

					}
				})
				//栏目展示隐藏
				$('.showSubBtn').on('click', function() {
					var _this = $(this);
					var id = _this.attr('data-id');
					var parent = _this.parents('.parent');
					var child = $('.child-node-' + id);
					var childAll = $('tr[parentid=' + id + ']');
					if(parent.hasClass('collapsed')) {
						_this.html('-');
						parent.addClass('expanded').removeClass('collapsed');
						child.css('display', '');
					} else {
						_this.html('+');
						parent.addClass('collapsed').removeClass('expanded');
						child.css('display', 'none');
						childAll.addClass('collapsed').removeClass('expanded').css('display', 'none');
						childAll.find('.showSubBtn').html('+');
					}
				})
			});
		</script>
	</body>

</html>