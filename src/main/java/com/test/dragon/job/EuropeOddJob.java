package com.test.dragon.job;

import static com.test.dragon.tools.DragonUtils.setSkip;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.test.dragon.DragonJob;
import com.test.dragon.tools.OddItemParser;
import com.test.tools.Logger;

import okhttp3.Request;

// 全场欧指
// curl -H 'Host: apk.win007.com' -H 'User-Agent: okhttp/3.10.0' --compressed
// 'http://apk.win007.com//phone/analyoddsdetail.aspx?scheid=1757870&oddstype=4&matchtime=20190730093000&ishalf=0&androidfrom=nowscore&fromkind=1&version=4.80&app_token=sOA9HfVbPo1ywNVYl1Hi9wypKCjh63cf7FXrAekSYYCzl2OzgGZulNovlmS2%2F5WSKkoi6v9DpusnDmFD379Pv%2F40uIfkowNb7vhleIPHPrmHzGv5gUg6zf%2F252R0BBIvMbrlYIc%2B4hI7Oj8hhMKW%2BZNW8lpH8N8PcPTE5XWQc5M%3D&ran=1564455378045000'
public class EuropeOddJob extends DragonJob {

  private static final String REQUEST_URL =
      "http://apk.win007.com//phone/analyoddsdetail.aspx?scheid=1757870&oddstype=4&matchtime=20190730093000&ishalf=0&androidfrom=nowscore&fromkind=1&version=4.80&app_token=sOA9HfVbPo1ywNVYl1Hi9wypKCjh63cf7FXrAekSYYCzl2OzgGZulNovlmS2%2F5WSKkoi6v9DpusnDmFD379Pv%2F40uIfkowNb7vhleIPHPrmHzGv5gUg6zf%2F252R0BBIvMbrlYIc%2B4hI7Oj8hhMKW%2BZNW8lpH8N8PcPTE5XWQc5M%3D&ran=1564455378045000";

  public EuropeOddJob(int matchID, Logger logger) {
    super(matchID, logger);
  }

  @Override
  public Request.Builder newRequestBuilder() {
    String newUrl = REQUEST_URL.replace("scheid=1757870", "scheid=" + mMatchID);

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
    OddItemParser item = new OddItemParser(OddItemParser.OddType.EUROPE, json);
    item.fill(items);

    // System.out.println(text);
  }
}
