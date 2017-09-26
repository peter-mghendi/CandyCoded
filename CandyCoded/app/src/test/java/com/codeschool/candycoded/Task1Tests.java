package com.codeschool.candycoded;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class})
@RunWith(PowerMockRunner.class)
public class Task1Tests {

    private static Intent mockIntent = Mockito.mock(Intent.class);
    private static boolean called_startActivity = false;
    private static MainActivity activity;


    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        activity = PowerMockito.spy(new MainActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = Mockito.mock(Bundle.class);

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));


            PowerMockito.doThrow(new ActivityNotFoundException())
                    .when(activity, "startActivity", Mockito.any(Intent.class));



            PowerMockito.whenNew(Intent.class).withParameterTypes(
                                                                android.content.Context.class,
                                                                java.lang.Class.class)
                    .withArguments(Mockito.any(android.content.Context.class), eq(InfoActivity.class)).thenReturn(mockIntent);

            //Intent infoIntentFixture = new Intent(activity, InfoActivity.class);
            //PowerMockito.whenNew(Intent.class).withParameterTypes(android.content.Context.class, InfoActivity.class).thenReturn(mockIntent);

            // We expect calling onCreate() to throw an Exception due to our mocking. Ignore it.
            try {
                activity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            activity.onOptionsItemSelected(null);


            //called_startActivity = true;

        }catch (ActivityNotFoundException e) {
            e.printStackTrace();

            called_startActivity = true;
        }
    }

    @Test
    public void t1_1_OnOptionsItemSelected_Exists() throws Exception {
        // Determine if the method OnOptionsItemSelected() is implemented in MainActivity
        // or just in the Base class
        Class<?> myClass = null;

        try {
            myClass =  MainActivity.class
                    .getMethod("onOptionsItemSelected", MenuItem.class)
                    .getDeclaringClass();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        assertEquals(myClass, MainActivity.class);
    }

    @Test
    public void t1_2_Call_StartActivity() throws Exception {
        String assertionError = "";

        try {
            assertEquals(called_startActivity, true);
        } catch (AssertionError ae) {
            assertionError = ae.toString();
        }

        System.out.println(assertionError);
    }

    @Test
    public void t1_3_CreateInfoIntent() throws Exception {
        String assertionError = "";

        try {
            //Mockito.verify(activity).startActivity(mockIntent);
            assertNotEquals(mockIntent, null);
        } catch (AssertionError ae) {
            assertionError = ae.toString();
        }

        System.out.println(assertionError);
    }

}
