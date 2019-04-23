package br.com.pexed.mapmarker;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WhenOneItemThenOneRowTest {
    private String searchString;

    @Rule
    public ActivityTestRule<ListLocationsActivity> mActivityRule = new ActivityTestRule<>(
            ListLocationsActivity.class);

    @Before
    public void init(){
        searchString = "Detroit";
    }

    @Test
    public void whenOneItem_thenOneRow() throws Exception{
        //Type location that has two or more itens and press search
        onView(withId(R.id.et_search_location)).perform(typeText(searchString), closeSoftKeyboard());
        onView(withId(R.id.bt_search)).perform(click());
        //Waiting for request
        Thread.sleep(1500);
        //Check result
        int size = mActivityRule.getActivity().getVm().getLocationValue().size();
        assert size == 1;
    }

}
