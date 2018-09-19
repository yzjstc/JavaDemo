alter table flux add partition(reportTime='${today}') location '/flux/reportTime=${today}';

insert into dataclear partition(reportTime='${today}') select url,urlname,ref,uagent,stat_uv,split(stat_ss,'_')[0],split(stat_ss,'_')[1],split(stat_ss,'_')[2],cip from flux where reportTime = '${today}';

insert into tongji1_temp select '${today}','pv',t1.pv from (select count(*) as pv from dataclear where reportTime='${today}') as t1;

insert into tongji1_temp select '${today}','uv',t2.uv from (select count(distinct uvid) as uv from dataclear where reportTime='${today}') as t2;

insert into tongji1_temp select '${today}','vv',t3.vv from (select count(distinct ssid) as vv from dataclear where reportTime='${today}') as t3;

insert into tongji1_temp select '${today}','br',t4.br from (select round(br_left_tab.br_count / br_right_tab.vv_count,4) as br from (select count(br_tab.ssid) as br_count from (select ssid from dataclear where reportTime='${today}' group by ssid having count(*) = 1) as br_tab) as br_left_tab, (select count(distinct ssid) as vv_count from dataclear where reportTime='${today}') as br_right_tab) as t4;

insert into tongji1_temp select '${today}','newip',t5.newip from (select count(distinct dataclear.cip) as newip from dataclear where dataclear.reportTime='${today}' and dataclear.cip not in (select distinct inner_dataclear_tab.cip from dataclear as inner_dataclear_tab where datediff('${today}',inner_dataclear_tab.reportTime)>0)) as t5;

insert into tongji1_temp select '${today}','newcust',t6.newcust from (select count(distinct dataclear.uvid) as newcust from dataclear where dataclear.reportTime='${today}' and dataclear.uvid not in (select inner_dataclear_tab.uvid from dataclear as inner_dataclear_tab where datediff('${today}',inner_dataclear_tab.reportTime)>0)) as t6;

insert into tongji1_temp select '${today}','avgtime',t7.avgtime from (select avg(avgtime_tab.use_time) as avgtime from (select max(sstime) - min(sstime) as use_time from dataclear where reportTime='${today}' group by ssid) as avgtime_tab) as t7;

insert into tongji1_temp select '${today}','avgdeep',t8.avgdeep from (select round(avg(avgdeep_tab.deep),4) as avgdeep from (select count(distinct urlname) as deep from dataclear where reportTime='${today}' group by ssid) as avgdeep_tab) as t8;

insert into tongji1 select '${today}',t1.pv,t2.uv,t3.vv,t4.br,t5.newip, t6.newcust, t7.avgtime,t8.avgdeep from (select value as pv from tongji1_temp where field='pv' and reportTime='${today}') as t1, (select value as uv from tongji1_temp where field='uv' and reportTime='${today}') as t2, (select value as vv from tongji1_temp where field='vv' and reportTime='${today}') as t3, (select value as br from tongji1_temp where field='br' and reportTime='${today}') as t4, (select value as newip from tongji1_temp where field='newip' and reportTime='${today}') as t5, (select value as newcust from tongji1_temp where field='newcust' and reportTime='${today}') as t6, (select value as avgtime from tongji1_temp where field='avgtime' and reportTime='${today}') as t7, (select value as avgdeep from tongji1_temp where field='avgdeep' and reportTime='${today}') as t8;
