package cn.isofts.pm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.isofts.pm.service.RoleService;

import com.isofts.dao.EasyDao;

@Service
public class RoleServiceImpl implements RoleService {
	
	// service
	
	@Autowired
	private EasyDao easyDao;
	
	// method
	
	/**
	 * 查询多个角色
	 */
	public List<Map<String, Object>> getRoles() {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT");
//		sql.append(" 	role_.*,");
//		sql.append(" 	creator_.FName FCreatorName,");
//		sql.append(" 	updator_.FName FUpdatorName");
//		sql.append(" FROM");
//		sql.append(" 	t_pm_role 				role_");
//		sql.append(" 	INNER JOIN t_bd_person	creator_  ON role_.FCreatorId = creator_.FId");
//		sql.append(" 	LEFT JOIN t_bd_person	updator_  ON role_.FUpdatorId = updator_.FId");
//		sql.append(" ORDER BY");
//		sql.append(" 	role_.FName");
		
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM vst_member WHERE vst_uid LIKE '%888888%'");
		
		return easyDao.list(sql.toString());
	}
	
}