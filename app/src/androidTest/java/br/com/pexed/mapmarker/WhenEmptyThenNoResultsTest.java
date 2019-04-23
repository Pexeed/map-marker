package br.com.pexed.mapmarker;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.pexed.mapmarker.view.activity.ListLocationsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WhenEmptyThenNoResultsTest {

    private String searchString;

    @Rule
    public ActivityTestRule<ListLocationsActivity> mActivityRule = new ActivityTestRule<>(
            ListLocationsActivity.class);

    @Before
    public void init(){
        searchString = "WontFindMe";
    }

    @Test
    public void whenEmpty_thenShowNoResults(){
        //Type location  and press search
        onView(withId(R.id.et_search_location)).perform(typeText(searchString), closeSoftKeyboard());
        onView(withId(R.id.bt_search)).perform(click());
        //Check if No Results appears
        onView(withId(R.id.tv_empty)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
