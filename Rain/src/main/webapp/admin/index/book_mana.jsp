<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="iframe-h">
	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>图书管理</title>
		<link rel="stylesheet" type="text/css" href="../../static/admin/layui/css/layui.css" />
		<link rel="stylesheet" type="text/css" href="../../static/admin/css/admin.css" />
	</head>

	<body>
		<div class="wrap-container clearfix">
				<div class="column-content-detail">
					<form class="layui-form" action="">
						<div class="layui-form-item">
							<div class="layui-inline tool-btn">
								<button class="layui-btn layui-btn-small layui-btn-normal addBtn" data-url="book-add.jsp"><i class="layui-icon">&#xe654;</i></button>
								<button class="layui-btn layui-btn-small layui-btn-danger delBtn" onclick="delAllBook();"><i class="layui-icon">&#xe640;</i></button>
								<button class="layui-btn layui-btn-small layui-btn-warm listOrderBtn hidden-xs" onclick="order();"><i class="iconfont">&#xe656;</i></button>
							</div>
							<!-- <div class="layui-inline">
								<input type="text" name="title" required lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
							</div>
							<div class="layui-inline">
								<select name="states" lay-filter="status">
									<option value="">请选择一个状态</option>
									<option value="010">正常</option>
									<option value="021">停止</option>
									<option value="0571">删除</option>
								</select>
							</div>
							<button class="layui-btn layui-btn-normal" lay-submit="search">搜索</button> -->
						</div>
					</form>
					<div class="layui-form" id="table-list">
						<table class="layui-table" lay-even lay-skin="nob">
							<colgroup>
								<col width="50">
								<col class="hidden-xs" width="50">
								<col class="hidden-xs" width="100">
								<col>
								<col class="hidden-xs" width="150">
								<col class="hidden-xs" width="150">
								<col width="80">
								<col width="150">
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
									<th class="hidden-xs">ID</th>
									<th class="hidden-xs">书名</th>
									<th>作者</th>
									<th class="hidden-xs">出版社</th>
									<th class="hidden-xs">价格</th>
									<th>分类</th>
									<th style="width: 250px;">介绍</th>
									<th>库存</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${ order_book == 1}">
								<!--ssssssssssss-->									
									<c:forEach items="${books }" var="b" varStatus="s">
										<tr>
											<td><input type="checkbox" name="boxs" value="${b.bookid}" lay-skin="primary" data-id="1"></td>
											<td class="hidden-xs">${b.bookid}</td>
											<td>${b.bname}</td>
											<td>${b.bauthor}</td>
											<td>${b.bpublish}</td>
											<td class="hidden-xs">${b.bprice}</td>
											<td class="hidden-xs">${b.bcategory}</td>
											<td class="hidden-xs">${b.binfo}</td>
											<td><button class="layui-btn layui-btn-mini layui-btn-normal">${b.bstock}</button></td>
											<td>
												<div class="layui-inline">
													<button class="layui-btn layui-btn-small layui-btn-normal go-btn" data-id="1" data-url="book-edit.jsp" onclick="return getBook(${b.bookid });"><i class="layui-icon">&#xe642;</i></button>
													<button class="layui-btn layui-btn-small layui-btn-danger del-btn" data-id="1" onclick="return delBook(${b.bookid });"><i class="layui-icon">&#xe640;</i></button>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${ order_book == 0}">
								<!-- dddddddddddd -->
									<c:forEach items="${books }" var="b" varStatus="s">
										<tr>
											<td><input type="checkbox" name="boxs" value="${b.bookid}" lay-skin="primary" data-id="1"></td>
											<td class="hidden-xs">${b.bookid}</td>
											<td>${b.bname}</td>
											<td>${b.bauthor}</td>
											<td>${b.bpublish}</td>
											<td class="hidden-xs">${b.bprice}</td>
											<td class="hidden-xs">${b.bcategory}</td>
											<td class="hidden-xs">${b.binfo}</td>
											<td><button class="layui-btn layui-btn-mini layui-btn-normal">${b.bstock}</button></td>
											<td>
												<div class="layui-inline">
													<button class="layui-btn layui-btn-small layui-btn-normal edit-btn" data-id="1" data-url="book-edit.jsp" onclick="return getBook(${b.bookid });"><i class="layui-icon">&#xe642;</i></button>
													<button class="layui-btn layui-btn-small layui-btn-danger del-btn" data-id="1" onclick="return delBook(${b.bookid });"><i class="layui-icon">&#xe640;</i></button>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<!-- <div class="page-wrap">
							<ul class="pagination">
								<li class="disabled"><span>«</span></li>
								<li class="active"><span>1</span></li>
								<li>
									<a href="#">2</a>
								</li>
								<li>
									<a href="#">»</a>
								</li>
							</ul>
						</div> -->
					</div>
				</div>
		</div>
		<script src="../../static/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../static/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../ui/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../adminjs/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
	</body>
	<script type="text/javascript">
			function delBook(id){
				$.ajax({
		            async: true,
		            url: "/Rain/admin/book_del?id="+id,
		            type: "get",
		            success: function (data) {
		            	
		            }
		        });
			}
			function delAllBook(){
				var s = '';
				/* var obq = document.getElementsByName('boxs');
				for (var i = 0; i < obq.length; i++) {
		             if (obq[i].checked) s += obj[i].value +",";
		        } */
				$.each($('input:checkbox'),function(){
	                if(this.checked){
	                    /* window.alert("你选了："+
	                        $('input[type=checkbox]:checked').length+"个，其中有："+$(this).val()); */
	                	s += $(this).val() +",";
	                }
	            });
				//alert(s);
				$.ajax({
		            async: true,
		            url: "/Rain/admin/book_delAll?id="+s,
		            type: "get",
		            success: function (data) {
		            	
		            }
		        });
			}
			function getBook(id){
				$.ajax({
		            async: true,
		            url: "/Rain/admin/book_get?id="+id,
		            type: "get",
		            success: function (data) {
		            }
		        });
			}
			function order(){
				$.ajax({
		            async: true,
		            url: "/Rain/admin/book_reverse",
		            type: "post",
		            success: function (data) {
		            }
		        });
			}
		</script>
</html>