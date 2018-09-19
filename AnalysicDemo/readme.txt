Demo的java代码使用jdk1.8.0开发
使用flume1.6.0开发
tomcat服务器使用tomcat8.0
Hadoop使用Hadoop2的伪分布模式

FluxDemo是丑陋的前端代码
收集数据提交的JS文件是tongji.js, log4j.properties文件是log日志文件,小弟在写代码的时候,调了非Apache的log包,真是丢人了,搞了半天,结果就是没刷到flume,惭愧惭愧

flux_hadoop01.conf是在host为Hadoop01,hadoop02的两台虚拟机的flume启动配置(此两台服务器构成一个Flume集群,将数据存到hdfs中,并进行分区,注:hadoop使用伪分布的模式)
flux_hadoop03.conf是host为Hadoop03的虚拟机的flume启动配置,将数据负载均衡刷到flume集群中
注意:flume的conf文件,注释最好单独写一行,小弟在启动flume的时候,在配置信息那行在后面直接写注释,flume报错!!!

启动flume指令,在控制台输出启动情况:
./bin/flume-ng agent -n a1 -c conf -f conf/flux_hadoop03.conf -DFlume.root.logger=INFO,console
