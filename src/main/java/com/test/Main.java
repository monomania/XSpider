package com.test;

import com.test.manual.HistoryTester;

public class Main {

  public static void main(String[] args) throws Exception {

    // DsSpider.runSt(240000, 588848);

    // DsHelper.read("/Users/Jesse/Desktop/odd"); // ~511531


    // QueryHelper.queryLeagues();

    // Radar.main(null);

    // new RuleFactory(RuleType.SCORE).build();
    // new RuleFactory(RuleType.BALL).build();

    // new HistoryRadar().run(100);

     HistoryTester.testOnLast1Weeks();
//    HistoryTester.testOnLastDay();

//    HistoryTester.testOnNewHistory(7);

    // HistoryTester.testOnRandomHistory();

    // HistoryTester.testOnNewHistory(1);
  }

}
