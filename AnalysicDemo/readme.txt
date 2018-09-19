Demo的java代码使用jdk1.8.0开发
使用flume1.6.0开发
tomcat服务器使用tomcat8.0
Hadoop使用Hadoop2的伪分布模式,Hive 使用1.2.0版本

FluxDemo是丑陋的前端代码
收集数据提交的JS文件是tongji.js, log4j.properties文件是log日志文件,小弟在写代码的时候,调了非Apache的log包,真是丢人了,搞了半天,结果就是没刷到flume,惭愧惭愧

flux_hadoop01.conf是在host为Hadoop01,hadoop02的两台虚拟机的flume启动配置(此两台服务器构成一个Flume集群,将数据存到hdfs中,并进行分区,注:hadoop使用伪分布的模式)
flux_hadoop03.conf是host为Hadoop03的虚拟机的flume启动配置,将数据负载均衡刷到flume集群中
注意:flume的conf文件,注释最好单独写一行,小弟在启动flume的时候,在配置信息那行在后面直接写注释,flume报错!!!

启动flume指令,在控制台输出启动情况:
./bin/flume-ng agent -n a1 -c conf -f conf/flux_hadoop03.conf -DFlume.root.logger=INFO,console

数据存到HDFS后,可以使用Hive进行简单的离线分析
需要做些前提准备:hived bin目录下:执行hive shell 进入hive命令行
1.在Hive中创建外部表关联hdfs中/flux,日志记录按天分区做收集
create external table flux(url string,urlname string,title string,chset string,scr string,col string,lg
string,je string,ec string,fv string,cn string,ref string,uagent string,stat_uv string,stat_ss
string,cip string) partitioned by (reportTime string) row format delimited fields terminated by '|' location '/flux';
(各个列分别对应:url,资源名,标签名,字符集,屏幕分辨率,色彩,语言,支持JVM,使用cookie,使用flash,随机数,上一个网页地址,浏览器版本,用户编号,会话id_会话次数_当前时间,客户端ip)

 create table dataclear (url string,urlname string,ref string,uagent string,uvid string,ssid string,sscoutn string,sstime string,cip string) partitioned by (reportTime string) row format delimited fields terminated by '|';
(对一些多余的数据进行数据清洗)

create table tongji1_temp(reportTime string,field string,value double) row format delimited fields terminated by '|';
(这个表作为过渡用表用于临时存放各个指标清洗后的运算)

create table tongji1 (reportTime string,pv int,uv int,vv int,br double,newip int,newcust int,avgtime double,avgdeep double) row format delimited fields terminated by '|';
(用于存放最后的清洗结果,各个列分别为:报告时间,访问量,独立访客数,会话数,跳出率,新增ip,新用户数,平均访问时长,平均访问深度)

./hive -d today=2018-09-18 -f ./tongji1.hql

利用sqoop将结果导出到数据库中
先到数据库中建表:
create database fluxdb;
use fluxdb;
create table tongji1(
reportTime date,
pv int,
uv int,
vv int,
br double,
newip int,
newcust int,
avgtime double,
avgdeep double
);
在sqoop的bin目录下:
sqoop export --connect jdbc:mysql://hadoop03:3306/fluxdb --username root --password root --export-dir '/user/hive/warehouse/tongji1' --table tongji1 -m 1 --fieldsterminated-by '|'