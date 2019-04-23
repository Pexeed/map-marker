package br.com.pexed.mapmarker;

import android.support.test.filters.MediumTest;
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
import static br.com.pexed.mapmarker.R.string.display_all;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WhenTwoItensThenDisplayAllTest {

    private String searchString;

    @Rule
    public ActivityTestRule<ListLocationsActivity> mActivityRule = new ActivityTestRule<>(
            ListLocationsActivity.class);

    @Before
    public void init(){
        searchString = "Springfield";
    }

    @Test
    public void whenTwoOrMoreItens_thenDisplayAllItem() throws Exception{
        //Type location that has two or more itens and press search
        onView(withId(R.id.et_search_location)).perform(typeText(searchString), closeSoftKeyboard());
        onView(withId(R.id.bt_search)).perform(click());
        //Waiting for request
        Thread.sleep(1500);
        //Check result
        int position = mActivityRule.getActivity().getVm().getLocationValue().size();
        onData(anything()).inAdapterView(withId(R.id.lv_location_list)).atPosition(position).
                onChildView(withId(R.id.item_address)).
                check(matches(withText(display_all)));
    }

}
