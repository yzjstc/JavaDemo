FluxDemo是丑陋的前端代码
收集数据提交的JS文件是tongji.js

flux_hadoop01.conf是在host为Hadoop01,hadoop02的两台虚拟机的flume启动配置(此两台服务器构成一个Flume集群,将数据存到hdfs中,并进行分区,注:hadoop使用伪分布的模式)
flux_hadoop03.conf是host为Hadoop03的虚拟机的flume启动配置,将数据负载均衡刷到flume集群中

启动flume指令:
bin/flume-ng agent -n a1 -c conf -f conf/flux_hadoop03.conf
