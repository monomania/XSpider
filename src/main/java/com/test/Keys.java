package com.test;

public interface Keys {

  String SKIP = "isSkip";

  String MATCH_ID = "matchID";
  String MATCH_TIME = "matchTime";
  String MATCH_STATUS = "matchStatus"; // 0=未开始，1=进行中，2=未知(可能是加时)，3=已结束
  String TIME_MIN = "timeMin";
  String ZHANYI = "zhanyi"; // 不知道是什么
  String TEMPERATURE = "temperature";
  String WEATHER = "weather";

  String HOST_ID = "hostID";
  String HOST_NAME = "hostName";
  String HOST_NAME_PINYIN = "hostNamePinyin";
  String HOST_LEAGUE_RANK = "hostLeagueRank";
  String HOST_JUESHA_RATE = "hostJueshaRate"; // 绝杀率
  String HOST_DA_RATE = "hostDaRate"; // 大球率
  String HOST_XIAO_RATE = "hostXiaoRate"; // 小球率


  String CUSTOM_ID = "customID";
  String CUSTOM_NAME = "customName";
  String CUSTOM_NAME_PINYIN = "customNamePinyin";
  String CUSTOM_LEAGUE_RANK = "customLeagueRank";
  String CUSTOM_JUESHA_RATE = "customJueshaRate"; // 绝杀率
  String CUSTOM_DA_RATE = "customDaRate"; // 大球率
  String CUSTOM_XIAO_RATE = "customXiaoRate"; // 小球率



  String LEAGUE = "league";
  String LEAGUE_ID = "leagueID";
  String LEAGUE_JUESHA_RATE = "leagueJueshaRate"; // 绝杀率
  String LEAGUE_DA_RATE = "leagueDaRate"; // 大球率
  String LEAGUE_XIAO_RATE = "leagueXiaoRate"; // 小球率



  String HOST_SCORE = "hostScore";
  String CUSTOM_SCORE = "customScore";
  String HOST_CORNER_SCORE = "hostCornerScore";
  String CUSTOM_CORNER_SCORE = "customCornerScore";
  String HOST_YELLOW_CARD = "hostYellowCard";
  String CUSTOM_YELLOW_CARD = "customYellowCard";
  String HOST_RED_CARD = "hostRedCard";
  String CUSTOM_RED_CARD = "customRedCard";
  String HOST_DANGER = "hostDanger";
  String CUSTOM_DANGER = "customDanger";
  String HOST_BEST_SHOOT = "hostBestShoot";
  String CUSTOM_BEST_SHOOT = "customBestShoot";
  String HOST_CONTROL_RATE = "hostControlRate";
  String CUSTOM_CONTROL_RATE = "customControlRate";


  String MIDDLE_HOST_SCORE = "middle_hostScore";
  String MIDDLE_CUSTOM_SCORE = "middle_customScore";
  String MIDDLE_HOST_CORNER_SCORE = "middle_hostCornerScore";
  String MIDDLE_CUSTOM_CORNER_SCORE = "middle_customCornerScore";

  String MIDDLE_HOST_YELLOW_CARD = "middle_hostYellowCard";
  String MIDDLE_CUSTOM_YELLOW_CARD = "middle_customYellowCard";
  String MIDDLE_HOST_RED_CARD = "middle_hostRedCard";
  String MIDDLE_CUSTOM_RED_CARD = "middle_customRedCard";



  String ORIGINAL_VICTORY_ODD = "original_victoryOdd";
  String ORIGINAL_DEFEAT_ODD = "original_defeatOdd";
  String ORIGINAL_DREW_ODD = "original_drawOdd";



  String ORIGINAL_SCORE_ODD = "original_scoreOdd";
  String ORIGINAL_SCORE_ODD_OF_VICTORY = "original_scoreOddOfVictory";
  String ORIGINAL_SCORE_ODD_OF_DEFEAT = "original_scoreOddOfDefeat";

  String ORIGINAL_BIG_ODD = "original_bigOdd";
  String ORIGINAL_BIG_ODD_OF_VICTORY = "original_bigOddOfVictory";
  String ORIGINAL_BIG_ODD_OF_DEFEAT = "original_bigOddOfDefeat";


  String OPENING_VICTORY_ODD = "opening_victoryOdd";
  String OPENING_DEFEAT_ODD = "opening_defeatOdd";
  String OPENING_DRAW_ODD = "opening_drawOdd";

  String OPENING_SCORE_ODD = "opening_scoreOdd";
  String OPENING_SCORE_ODD_OF_VICTORY = "opening_scoreOddOfVictory";
  String OPENING_SCORE_ODD_OF_DEFEAT = "opening_scoreOddOfDefeat";

  String OPENING_BIG_ODD = "opening_bigOdd";
  String OPENING_BIG_ODD_OF_VICTORY = "opening_bigOddOfVictory";
  String OPENING_BIG_ODD_OF_DEFEAT = "opening_bigOddOfDefeat";

  String HISTORY_VICTORY_RATE_OF_HOST = "historyVictoryRateOfHost";
  String RECENT_VICTORY_RATE_OF_HOST = "recentVictoryRateOfHost";
  String RECENT_VICTORY_RATE_OF_CUSTOM = "recentVictoryRateOfCustom";
  String RECENT_GOAL_OF_HOST = "recentGoalOfHost";
  String RECENT_GOAL_OF_CUSTOM = "recentGoalOfCustom";
  String RECENT_LOSS_OF_HOST = "recentLossOfHost";
  String RECENT_LOSS_OF_CUSTOM = "recentLossOfCustom";


  String HOST_SCORE_OF_3 = "hostScoreOf3";
  String CUSTOM_SCORE_OF_3 = "customScoreOf3";
  String HOST_LOSS_OF_3 = "hostLossOf3";
  String CUSTOM_LOSS_OF_3 = "customLossOf3";
  String HOST_CORNER_OF_3 = "hostCornerOf3";
  String CUSTOM_CORNER_OF_3 = "customCornerOf3";
  String HOST_YELLOW_CARD_OF_3 = "hostYellowCardOf3";
  String CUSTOM_YELLOW_CARD_OF_3 = "customYellowCardOf3";
  String HOST_CONTROL_RATE_OF_3 = "hostControlRateOf3";
  String CUSTOM_CONTROL_RATE_OF_3 = "customControlRateOf3";

  String BF_LR_HOST = "bf_lr_home"; // 主队必发冷热
  String BF_LR_DREW = "bf_lr_drew"; // 和局必发冷热
  String BF_LR_CUSTOM = "bf_lr_custom"; // 客队必发冷热

  // companyID : 105 = 竞彩, 1 = 澳门, 3 = Crown, 8 = Bet365
  String ODD_COMPANY_FIRST_VICTORY_ = "odd_company_first_victory_";
  String ODD_COMPANY_FIRST_DREW_ = "odd_company_first_drew_";
  String ODD_COMPANY_FIRST_DEFEAT_ = "odd_company_first_defeat_";

  String ODD_COMPANY_OPEN_VICTORY_ = "odd_company_open_victory_";
  String ODD_COMPANY_OPEN_DREW_ = "odd_company_open_drew_";
  String ODD_COMPANY_OPEN_DEFEAT_ = "odd_company_open_defeat_";
}
