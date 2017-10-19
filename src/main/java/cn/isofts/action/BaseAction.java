package cn.isofts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.isofts.dao.EasyDao;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;

	// parameters

	public Object returnObject;

	// dao

	@Autowired
	protected EasyDao basicService;

	public HttpServletRequest request;

	public HttpServletResponse response;

	public Map<String, Object> session;

	// get and set

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

}