$(function() {
	var grid_render_id = "#userTable";
	var pager_render_id = "#userTablepager";

	$(grid_render_id).jqGrid(
			{
				caption : "用户列表",
				height : 460,
				rowNum : 15,
				rowList : [ 15, 25, 50, 100 ],
				url : "${ctx}/sys/user/listData",
				datatype : "json",
				colNames : [ 'ID', '用户编码', '用户账号', '用户名称', '启用', '所属组织','创建时间', '创建人', '状态', '更新时间' ],
				colModel : [ {name : 'id',index : 'id',hidden : true},
				             {name : 'code',index : 'code',width : 120},
				             {name : 'account',index : 'account',width : 80},
				             {name : 'name',index : 'name',width : 80},
				             {name : 'isEnable',index : 'isEnable',width : 60},
				             {name : 'org.name',index : 'org.name',width : 200}, 
				             {name : 'createTime',index : 'createTime',width : 120},
				             {name : 'creator',index : 'creator',width : 60},
				             {name : 'status',index : 'status',width : 60},
				             {name : 'updateTime',index : 'updateTime',width : 120}
				            ],
				viewrecords : true,
				pager : pager_render_id,
				altRows : true,
				sortname : "createTime",
				autowidth : true,
				multiselect : true,
				//multikey: "ctrlKey",
				multiboxonly : true,
				jsonReader : {
					root : "result",
					page : "currPage",
					total : "totalPage",
					records : "totalCount"
				},
				loadComplete : function() {
					var table = this;
					setTimeout(function() {
						$.jqGridExt.setStyleCheckbox(table);
						$.jqGridExt.updateActionIcons(table);
						$.jqGridExt.updatePagerIcons(table);
						$.jqGridExt.enableTooltips(table);
					}, 0);
				}
			});
	//navButtons
	$(grid_render_id).jqGrid(
			'navGrid',
			pager_render_id,
			{ //navbar options
				alertcap : "消息",
				alerttext : "请您先选择要操作的记录！",
				edit : true,
				edittext : "修改",
				editicon : 'icon-pencil blue',
				editfunc : edit,
				add : true,
				addtext : "新建",
				addicon : 'icon-plus-sign purple',
				addfunc : addNew,
				del : true,
				deltext : "删除",
				delicon : 'icon-trash red',
				delfunc : remove,
				search : false,
				searchtext : "查询",
				searchicon : 'icon-search orange',
				refresh : true,
				refreshtext : "刷新",
				refreshicon : 'icon-refresh green',
				view : false,
				viewtext : "查看",
				viewicon : 'icon-zoom-in grey',
			},
			{
			//  default settings for edit
			},
			{ //  default settings for add
			},
			{}, // delete instead that del:false we need this
			{ // search options
				caption : "高级查询",
				recreateForm : true,
				afterShowSearch : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title')
							.wrap('<div class="widget-header" />')
					$.jqGridExt.setStyleSearchForm(form);
				},
				afterRedraw : function() {
					$.jqGridExt.setStyleSearchFilters($(this));
				},
				showQuery : false,
				multipleSearch : true
			}, {} /* view parameters*/
	);

	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
	$("#add_dialog_save").on('click', function() {
		if(!$("#addNew_form").valid()){
			return
		}
		$("#addNew_form").ajaxSubmit({
			dataType : 'json',
			success : function(data) {
				if ("success" == data.status) {
					$('#add_dialog').modal("hide");
					$(grid_render_id).jqGrid().trigger("reloadGrid");
				} else {
					bootbox.alert(data.msg);
				}
			}
		});
	});

	$("#org_edit_dialog_save").on('click', function() {
		$("#org_edit_form").ajaxSubmit({
			dataType : 'json',
			success : function(data) {
				console.info(data);
				if ("success" == data.status) {
					$('#org_edit_dialog').modal("hide");
					$(grid_render_id).jqGrid().trigger("reloadGrid");
				} else {
					bootbox.alert(data.msg);
				}
			}
		});
	});
	
	function addNew() {
		$("#add_dialog_content").load('${ctx}/sys/user/addNew');
		$("#add_dialog").modal("show");
	}

	function edit(id) {
		$("#edit_dialog_content").load('${ctx}/sys/user/edit?id=' + id);
		$("#edit_dialog").modal("show");
	}

	function remove(id) {
		bootbox.confirm("您确认要删除该用户吗?", function(result) {
			if (result) {
				$.get("${ctx}/sys/user/remove/" + id,
						function(data) {
							if ("success" == data.status) {
								$(grid_render_id).jqGrid().trigger("reloadGrid");
							} else if ("error" == data.status) {
								bootbox.alert(data.msg);
							}
						}, "json");
			}
		});
	}
})

