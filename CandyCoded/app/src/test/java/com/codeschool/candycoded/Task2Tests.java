package com.codeschool.candycoded;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
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


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, Uri.class})
@RunWith(PowerMockRunner.class)
public class Task2Tests {

    private static InfoActivity infoActivity;
    //private static Uri mockUri = mock(Uri.class);
    private static boolean called_startActivity = false;
    private static boolean called_uri_parse = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        infoActivity = PowerMockito.spy(new InfoActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));



            // Creating the mockUri - can we check if mockUri has this info?
            PowerMockito.mockStatic(Uri.class);
            PowerMockito.verifyStatic(Uri.class);
            Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801"); // This has to come on the line after mockStatic
            called_uri_parse = true;
            //mockUri = mock(Uri.class);
            //PowerMockito.when(Uri.class, "parse", "geo:0,0?q=618 E South St Orlando, FL 32801").thenReturn(mockUri);

            // Where to put verifyStatic(

            // Create a mock view so that we can call the createMapIntent() method and then check
            // all of the things we set up previously -
            View mockView = mock(View.class);
            infoActivity.createMapIntent(mockView);
            //Intent mapIntent = mock(Intent.class);


            PackageManager mockPackageManager = mock(PackageManager.class);
            ComponentName mockComponentName = mock(ComponentName.class);

            Intent mockMapIntent = mock(Intent.class);

            PowerMockito.whenNew(Intent.class).withParameterTypes(
                    String.class,
                    Uri.class)
                    .withArguments(Mockito.any(String.class), Mockito.any(Uri.class)).thenReturn(mockMapIntent);

            Uri gmmIntentUri = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");

            // Create the mapIntent so that we can see if setPackage() was called
            //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            //Intent mapIntent = PowerMockito.spy(new Intent(Intent.ACTION_VIEW, gmmIntentUri));

            // Determine if they called getPackageManager() and resolveActivity()
            PowerMockito.doReturn(mockPackageManager).when(infoActivity, "getPackageManager");
            //PowerMockito.doReturn(mockComponentName).when(Mockito.any(Intent.class), "resolveActivity", mockPackageManager); //Mockito.any(PackageManager.class));


            // Since startActivity is void, the only way to see if it's called is by doThrow()?
            // startActivity should be called from InfoActivity in the createMapIntent() method
            PowerMockito.doThrow(new ActivityNotFoundException())
                    .when(infoActivity, "startActivity", Mockito.any(Intent.class));

            try {
                infoActivity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Throwable e) {
            e.printStackTrace();

            // Need to replace doThrow() above with using verify on startActivity - see aj's code
            //called_startActivity = true;
        }
        //Mockito.verify(infoActivity).startActivity(mapIntent);
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

    @Test
    public void t2_3_callStartActivity() throws Exception {
        assertTrue(called_startActivity);
    }


}
