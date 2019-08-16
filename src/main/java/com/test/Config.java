package com.test;

import com.test.tools.Logger;

public class Config {

  // 线程总数
  public static final int SPIDER_THREAD_COUNT = 8;

  public static final int RADAR_THREAD_COUNT = 1;

  // public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_x.db";
  // public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_dragon.db";
  public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_ds.db";

  // 日志输出
  public static final Logger LOGGER = Logger.SYSTEM;

  // 实时抓取比赛时, 一圈不低于1分钟
  public static final long MIN_ONE_LOOP = 60 * 1000;

  public static final String PROXY_STRING = "58.218.201.114:6936 58.218.200.214:5791 58.218.201.114:5811 58.218.201.74:5592 58.218.200.214:8169 58.218.201.74:8495";
}
