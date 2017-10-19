package cn.isofts.jdbc.test.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试并发查询子表的效率
 */
public class PeopleAccessMemberThread2 implements Runnable {

	public int index;

	public Connection conn;
	
	public static List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	
	public static Long startTime;
	
	public static Long endTime;
	
	public PeopleAccessMemberThread2(Connection conn, int index) {
		this.conn = conn;
		this.index = index;
	}

	@Override
	public void run() {
		try {
			System.out.println("SELECT * FROM vst_member_${index} WHERE vst_uid LIKE '%888888%'".replace("${index}", index + ""));
			
			PreparedStatement psmt = conn.prepareStatement("SELECT * FROM vst_member_${index} WHERE vst_uid LIKE '%888888%'".replace("${index}", index + ""));
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("1", rs.getObject(1));
				map.put("2", rs.getObject(2));
				map.put("3", rs.getObject(3));
				
				data.add(map);
			}

			endTime = new Date().getTime();
			System.out.println(endTime + " access vst_member");

			rs.close();
			psmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// 连接池
		Map<Integer, Connection> connPool = new HashMap<Integer, Connection>();

		Class.forName("com.mysql.jdbc.Driver");

		for (int i = 0; i <= 9; i++) {
			Connection conn = DriverManager.getConnection("jdbc:mysql://10.2.0.85:3306/test_sharding_2000?useUnicode=true&characterEncoding=UTF-8", "root", "");
			connPool.put(i, conn);
		}

		startTime = new Date().getTime();
		System.out.println(startTime);

		// 创建线程
		for (int i = 0; i <= 9; i++) {
			Thread thread = new Thread(new PeopleAccessMemberThread2(connPool.get(i), i));
			thread.start();

			System.out.println("启动线程成功！" + thread.getId() + " " + i);
		}
		
		Thread.sleep(3000);
		
		System.out.println(data);
		System.out.println(data.size());
		System.out.println(endTime - startTime);
	}

}
