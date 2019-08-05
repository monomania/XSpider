package com.test.db;

import static com.test.tools.Utils.valueOfFloat;
import static com.test.tools.Utils.valueOfInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.test.Keys;
import com.test.entity.Match;

public class QueryHelper implements Keys {

  public static final String SQL_BASE =
      "select * from football where 1=1 " +
          "AND cast(timeMin as int) >0 AND cast(timeMin as int) <= 100 " +
          "AND hostScore >=0 " +
          "AND customScore >=0 " +
          "AND original_scoreOdd is not null " +
          "AND original_scoreOddOfVictory >0.7 " +
          "AND original_scoreOddOfDefeat >0.7 " +
          "AND original_bigOdd > 0 " +
          "AND original_bigOddOfVictory >0.7 " +
          "AND original_bigOddOfDefeat >0.7 " +
          "AND original_drawOdd >0 " +
          "AND original_victoryOdd >0 " +
          "AND original_defeatOdd >0 " +

          "AND opening_scoreOdd is not null " +
          "AND opening_scoreOddOfVictory >0.7 " +
          "AND opening_scoreOddOfDefeat >0.7 " +
          "AND opening_bigOdd > 0 " +
          "AND opening_bigOddOfVictory >0.7 " +
          "AND opening_bigOddOfDefeat >0.7 " +
          "AND opening_drawOdd >0 " +
          "AND opening_victoryOdd >0 " +
          "AND opening_defeatOdd >0 " +
          "AND league is not null " +

          "AND odd_company_first_victory_1>0 " +
          "AND odd_company_first_victory_8>0 " +

          "AND hostBestShoot >=0 " +
          "AND customBestShoot >=0 " +
          "AND hostCornerScore >=0 " +
          "AND customCornerScore >=0 " +
          "AND hostBestShoot>=0 and customBestShoot>=0 " +
          "AND hostScoreOf3 >=0 and customScoreOf3 >=0 " +
          "AND hostLossOf3>=0 and customLossOf3>=0 ";

  public static final String SQL_MIDDLE =
      "AND middle_hostScore >=0 " +
          "AND middle_customScore >=0 " +
          "AND middle_bigOdd is not null " +
          "AND middle_bigOddOfVictory >0.7 " +
          "AND middle_bigOddOfDefeat >0.7 " +
          "AND middle_scoreOdd is not null " +
          "AND middle_scoreOddOfVictory >0.7 " +
          "AND middle_scoreOddOfDefeat >0.7 ";

  // 进行中的比赛
  public static String SQL_RT = "AND matchStatus>=1 AND matchStatus<=4 ";

  // 已结束的比赛
  public static String SQL_ST = "AND matchStatus=-1 ";

  public static String SQL_ORDER = "order by matchTime desc limit 5000";

  public static List<Match> doQuery(String sql) throws Exception {
    final List<Match> matches = new ArrayList<>();
    final DataSource ds = new DbHelper().open();
    QueryRunner runner = new QueryRunner(ds);
    List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler());
    for (Map<String, Object> map : mapList) {
      final Match match = buildMatch(map);
      matches.add(match);
    }

