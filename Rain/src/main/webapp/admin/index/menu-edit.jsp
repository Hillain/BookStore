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
		<div class="wrap-container">
			<form class="layui-form" style="width: 90%;padding-top: 50px;" method="post" action="/Rain/admin/cate_edit" id="data_form">
					<div class="layui-form-item">
						<label class="layui-form-label">应用：</label>
						<div class="layui-input-block">
							<input type="text" name="title" required lay-verify="required" value="若水图书" disabled="true" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item" style="padding-top: 10px;">
						<label class="layui-form-label">名称：</label>
						<div class="layui-input-block">
							<input type="text" name="lx" required lay-verify="required" value="${cateinfo.lx }" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item" style="padding-top: 10px;">
						<label class="layui-form-label">创建者：</label>
						<div class="layui-input-block">
							<input type="text" name="title" required lay-verify="required" value="超级管理员" disabled="true" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item" style="padding-top: 10px;">
						<label class="layui-form-label">状态：</label>
						<div class="layui-input-block">
							<c:if test="${cateinfo.state==1}">
								<input type="radio" name="state" value="1" title="显示" checked>
								<input type="radio" name="state" value="0" title="隐藏">
							</c:if> 
							<c:if test="${cateinfo.state==0}">
								<input type="radio" name="state" value="1" title="显示">
								<input type="radio" name="state" value="0" title="隐藏" checked>
							</c:if> 
						</div>
					</div>
					<div class="layui-form-item layui-form-text" style="padding-top: 10px;">
						<label class="layui-form-label">分类描述：</label>
						<div class="layui-input-block">
							<textarea name="descr" id="area" class="layui-textarea">${cateinfo.descr}</textarea>
						</div>
					</div>
					<div class="layui-form-item" style="padding-top: 15px;">
						<div class="layui-input-block">
							<input name="id" value="${cateinfo.id }" style="visibility: hidden;">
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
					$.ajax({
			            async: true,
			            url: "/Rain/admin/cate_edit",
			            data: $('#data_form').serialize(),
			            type: "post",
			            success: function (data) {
			            }
			        });
					//当你在iframe页面关闭自身时
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭
					layer.closeAll();
					return false;
				});
			});
		</script>
	</body>
</html>