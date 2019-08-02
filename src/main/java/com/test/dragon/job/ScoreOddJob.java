package com.test.dragon.job;

import static com.test.dragon.tools.DragonUtils.setSkip;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.test.dragon.kernel.DragonJob;
import com.test.dragon.tools.OddItemParser;
import com.test.tools.Logger;

import okhttp3.Request;

// 全场让球
// curl -H 'Host: apk.win007.com' -H 'User-Agent: okhttp/3.10.0' --compressed
// 'http://apk.win007.com//phone/analyoddsdetail.aspx?scheid=1662673&oddstype=1&matchtime=20190730183000&ishalf=0&androidfrom=nowscore&fromkind=1&version=4.80&app_token=sOA9HfVbPo1ywNVYl1Hi9wypKCjh63cf7FXrAekSYYCzl2OzgGZulNovlmS2%2F5WSKkoi6v9DpusnDmFD379Pv%2F40uIfkowNb7vhleIPHPrmHzGv5gUg6zf%2F252R0BBIvMbrlYIc%2B4hI7Oj8hhMKW%2BZNW8lpH8N8PcPTE5XWQc5M%3D&ran=1564488609287000'
public class ScoreOddJob extends DragonJob {

  private static final String REQUEST_URL =
      "http://apk.win007.com//phone/analyoddsdetail.aspx?scheid=1753506&oddstype=1&matchtime=20190730093000&ishalf=0&androidfrom=nowscore&fromkind=1&version=4.80&app_token=sOA9HfVbPo1ywNVYl1Hi9wypKCjh63cf7FXrAekSYYCzl2OzgGZulNovlmS2%2F5WSKkoi6v9DpusnDmFD379Pv%2F40uIfkowNb7vhleIPHPrmHzGv5gUg6zf%2F252R0BBIvMbrlYIc%2B4hI7Oj8hhMKW%2BZNW8lpH8N8PcPTE5XWQc5M%3D&ran=1564455356992000'";

  public ScoreOddJob(int matchID, Logger logger) {
    super(matchID, logger);
  }

  @Override
  public Request.Builder newRequestBuilder() {
    String newUrl = REQUEST_URL.replace("scheid=1753506", "scheid=" + mMatchID)
        .replace("ran=1564455356992000", "ran=" + System.currentTimeMillis() * 1000);

    return new Request.Builder().url(newUrl);
  }

  @Override
  public void handleResponse(String text, Map<String, String> items) {
    JSONArray json = JSON.parseArray(text);
    if (json == null) {
      mLogger
          .log(String.format("Skipped: %s [%d] \n %s", getClass().getSimpleName(), mMatchID, text));
      setSkip(items);
      return;
    }
    OddItemParser item = new OddItemParser(OddItemParser.OddType.SCORE, json);
    item.fill(items);


    // System.out.println(items);
  }

}