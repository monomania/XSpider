数据设计
1. 比赛信息表(抓取近5年数据)
   主要数据来源：
   基本指数：http://score.nowscore.com/analysis/1709701cn.html
   基本比赛信息：http://score.nowscore.com/detail/1709701cn.html
 - 比赛ID，自增即可；
 - 主队名称
 - 客队名称
 - 联赛名称
 - 联赛ID（看网页有没有）
 - 比赛日期(用long类型存储)
 - 是否杯赛(比赛名称包含'杯')
 - 是否是一级比赛(白名单内)
 - 所属国家(用映射表，或者看网页是否有返回)
 - 欧赔威廉希尔初盘/临场盘胜平负赔率
 - 亚赔bet365初盘/临场盘让球盘口、让球赔率
 - 亚赔bet365初盘/临场盘大小球盘口、让球赔率
 - 初盘角球大小盘口、赔率
 - 主队联赛排名
 - 客队联赛排名
 - 主队联赛主场排名
 - 客队联赛客场排名
 - 必发指数胜平负
 - 一年内主客队交战历史主队胜率，平率，负率
 - 主客队近3场/10场平均进球数
 - 主客队近3场/10场平均丢球数
 - 主客队近3场/10场平均角球数
 - 主客队近3场/10场平均黄卡数
 - 主客队近3场/10场平均射门/被射门次数
 - 主客队近10场胜率、让胜率、大球率
 - 同赛事主客队近10场胜率、让胜率、大球率
 （裁判数据源：http://score.nowscore.com/info/referee.aspx?id=1656691）
 - 裁判场均黄卡数
 - 裁判场均红卡数
 - 裁判执法主胜率
 - 裁判执法客胜率
 - 比赛最终进球比分
 - 比赛半场进球比分
 - 比赛最终进球数
 - 主队最终主队进球数
 - 客队最终客队进球数
 - 比赛最终角球数
 - 比赛最终主队角球数
 - 比赛最终客队角球数
 - 比赛最终黄卡数
 - 比赛最终主队黄卡数
 - 比赛最终客队黄卡数

 


2. 实时指数表(按分钟取)
 主要数据源：
 实时指数：http://score.nowscore.com/odds/3in1Odds.aspx?companyid=3&id=1738311
 实时角球指数：http://score.nowscore.com/odds/cornerDetail.aspx?id=1738311
 - 比赛ID，关联比赛信息表
 - 当前时刻(分钟)
 - 总进球数
 - 主队进球数
 - 客队进球数
 - 让球盘口
 - 让球主赔率
 - 让球客赔率
 - 欧指主胜赔率
 - 欧指平局赔率
 - 欧指主负赔率
 - 角球总数
 - 主队角球数
 - 客队角球数
 - 角球大小盘口
 - 角球大赔率
 - 角球小赔率
 - 射门总数
 - 主队射门次数
 - 客队射门次数
 - 射正总数
 - 主队射正次数
 - 客队射正次数
 - 危险进攻总数
 - 主队危险进攻数
 - 客队危险进攻数
 - 主队控球率
 - 客队控球率
 - 黄卡总数
 - 主队黄卡数
 - 客队黄卡数
 - 红卡总数
 - 主队红卡数
