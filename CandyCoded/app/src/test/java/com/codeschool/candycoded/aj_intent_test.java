package com.codeschool.candycoded;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.javaparser.ast.CompilationUnit;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest(value = {AppCompatActivity.class, Intent.class}, fullyQualifiedNames = {"com.codeschool.candycoded.*"})
@RunWith(PowerMockRunner.class)
public class aj_intent_test {


    /* Will hold the AST of the file. */
    private static CompilationUnit ast;

    /* Used to track whether task milestones are satisfied. */
    private static boolean called_Intent = false;
    private static boolean called_Intent_correctly = false;
    private static boolean called_startActivity_correctly = false;


    /* Parse the AST and test mocks. */
    @BeforeClass
    public static void setup() throws Exception {

        // Spy on a MainActivity instance.
        MainActivity activity = PowerMockito.spy(new MainActivity());
        // Create a fake Bundle to pass in.
        Bundle bundle = Mockito.mock(Bundle.class);
        // Create a fake ImageView to return from findViewById().
        ImageView imageView = Mockito.mock(ImageView.class);
        // Create a spy GridView to return from findViewById().
        ListView listView = PowerMockito.spy(new ListView(activity));
        // Create a spy Intent to return from new Intent().
        Intent intent = PowerMockito.spy(new Intent(activity, DetailActivity.class));

        // Don't interrupt setup if the mocks fail.
        try {
            // Return a mocked ImageView from the call to findViewById();
            PowerMockito.doReturn(listView).when(activity, "findViewById", R.id.list_view_candy);

            // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
            PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity.class));

            // Return a mocked Intent from the call to its constructor.
            PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent);

            // We expect calling onCreate() to throw an Exception due to our mocking. Ignore it.
            try {
                activity.onCreate(bundle);
            }
            catch (Exception e) {}

            // Get a handle on the click listener so we can call it manually.
            ArgumentCaptor<OnItemClickListener> captor = ArgumentCaptor.forClass(OnItemClickListener.class);
            Mockito.verify(listView).setOnItemClickListener(captor.capture());
            OnItemClickListener oicl = captor.getAllValues().get(0);

            // Call the click listener.
            oicl.onItemClick(listView, listView, 0, 1);

            // Check if new Intent() was called with any arguments.
            try {
                PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withNoArguments();
                called_Intent = true;
            } catch (Throwable e) {
                e.printStackTrace();
            }

            try {
                PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withArguments(Mockito.any(MainActivity.class), Mockito.any(Class.class));
                called_Intent = true;
            } catch (Throwable e) {
                e.printStackTrace();
            }

            // Check if new Intent() was called with the correct arguments.
            PowerMockito.verifyNew(Intent.class, Mockito.atLeastOnce()).withArguments(Mockito.eq(activity), Mockito.eq(DetailActivity.class));
            called_Intent_correctly = true;

            // Check if startActivity() was called with the correct argument.
            Mockito.verify(activity).startActivity(Mockito.eq(intent));
            called_startActivity_correctly = true;
        }
        catch (Throwable e) {}
    }

  /* Task 1 */

    /* Check that Intent() was called with any arguments. */
    @Test
    public void t1_2_no_constructor_call() {
        assertTrue(called_Intent);
    }

    /* Check that Intent() was called with the correct arguments. */
    @Test
    public void t1_3_wrong_constructor_call() {
        assertTrue(called_Intent_correctly);
    }

  /* Task 2 */

    /* Check that startActivity() was called with the correct argument. */
    @Test
    public void t2_1_no_startActivity_call() {
        assertTrue(called_startActivity_correctly);
    }
}
