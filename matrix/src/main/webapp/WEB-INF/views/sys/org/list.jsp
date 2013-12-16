<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="row">
			<div class="col-sm-3 widget-container-span"
				style="padding-right: 0px;">
				<div class="widget-box" style="margin-top: 0px;">
					<div class="widget-header header-color-blue">
						<h6 class="bigger lighter">组织树</h6>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<div class="table-responsive">
								<div class="zTreeDemoBackground left">
									<ul id="orgTree" class="ztree"></ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /span -->

			<div class="col-sm-9">
				<table id="orgTable"></table>
				<div id="orgTablepager"></div>
			</div>
		</div>

		<script type="text/javascript">
			var selectedOrgNode = null;
			var setting = {
					async: {
						enable: true,
						dataType: "json",
						url:"${ctx}/sys/org/getOrgTree",
						autoParam:["id"]
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: null
						}
					},
					callback: {
						onClick: function (event, treeId, treeNode) {
							selectedOrgNode = treeNode;
							//$("#orgTable").jqGrid('setGridParam',{postData:{"parentId":selectedOrgId}}).trigger("reloadGrid");
							$("#orgTable").jqGrid().trigger("reloadGrid");
						}
					}
			};
			$(function() {
				$.fn.zTree.init($("#orgTree"), setting);

				var grid_selector = "#orgTable";
				var pager_selector = "#orgTablepager";

				$(grid_selector).jqGrid({
					url:"${ctx}/sys/org/listData",
					postData : {"parentId" : function(){return selectedOrgNode == null ? "" : selectedOrgNode.id}},
					datatype : "json",
					height : 250,
					colNames : [ 'ID', '组织编码', '组织全称', '创建时间','创建人', '状态', '最后更新时间', '描述' ],
					colModel : [
					            {name : 'id',index : 'id',width : 60},
					            {name : 'code',index : 'code',width : 60, editable: true,editoptions:{size:"36",maxlength:"32"}},
					            {name : 'fullName',index : 'fullName',width : 60, editable: true,editoptions:{size:"36",maxlength:"254"}},
					            {name : 'createTime',index : 'createTime',width : 60,editable:true, sorttype:"date", unformat: pickDate},
					            {name : 'creator',index : 'creator',width : 60},
					            {name : 'status',index : 'status',width : 60},
					            {name : 'updateTime',index : 'updateTime',width : 60},
					            {name : 'description',index : 'description',width : 60}
							   ],

					viewrecords : true,
					rowNum : 10,
					rowList : [ 15,25,50,100 ],
					pager : pager_selector,
					altRows : true,
					//toppager: true,

					multiselect : true,
					//multikey: "ctrlKey",
					multiboxonly : true,
					
					jsonReader : {
					      root:"result",
					      page: "currpage",
					      total: "totalpage",
					      records: "totalCount"
					},

					loadComplete : function() {
						var table = this;
						setTimeout(function() {
							styleCheckbox(table);
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},

					//editurl:
					caption : "组织列表",
					autowidth : true
				});

				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
								.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}
				//navButtons
				$(grid_selector).jqGrid('navGrid',pager_selector,{ //navbar options
							alertcap : "信息",
							alerttext : "请您先选择要操作的记录！",
							edit : true,
							editicon : 'icon-pencil blue',
							editfunc : function(id){
								$("#org_edit_dialog_content").load('${ctx}/sys/org/edit?id='+id);
								$("#org_edit_dialog").modal("show");
							},
							add : true,
							addicon : 'icon-plus-sign purple',
							addfunc	: function () {
								var selectedOrgId = selectedOrgNode == null ? "" : selectedOrgNode.id;
								$("#org_add_dialog_content").load('${ctx}/sys/org/addNew?parentId='+selectedOrgId);
								$("#org_add_dialog").modal("show");
							},
							del : true,
							delicon : 'icon-trash red',
							search : true,
							searchicon : 'icon-search orange',
							refresh : true,
							refreshicon : 'icon-refresh green',
							view : true,
							viewicon : 'icon-zoom-in grey',
						},
						{
						//  default settings for edit
						}, 
						{ //  default settings for add
						},
						{},  // delete instead that del:false we need this
						{	// search options
							caption : "高级查询",
							recreateForm : true,
							afterShowSearch : function(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
								style_search_form(form);
							},
							afterRedraw : function() {
								style_search_filters($(this));
							},
							showQuery : true,
							multipleSearch:true
						}, 
						{} /* view parameters*/
					);
				
				

				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
				}

				

			

				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
				function styleCheckbox(table) {
					/**
						$(table).find('input:checkbox').addClass('ace')
						.wrap('<label />')
						.after('<span class="lbl align-top" />')
					
					
						$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
						.find('input.cbox[type=checkbox]').addClass('ace')
						.wrap('<label />').after('<span class="lbl align-top" />');
					 */
				}

				//unlike navButtons icons, action icons in rows seem to be hard-coded
				//you can change them like this in here if you want
				function updateActionIcons(table) {
					/**
					var replacement = 
					{
						'ui-icon-pencil' : 'icon-pencil blue',
						'ui-icon-trash' : 'icon-trash red',
						'ui-icon-disk' : 'icon-ok green',
						'ui-icon-cancel' : 'icon-remove red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					 */
				}

				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = {
						'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
						'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
						'ui-icon-seek-next' : 'icon-angle-right bigger-140',
						'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
					};
					$(
							'.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
							.each(
									function() {
										var icon = $(this);
										var $class = $.trim(icon.attr('class')
												.replace('ui-icon', ''));

										if ($class in replacement)
											icon.attr('class', 'ui-icon '
													+ replacement[$class]);
									})
				}

				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({
						container : 'body'
					});
					$(table).find('.ui-pg-div').tooltip({
						container : 'body'
					});
				}

				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
				$("#org_add_dialog_save").on('click',function() {
						$("#org_add_form").ajaxSubmit({
					        dataType:'json',
					        success:function (data){
					        	console.info(data);
					            if("success" == data.status){
					                $('#org_add_dialog').modal("hide");
					                
					                var treeObj = $.fn.zTree.getZTreeObj("orgTree");
					                var nodes = treeObj.getSelectedNodes();
					                if (nodes.length>0) {
					                	treeObj.reAsyncChildNodes(nodes[0], "refresh");
					                }else{
					                	treeObj.reAsyncChildNodes(null, "refresh");
					                }
					                
					                $("#orgTable").jqGrid().trigger("reloadGrid");
					        	}else{
					        		bootbox.alert(data.msg);
					        	}
					        }
					      });
				});
				
				$("#org_edit_dialog_save").on('click',function() {
					$("#org_edit_form").ajaxSubmit({
				        dataType:'json',
				        success:function (data){
				        	console.info(data);
				            if("success" == data.status){
				                $('#org_edit_dialog').modal("hide");
				                $("#orgTable").jqGrid().trigger("reloadGrid");
				        	}else{
				        		bootbox.alert(data.msg);
				        	}
				        }
				      });
			});
				
			})
		</script>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
<!-- 新增组织 -->
<div class="modal fade" id="org_add_dialog" tabindex="-1" role="dialog" aria-labelledby="org_add_dialog_title" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="org_add_dialog_title">新增-组织</h4>
      </div>
      <div id="org_add_dialog_content" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button id="org_add_dialog_save" type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

<!-- 修改组织 -->
<div class="modal fade" id="org_edit_dialog" tabindex="-1" role="dialog" aria-labelledby="org_edit_dialog_title" aria-hidden="true">
  <div class="modal-dialog">
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




