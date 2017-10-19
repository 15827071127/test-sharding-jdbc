package cn.isofts.pm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {

	/**
	 * 查询多个角色
	 */
	List<Map<String, Object>> getRoles();

}