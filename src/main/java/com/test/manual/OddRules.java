package com.test.manual;

import static com.test.tools.Utils.valueOfFloat;
import static com.test.tools.Utils.valueOfInt;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.test.Keys;
import com.test.db.QueryHelper;
import com.test.entity.Estimation;
import com.test.tools.Utils;

public class OddRules implements Keys {

  // 数据库查询条数
  private static final int DEFAULT_SQL_COUNT = 1000000;
  // 数据低于多少条则不要
  private static final int DEFAULT_MIN_RULE_COUNT = 500;
  // 最低胜率要求
  private static final float DEFAULT_MIN_VICTORY_RATE = 0.55f;
  // 最低盈利率要求
  private static final float DEFAULT_MIN_PROFIT_RATE = 0.95f;

  private final Set<String> mRuleKeys = new HashSet<>();
  private final Map<String, Float> mHostSum = new HashMap<>();
  private final Map<String, Float> mCustomSum = new HashMap<>();
  private final Map<String, Integer> mHostCount = new HashMap<>();
  private final Map<String, Integer> mDrewCount = new HashMap<>();
  private final Map<String, Integer> mCustomCount = new HashMap<>();

  // 规则
  private final Map<String, Integer> mRules = new HashMap<>();
  // 命中率
  private final Map<String, Float> mRuleVictoryRate = new HashMap<>();
  // 盈利率
  private final Map<String, Float> mRuleProfitRate = new HashMap<>();

  public void make() throws Exception {
    mHostSum.clear();
    mCustomSum.clear();

    int[] timeMinArray = new int[] {45, 60, 75};
    Float[] thresholds = new Float[] {0.05f, 0.1f, 0.15f, 0.20f};
    Map<Integer, List<Map<String, Object>>> testMap = new HashMap<>();
    for (int timeMin : timeMinArray) {
      List<Map<String, Object>> matches = QueryHelper.doQuery(buildSql(timeMin), DEFAULT_SQL_COUNT);
      Collections.shuffle(matches);
      List<Map<String, Object>> trains = matches.subList(0, (int) (matches.size() * 0.8));
      // 训练
      trains.forEach(map -> calMatch(map, timeMin));

      // 保存测试集
      testMap.put(timeMin, matches.subList((int) (matches.size() * 0.8), matches.size()));
    }

    mRuleKeys.forEach(this::calRuleKeys);
    print();

    testMap.keySet().forEach(timeMin -> {
      System.out.println("\n\n分钟: " + timeMin);
      List<Estimation> estimations = testMap.get(timeMin).stream()
          .map(map -> estOneMatch(map, timeMin))
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

      Arrays.asList(thresholds).forEach(threshold -> {
        // 测试
        System.out.println("threshold=" + threshold);
        List<Estimation> filtered = estimations.stream()
            .filter(estimation -> estimation.mProbability >= threshold)
            .collect(Collectors.toList());

        float profit = filtered.stream()
            .map(estimation -> Utils.calGain(timeMin, estimation.mMatch, estimation))
            .mapToInt(newGain -> (int) (newGain * 10000)).sum() / 10000.00f;
        int victory = filtered.stream()
            .map(estimation -> Utils.calGain(timeMin, estimation.mMatch, estimation))
            .mapToInt(newGain -> newGain > 0 ? 1 : 0)
            .sum();
        System.out.println(String.format("总预测场次：%d, 胜率: %.2f, 盈利: %.2f",
            filtered.size(),
            victory * 1.00f / filtered.size(),
            profit));
      });
    });

  }



  public Estimation estOneMatch(Map<String, Object> match, int timeMin) {
    final String ruleKey = ruleKey(match, timeMin);
    if (!mRuleKeys.contains(ruleKey)) {
      return null;
    }

    float estValue = mRules.get(ruleKey);
    float victoryRate = mRuleVictoryRate.get(ruleKey);
    float profitRate = mRuleProfitRate.get(ruleKey);
    int hostCount = mHostCount.get(ruleKey);
    int drewCount = mDrewCount.get(ruleKey);
    int customCount = mCustomCount.get(ruleKey);

    if (victoryRate < DEFAULT_MIN_VICTORY_RATE) {
      return null;
    }
    if (profitRate < DEFAULT_MIN_PROFIT_RATE) {
      return null;
    }
    if (hostCount + drewCount + customCount < DEFAULT_MIN_RULE_COUNT) {
      return null;
    }
    float prob0 = estValue == 0 ? victoryRate : (1 - victoryRate);

    return new Estimation(match, estValue, prob0, 0, 1 - prob0, profitRate);
  }

