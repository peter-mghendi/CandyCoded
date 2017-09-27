package com.codeschool.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, Intent.class, Uri.class, InfoActivity.class})
@RunWith(PowerMockRunner.class)
public class Task3 {

    private static InfoActivity infoActivity;
    private static boolean created_intent = false;
    private static boolean set_data = false;
    private static boolean called_uri_parse = false;
    private static boolean called_startActivity_correctly = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        infoActivity = PowerMockito.spy(new InfoActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);
        Uri mockUri = mock(Uri.class);
        Intent intent = PowerMockito.spy(new Intent(Intent.ACTION_DIAL));

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);


            try {
                infoActivity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            PowerMockito.mockStatic(Uri.class);
            PowerMockito.when(Uri.class, "parse", "tel:0123456789").thenReturn(mockUri);
            try {
                infoActivity.createPhoneIntent(null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            PowerMockito.verifyStatic(Uri.class);
            Uri.parse("tel:0123456789");
            called_uri_parse = true;

            PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).
                    withArguments(Mockito.eq(Intent.ACTION_DIAL));
            created_intent = true;

            verify(intent).setData(mockUri);
            set_data = true;

            Mockito.verify(infoActivity).startActivity(Mockito.eq(intent));
            called_startActivity_correctly = true;


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t3_1_createPhoneIntent_Exists() throws Exception {
        Class<?> myClass = null;

        try {
            myClass =  InfoActivity.class
                    .getMethod("createPhoneIntent", View.class)
                    .getDeclaringClass();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        assertEquals(myClass, InfoActivity.class);
    }

    @Test
    public void t3_2_createIntent() throws Exception {
        assertTrue(created_intent);
    }

    @Test
    public void t3_3_setData() throws Exception {
        assertTrue(set_data);
    }

    @Test
    public void t3_4_callUriParse() throws Exception {
        assertTrue(called_uri_parse);
    }

    @Test
    public void t3_4_no_startActivity_call() {
        assertTrue(called_startActivity_correctly);
    }


}

