package ard.piraso.ui.base.formatter;

import org.junit.Test;

/**
 * Test for {@link XMLFormatter} class.
 */
public class XMLFormatterTest {
    @Test
    public void testPrettyPrint() throws Exception {
        System.out.println(XMLFormatter.prettyPrint("<action date=\"1192817844568\" impressionId=\"dev-backend_54814273828_5\" creativeName=\"syc_006\" formFamily=\"StrayerCampus\" csDepth=\"0\" ip=\"192.168.17.10\" type=\"impression\" campaignName=\"geekcamp_01\" csImpressionId=\"dev-backend_54814273828_5\" googleBanner=\"\" googleAdGroup=\"\" PI=\"\" rawPI=\"\" TOD=\"11\" DOW=\"FRIDAY\" OS=\"WinXP\" browser=\"Gecko(Firefox)\" userId=\"13a4b6a70e1365f6089341ba6bf44080\" userAgent=\"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7\" hostURL=\"http://dev-ws01.adchemy.colo/forms/preview.jsp?creative=syc_006\" referer=\"http://dev-ws01.adchemy.colo/forms/listCreatives.jsp?customer=strayerCampus\" desc=\"\" title=\"\" siteId=\"\" adId=\"\" keywordId=\"\" displayOrder=\"0\" geoCountry=\"--\" geoState=\"\" geoAreaCode=\"\" geoZip=\"\" geoBandwidth=\"\" geoDirectMarketingArea=\"\" KWGroup=\"\" KW=\"\" userKW=\"\" KWMatchType=\"\" vertical1=\"\" vertical2=\"\" vertical3=\"\"/>"));
    }
}
