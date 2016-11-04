package br.edu.ufcg.embedded.motofest;

import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase2;

import br.edu.ufcg.embedded.motofest.activity.CameraActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;


public class CameraActivityTest extends ActivityInstrumentationTestCase2<CameraActivity> {

    UiDevice mDevice;

    public CameraActivityTest() {
        super(CameraActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    public void testCameraSwitch() throws InterruptedException {
        onView(withId(R.id.button_switch_camera)).perform(click());
        sleep(1000);
        onView(withId(R.id.button_switch_camera)).perform(click());

        onView(withId(R.id.button_flash)).perform(click());
        onView(withId(R.id.button_capture)).perform(click());

        sleep(3000);
        onView(withId(R.id.iv_shared)).perform(click());
        sleep(2000);

        mDevice.pressBack();
        sleep(2000);
        onView(withId(R.id.iv_save)).perform(click());
        sleep(500);
        onView(withId(R.id.iv_shared)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_close)).perform(click());

    }

    public void testCameraXBotao() {
        onView(withId(R.id.button_close)).perform(click());
    }

    public void tearDown() throws Exception {
        getActivity().finish();
        super.tearDown();
    }
}