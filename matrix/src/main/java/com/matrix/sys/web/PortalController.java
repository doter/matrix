/**
 * <b>包名：</b>org.matrix.sys.controller<br/>
 * <b>文件名：</b>PortalController.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-24-下午10:15:58<br/>
 * <br/>
 */
package com.matrix.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>类名称：</b>PortalController<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-24 下午10:15:58<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
@Controller
@RequestMapping("/portal")
public class PortalController {
	
	@RequestMapping("/about")
	public String about() {
		return "/portal/about";
	}

	@RequestMapping("/link")
	public String link() {
		return "/portal/link";
	}
	
	@RequestMapping("/repair")
	public String repair() {
		return "/portal/repair";
	}
	
	@RequestMapping("/seq")
	public String seq() {
		return "/portal/seq";
	}
	
	@RequestMapping("/about2")
	public String about2() {
		return "/portal/about2";
	}
	
	@RequestMapping("/qun")
	public String qun() {
		return "/portal/qun";
	}

}
