package com.arliss.trakker.pojo.library.test;

import com.arliss.trakker.pojo.library.*;
import junit.framework.Assert;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 8/31/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class WilliamHillTicketParserTest {
    String _footballStraightBetTicketInfo = "VIE! ‘<4>'lElk\"€'34£%E..lI10I08PM\n" +
            "        Hlll -\n" +
            "        E] Ticket Info\n" +
            "        Date: 01SEP13\n" +
            "        Time: 22:04\n" +
            "        Ticket ID: 721 D-E2D7-822A\n" +
            "        Bet Cost: $11.00\n" +
            "        Cost Per Bet: $11.00\n" +
            "        Status: OPEN\n" +
            "        Bet Description:\n" +
            "        465 8SEP13 DOLPHI OV 40.5 -110\n" +
            "        i i\n";
    String _footballParlayTicketInfo = " IE3 ‘<4>’lElk‘O‘43...lI9I45PM\n" +
            "        Hlll.\n" +
            "        Open Tickets\n" +
            "        E] Ticket Info\n" +
            "        Date: 01 SEP1 3\n" +
            "        Time: 21 :44\n" +
            "        Ticket ID: A21 D-B48D—822A\n" +
            "        Type: PARLY\n" +
            "        Bet Cost: $5.00\n" +
            "        Cost Per Bet: $5.00\n" +
            "        Status: OPEN\n" +
            "        Bet Descriptions:\n" +
            "        1. 451 5SEP13 RAVENS 9.0 -110\n" +
            "        2. 454 8SEP13 BILLS UN 49.5 -110\n" +
            "        3. 455 8SEP13 TITANS 260\n" +
            "        4. 457 8SEP13 FALCON OV 54.0\n" +
            "        -110\n";
    String _footballStraightBetConfirmation = " 33I;| lElk\"®‘43... 10:04PM\n" +
            "        /5  <\n" +
            "        Conﬁrm Bet\n" +
            "        Bet Conﬁrmation\n" +
            "        RODNEY SMITH (4292)\n" +
            "        Ticket Id: 721 D-E2D7-822A\n" +
            "        $11 Straight Bet\n" +
            "        08-SEP-2013 BI: 465 DOLPHINS -110 OVER\n" +
            "        40.5\n" +
            "        Cost: $11\n" +
            "        Payoff: $21\n" +
            "        Your bet has been placed.\n" +
            "        'Ticket Id’ is your conﬁrmation.";
    String _footballParlayConfirmation =" 33|§| lEl\\\"Q‘4.G.... 9:44PM\n" +
            "        /5  (\n" +
            "        Conﬁrm Bet\n" +
            "        Bet Conﬁrmation\n" +
            "        Ticket Id: A21 D-B48D-822A\n" +
            "        $5 4 Leg Parlay\n" +
            "        O5-SEP-2013 BI: 451 RAVENS -I10 POINT\n" +
            "        LINE 9.0\n" +
            "        08-SEP-2013 BI: 454 BILLS -I10 UNDER 49.5\n" +
            "        08-SEP-2013 BI: 455 TITANS 260 MONEY\n" +
            "        LINE\n" +
            "        O8-SEP-2013 BI: 457 FALCONS -I10 OVER\n" +
            "        54.0\n" +
            "        Cost: $5\n" +
            "        Payoff: $126\n" +
            "        Your bet has been placed.\n" +
            "        'Ticket Id’ is your conﬁrmation.\n";


    String _baseballMoneyLineConfirmation = "AT&T [El EN‘: ’Q‘ 49,5 4. 3:16 PM\n" +
            "        /5  <\n" +
            "        Conﬁrm Bet\n" +
            "        Bet Conﬁrmation\n" +
            "        Ticket Id: 141 C-B5C9-8229\n" +
            "        $4 Straight Bet\n" +
            "        31-AUG-2013 BI: 923 MARINERS -103\n" +
            "        MONEY LINE\n" +
            "        Cost: $4\n" +
            "        Payoff: $7.90\n" +
            "        Your bet has been placed.\n" +
            "        'Ticket Id’ is your conﬁrmation.";

    String _baseballParlayConfirmation = " I;1 lEl:\\*:’Q‘4.e.... 3:18PM\n" +
            "        /5  (\n" +
            "        Conﬁrm Bet\n" +
            "        Bet Conﬁrmation\n" +
            "        Ticket Id: 091C-D6CB-8229\n" +
            "        $4 5 Leg Parlay\n" +
            "        31-AUG-2013 BI: 903 METS/Wheeler vs\n" +
            "        NATIONALS/Haren -'| '| 0 OVER 7.5\n" +
            "        31-AUG-2013 BI: 906 PIRATES/Burnett vs\n" +
            "        CARDINALS/Lynn -'| 10 UNDER 7.5\n" +
            "        31-AUG-2013 BI: 920 TIGERS/Sanchez vs\n" +
            "        INDIANS/Kazmir -'| 10 UNDER 8.5\n" +
            "        31-AUG-2013 BI: 907 MARLINS/Turner vs\n" +
            "        BRAVES/Minor -'| 20 OVER 7.0\n" +
            "        31-AUG-2013 BI: 921 WHITE SOX/Danks VS\n" +
            "        RED SOX/Peavy -120 OVER 8.5\n" +
            "        Cost: $4\n" +
            "        Payoff: $93.55\n" +
            "        Your bet has been placed.\n" +
            "        'Ticket Id’ is your conﬁrmation.\n";

    String _baseballParlayTicketInfo = "AT&T ‘<45 [El '63‘ 49 A 3:05 PM\n" +
            "        E] Ticket Info\n" +
            "        Date: 23AUG13\n" +
            "        Time: 08:29\n" +
            "        Ticket ID: 9602-C14B-8221\n" +
            "        Type: PARLY\n" +
            "        Bet Cost: $4.00\n" +
            "        Cost Per Bet: $4.00\n" +
            "        Status: OPEN\n" +
            "        Bet Descriptions:\n" +
            "        1. 902 23AUG13 PHILLIES (A) -120\n" +
            "        2. 913 23AUG13 TWINS (A) 150\n" +
            "        3. 916 23AUG13 ORIOLES (A) -130\n" +
            "        4. 904 23AUG13 MARLINS (A) 130\n" +
            "        5. 918 23AUG13 TB RAYS (A) -110\n" +
            "        6. 919 23AUG13 BL.JAYS (A) -140\n" +
            "        7.921 23AUG13 RANGERS (A) 115\n" +
            "        8. 930 23AUG13 DODGERS (A) -110\n" +
            "        L";
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void test_footballparlayConfirmation() throws Exception {
        WilliamHillTicketParser parser = new WilliamHillTicketParser("test");
        Ticket t = parser.parse(_footballParlayConfirmation);

        DateTime expected = null;
        Assert.assertEquals(expected,t.getDateTime());
        Money expectedWager = Money.parse("USD 5.00");
        Assert.assertEquals(expectedWager,t.getWagerAmount());
        Assert.assertEquals("William Hill", t.getSportsBook());
        Assert.assertEquals(TicketType.Parlay,t.getTicketType());
        Assert.assertEquals(4,t.getGameCount());
        Assert.assertEquals(Money.parse("USD 126"),t.getPayoff());

        List<Game> games = t.getGames();
        Game g = games.get(0);
        Assert.assertEquals("RAVENS",g.getTeam());
        Assert.assertEquals(new DateTime(2013,9,5,0,0,0,0), g.getDateTime());
        Assert.assertEquals(GameBetType.PointsLine, g.getBetType());
        Assert.assertEquals(9.0f,g.getValue());

        g = games.get(1);
        Assert.assertEquals("BILLS",g.getTeam());
        Assert.assertEquals(new DateTime(2013,9,8,0,0,0,0), g.getDateTime());
        Assert.assertEquals(GameBetType.Under, g.getBetType());
        Assert.assertEquals(49.5f,g.getValue());

        g = games.get(2);
        Assert.assertEquals("TITANS",g.getTeam());
        Assert.assertEquals(new DateTime(2013,9,8,0,0,0,0), g.getDateTime());
        Assert.assertEquals(GameBetType.MoneyLine, g.getBetType());
        Assert.assertEquals(260f,g.getValue());

        g = games.get(3);
        Assert.assertEquals("FALCONS",g.getTeam());
        Assert.assertEquals(new DateTime(2013,9,8,0,0,0,0), g.getDateTime());
        Assert.assertEquals(GameBetType.Over, g.getBetType());
        Assert.assertEquals(54f,g.getValue());

    }
    @Test
    public void test_footballStraightBetConfirmation() throws Exception {
        WilliamHillTicketParser parser = new WilliamHillTicketParser("test");
        Ticket t = parser.parse(_footballStraightBetConfirmation);

        DateTime expected = null;
        Assert.assertEquals(expected,t.getDateTime());
        Money expectedWager = Money.parse("USD 11.00");
        Assert.assertEquals(expectedWager,t.getWagerAmount());
        Assert.assertEquals("William Hill", t.getSportsBook());
        Assert.assertEquals(TicketType.StraightBet,t.getTicketType());
        Assert.assertEquals(Money.parse("USD 21"),t.getPayoff());

        List<Game> games = t.getGames();
        Game g = games.get(0);
        Assert.assertEquals("DOLPHINS",g.getTeam());
        Assert.assertEquals(new DateTime(2013,9,8,0,0,0,0), g.getDateTime());
        Assert.assertEquals(GameBetType.Over, g.getBetType());
        Assert.assertEquals(40.5f,g.getValue());



    }



}
