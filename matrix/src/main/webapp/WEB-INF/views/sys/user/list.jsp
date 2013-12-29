<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="row">
	<div class="col-xs-12">
		<div class="widget-box">
			<div class="widget-header widget-header-small">
				<h5 class="lighter">用户查询</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main">
<<<<<<< HEAD
					<form id="user-form-search" class="form-search">
					<fieldset>
						<label>用户账号：</label>
						<input type="text" name="account"/>&nbsp;&nbsp;
						<label>用户编码：</label>
						<input type="text" name="code"/>&nbsp;&nbsp;
						<label>用户名称：</label>
						<input type="text" name="name"/>&nbsp;&nbsp;
						<label>所属组织：</label>
						<input type="hidden" id ="queryOrgId" name="org.id"/>
						<input type="text" id ="queryorgFullName" name="org.fullName"/>&nbsp;&nbsp;
						<label>启用/禁用：</label>
						<select name="isEnable">
							<option value="">--全部--</option>
							<option value="1">启用</option>
							<option value="0">禁用</option>
						</select>&nbsp;&nbsp;
						
						
						<button onclick="search();" type="button" class="btn btn-purple btn-sm">
							查询 <i class="icon-search icon-on-right bigger-110"></i>
						</button>							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12">
		<table id="userTable"></table>
		<div id="userTablepager"></div>
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<!-- 新增用户 -->
<div class="modal fade" id="add_dialog" tabindex="-1" role="dialog" aria-labelledby="add_dialog_title" aria-hidden="true">
  <div class="modal-dialog" style="width: 760px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="add_dialog_title">新增-用户</h4>
      </div>
      <div id="add_dialog_content" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button id="add_dialog_save" type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

<!-- 修改组织 -->
<div class="modal fade" id="edit_dialog" tabindex="-1" role="dialog" aria-labelledby="edit_dialog_title" aria-hidden="true">
  <div class="modal-dialog" style="width: 970px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="edit_dialog_title">修改-用户</h4>
      </div>
      <div id="edit_dialog_content" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button id="edit_dialog_save" type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

=======
					<form class="form-search">
						<input type="text" class="search-query" placeholder="用户名" />
						<button type="button" class="btn btn-purple btn-sm">
							查询 <i class="icon-search icon-on-right bigger-110"></i>
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12">
		<table id="userTable"></table>
		<div id="userTablepager"></div>
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<!-- 新增用户 -->
<div class="modal fade" id="add_dialog" tabindex="-1" role="dialog" aria-labelledby="add_dialog_title" aria-hidden="true">
  <div class="modal-dialog" style="width: 760px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="add_dialog_title">新增-用户</h4>
      </div>
      <div id="add_dialog_content" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button id="add_dialog_save" type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

<!-- 修改组织 -->
<div class="modal fade" id="org_edit_dialog" tabindex="-1" role="dialog" aria-labelledby="org_edit_dialog_title" aria-hidden="true">
  <div class="modal-dialog" style="width: 970px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="org_edit_dialog_title">修改-组织</h4>
      </div>
      <div id="org_edit_dialog_content" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button id="org_edit_dialog_save" type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>
>>>>>>> branch 'master' of https://github.com/doter/matrix.git
<script type="text/javascript" charset="utf-8">
<%@ include file="list.js" %>
</script>
