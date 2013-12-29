<<<<<<< HEAD
var grid_render_id = "#userTable";
var pager_render_id = "#userTablepager";
$(function() {
	$(grid_render_id).jqGrid(
			{
				caption : "用户列表",
				height : 460,
				rowNum : 15,
				rowList : [ 15, 25, 50, 100 ],
				url : "${ctx}/sys/user/listData",
				datatype : "json",
				colNames : [ 'ID', '用户编码', '用户账号', '用户名称', '启用', '所属组织','创建时间', '创建人', '状态', '更新时间','操作'],
				colModel : [ {name : 'id',index : 'id',hidden : true},
				             {name : 'code',index : 'code',width : 120},
				             {name : 'account',index : 'account',width : 80},
				             {name : 'name',index : 'name',width : 80},
				             {name : 'isEnable',index : 'isEnable',width : 60},
				             {name : 'org.name',index : 'org.name',width : 200}, 
				             {name : 'createTime',index : 'createTime',width : 120},
				             {name : 'creator',index : 'creator',width : 60},
				             {name : 'status',index : 'status',width : 60},
				             {name : 'updateTime',index : 'updateTime',width : 120},
				             {name : 'id', index: 'id', align:"center",formatter: function (cellvalue, options, rowObject) {
				            	 var actions = "<a href='javascript:void(0)'  onclick=\"resetPw('" + cellvalue + "')\">密码重置</a>"
				            	 return actions;
				             }}
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
			},
			{}/* view parameters*/
			
	).jqGrid('navButtonAdd',pager_render_id,{
		   caption:"提交", 
		   title : "提交用户信息",
		   buttonicon:"icon-ok blue", 
		   onClickButton: submit,
		   position:"before refresh_userTable"
		});

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
	
	$("#edit_dialog_save").on('click', function() {
		if(!$("#edit_form").valid()){
			return
		}
		$("#edit_form").ajaxSubmit({
			dataType : 'json',
			success : function(data) {
				if ("success" == data.status) {
					$('#edit_dialog').modal("hide");
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

function resetPw(id) {
		alert(id);
}

function submit(id) {
	var selectedId = jQuery(grid_render_id).jqGrid('getGridParam','selrow');
	if(null == selectedId || "" == selectedId){
		bootbox.alert("请选择要提交的记录！");
		return;
	}
	bootbox.confirm("您确认要提交该用户信息吗?", function(result) {
		if (result) {
			$.get("${ctx}/sys/user/submit/" + selectedId,
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

var queryOrgTree = $("#queryorgFullName").dropDownTree({ssource:"${ctx}/sys/org/getOrgTree",fieldId:"queryOrgId",selectEmptyText:"清空组织",queryAddon:false});
$("#queryorgFullName").click(function(event){
	queryOrgTree.show();
 });


function getQueryFilter() {
	var rules = '';
	var form = document.getElementById("user-form-search");
    for (var i = 0; i < form.elements.length; i++) {
        var e = form.elements[i];
        if("" == e.value){
    		continue;
    	}
        
        if(e.name == 'account'){
        	rules += '{"t":"s","f":"account","op":"like","v":"'+e.value+'"},';
		}else if(e.name == 'code'){
			rules += '{"t":"s","f":"code","op":"like","v":"'+e.value+'"},';
		}else if(e.name == 'name'){
			rules += '{"t":"s","f":"name","op":"like","v":"'+e.value+'"},';
		}else if(e.name == 'org.id'){
			rules += '{"t":"s","f":"org.id","op":"eq","v":"'+e.value+'"},';
		}else if(e.name == 'isEnable'){
			rules += '{"t":"b","f":"isEnable","op":"eq","v":"'+e.value+'"},';
		}
    } 
    if(0 == rules.length){
    	return "";
    }
    rules = rules.substring(0,rules.length-1);
    return "{\"groupOp\":\"AND\",\"rules\":["+rules+"]}";
};

function search(){
	$(grid_render_id).jqGrid('setGridParam',{postData:{"filters":getQueryFilter()}}).trigger("reloadGrid");
}
=======
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

>>>>>>> branch 'master' of https://github.com/doter/matrix.git