  private void print() {
    mRules.keySet().stream()
        .filter(s -> mHostCount.get(s) + mDrewCount.get(s)
            + mCustomCount.get(s) >= DEFAULT_MIN_RULE_COUNT)
        .filter(s -> mRuleProfitRate.get(s) >= DEFAULT_MIN_PROFIT_RATE)
        .filter(s -> mRuleVictoryRate.get(s) >= DEFAULT_MIN_VICTORY_RATE)
        .sorted((o1, o2) -> (int) (mRuleProfitRate.get(o2) * 1000 - mRuleProfitRate.get(o1) * 1000))
        // .sorted((o1, o2) -> mHostCount.get(o2) + mDrewCount.get(o2) + mCustomCount.get(o2)
        // - (mHostCount.get(o1) + mDrewCount.get(o1) + mCustomCount.get(o1)))
        .forEach(ruleKey -> {
          int hostCount = mHostCount.get(ruleKey);
          int drewCount = mDrewCount.get(ruleKey);
          int customCount = mCustomCount.get(ruleKey);
          System.out.println(
              (hostCount + drewCount + customCount) + "@" + ruleKey + "@"
                  + mRuleProfitRate.get(ruleKey) + "@" + mRuleVictoryRate.get(ruleKey) + "@"
                  + mRules.get(ruleKey));
        });

    int total = mRules.keySet().stream().mapToInt(ruleKey -> {
      int hostCount = mHostCount.get(ruleKey);
      int drewCount = mDrewCount.get(ruleKey);
      int customCount = mCustomCount.get(ruleKey);
      return hostCount + drewCount + customCount;
    }).sum();

    System.out.println(total);
  }

  private void calRuleKeys(String ruleKey) {
    int hostCount = mHostCount.get(ruleKey);
    int customCount = mCustomCount.get(ruleKey);
    float hostSum = mHostSum.get(ruleKey);
    float customSum = mCustomSum.get(ruleKey);

    int selectValue = hostSum > customSum ? 0 : 2;
    float profitRate = Math.max(hostSum, customSum) * 1.00f / (hostCount + customCount);
    float victoryRate = Math.max(hostCount, customCount) * 1.00f / (hostCount + customCount);
    mRules.put(ruleKey, selectValue);
    mRuleVictoryRate.put(ruleKey, victoryRate);
    mRuleProfitRate.put(ruleKey, profitRate);
  }


