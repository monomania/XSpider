package com.test.entity;

import java.util.Map;

public class Match {

  public final Map<String, Object> mDbMap;

  public Match(Map<String, Object> dbMap) {
    mDbMap = dbMap;
  }

  public int mMatchID; // matchID
  public long mMatchTime; // 比赛时间
  public int mTimeMin; // 当前分钟
  public String mHostName;
  public String mCustomName;
  public String mHostNamePinyin; // 主队名称拼音
  public String mCustomNamePinyin; // 客队名称拼音
  public String mLeague; // 联赛名称
  public int mHostScore; // 主队比分
  public int mCustomScore; // 客队比分
  public int mStatus; // 比赛状态: -1=已完场, 0=不知道， [1~4]=进行中

  public int mHostLeagueRank; // 主队联赛排名, 可能为0
  public int mHostLeagueOnHostRank; // 主队主场排名, 可能为0
  public int mCustomLeagueRank; // 客队联赛排名, 可能为0
  public int mCustomLeagueOnCustomRank; // 客队客场排名, 可能为0

  public float mOriginalScoreOdd; // 亚盘初盘让球盘口
  public float mOriginalScoreOddOfVictory; // 亚盘初盘让球盘口
  public float mOriginalScoreOddOfDefeat; // 亚盘初盘让球盘口

  public float mOpeningScoreOdd; // 亚盘即时盘让球盘口
  public float mOpeningScoreOddOfVictory; // 亚盘即时盘让球盘口
  public float mOpeningScoreOddOfDefeat; // 亚盘即时盘让球盘口

  public float mMiddleScoreOdd; // 亚盘中场盘让球盘口
  public float mMiddleScoreOddOfVictory; // 亚盘中场盘让球盘口
  public float mMiddleScoreOddOfDefeat; // 亚盘中场盘让球盘口

  public float mMiddleVictoryOdd; // 中场欧盘
  public float mMiddleDrewOdd; // 中场欧盘
  public float mMiddleDefeatOdd; // 中场欧盘

  public int mMiddleHostScore; // 中场得分
  public int mMiddleCustomScore; // 中场得分

  public float mOriginalVictoryOdd; // 欧指初盘胜赔
  public float mOriginalDrewOdd; // 欧指初盘平赔
  public float mOriginalDefeatOdd; // 欧指初盘负赔

  public float mOpeningVictoryOdd; // 欧指即时盘胜赔
  public float mOpeningDrewOdd; // 欧指即时盘平赔
  public float mOpeningDefeatOdd; // 欧指即时盘负赔

  public float mOriginalBigOdd; // 初盘大小球盘口
  public float mOriginalBigOddOfVictory; // 初盘大小球赔率
  public float mOriginalBigOddOfDefeat; // 初盘大小球赔率

  public float mOpeningBigOdd; // 即时盘大小球盘口
  public float mOpeningBigOddOfVictory; // 即时盘大小球赔率
  public float mOpeningBigOddOfDefeat; // 即时盘大小球赔率

  public float mMiddleBigOdd; // 中场盘大小球盘口
  public float mMiddleBigOddOfVictory; // 中场盘大小球赔率
  public float mMiddleBigOddOfDefeat; // 中场盘大小球赔率

  public float mMin45HostBestShoot;
  public float mMin45HostDanger;
  public float mMin45CustomBestShoot;
  public float mMin45CustomDanger;

  public int mHostScoreMinOf70; // 70分钟主队得分
  public int mCustomScoreMinOf70; // 70分钟客队得分
  public float mBigOddOfMin70; // 70分钟大小球盘口
  public float mBigOddOfVictoryOfMin70; // 70分钟大球赔率
  public float mBigOddOfDefeatOfMin70; // 70分钟小球赔率
  public float mScoreOddOfMin70; // 70'让球盘口
  public float mScoreOddOfVictoryOfMin70; // 70'让球盘口上盘赔率
  public float mScoreOddOfDefeatOfMin70; // 70’让球盘口下盘赔率

  public int mHostScoreMinOf25; // 25分钟主队得分
  public int mCustomScoreMinOf25; // 25分钟客队得分
  public float mBigOddOfMin25; // 25分钟大小球盘口
  public float mBigOddOfVictoryOfMin25; // 25分钟大球赔率
  public float mBigOddOfDefeatOfMin25; // 25分钟小球赔率

  public int mHostScoreOfMiddle; // 中场主队得分
  public int mCustomScoreOfMiddle; // 中场客队得分

  public float mHostScoreOf3; // 主队近3场平均进球数
  public float mCustomScoreOf3; // 客队近3场进球数

  public float mHostLossOf3; // 主队近3场平均进球数
  public float mCustomLossOf3; // 客队近3场进球数

  public float mHostControlRateOf3; // 主队近3场控球率
  public float mCustomControlRateOf3; // 客队近3场控球率

  public float mHostCornerOf3; // 主队近3场角球次数
  public float mCustomCornerOf3; // 客队近3场角球次数

  public float mHostBestShoot; // 主队射正次数
  public float mCustomBestShoot; // 客队射正次数

  public float mHostControlRate; // 主队射正次数
  public float mCustomControlRate; // 客队射正次数

  public float mHostCornerScore; // 主队角球数
  public float mCustomCornerScore; // 客队角球数

  public float mHistoryVictoryRateOfHost; // 主队历史交锋让胜率
  public float mRecentVictoryRateOfHost; // 主队近期让胜率;
  public float mRecentVictoryRateOfCustom; // 客队近期让胜率

  public float mJCVictoryOdd; // 竞彩胜赔
  public float mAMVictoryOdd; // 澳门
  public float mCrownVictoryOdd; // 皇冠
  public float mBet365VictoryOdd; // bet365

}
