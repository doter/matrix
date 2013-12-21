package com.matrix.sys.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googlecode.genericdao.search.Sort;
import com.matrix.core.util.Page;
import com.matrix.core.util.TreeUtils;
import com.matrix.core.web.BaseController;
import com.matrix.core.web.util.AjaxResult;
import com.matrix.sys.model.Organization;
import com.matrix.sys.service.OrganizationService;

@Controller
@RequestMapping(value = "/sys/org")
public class OrganizationController extends BaseController{
	@Autowired
	private OrganizationService service;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list() {
		return "sys/org/list";
	}

	@RequestMapping(value="/listData", method = RequestMethod.GET)
	@ResponseBody
	public Object listData(@RequestParam String parentId, 
			@RequestParam(value = "page", defaultValue = "1") Integer currPage,
			@RequestParam(value = "rows", defaultValue = "20") Integer pageSize,
			@RequestParam(value = "sidx") String sortField,
			@RequestParam(value = "sord") String sortType,
			String filters) {
		
		Page<Organization> page = new Page<Organization>();
		page.setPageSize(pageSize);
		page.setCurrPage(currPage);
		if(StringUtils.isNotEmpty(sortField)){
			page.getSorts().add(new Sort(sortField, "desc".equalsIgnoreCase(sortType) ? true : false));
		}
		
		Map queryParams = new HashMap();
		queryParams.put("parentId", parentId);
		if(StringUtils.isNotEmpty(filters)){
			queryParams.put("filters", filters);
		}
		
		page = service.findPage(page, queryParams);
		return page;
	}
	
	@RequestMapping(value="/addNew", method = RequestMethod.GET)
	public @ModelAttribute("org") Organization addNew(String parentId) {
		Organization org = new Organization();
		if(StringUtils.isNotEmpty(parentId)){
			Organization parent = this.service.get(parentId);
			org.setParent(parent);
		}
		return org;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String id) {
		return "sys/org/edit";
	}
	
	@ModelAttribute("org")
	public Organization getValue(String id){
		Organization org = null;
		if(StringUtils.isNotEmpty(id)){
			org = service.get(id);
		}
		return org;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult save(@ModelAttribute("org")Organization org) {
		service.save(org);
		AjaxResult rs = new AjaxResult(AjaxResult.STATUS_SUCCESS);
		return rs;
	}
	
	@RequestMapping(value="/remove/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult remove(@PathVariable String id) {
		AjaxResult rs = new AjaxResult();
		if(StringUtils.isEmpty(id)){
			rs.setStatus(AjaxResult.STATUS_ERROR);
			rs.setMsg("删除时参数id不能为空！");
			return rs;
		}
		try{
			service.removeById(id);
			rs.setStatus(AjaxResult.STATUS_SUCCESS);
			rs.setMsg("删除成功！");
		}catch(Exception e){
			rs.setStatus(AjaxResult.STATUS_ERROR);
			rs.setMsg("删除失败！<br/>" + e.getMessage());
		}
		
		return rs;
	}
	
	
	
	/**
	 * 
	 * @param id 组织ID
	 * @return
	 */
	@RequestMapping(value="/getOrgTree", method = RequestMethod.POST)
	@ResponseBody
	public String  getOrgTree(String id) {
		return TreeUtils.treeToJson(service.getChildren(id));
	}
	
	
}


