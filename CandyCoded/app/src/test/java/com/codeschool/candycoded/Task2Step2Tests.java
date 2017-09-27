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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest(value = {AppCompatActivity.class, Intent.class, Uri.class}, fullyQualifiedNames = {"com.codeschool.candycoded.*"})
@RunWith(PowerMockRunner.class)
public class Task2Step2Tests {

    private static InfoActivity infoActivity;
    private static boolean created_intent = false;
    private static boolean created_intent_correctly = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        infoActivity = PowerMockito.spy(new InfoActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);
        //Uri mockUri = mock(Uri.class);
        Uri actualUri = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");
        //Uri spyUri = PowerMockito.spy(Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801"));


        Intent intent = PowerMockito.spy(new Intent(Intent.ACTION_VIEW, actualUri));

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));


            //PowerMockito.whenNew(Intent.class).withNoArguments().thenReturn(intent);
            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);

            try {
                infoActivity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }


            View mockView = mock(View.class);
            PowerMockito.mockStatic(Uri.class);
            infoActivity.createMapIntent(null);
            Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");

            try {
                PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withArguments(Mockito.eq(Intent.ACTION_VIEW), Mockito.eq(actualUri));
                //PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withNoArguments();
                created_intent = true;
            } catch (Throwable e) {
                e.printStackTrace();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2_3_createIntent() throws Exception {
        assertTrue(created_intent);
    }

    //@Test
    //public void t2_3_callStartActivity() throws Exception {
    //    assertTrue(called_startActivity);
    //}


}

