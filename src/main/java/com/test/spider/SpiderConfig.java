package com.test.spider;

import java.text.SimpleDateFormat;

public class SpiderConfig {

  public static final String USER_AGENT =
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";

  public static final int DOWNLOAD_RETRY_COUNT = 1;
  public static final int THREAD_SLEEP_TIME = 1000;
  public static final int TOTAL_THREAD_COUNT = 10; // 抓数据线程
  public static final int MAX_CACHE_ITEMS = 1000; // 先攒数据，用于创建数据库的初始key

  public static final int STATIC_ID_START = 1756889; // 起始ID
  public static final int STATIC_ID_END = 1756899; // 结束ID
  // public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_x.db";
  public static final String DATABASE_URL = "jdbc:sqlite:sqlite/football_dragon.db";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static final String PROXY_STRING =
      "163.204.220.121:4242 58.243.207.16:4243 118.79.54.32:4281 112.194.201.251:4284 112.84.215.69:4207 175.153.21.68:4246 220.249.149.118:4254 112.192.255.20:4284 42.180.252.218:4212 124.161.43.45:4258 112.195.48.121:4215 112.83.180.41:4231 153.101.247.41:4203 123.156.191.249:4281 42.5.150.5:4286 119.5.72.13:4285 110.52.224.33:4246 27.209.202.125:4246 36.33.20.232:4226 113.231.46.243:4286 112.123.41.28:4254 153.99.7.169:4207 175.167.62.19:4284 124.152.85.71:4264 60.17.248.79:4252 112.87.78.112:4250 221.203.191.10:4212 124.163.74.121:4225 183.188.219.66:4281 119.5.144.162:4284 175.44.108.163:4254 175.167.35.98:4252 124.161.43.228:4258 58.255.199.3:4225 101.27.21.254:4263 58.241.203.24:4260 112.245.181.158:4243 110.52.224.210:4246 112.84.244.79:4207 39.90.227.57:4260 39.66.142.227:4274 112.195.98.167:4255 112.252.68.219:4274 123.156.185.208:4281 183.188.241.102:4281 36.34.12.63:4226 39.66.73.229:4274 116.149.194.49:4226 113.229.123.39:4226 183.188.209.189:4281";
}
