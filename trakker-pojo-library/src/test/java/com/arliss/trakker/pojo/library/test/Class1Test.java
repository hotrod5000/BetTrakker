package com.arliss.trakker.pojo.library.test;

import com.arliss.trakker.pojo.library.*;
import com.google.gson.*;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/10/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Class1Test {

    @Test
    public void test1(){
        Class1 sut = new Class1();
        Assert.assertEquals(42, sut.getTheNumber());
    }
    @Test
    public void testMsNbc() throws Exception{
        String url = "http://scores.nbcsports.msnbc.com/ticker/data/gamesMSNBC.js.asp?jsonp=true&sport=MLB&period=20130920";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();

    }
    @Test
    public void testPostStuff() throws Exception{
        Map<String, String> comment = new HashMap<String, String>();
        comment.put("GcmRegId", "xxx");
        comment.put("RegId", "10");
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        HttpHelpers.makeRequest("http://localhost:60022/api/GcmReg", json);
    }


    @Test
    public void gameScoreFromJson(){
        String rawJson = "[{\"League\":\"NFL\",\"GameStatus\":\"FINAL\",\"Team1\":{\"Team\":\"NYJ\",\"Score\":21},\"Team2\":{\"Team\":\"BAL\",\"Score\":10},\"EventDate\":\"2013-09-29T00:00:00\"},{\"League\":\"NFL\",\"GameStatus\":\"FINAL\",\"Team1\":{\"Team\":\"CIN\",\"Score\":6},\"Team2\":{\"Team\":\"CLE\",\"Score\":17},\"EventDate\":\"2013-09-29T00:00:00\"}]";


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gsonBuilder.registerTypeAdapter(DateTime.class, new JodaDateTimeDeserializer());
        Gson gson = gsonBuilder.create();

        GameScore[] scores = gson.fromJson(rawJson, GameScore[].class);
        Assert.assertEquals(scores.length, 2);

        Assert.assertEquals("FINAL", scores[0].getGameStatus());

    }


    @Ignore
    @Test
    public void testFootballScores() throws Exception{
        String url = "&nfl_s_delay=120&nfl_s_stamp=0915083009&nfl_s_left1=NY%20Jets%2010%20%20%20^New%20England%2013%20(FINAL)&nfl_s_right1_count=0&nfl_s_url1=http://sports.espn.go.com/nfl/boxscore?gameId=330912017&nfl_s_left2=St.%20Louis%2024%20%20%20^Atlanta%2031%20(FINAL)&nfl_s_right2_count=0&nfl_s_url2=http://sports.espn.go.com/nfl/boxscore?gameId=330915001&nfl_s_left3=Carolina%2023%20%20%20^Buffalo%2024%20(FINAL)&nfl_s_right3_count=0&nfl_s_url3=http://sports.espn.go.com/nfl/boxscore?gameId=330915002&nfl_s_left4=Minnesota%2030%20%20%20^Chicago%2031%20(FINAL)&nfl_s_right4_count=0&nfl_s_url4=http://sports.espn.go.com/nfl/boxscore?gameId=330915003&nfl_s_left5=Washington%2020%20%20%20^Green%20Bay%2038%20(FINAL)&nfl_s_right5_count=0&nfl_s_url5=http://sports.espn.go.com/nfl/boxscore?gameId=330915009&nfl_s_left6=^Miami%2024%20%20%20Indianapolis%2020%20(FINAL)&nfl_s_right6_count=0&nfl_s_url6=http://sports.espn.go.com/nfl/boxscore?gameId=330915011&nfl_s_left7=Dallas%2016%20%20%20^Kansas%20City%2017%20(FINAL)&nfl_s_right7_count=0&nfl_s_url7=http://sports.espn.go.com/nfl/boxscore?gameId=330915012&nfl_s_left8=^San%20Diego%2033%20%20%20Philadelphia%2030%20(FINAL)&nfl_s_right8_count=0&nfl_s_url8=http://sports.espn.go.com/nfl/boxscore?gameId=330915021&nfl_s_left9=Cleveland%206%20%20%20^Baltimore%2014%20(FINAL)&nfl_s_right9_count=0&nfl_s_url9=http://sports.espn.go.com/nfl/boxscore?gameId=330915033&nfl_s_left10=Tennessee%2024%20%20%20^Houston%2030%20(FINAL%20-%20OT)&nfl_s_right10_count=0&nfl_s_url10=http://sports.espn.go.com/nfl/boxscore?gameId=330915034&nfl_s_left11=Detroit%2021%20%20%20^Arizona%2025%20(FINAL)&nfl_s_right11_count=0&nfl_s_url11=http://sports.espn.go.com/nfl/boxscore?gameId=330915022&nfl_s_left12=^New%20Orleans%2016%20%20%20Tampa%20Bay%2014%20(FINAL)&nfl_s_right12_count=0&nfl_s_url12=http://sports.espn.go.com/nfl/boxscore?gameId=330915027&nfl_s_left13=Jacksonville%209%20%20%20^Oakland%2019%20(FINAL)&nfl_s_right13_count=0&nfl_s_url13=http://sports.espn.go.com/nfl/boxscore?gameId=330915013&nfl_s_left14=^Denver%2041%20%20%20NY%20Giants%2023%20(FINAL)&nfl_s_right14_count=0&nfl_s_url14=http://sports.espn.go.com/nfl/boxscore?gameId=330915019&nfl_s_left15=San%20Francisco%200%20%20%20Seattle%2012%20(07:44%20IN%203RD)&nfl_s_right15_count=0&nfl_s_url15=http://sports.espn.go.com/nfl/boxscore?gameId=330915026&nfl_s_left16=Pittsburgh%20at%20Cincinnati%20(8:30%20PM%20ET)&nfl_s_right16_count=0&nfl_s_url16=http://sports.espn.go.com/nfl/preview?gameId=330916004&nfl_s_count=16&nfl_s_loaded=true";
        String s = URLDecoder.decode(url, "UTF-8");
        Pattern MY_PATTERN = Pattern.compile("left[0-9]{1,2}=(.*?\\))");
        Matcher m = MY_PATTERN.matcher(url);

        List<String> games = new ArrayList<String>();
        while (m.find()) {
            String ss = m.group(1);
            games.add(URLDecoder.decode(ss, "UTF-8"));//.replace("^",""));
        }

        for(String game : games){
            if(game.contains(" at ")){
                continue;
            }
            String[] split = game.split("^");

            Assert.assertEquals(2, split.length);
            String leftTrimmed = split[0].trim();
            String[] rightSplit = split[1].split("\\(");
            String rightTrimmed = rightSplit[0].trim();
            String status = rightSplit[1].trim().replace("\\)","");

            TeamScore t1 = parseTeamScore(leftTrimmed);
            TeamScore t2 = parseTeamScore(rightTrimmed);

            int lastSpace = leftTrimmed.lastIndexOf(" ");
            String score =  leftTrimmed.substring(lastSpace+1);

            GameScore gs = new GameScore();
            gs.setTeam1(t1);
            gs.setTeam2(t2);
            gs.setGameStatus(status);
            gs.setEventDate(DateTime.now());
        }
    }
    TeamScore parseTeamScore(String s){
        int lastSpace = s.lastIndexOf(" ");
        String score = s.substring(lastSpace).trim();
        String team = s.substring(0,lastSpace).trim();
        TeamScore t = new TeamScore();
        t.setScore(Integer.parseInt(score));
        t.setTeam(team);
        return t;
    }
    @Test
    public void testGetScore() throws Exception{
        Ticket t = new Ticket();
        t.setTicketType(TicketType.StraightBet);
        //String url = "gameId=330904115&mlb_s_left2=Minnesota%205%20%20%20^Houston%206%20(FINAL)&mlb_s_right2_1=W:%20Bedard%20L:%20Duensing&mlb_s_right2_count=1&mlb_s_url2=http://sports.espn.go.com/mlb/boxscore?";
        String url = "&nfl_s_delay=120&nfl_s_stamp=0915083009&nfl_s_left1=NY%20Jets%2010%20%20%20^New%20England%2013%20(FINAL)&nfl_s_right1_count=0&nfl_s_url1=http://sports.espn.go.com/nfl/boxscore?gameId=330912017&nfl_s_left2=St.%20Louis%2024%20%20%20^Atlanta%2031%20(FINAL)&nfl_s_right2_count=0&nfl_s_url2=http://sports.espn.go.com/nfl/boxscore?gameId=330915001&nfl_s_left3=Carolina%2023%20%20%20^Buffalo%2024%20(FINAL)&nfl_s_right3_count=0&nfl_s_url3=http://sports.espn.go.com/nfl/boxscore?gameId=330915002&nfl_s_left4=Minnesota%2030%20%20%20^Chicago%2031%20(FINAL)&nfl_s_right4_count=0&nfl_s_url4=http://sports.espn.go.com/nfl/boxscore?gameId=330915003&nfl_s_left5=Washington%2020%20%20%20^Green%20Bay%2038%20(FINAL)&nfl_s_right5_count=0&nfl_s_url5=http://sports.espn.go.com/nfl/boxscore?gameId=330915009&nfl_s_left6=^Miami%2024%20%20%20Indianapolis%2020%20(FINAL)&nfl_s_right6_count=0&nfl_s_url6=http://sports.espn.go.com/nfl/boxscore?gameId=330915011&nfl_s_left7=Dallas%2016%20%20%20^Kansas%20City%2017%20(FINAL)&nfl_s_right7_count=0&nfl_s_url7=http://sports.espn.go.com/nfl/boxscore?gameId=330915012&nfl_s_left8=^San%20Diego%2033%20%20%20Philadelphia%2030%20(FINAL)&nfl_s_right8_count=0&nfl_s_url8=http://sports.espn.go.com/nfl/boxscore?gameId=330915021&nfl_s_left9=Cleveland%206%20%20%20^Baltimore%2014%20(FINAL)&nfl_s_right9_count=0&nfl_s_url9=http://sports.espn.go.com/nfl/boxscore?gameId=330915033&nfl_s_left10=Tennessee%2024%20%20%20^Houston%2030%20(FINAL%20-%20OT)&nfl_s_right10_count=0&nfl_s_url10=http://sports.espn.go.com/nfl/boxscore?gameId=330915034&nfl_s_left11=Detroit%2021%20%20%20^Arizona%2025%20(FINAL)&nfl_s_right11_count=0&nfl_s_url11=http://sports.espn.go.com/nfl/boxscore?gameId=330915022&nfl_s_left12=^New%20Orleans%2016%20%20%20Tampa%20Bay%2014%20(FINAL)&nfl_s_right12_count=0&nfl_s_url12=http://sports.espn.go.com/nfl/boxscore?gameId=330915027&nfl_s_left13=Jacksonville%209%20%20%20^Oakland%2019%20(FINAL)&nfl_s_right13_count=0&nfl_s_url13=http://sports.espn.go.com/nfl/boxscore?gameId=330915013&nfl_s_left14=^Denver%2041%20%20%20NY%20Giants%2023%20(FINAL)&nfl_s_right14_count=0&nfl_s_url14=http://sports.espn.go.com/nfl/boxscore?gameId=330915019&nfl_s_left15=San%20Francisco%200%20%20%20Seattle%2012%20(07:44%20IN%203RD)&nfl_s_right15_count=0&nfl_s_url15=http://sports.espn.go.com/nfl/boxscore?gameId=330915026&nfl_s_left16=Pittsburgh%20at%20Cincinnati%20(8:30%20PM%20ET)&nfl_s_right16_count=0&nfl_s_url16=http://sports.espn.go.com/nfl/preview?gameId=330916004&nfl_s_count=16&nfl_s_loaded=true";
        String s = URLDecoder.decode(url, "UTF-8");

        //matches("left[0-9]{1,2")) //consider this a date and therefore a game
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://sports.espn.go.com/nfl/bottomline/scores");
        HttpResponse response = client.execute(request);

        String html = "";
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();
        html = str.toString();
        Pattern MY_PATTERN = Pattern.compile("left[0-9]{1,2}=(.*?\\))");
        Matcher m = MY_PATTERN.matcher(html);

        List<String> games = new ArrayList<String>();
        while (m.find()) {
            String ss = m.group(1);
            games.add(URLDecoder.decode(ss, "UTF-8").replace("^",""));
        }
    }
}