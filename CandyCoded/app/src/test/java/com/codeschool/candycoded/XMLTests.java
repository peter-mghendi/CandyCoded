package com.codeschool.candycoded;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class XMLTests {

    /* Used to track whether task milestones are satisfied. */
    private boolean textView_is_clickable = false;


    private class ViewContainer {
        public String id;
        public String onClick;
        public String clickable;

        public ViewContainer(String idProperty, String onClickProperty, String clickableProperty) {
            id = idProperty;
            onClick = onClickProperty;
            clickable = clickableProperty;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!ViewContainer.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final ViewContainer other = (ViewContainer) obj;
            if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
                return false;
            }
            if ((this.onClick == null) ? (other.onClick != null) : !this.onClick.equals(other.onClick)) {
                return false;
            }
            if ((this.clickable == null) ? (other.clickable != null) : !this.clickable.equals(other.clickable)) {
                return false;
            }
            return true;
        }
    }

    private ArrayList<ViewContainer> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<ViewContainer> viewContainers = new ArrayList<ViewContainer>();

        parser.require(XmlPullParser.START_TAG, null, "android.support.constraint.ConstraintLayout");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("TextView")) {
                // TO DO
                String idProperty = parser.getAttributeValue(null, "android:id");
                System.out.println("XML id property = " + idProperty);
                String onClickProperty = parser.getAttributeValue(null, "android:onClick");
                System.out.println("XML onClick property = " + onClickProperty);
                String clickableProperty = parser.getAttributeValue(null, "android:clickable");
                System.out.println("XML clickable property = " + clickableProperty);

                ViewContainer viewContainer = new ViewContainer(idProperty, onClickProperty, clickableProperty);
                viewContainers.add(viewContainer);
                // This will go to the end of the TextView tag
                parser.next();
            } else {
                skip(parser);
            }
        }

        return viewContainers;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    @Test
    public void test_combined() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("res/layout/activity_info.xml");

        ArrayList<ViewContainer> viewContainers = new ArrayList<ViewContainer>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            assertEquals(false, factory.getFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES));
            XmlPullParser parser = factory.newPullParser();
            //XmlPullParser parser = Xml.newPullParser();
            //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            viewContainers = readFeed(parser);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            inputStream.close();
        }

        ViewContainer addressView = new ViewContainer("@+id/text_view_address", "createMapIntent", "true");
        boolean address_set_correct =  viewContainers.contains(addressView) ;
        ViewContainer phoneView = new ViewContainer("@+id/text_view_phone", "createPhoneIntent", "true");
        boolean phone_set_correct = viewContainers.contains(phoneView);

        assertTrue(address_set_correct);
        assertTrue(phone_set_correct);
    }
}
