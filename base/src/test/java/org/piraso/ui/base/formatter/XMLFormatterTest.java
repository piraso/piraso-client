/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.piraso.ui.base.formatter;

import org.piraso.ui.api.formatter.XMLFormatter;
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
