package com.test;

import com.test.tools.Logger;

public class Config {

  // 线程总数
  public static final int MAX_THREAD_COUNT = 10;

  // public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_x.db";
  // public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_dragon.db";
  public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_ds.db";

  // 日志输出
  public static final Logger LOGGER = Logger.SYSTEM;

  // 实时抓取比赛时, 一圈不低于1分钟
  public static final long MIN_ONE_LOOP = 60 * 1000;

  public static final String PROXY_STRING =
      "58.218.200.223:2030 58.218.200.227:7224 58.218.200.223:5862 58.218.200.227:7393 58.218.200.223:8611 58.218.200.223:6946 58.218.200.223:5009 58.218.200.227:3141 58.218.200.227:2028 58.218.200.227:8957 58.218.200.223:8899 58.218.200.226:2707 58.218.200.226:5667 58.218.200.223:8706 58.218.200.226:8190 58.218.200.228:7504 58.218.200.223:7238 58.218.200.225:6111 58.218.200.226:2489 58.218.200.225:9060 58.218.200.226:2957 58.218.200.226:3164 58.218.200.223:5468 58.218.200.227:3877 58.218.200.225:7299 58.218.200.226:4960 58.218.200.223:8852 58.218.200.223:7516 58.218.200.227:2029 58.218.200.223:2518 58.218.200.226:9176 58.218.200.227:5636 58.218.200.226:8913 58.218.200.228:4447 58.218.200.228:9000 58.218.200.225:6036 58.218.200.226:6055 58.218.200.223:6614 58.218.200.223:6463 58.218.200.227:5886 58.218.200.223:2923 58.218.200.226:8195 58.218.200.227:3831 58.218.200.226:5196 58.218.200.223:8301 58.218.200.226:8893 58.218.200.227:7972 58.218.200.227:3258 58.218.200.227:4530 58.218.200.223:2983 58.218.200.227:8788 58.218.200.226:7973 58.218.200.227:7482 58.218.200.227:8029 58.218.200.227:5128 58.218.200.227:2673 58.218.200.228:8305 58.218.200.228:6182 58.218.200.227:5546 58.218.200.227:2496 58.218.200.227:4211 58.218.200.226:5848 58.218.200.223:4778 58.218.200.226:5533 58.218.200.223:5980 58.218.200.223:5060 58.218.200.227:8861 58.218.200.223:4903 58.218.200.223:4349 58.218.200.223:6221 58.218.200.223:3341 58.218.200.223:5544 58.218.200.223:7104 58.218.200.226:5435 58.218.200.228:5193 58.218.200.228:5270 58.218.200.223:3658 58.218.200.227:9169 58.218.200.223:5780 58.218.200.223:2156 58.218.200.226:8718 58.218.200.227:6765 58.218.200.226:7241 58.218.200.226:2787 58.218.200.226:7539 58.218.200.226:2134 58.218.200.227:7903 58.218.200.223:2940 58.218.200.226:3349 58.218.200.223:7815 58.218.200.226:6135 58.218.200.227:8177 58.218.200.225:4097 58.218.200.226:7294 58.218.200.223:7426 58.218.200.226:4958 58.218.200.223:2338 58.218.200.223:3863 58.218.200.223:2247 58.218.200.228:9162 58.218.200.228:2199 58.218.200.226:4758 58.218.200.227:4371 58.218.200.223:4123 58.218.200.223:8168 58.218.200.227:6699 58.218.200.223:6818 58.218.200.226:4312 58.218.200.226:7417 58.218.200.227:2978 58.218.200.228:6101 58.218.200.228:2580 58.218.200.227:8907 58.218.200.227:4962 58.218.200.227:2153 58.218.200.223:7947 58.218.200.226:7517 58.218.200.223:7215 58.218.200.227:4908 58.218.200.227:4782 58.218.200.223:6607 58.218.200.223:6261 58.218.200.223:6671 58.218.200.227:5294 58.218.200.227:9080 58.218.200.226:3216 58.218.200.226:4240 58.218.200.226:8646 58.218.200.227:5243 58.218.200.227:4479 58.218.200.226:6585 58.218.200.227:5325 58.218.200.223:2711 58.218.200.223:6105 58.218.200.227:6601 58.218.200.226:5198 58.218.200.227:4350 58.218.200.228:5153 58.218.200.227:7325 58.218.200.227:2770 58.218.200.225:6455 58.218.200.226:2168 58.218.200.227:2894 58.218.200.228:4550 58.218.200.227:6717 58.218.200.223:5836 58.218.200.226:4015 58.218.200.226:6549 58.218.200.228:7044 58.218.200.223:3668";
}
