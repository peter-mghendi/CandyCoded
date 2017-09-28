package com.codeschool.candycoded;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;


public class XMLTests {

    @Test
    public void test_combined() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("res/layout/activity_info.xml");

        ArrayList<XMLTestHelpers.ViewContainer> viewContainers = new ArrayList<XMLTestHelpers.ViewContainer>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            parser.nextTag();
            viewContainers = XMLTestHelpers.readFeed(parser);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            inputStream.close();
        }

        XMLTestHelpers.ViewContainer addressView = new XMLTestHelpers.ViewContainer("@+id/text_view_address", "createMapIntent", "true");
        boolean address_set_correct =  viewContainers.contains(addressView) ;
        XMLTestHelpers.ViewContainer phoneView = new XMLTestHelpers.ViewContainer("@+id/text_view_phone", "createPhoneIntent", "true");
        boolean phone_set_correct = viewContainers.contains(phoneView);

        assertTrue(address_set_correct);
        assertTrue(phone_set_correct);
    }
}
