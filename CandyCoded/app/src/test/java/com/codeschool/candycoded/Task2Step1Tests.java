package com.codeschool.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, Uri.class, Intent.class})
@RunWith(PowerMockRunner.class)
public class Task2Step1Tests {

    private static InfoActivity infoActivity;
    //private static Uri mockUri = mock(Uri.class);
    private static boolean called_startActivity = false;
    private static boolean called_uri_parse = false;
    private static boolean created_intent = false;
    private static boolean created_intent_correctly = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        infoActivity = PowerMockito.spy(new InfoActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);
        Uri mockUri = mock(Uri.class);
        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            try {
                infoActivity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Creating the mockUri - can we check if mockUri has this info?
            //when(Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801")).thenReturn(mockUri);
            PowerMockito.mockStatic(Uri.class);
            PowerMockito.doReturn(mockUri).when(Uri.class);
            try {
                infoActivity.createMapIntent(null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            PowerMockito.verifyStatic(Uri.class);
            Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801"); // This has to come on the line after mockStatic
            //when(Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801")).thenReturn(actualUri);
            called_uri_parse = true;


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2_1_createMapIntent_Exists() throws Exception {
        Class<?> myClass = null;

        try {
            myClass =  InfoActivity.class
                    .getMethod("createMapIntent", View.class)
                    .getDeclaringClass();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        assertEquals(myClass, InfoActivity.class);
    }

    @Test
    public void t2_2_createGeoUri() throws Exception {
        // Was hoping we could check that the mockUri is equivalent to this one below
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");

        //assertNotNull(mockUri);
        assertTrue(called_uri_parse);
    }



}
