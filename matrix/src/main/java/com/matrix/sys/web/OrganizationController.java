package com.matrix.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Object listData(String parentId) {
		Page<Organization> page = new Page<Organization>();
		Map queryParams = new HashMap();
		queryParams.put("parentId", parentId);
		page = service.findPage(page, queryParams);
		return page;
	}
	
	@RequestMapping(value="/addNew", method = RequestMethod.GET)
	public @ModelAttribute("org") Organization addNew(String parentId) {
		Organization org = new Organization();
		//Organization parent = new Organization();
		if(StringUtils.isNotEmpty(parentId)){
			Organization parent = this.service.get(parentId);
			org.setParent(parent);
		}
		//org.setParent(parent);
		return org;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult  save(Organization org) {
		service.save(org);
		AjaxResult rs = new AjaxResult(AjaxResult.STATUS_SUCCESS);
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
		//return service.getChildren(orgId);
		return TreeUtils.treeToJson(service.getChildren(id));
	}
	
	/**
	 * 生成组织机构json
	 */
	private String orgTreeJson(List<Organization> list) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			for (int i = 0; i < list.size(); i++) {
				Organization org = list.get(i);
				sb.append("{\"id\":\"").append(org.getId()).append("\"");
				
				
				if(null == org.getParent()){
					sb.append(",\"pId\":null");
				}else{
					sb.append(",\"pId\":\"").append(org.getParent().getId()).append("\"");
				}
				
				sb.append(",\"name\":\"").append(org.getName()).append("\"");
				sb.append(",\"isParent\":").append("true").append("");
				//if (org.getLeaf()) {
				//	sb.append("false");
				//} else {
				//	sb.append("true");
				//}
				sb.append("");
				if (i == list.size() - 1) {
					sb.append("}");
				} else {
					sb.append("},");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("]");
		return sb.toString();
	}
}