  private void calMatch(Map<String, Object> match, int timeMin) {
    final String timePrefix = "min" + timeMin + "_";
    float minScoreOdd = timeMin > 0
        ? valueOfFloat(match.get(timePrefix + "scoreOdd"))
        : valueOfFloat(match.get(OPENING_SCORE_ODD));
    float minScoreOddVictory = timeMin > 0
        ? valueOfFloat(match.get(timePrefix + "scoreOddOfVictory"))
        : valueOfFloat(match.get(OPENING_SCORE_ODD_OF_VICTORY));
    float minScoreOddDefeat = timeMin > 0
        ? valueOfFloat(match.get(timePrefix + "scoreOddOfDefeat"))
        : valueOfFloat(match.get(OPENING_SCORE_ODD_OF_DEFEAT));
    int hostScore = valueOfInt(match.get(HOST_SCORE));
    int customScore = valueOfInt(match.get(CUSTOM_SCORE));
    int minHostScore = timeMin > 0 ? valueOfInt(match.get(timePrefix + "hostScore")) : 0;
    int minCustomScore = timeMin > 0 ? valueOfInt(match.get(timePrefix + "customScore")) : 0;
    float deltaScore = (hostScore - minHostScore) - (customScore - minCustomScore) + minScoreOdd;

    final String ruleKey = ruleKey(match, timeMin);
    mRuleKeys.add(ruleKey);

    float hostSum = deltaScore >= 0.5f
        ? minScoreOddVictory
        : (deltaScore >= 0.25 ? (0.5f + 0.5f * minScoreOddVictory) : 0f);
    float customSum = deltaScore <= -0.5f
        ? minScoreOddDefeat
        : (deltaScore <= -0.25 ? (0.5f + 0.5f * minScoreOddDefeat) : 0f);
    float totalHostSum = (mHostSum.containsKey(ruleKey) ? mHostSum.get(ruleKey) : 0) + hostSum;
    float totalCustomSum =
        (mCustomSum.containsKey(ruleKey) ? mCustomSum.get(ruleKey) : 0) + customSum;
    mHostSum.put(ruleKey, totalHostSum);
    mCustomSum.put(ruleKey, totalCustomSum);

    int hostCount = deltaScore > 0 ? 1 : 0;
    int drewCount = deltaScore == 0 ? 1 : 0;
    int customCount = deltaScore < 0 ? 1 : 0;
    int totalHostCount =
        (mHostCount.containsKey(ruleKey) ? mHostCount.get(ruleKey) : 0) + hostCount;
    int totalDrewCount =
        (mDrewCount.containsKey(ruleKey) ? mDrewCount.get(ruleKey) : 0) + drewCount;
    int totalCustomCount =
        (mCustomCount.containsKey(ruleKey) ? mCustomCount.get(ruleKey) : 0) + customCount;
    mHostCount.put(ruleKey, totalHostCount);
    mDrewCount.put(ruleKey, totalDrewCount);
    mCustomCount.put(ruleKey, totalCustomCount);
  }

  private String ruleKey(Map<String, Object> match, int timeMin) {
    String timePrefix = "min" + timeMin + "_";
    float originalScoreOdd = valueOfFloat(match.get(ORIGINAL_SCORE_ODD));
    float openingScoreOdd = valueOfFloat(match.get(OPENING_SCORE_ODD));
    float minScoreOdd = timeMin > 0
        ? valueOfFloat(match.get(timePrefix + "scoreOdd"))
        : valueOfFloat(match.get(OPENING_SCORE_ODD));
    int minHostScore = timeMin > 0 ? valueOfInt(match.get(timePrefix + "hostScore")) : 0;
    int minCustomScore = timeMin > 0 ? valueOfInt(match.get(timePrefix + "customScore")) : 0;
    int minScoreDistance = minHostScore - minCustomScore;

    int minHostShoot = timeMin > 0 ? valueOfInt(match.get(timePrefix + "hostBestShoot")) : 0;
    int minCustomShoot = timeMin > 0 ? valueOfInt(match.get(timePrefix + "customBestShoot")) : 0;
    int minShootDistance = (minHostShoot - minCustomShoot) / 2;

    return StringUtils
        .join(new float[] {timeMin, minScoreOdd, minScoreDistance}, '@');
  }

  public String buildSql(int timeMin) {
    String timePrefix = "min" + timeMin + "_";
    String selectSql = "select hostScore, customScore, original_scoreOdd, opening_scoreOdd, "
        + (timeMin > 0 ? (timePrefix + "scoreOdd, ") : "")
        + (timeMin > 0
            ? (timePrefix + "scoreOddOfVictory, ")
            : (OPENING_SCORE_ODD_OF_VICTORY + ", "))
        + (timeMin > 0
            ? (timePrefix + "scoreOddOfDefeat, ")
            : (OPENING_SCORE_ODD_OF_DEFEAT + ", "))
        + (timeMin > 0 ? (timePrefix + "hostScore, ") : "")
        + (timeMin > 0 ? (timePrefix + "customScore, ") : "")
        + "1 "
        + "from football where 1=1 "
        + "and " + (timeMin > 0 ? (timePrefix + "scoreOddOfVictory>=1.7 ") : "1=1 ")
        + "and " + (timeMin > 0 ? (timePrefix + "scoreOddOfDefeat>=1.7 ") : "1=1 ");

    return selectSql + QueryHelper.SQL_AND + QueryHelper.SQL_ST;
  }
}
