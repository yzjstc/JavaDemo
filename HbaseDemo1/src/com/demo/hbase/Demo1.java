package com.demo.hbase;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Test;

public class Demo1 {
	@Test
	public void create() throws Exception{
		//1.创建HBaseAdmin
		Configuration conf = HBaseConfiguration.create();
		/*
		 * HBase依靠zookeeper进行集群节点的管理和HRegion寻址
		 * 此方法对HBase进行配置(此处hadoop的虚拟机位置在Hosts文件进行了配置)
		 */
		conf.set("hbase.zookeeper.quorum", "hadoop01:2182,hadoop02:2182,hadoop03:2182");
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		//2.创建表
		/*
		 * 由于Hbase底层使用的是字节码存储,所以使用的时候
		 * 无论是表名/列族/列名/行键/单元值等,都需要.getBytes()获得字节码
		 * Descriptor意为描述
		 */
		//对新建的Table进行描述 
		HTableDescriptor tabDesc = new HTableDescriptor(TableName.valueOf("tb1"));
		//对表中的列族起名并添加到Table描述
		HColumnDescriptor cf1Desc = new HColumnDescriptor("cf1".getBytes());
		tabDesc.addFamily(cf1Desc);
		HColumnDescriptor cf2Desc = new HColumnDescriptor("cf2".getBytes());
		tabDesc.addFamily(cf2Desc);
		//描述好表名/列族后,执行建表方法
		//相当于在hbase shell 操作  >create 'tb1',{NAME => 'cf1'},{NAME => 'cf2'}
		//或    > tb1 = create 'tb1','cf1','cf2'
		admin.createTable(tabDesc);
		
		//3.用后记得关闭连接
		admin.close();
		
	}
	
	/*
	 * 往Hbase中添加数据或者修改数据
	 */
	@Test
	public void put() throws Exception{
		//1.创建HTable
		/*
		 * 和建表不同, 对表操作的时候表已经存在了
		 * 此时,应该创建HTable和已经创建好的表进行关联
		 */
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		HTable tab = new HTable(conf, "tb1");
		
		//2.写入数据
		/*
		 * 注意: 必须全部都是字节,不能直接传字符
		 * 写入数据首先要创建一个put对象,指定此次写数据的行键
		 * 然后,往Put对象中,传入本次传入的数据
		 * 最后通过HTable对象调用put方法提交
		 */
		Put put = new Put("rk1".getBytes());
		put.add("cf1".getBytes(), "c1".getBytes(), "v1111".getBytes());
		put.add("cf1".getBytes(), "c2".getBytes(), "v2111".getBytes());
		tab.put(put);//put(Put put) 也可以批量操作 put(List<put> puts)
		//相当于: >put 'tb1','rk1','cf1:c1','v1111'
		//      >put 'tb1','rk1','cf1:c2','v2111'
		
		//3.关闭连接
		tab.close();
	}
	
	@Test
	public void get() throws Exception{
		//1.创建HTable
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		HTable tab = new HTable(conf, "tb1");
		
		//2.获取数据
		/*
		 * 指定行键的Get对象
		 * HTable对象调用get方法得到结果对象
		 */
		Get get = new Get("rk1".getBytes());
		Result result = tab.get(get);
		byte[] row = result.getRow();//得到行键
		System.out.println(new String(row));
		//通过行键,得到指定列族的列的单元值
		byte[] value = result.getValue("cf1".getBytes(), "c1".getBytes());
		System.out.println(new String(value));
		//相当于: >get 'tb1','rk1','cf1:c1'
		
		//3.关闭连接
		tab.close();
	}
	
	@Test
	public void delete() throws Exception{
		//1.创建HTable
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		HTable tab = new HTable(conf, "tb1");
		
		//2.删除数据
		//通过指定行键,删除指定行数据
		Delete del = new Delete("rk1".getBytes());
		tab.delete(del);
		
		//3.关闭连接
		tab.close();
	}
	
	@Test
	public void drop() throws Exception{
		//1.创建HBaseAdmin
		//对于表级操作,不是HTable对象负责了,而是HBaseAdmin
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		//2.删除表
		//删除前需先置为不可用状态,相当于: >disable 'tb1'
		admin.disableTable("tb1".getBytes());
		//置为不可用后,可删除,相当于: >drop 'tb1'
		admin.deleteTable("tb1".getBytes());
		
		//3.关闭连接
		admin.close();
	}
}
