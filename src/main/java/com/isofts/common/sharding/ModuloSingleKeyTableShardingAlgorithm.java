package com.isofts.common.sharding;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * 根据单个sharding key取模，对表分片算法
 * 
 * @author Kevin
 *
 */
public class ModuloSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
	
	/**
	 * sql == 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM t_order WHERE order_id = 11
	 * 		└── SELECT * FROM t_order_1 WHERE order_id = 11
	 * 
	 * SELECT * FROM t_order WHERE order_id = 44
	 * 		└── SELECT * FROM t_order_0 WHERE order_id = 44
	 * 
	 * @param tableNames 切分的所有表名
	 * @param shardingValue 等号右边的值
	 * 
	 */
	@Override
	public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
//		Integer modValue = shardingValue.getValue() % 100;
//		String modStr = modValue < 10 ? "0" + modValue : modValue.toString();
		
		// 取模
		Integer modValue = shardingValue.getValue() % 10;
		// 表名后缀
		String modStr = "_" + modValue;
		
		for (String tableName : tableNames) {
			if (tableName.endsWith(modStr)) {
				return tableName;
			}
		}
		
		throw new IllegalArgumentException();
	}

	/**
	 * sql between 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM t_order WHERE order_id BETWEEN 10 AND 20
	 *      ├── SELECT * FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
	 *      └── SELECT * FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
	 * 
	 * @param tableNames 切分的所有表名
	 * @param shardingValue BETWEEN的参数值
	 * 
	 */
	@Override
	public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
		// 符合条件的表名
		Collection<String> result = new LinkedHashSet<String>(tableNames.size());
		// BETWEEN的参数值
		Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
		
		// 遍历BETWEEN的参数
		for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
			// 取模
			Integer modValue = i % 10;
			// 表名后缀
			String modStr = "_" + modValue;
			
			for (String tableName : tableNames) {
				if (tableName.endsWith(modStr)) {
					result.add(tableName);
				}
			}
		}
		
		return result;
	}

	/**
	 * sql in 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM t_order WHERE order_id IN (11, 44)
	 *      ├── SELECT * FROM t_order_0 WHERE order_id IN (11, 44)
	 *      └── SELECT * FROM t_order_1 WHERE order_id IN (11, 44)
	 * 
	 * SELECT * FROM t_order WHERE order_id IN (11, 13, 15)
	 * 		└── SELECT * FROM t_order_1 WHERE order_id IN (11, 13, 15)
	 * 
	 * SELECT * FROM t_order WHERE order_id IN (22, 24, 26)
	 * 		└── SELECT * FROM t_order_0 WHERE order_id IN (22, 24, 26)
	 * 
	 * @param tableNames 切分的所有表名
	 * @param shardingValue IN的参数值
	 * 
	 */
	@Override
	public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
		// 符合条件的表名
		Collection<String> result = new LinkedHashSet<String>(tableNames.size());

		// 遍历IN中的参数
		for (Integer value : shardingValue.getValues()) {
			// 取模
			Integer modValue = value % 10;
			// 表名后缀
			String modStr = "_" + modValue;

			for (String tableName : tableNames) {
				if (tableName.endsWith(modStr)) {
					result.add(tableName);
				}
			}
		}

		return result;
	}

}
