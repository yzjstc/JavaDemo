package com.demo.hbase;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;

public class Demo2 {
	public static void main(String[] args) throws Exception {
		//1.创建HTable
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2182,hadoop03:2182");
		HTable tab = new HTable(conf, "tb1");
		
		//2.扫描表
		//--全表扫描
//		Scan scan = new Scan();//获得扫描器
		
		//--范围扫描
		//Scan scan = new Scan();
		//scan.setStartRow("rk3".getBytes());//开始扫描的行键
		//scan.setStopRow("rk6".getBytes());//结束扫描的行键
		
		//filter过滤,其实还是先全表扫描之后,再过滤结果
		Scan scan = new Scan();
		/*
		 * CompareOp枚举类的常量还是比较好认,NOT_EQUAL -> 不等于,EQUAL -> 等于...
		 */
//		Filter filter = new RowFilter(CompareOp.NOT_EQUAL, new BinaryComparator("rk3".getBytes()));//行键过滤
//		Filter filter = new RowFilter(CompareOp.EQUAL,new RegexStringComparator("^[^1]+1[^1]+$|^.*x$"));//行键正则匹配过滤
//		Filter filter = new KeyOnlyFilter();//过滤结果只有行键,列族,列名,而值为空
//		Filter filter = new RandomRowFilter(0.5f);//每个结果有50%的机会出现,不是只出现50%的结果
//		Filter filter = new ColumnPrefixFilter("c2".getBytes());//列名前缀匹配过滤
//		Filter filter = new ValueFilter(CompareOp.EQUAL, new RegexStringComparator("^v[^2].*2.*$"));//值的正则匹配过滤
//		Filter filter = new SingleColumnValueFilter("cf1".getBytes(), "c1".getBytes(), CompareOp.EQUAL, new RegexStringComparator("^[\\w&&[^2]]*$"));//指定列匹配过滤
		Filter f1 = new KeyOnlyFilter();
		Filter f2 = new RandomRowFilter(0.5f);
		Filter flist = new FilterList(FilterList.Operator.MUST_PASS_ALL,f1,f2);//多重过滤需要做成一个FilterList,Fileter是参数可变的
		//scan.setFilter(filter);
		scan.setFilter(flist);
		ResultScanner rs = tab.getScanner(scan);//得到表的扫描结果
		
		//3.遍历扫描结果打印
		for(Result r : rs){
			//获取行键
			String rk = new String(r.getRow());
			//获取当前行的所有列的数据, r.getMap()
			//NavigableMap<列族,NavigableMap<列名,NavigableMap<时间戳,值>>>,通过遍历该map得到行所有列数据
			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = r.getMap();
			for(Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : map.entrySet()){
				//获取列族
				String cf = new String(entry.getKey());
				//获得列族的所有列名->值的Map
				NavigableMap<byte[], NavigableMap<Long, byte[]>> cMap = entry.getValue();
				for (Entry<byte[],NavigableMap<Long, byte[]>> cEntry : cMap.entrySet()) {
					//获取列名
					String colName = new String(cEntry.getKey());
					//获取该列的最后一次更新的时间戳
					Long cKey = cEntry.getValue().firstEntry().getKey();
					//获取指定行键的列最后一次更新的值
					String cValue = new String(cEntry.getValue().firstEntry().getValue());
					
					StringBuffer sb = new StringBuffer("--rk:[");
					sb.append(rk).append("],cf:[").append(cf).append("],c:[").append(colName)
					.append("],v:[").append(cKey).append("],value:[").append(cValue).append("]");
					System.out.println(sb.toString());
				}
			}
		}
		
	}
}



























