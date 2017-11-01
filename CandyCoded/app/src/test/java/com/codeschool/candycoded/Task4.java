package com.codeschool.candycoded;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest({AppCompatActivity.class, Intent.class, DetailActivity.class, Method.class })
@RunWith(PowerMockRunner.class)
public class Task4 {

    public static final String SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - ";
    public static final String HASHTAG_CANDYCODED = " #candycoded";
    public static final String mCandyImageUrl = "";

    private static DetailActivity detailActivity;
    private static boolean onOptionsItemSelected_result = true;
    private static boolean called_createShareIntent = false;
    private static boolean created_intent = false;
    private static boolean set_type = false;
    private static boolean called_put_extra = false;
    private static boolean called_startActivity_correctly = false;

    // Mockito setup
    @BeforeClass
    public static void setup() throws Exception {
        // Spy on a MainActivity instance.
        detailActivity = PowerMockito.spy(new DetailActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = mock(Bundle.class);

        Intent intent = PowerMockito.spy(new Intent(Intent.ACTION_SEND));

        try {
            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);


            try {
                detailActivity.onCreate(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            onOptionsItemSelected_result = detailActivity.onOptionsItemSelected(null);

            PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).
                    withArguments(Mockito.eq(Intent.ACTION_SEND));
            created_intent = true;

            verify(intent).setType("text/plain");
            set_type = true;

            verify(intent).putExtra(Intent.EXTRA_TEXT, SHARE_DESCRIPTION + mCandyImageUrl + HASHTAG_CANDYCODED);
            called_put_extra = true;

            verify(detailActivity).startActivity(Mockito.eq(intent));
            called_startActivity_correctly = true;


        } catch (Throwable e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void test_combined() throws Exception {
        onOptionsItemSelected_Exists();
        assertFalse("onOptionsItemSelected() does not return true.", onOptionsItemSelected_result);
        createShareIntent_Exists();
        assertTrue("The Intent was not created correctly.", created_intent);
        assertTrue("The Intent's type needs to be set with setType().", set_type);
        assertTrue("Send extra data with the Intent with putExtra().", called_put_extra);
        assertTrue("The method startActivity() was not called.", called_startActivity_correctly);
    }

    public void onOptionsItemSelected_Exists() throws Exception {
        Class<?> myClass = null;

        try {
            myClass =  DetailActivity.class
                    .getMethod("onOptionsItemSelected", MenuItem.class)
                    .getDeclaringClass();
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        }

        assertEquals("onOptionsItemSelected() method doesn't exist in DetailActivity class.",
                myClass, DetailActivity.class);
    }

    public void createShareIntent_Exists() throws Exception {
        Method myMethod = null;

        try {
            myMethod =  DetailActivity.class.getDeclaredMethod("createShareIntent");
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        }

        assertNotNull("createShareIntent() method doesn't exist in DetailActivity class.", myMethod);
    }
}

