package br.edu.ufcg.embedded.motofest;

import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase2;

import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

/**
 * Created by treinamentoasus on 06/10/15.
 */
public class ActivitySplashScreenTest extends ActivityInstrumentationTestCase2<SplashScreenActivity> {

    UiDevice mDevice;

    public ActivitySplashScreenTest() {
        super(SplashScreenActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    public void testEntrarSemLogar() throws InterruptedException {
        sleep(3000);
        mDevice.pressBack();

        sleep(3000);
        onView(withId(R.id.continue_app)).perform(click());
        sleep(1000);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito

        mDevice.pressBack();
        openDrawer(R.id.drawer_layout);//menu da esquerda
        //CLICK NO TIME LINE
        sleep(500);
        onView(withText(R.string.timeline)).perform(click());

        sleep(1000);

        onView(withId(R.id.btSignInDefault)).perform(click());
        sleep(2000);
        mDevice.click(360, 570);//clica no primeiro email, para logar-se

        sleep(1000);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito
    }

    public void testDesativaAtivaSom() throws InterruptedException {
        sleep(2000);
        mDevice.pressBack();
        sleep(1000);
        onView(withId(R.id.checkBox_sound)).perform(click());
        sleep(1000);
        onView(withId(R.id.checkBox_sound)).perform(click());
        sleep(1000);
        mDevice.pressBack();
        sleep(1000);

    }
    //usado para verificar se apos logar o app vai diretamente para a activity inicial
    //public void testzEntrarAposLogar() throws InterruptedException {
    //    sleep(5000);
    //    onView(withText(R.string.programacao)).check(matches(isDisplayed()));
    //    sleep(3000);
    //}
}
