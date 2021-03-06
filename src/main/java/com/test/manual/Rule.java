package com.test.manual;

import java.util.Objects;

public class Rule {

  public final RuleType mType;
  public final String mRuleKey;
  public final int mTimeMin;

  public final int mHostTotal;
  public final int mDrewTotal;
  public final int mCustomTotal;
  public final float mHostProfit;
  public final float mCustomProfit;

  public Rule(RuleType type, String ruleKey, int timeMin, int hostTotal, int drewTotal,
      int customTotal, float hostProfit, float customProfit) {
    mType = type;
    mRuleKey = ruleKey;
    mTimeMin = timeMin;
    mHostTotal = hostTotal;
    mDrewTotal = drewTotal;
    mCustomTotal = customTotal;
    mHostProfit = hostProfit;
    mCustomProfit = customProfit;
  }

  public final int total() {
    return mHostTotal + mDrewTotal + mCustomTotal;
  }

  public final int value() {
    return mHostProfit > mCustomProfit ? 0 : 2;
  }

  public final float prob0() {
    final int total = total();
    return total <= 0 ? 0 : mHostTotal * 1.00f / total;
  }

  public final float prob1() {
    final int total = total();
    return total <= 0 ? 0 : mDrewTotal * 1.00f / total;
  }

  public final float prob2() {
    final int total = total();
    return total <= 0 ? 0 : mCustomTotal * 1.00f / total;
  }

  public final float victoryRate() {
    int total = mHostTotal + mCustomTotal;
    return total <= 0
        ? 0
        : (mHostProfit > mCustomProfit ? mHostTotal : mCustomTotal) * 1.00f / total;
  }

  public final float profitRate() {
    int total = mHostTotal + mCustomTotal;
    return total <= 0 ? 0 : Math.max(mHostProfit, mCustomProfit) / total;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Rule)) return false;
    return Objects.equals(this.mType, ((Rule) obj).mType)
        && Objects.equals(this.mRuleKey, ((Rule) obj).mRuleKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mType, mRuleKey);
  }
}
