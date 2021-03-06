package com.matrix.sys.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.matrix.sys.enums.ResourceType;
import com.matrix.sys.enums.ResourceTypeEditor;
import com.matrix.sys.model.Resource;
import com.matrix.sys.service.ResourceService;

@Controller
@RequestMapping(value = "/sys/res")
public class ResourceController extends BaseController{

	@Autowired
	private ResourceService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(ResourceType.class, new ResourceTypeEditor());
	}

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list() {
		return "sys/res/list";
	}
	
	@RequestMapping(value="/listData", method = RequestMethod.GET)
	@ResponseBody
	public Object listData(@RequestParam(value = "page", defaultValue = "1") Integer currPage,
			@RequestParam(value = "rows", defaultValue = "20") Integer pageSize,
			@RequestParam(value = "sidx") String sortField,
			@RequestParam(value = "sord") String sortType,
			String filters) {
		
		Page<Resource> page = new Page<Resource>();
		page.setPageSize(pageSize);
		page.setCurrPage(currPage);
		page.getSorts().add(new Sort("levelCode",false));
		
		
		Map queryParams = new HashMap();
		
		page = service.findPage(page, queryParams);
		return page;
	}
	
	@RequestMapping(value="/addNew", method = RequestMethod.GET)
	public @ModelAttribute("res") Resource addNew(String parentId) {
		Resource res = new Resource();
		if(StringUtils.isNotEmpty(parentId)){
			Resource parent = this.service.get(parentId);
			res.setParent(parent);
			res.setFullName(parent.getFullName()+".");
		}
		return res;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String id) {
		return "sys/res/edit";
	}
	
	@ModelAttribute("res")
	public Resource getValue(String id){
		Resource res = null;
		if(StringUtils.isNotEmpty(id)){
			res = service.get(id);
		}else{
			res = new Resource();
		}
		return res;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult save(@ModelAttribute("res")Resource res) {
		AjaxResult rs = new AjaxResult();
		try{
			if(null == res.getIsSuper()){
				res.setIsSuper(false);
			}
			service.save(res);
			rs.setStatus(AjaxResult.STATUS_SUCCESS);
			rs.setMsg("保存成功！");
		}catch(Exception e){
			rs.setStatus(AjaxResult.STATUS_ERROR);
			rs.setMsg("保存失败！<br/>" + e.getMessage());
		}
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
	
	@RequestMapping(value="/getResourceTree")
	@ResponseBody
	public String  getResourceTree() {
		return TreeUtils.treeToJson(service.getAll());
	}
}
