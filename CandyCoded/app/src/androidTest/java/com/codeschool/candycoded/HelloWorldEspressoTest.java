package com.codeschool.candycoded;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class HelloWorldEspressoTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(
            DetailActivity.class);

    @Rule
    public IntentsTestRule<DetailActivity> mActivityIntentRule = new IntentsTestRule<>(
                DetailActivity.class);

//    @Before
//    public void stubAllExternalIntents() {
//        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
//        // every test run. In this case all external Intents will be blocked.
//        intending(not(isInternal())).respondWith(
//                new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
//     }

    @Test
    public void checkButtonExists() {
        // Type text and then press the button.
        //onView(withId(R.id.button_share));//.check(matches(withText("Share")));
        //onView(withId(R.id.button_share)).perform(click());
        //intended(allOf(hasAction(Intent.ACTION_SEND),hasExtra(Intent.EXTRA_TEXT, "BLAH")));

    }


}