    return matches;
  }


  public static void updateHistory() throws Exception {
    String sql = SQL_BASE + "order by matchTime desc limit 10000";
    List<Match> all = doQuery(sql);
    for (Match match : all) {
      float historyVictoryRateOfHost = historyVictoryRateOfHost(match, all);
      float recentVictoryRateOfHost =
          recentVictoryRate(match.mMatchTime, match.mHostNamePinyin, all);
      float recentVictoryRateOfCustom =
          recentVictoryRate(match.mMatchTime, match.mCustomNamePinyin, all);

      String updateSql = String.format(
          "update football set historyVictoryRateOfHost='%s', recentVictoryRateOfHost='%s', recentVictoryRateOfCustom='%s' where matchID=%d",
          historyVictoryRateOfHost,
          recentVictoryRateOfHost,
          recentVictoryRateOfCustom,
          match.mMatchID);

      final DataSource ds = new DbHelper().open();
      QueryRunner runner = new QueryRunner(ds);
      int updateCount = runner.update(updateSql);
      System.out.println(updateCount);
    }
  }


  public static float historyVictoryRateOfHost(Match thisMatch, List<Match> all) {
    List<Match> hostMatches = all.stream()
        .filter(match -> thisMatch.mMatchTime > match.mMatchTime) // 必须是本场比赛以前发生的比赛
        .filter(match -> match.mHostNamePinyin.equals(thisMatch.mHostNamePinyin)
            && match.mCustomNamePinyin.equals(thisMatch.mCustomNamePinyin))
        .limit(3).collect(Collectors.toList());
    List<Match> awayMatches = all.stream()
        .filter(match -> thisMatch.mMatchTime > match.mMatchTime)
        .filter(match -> match.mHostNamePinyin.equals(thisMatch.mCustomNamePinyin)
            && match.mCustomNamePinyin.equals(thisMatch.mCustomNamePinyin))
        .limit(3).collect(Collectors.toList());

    int hostVictory = (int) hostMatches.stream()
        .filter(other -> other.mHostScore - other.mCustomScore + other.mOriginalScoreOdd > 0)
        .count();
    hostVictory += (int) hostMatches.stream()
        .filter(other -> other.mHostScore - other.mCustomScore + other.mOriginalScoreOdd < 0)
        .count();

    int total = hostMatches.size() + awayMatches.size();
    return total > 0 ? hostVictory * 1.00f / total : 0.5f;
  }

  public static float recentVictoryRate(long matchTime, String pinyin, List<Match> all) {
    // 主队主场相同
    List<Match> hostMatches = all.stream()
        .filter(match -> matchTime > match.mMatchTime)
        .filter(match -> match.mHostNamePinyin.equals(pinyin))
        .limit(5).collect(Collectors.toList());
    List<Match> awayMatches = all.stream()
        .filter(match -> matchTime > match.mMatchTime)
        .filter(match -> match.mCustomNamePinyin.equals(pinyin))
        .limit(5).collect(Collectors.toList());
    int hostVictory = (int) hostMatches.stream()
        .filter(other -> other.mHostScore - other.mCustomScore + other.mOriginalScoreOdd > 0)
        .count();
    hostVictory += (int) hostMatches.stream()
        .filter(other -> other.mHostScore - other.mCustomScore + other.mOriginalScoreOdd < 0)
        .count();

    int total = hostMatches.size() + awayMatches.size();
    return total > 0 ? hostVictory * 1.00f / total : 0.5f;
  }

  public static String buildSqlIn(List<Integer> matchIds) {
    String sqlIn = "AND matchID in (";
    for (int i = 0; i < matchIds.size(); i++) {
      if (i > 0) {
        sqlIn += ", ";
      }
      sqlIn += matchIds.get(i);
    }
    sqlIn += ") ";

    return sqlIn;
  }


  /**
   * 从数据库属性Map构建一个Match模型.
   */
  private static Match buildMatch(Map<String, Object> dbMap) {
    Match match = new Match();
    match.mMatchID = valueOfInt(dbMap.get(MATCH_ID));
    match.mTimeMin = valueOfInt(dbMap.get(TIME_MIN));
    match.mMatchTime = Long.parseLong(String.valueOf(dbMap.get(MATCH_TIME)));
    match.mHostNamePinyin = String.valueOf(dbMap.get(HOST_NAME_PINYIN));
    match.mCustomNamePinyin = String.valueOf(dbMap.get(CUSTOM_NAME_PINYIN));
    match.mHostName = String.valueOf(dbMap.get(HOST_NAME));
    match.mCustomName = String.valueOf(dbMap.get(CUSTOM_NAME));
    match.mLeague = String.valueOf(dbMap.get(LEAGUE));
    match.mHostScore = valueOfInt(dbMap.get(HOST_SCORE));
    match.mCustomScore = valueOfInt(dbMap.get(CUSTOM_SCORE));
    match.mStatus = valueOfInt(dbMap.get(MATCH_STATUS));

    match.mHostLeagueRank = valueOfInt(dbMap.get(HOST_LEAGUE_RANK));
    match.mHostLeagueOnHostRank =
        valueOfInt(dbMap.get("hostLeagueOnHostRank"));
    match.mCustomLeagueRank = valueOfInt(dbMap.get(CUSTOM_LEAGUE_RANK));
    match.mCustomLeagueOnCustomRank =
        valueOfInt(dbMap.get("customLeagueOnCustomRank"));

    match.mOriginalScoreOdd = valueOfFloat(dbMap.get(ORIGINAL_SCORE_ODD));
    match.mOriginalScoreOddOfVictory =
        valueOfFloat(dbMap.get(ORIGINAL_SCORE_ODD_OF_VICTORY));
    match.mOriginalScoreOddOfDefeat =
        valueOfFloat(dbMap.get(ORIGINAL_SCORE_ODD_OF_DEFEAT));

    match.mOpeningScoreOdd = valueOfFloat(dbMap.get("opening_scoreOdd"));
    match.mOpeningScoreOddOfVictory =
        valueOfFloat(dbMap.get("opening_scoreOddOfVictory"));
    match.mOpeningScoreOddOfDefeat =
        valueOfFloat(dbMap.get("opening_scoreOddOfDefeat"));

    match.mMiddleScoreOdd = valueOfFloat(dbMap.get("middle_scoreOdd"));
    match.mMiddleScoreOddOfVictory =
        valueOfFloat(dbMap.get("middle_scoreOddOfVictory"));
    match.mMiddleScoreOddOfDefeat =
        valueOfFloat(dbMap.get("middle_scoreOddOfDefeat"));

    match.mMiddleVictoryOdd = valueOfFloat(dbMap.get("middle_victoryOdd"));
    match.mMiddleDrewOdd = valueOfFloat(dbMap.get("middle_drewOdd"));
    match.mMiddleDefeatOdd = valueOfFloat(dbMap.get("middle_defeatOdd"));

    match.mMiddleHostScore = valueOfInt(dbMap.get("middle_hostScore"));
    match.mMiddleCustomScore = valueOfInt(dbMap.get("middle_customScore"));

    match.mOriginalVictoryOdd =
        valueOfFloat(dbMap.get("original_victoryOdd"));
    match.mOriginalDrawOdd = valueOfFloat(dbMap.get("original_drawOdd"));
    match.mOriginalDefeatOdd = valueOfFloat(dbMap.get("original_defeatOdd"));
    match.mOpeningVictoryOdd = valueOfFloat(dbMap.get("opening_victoryOdd"));
    match.mOpeningDrawOdd = valueOfFloat(dbMap.get("opening_drawOdd"));
    match.mOpeningDefeatOdd = valueOfFloat(dbMap.get("opening_defeatOdd"));

    match.mOriginalBigOdd = valueOfFloat(dbMap.get("original_bigOdd"));
    match.mOriginalBigOddOfVictory =
        valueOfFloat(dbMap.get("original_bigOddOfVictory"));
    match.mOriginalBigOddOfDefeat =
        valueOfFloat(dbMap.get("original_bigOddOfDefeat"));

    match.mOpeningBigOdd = valueOfFloat(dbMap.get("opening_bigOdd"));
    match.mOpeningBigOddOfVictory =
        valueOfFloat(dbMap.get("opening_bigOddOfVictory"));
    match.mOpeningBigOddOfDefeat =
        valueOfFloat(dbMap.get("opening_bigOddOfDefeat"));

    match.mMiddleBigOdd = valueOfFloat(dbMap.get("middle_bigOdd"));
    match.mMiddleBigOddOfVictory =
        valueOfFloat(dbMap.get("middle_bigOddOfVictory"));
    match.mMiddleBigOddOfDefeat =
        valueOfFloat(dbMap.get("middle_bigOddOfDefeat"));

    match.mHostScoreMinOf70 = valueOfInt(dbMap.get("min70_hostScore"));
    match.mCustomScoreMinOf70 = valueOfInt(dbMap.get("min70_customScore"));
    match.mBigOddOfMin70 = valueOfFloat(dbMap.get("min70_bigOdd"));
    match.mBigOddOfVictoryOfMin70 =
        valueOfFloat(dbMap.get("min70_bigOddOfVictory"));
    match.mBigOddOfDefeatOfMin70 =
        valueOfFloat(dbMap.get("min70_bigOddOfDefeat"));
    match.mScoreOddOfMin70 = valueOfFloat(dbMap.get("min70_scoreOdd"));
    match.mScoreOddOfVictoryOfMin70 =
        valueOfFloat(dbMap.get("min70_scoreOddOfVictory"));
    match.mScoreOddOfDefeatOfMin70 =
        valueOfFloat(dbMap.get("min70_scoreOddOfDefeat"));

    match.mHostScoreMinOf25 = valueOfInt(dbMap.get("min25_hostScore"));
    match.mCustomScoreMinOf25 = valueOfInt(dbMap.get("min25_customScore"));
    match.mBigOddOfMin25 = valueOfFloat(dbMap.get("min25_bigOdd"));
    match.mBigOddOfVictoryOfMin25 =
        valueOfFloat(dbMap.get("min25_bigOddOfVictory"));
    match.mBigOddOfDefeatOfMin25 =
        valueOfFloat(dbMap.get("min25_bigOddOfDefeat"));

    match.mHostScoreOfMiddle = valueOfInt(dbMap.get("middle_hostScore"));
    match.mCustomScoreOfMiddle = valueOfInt(dbMap.get("middle_customScore"));

    match.mHostScoreOf3 = valueOfFloat(dbMap.get("hostScoreOf3"));
    match.mCustomScoreOf3 = valueOfFloat(dbMap.get("customScoreOf3"));

    match.mHostLossOf3 = valueOfFloat(dbMap.get("hostLossOf3"));
    match.mCustomLossOf3 = valueOfFloat(dbMap.get("customLossOf3"));

    match.mHostControlRateOf3 = valueOfFloat(dbMap.get("hostControlRateOf3"));
    match.mCustomControlRateOf3 = valueOfFloat(dbMap.get("customControlRateOf3"));

    match.mHostCornerOf3 = valueOfFloat(dbMap.get("hostCornerOf3"));
    match.mCustomCornerOf3 = valueOfFloat(dbMap.get("customCornerOf3"));

    match.mHostBestShoot =
        valueOfFloat(dbMap.get("hostBestShoot")) * (90.00f / match.mTimeMin);
    match.mCustomBestShoot = valueOfFloat(dbMap.get("customBestShoot"))
        * (90.00f / match.mTimeMin);

    match.mHostControlRate = valueOfFloat(dbMap.get("hostControlRate"));
    match.mCustomControlRate = valueOfFloat(dbMap.get("customControlRate"));

    match.mHostCornerScore = valueOfFloat(dbMap.get("hostCornerScore"));
    match.mCustomBestShoot = valueOfFloat(dbMap.get("customCornerScore"));

    match.mHistoryVictoryRateOfHost = valueOfFloat(dbMap.get("historyVictoryRateOfHost"));
    match.mRecentVictoryRateOfHost = valueOfFloat(dbMap.get("recentVictoryRateOfHost"));
    match.mRecentVictoryRateOfCustom = valueOfFloat(dbMap.get("recentVictoryRateOfCustom"));

    match.mJCVictoryOdd = valueOfFloat(dbMap.get(ODD_COMPANY_FIRST_VICTORY_ + "105"));
    match.mAMVictoryOdd = valueOfFloat(dbMap.get(ODD_COMPANY_FIRST_VICTORY_ + "1"));
    match.mCrownVictoryOdd = valueOfFloat(dbMap.get(ODD_COMPANY_FIRST_VICTORY_ + "3"));
    match.mBet365VictoryOdd = valueOfFloat(dbMap.get(ODD_COMPANY_FIRST_VICTORY_ + "8"));

    return match;
  }


}