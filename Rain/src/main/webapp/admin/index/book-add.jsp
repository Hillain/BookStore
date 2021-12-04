<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="../../static/admin/layui/css/layui.css" />
		<link rel="stylesheet" type="text/css" href="../../static/admin/css/admin.css" />
	</head>
	<body>
		<form class="layui-form" style="width: 90%;padding-top: 20px;" method="post" id="data_form" action="/Rain/admin/book_add" enctype="multipart/form-data">
					<div class="layui-form-item">
						<label class="layui-form-label">分类：</label>
						<div class="layui-input-block">
							<select name="bcategory" lay-verify="required">
								<c:forEach items="${cates }" var="c">
									<option value="${c.id }">${c.lx}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">书名：</label>
						<div class="layui-input-block">
							<input type="text" name="bname" required lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">作者：</label>
						<div class="layui-input-block">
							<input type="text" name="bauthor" required lay-verify="required" placeholder="请输入作者" autocomplete="off" class="layui-input">
						</div>

					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">出版社：</label>
						<div class="layui-input-block">
							<input type="text" name="bpublish" required lay-verify="required" placeholder="请输入出版社" autocomplete="off" class="layui-input">
						</div>

					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">价格：</label>
						<div class="layui-input-block">
							<input type="text" name="bprice" required lay-verify="required" placeholder="请输入价格" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">介绍：</label>
						<div class="layui-input-block">
							<textarea name="binfo" placeholder="请输入内容" class="layui-textarea"></textarea>
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">库存</label>
						<div class="layui-input-block">
							<input type="text" name="bstock" required lay-verify="required" placeholder="请输入库存" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">图片</label>
						<div class="layui-input-block">
							<input type="file" name="file" accept="image/*">
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo">立即提交</button>
							<button type="reset" class="layui-btn layui-btn-primary">重置</button>
						</div>
					</div>
				</form>
		</div>

		<script src="../../static/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../ui/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../../adminjs/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script>
			layui.use(['form'], function() {
				var form = layui.form();
				form.render();
				//监听提交
				form.on('submit(formDemo)', function(data) {
					var form=document.querySelector("#data_form");
					var formData = new FormData(form);
					$.ajax({
						async: true,
			            url: "/Rain/admin/book_add",
			            data: formData,
			            type: "post",
			            contentType: false,
			            processData: false,//这两个一定设置为false
			            success: function (data) {
			            	
			            }
			        });
					//当你在iframe页面关闭自身时
					var index = parent.layer.getFrameIndex(window.name); 
					parent.layer.close(index); 
					layer.closeAll();
					return false;
				});
			});
		</script>
	</body>
</html>