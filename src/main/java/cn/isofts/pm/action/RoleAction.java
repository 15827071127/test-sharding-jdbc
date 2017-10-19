package cn.isofts.pm.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.isofts.action.BaseAction;
import cn.isofts.pm.service.RoleService;

import com.isofts.dao.EasyDao;

/**
 * 角色
 * 
 * @author Kevin
 *
 */
@Controller
@Scope("session")
@Namespace("/role")
@SuppressWarnings("serial")
@ParentPackage("json-default")
public class RoleAction extends BaseAction {
	
	// parameter
	
	private Map<String, String> role;
	
	// service
	
	@Autowired
	private EasyDao easyDao;
	
	@Autowired
	private RoleService roleService;
	
	// action

	/**
	 * 角色数据
	 */
	@Action(
	    value = "json", 
	    results = { 
	        @Result(
	            name = "json", 
	            type = "json", 
	            params  = { "root", "returnObject" }
	        ) 
		}
	)
	public String getJson() {
		String type = request.getParameter("type");
		
		// tree
		if ("0".equals(type)) {
			
		// treegrid
		} else if ("1".equals(type)) {
			
		// datagrid
		} else if ("2".equals(type)) {
			
		// combobox
		} else if ("3".equals(type)) {
			setReturnObject(roleService.getRoles());
			
		}

		return "json";
	}

	// get and set
	
	public Map<String, String> getRole() {
		return role;
	}

	public void setRole(Map<String, String> role) {
		this.role = role;
	}
	
}