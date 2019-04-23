package br.com.pexed.mapmarker;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.pexed.mapmarker.view.activity.ListLocationsActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class WhenSearchThenListLocationTest {

    private String searchString;
    private String resultString;

    @Rule
    public ActivityTestRule<ListLocationsActivity> mActivityRule = new ActivityTestRule<>(
            ListLocationsActivity.class);

    @Before
    public void initSearchLocationString(){
        searchString = "California";
        resultString = "California, USA";
    }

    @Test
    public void whenSearch_thenListLocation() throws Exception{
        //Type location and press search
        onView(withId(R.id.et_search_location)).perform(typeText(searchString), closeSoftKeyboard());
        onView(withId(R.id.bt_search)).perform(click());
        //Waiting for request
        Thread.sleep(1500);
        //Check result
        onData(anything()).inAdapterView(withId(R.id.lv_location_list)).atPosition(0).
                onChildView(withId(R.id.item_address)).
                check(matches(withText(resultString)));
    }

}
