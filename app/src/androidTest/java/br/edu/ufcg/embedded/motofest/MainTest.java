package br.edu.ufcg.embedded.motofest;

import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.ActivityInstrumentationTestCase2;

import br.edu.ufcg.embedded.motofest.activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.anything;

import android.support.test.uiautomator.UiDevice;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {

    UiDevice mDevice;

    public MainTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    public void testAvaliar() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito
        onView(withText(R.string.action_rate)).perform(click());//clica no menu avaliar

        sleep(3000);
        mDevice.pressBack();
        sleep(1000);
        mDevice.pressBack();
        sleep(5000);
    }

    public void testCompartilhar() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito
        onView(withText(R.string.action_share)).perform(click());//clica no menu compartilhar

        sleep(3000);
        mDevice.pressBack();
        sleep(1000);
        mDevice.pressBack();
        sleep(1000);
    }

    public void testConfiguracoes() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito
        onView(withText(R.string.action_settings)).perform(click());//clica no menu configuracoes

        onView(withText(R.string.timeline)).check(matches(isDisplayed()));

        onView(withId(R.id.switchTimeline)).perform(click());
        onView(withId(R.id.switchTimeline)).perform(click());

        sleep(500);
        onView(withId(R.id.switchTimelineSilenciosa)).perform(click());//

        onView(withId(R.id.switchSplashLauncher)).perform(click());//
        onView(withId(R.id.switchSplashLauncher)).perform(click());//

        onView(withId(R.id.switchSplashSound)).perform(click());//Som do inicio do app

        onView(withId(R.id.switchTheme)).perform(click());//muda a cor da barra supeior para vermelho

        sleep(500);
        mDevice.pressBack();
        sleep(1000);
    }

    public void testVoltarActivityMain() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        //CLICK NO TIME LINE
        sleep(500);

        onView(withText(R.string.to_motofest)).perform(click());
    }

    public void testTimeLine() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        //CLICK NO TIME LINE
        sleep(500);
        onView(withText(R.string.timeline)).perform(click());
        onView(withId(R.id.boxMessage)).perform(click());

        sleep(500);
        mDevice.pressBack();
        mDevice.pressBack();

        sleep(500);
        onView(withId(R.id.boxMessage)).perform(click());
        onView(withId(R.id.layoutBoxMessage_et)).perform(typeText(""));
        onView(withId(R.id.bt_send)).perform(click());

        onView(withId(R.id.layoutBoxMessage_et)).perform(typeText("ola!"));//escreve 'ola' no box message[no adv não é possivel]
        onView(withId(R.id.bt_send)).perform(click());
    }

    public void testMaps() throws InterruptedException, UiObjectNotFoundException {
        openDrawer(R.id.drawer_layout);//menu da esquerda

        sleep(500);
        onView(withText(R.string.title_map)).perform(click());

        sleep(500);
        onView(withId(R.id.map_container)).check(matches(isDisplayed()));//verifica se o mapa é mostrado
        sleep(500);
        onView(withId(R.id.action_b)).perform(click());//clica o notão "+" existente no layout mapa
        onView(withId(R.id.action_b)).perform(click());//clica no botão "minha localização"
        onView(withId(R.id.map_container)).check(matches(isDisplayed()));//verifica se o mapa é mostrado
        onView(withId(R.id.action_c)).perform(click());//clica no botão "motofest"
        onView(withId(R.id.map_container)).check(matches(isDisplayed()));//verifica se o mapa é mostrado
        onView(withId(R.id.action_location)).perform(click());//clica no botão de marcadores
        onView(withId(R.id.bt_cancel)).perform(click());//clica no botão para cancelar
        onView(withId(R.id.action_location)).perform(click());//clica no botão de marcadores
        onView(withText(R.string.booth)).perform(click());//seleciona o checkbox barracão
        onView(withText(R.string.wc)).perform(click());//seleciona o checkbox banheiro
        onView(withText(R.string.security)).perform(click());//seleciona o checkbox policia

        sleep(500);
        onView(withId(R.id.bt_ok)).perform(click());
        sleep(1000);

        onView(withId(R.id.action_location)).perform(click());//clica no botão de marcadores
        onView(withText(R.string.booth)).perform(click());//seleciona o checkbox barracão
        onView(withText(R.string.wc)).perform(click());//seleciona o checkbox banheiro
        onView(withText(R.string.security)).perform(click());//seleciona o checkbox policia

        sleep(500);
        onView(withId(R.id.bt_ok)).perform(click());
    }

    public void testPartnes() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        //CLICK NOS PARCEIROS
        sleep(500);
        onView(withText(R.string.partners)).perform(click());

        sleep(500);
        onData(anything()).inAdapterView(withId(R.id.list_partners)).atPosition(1).onChildView(withId(R.id.parceiros_down_image)).perform(click());
        sleep(500);
        onView(withId(R.id.x_bt)).perform(click());

        sleep(500);
        onData(anything()).inAdapterView(withId(R.id.list_partners)).atPosition(3).onChildView(withId(R.id.parceiros_down_image)).perform(click());
        sleep(500);
        onView(withId(R.id.x_bt)).perform(click());

        onView(withId(R.id.fragments_partner)).perform(swipeUp());//rola a tela de parceiros pra cima

        sleep(500);
        onData(anything()).inAdapterView(withId(R.id.list_partners)).atPosition(24).onChildView(withId(R.id.parceiros_down_image)).perform(click());
        sleep(500);
        onView(withId(R.id.x_bt)).perform(click());

        onView(withId(R.id.fragments_partner)).perform(swipeDown());//rola a tela de parceiros pra baixo
    }

    public void testContactUs() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        sleep(500);

        onView(withText(R.string.contact_us)).perform(click());
        sleep(500);

        onView(withId(R.id.avaliation_text)).perform(typeText(""));
        sleep(500);
        onView(withId(R.id.avaliation_bt)).perform(click());

        onView(withId(R.id.avaliation_text)).perform(typeText("Teste Espresso"));
        sleep(500);
        onView(withId(R.id.avaliation_bt)).perform(click());

    }

    public void testHelp() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        //CLICK NO HELP
        sleep(500);

        onView(withId(R.id.drawer_layout)).perform(swipeUp());
        onView(withText(R.string.help)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.ajuda_list)).atPosition(0).perform(click());
        sleep(1000);
        onView(withId(R.id.textHelp)).check(matches(withText(R.string.programacao_descricao)));
        onData(anything()).inAdapterView(withId(R.id.ajuda_list)).atPosition(0).perform(click());

        onView(withId(R.id.ajuda_list)).perform(swipeUp());
        onData(anything()).inAdapterView(withId(R.id.ajuda_list)).atPosition(3).perform(click());
        sleep(1000);
        onView(withId(R.id.textHelp)).check(matches(withText(R.string.marker_decricao)));
        onData(anything()).inAdapterView(withId(R.id.ajuda_list)).atPosition(3).perform(click());

        onView(withId(R.id.ajuda_list)).perform(swipeUp());
        onData(anything()).inAdapterView(withId(R.id.ajuda_list)).atPosition(5).perform(click());
        sleep(1000);
        onView(withId(R.id.textHelp)).check(matches(withText(R.string.main_camera_descricao)));

    }

    public void testSobre() throws InterruptedException {
        openDrawer(R.id.drawer_layout);//menu da esquerda
        sleep(500);
        onView(withId(R.id.drawer_layout)).perform(swipeUp());
        onView(withText(R.string.about)).perform(click());

        onView(withId(R.id.textView)).check(matches(isDisplayed()));
    }

    public void testBotoesPrincipais() throws InterruptedException {
        sleep(1000);
        //O evento
        onView(withText(R.string.the_event)).perform(click());
        sleep(1000);
        onView(withId(R.id.drawer_layout)).perform(swipeUp());
        sleep(500);
        onView(withId(R.id.drawer_layout)).perform(swipeUp());
        sleep(500);
        onView(withId(R.id.webView2)).check(matches(isDisplayed()));//verifica se a segunda metade do texto é mostrada

        onView(withId(R.id.webView2)).perform(swipeDown());
        sleep(500);
        onView(withId(R.id.webView1)).perform(swipeDown());
        sleep(500);

        //Programação eventos_list
        onView(withText(R.string.programacao)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.eventos_list)).atPosition(2).onChildView(withId(R.id.video_link)).perform(click());//clica no link you tube da banda dammage
        sleep(3000);
        mDevice.pressBack();

        onView(withId(R.id.fragments_programation)).perform(swipeUp());//rola a tela do aplicativo pra cima
        sleep(500);

        onData(anything()).inAdapterView(withId(R.id.eventos_list)).atPosition(24).onChildView(withId(R.id.video_link)).perform(click());//clica no link you tube do cover raul seixas
        sleep(3000);
        mDevice.pressBack();

        onView(withId(R.id.layoutCount)).perform(swipeDown());//rola a tela do  aplicativo pra baixo
        sleep(500);

        //Apoio
        onView(withText(R.string.apoio)).perform(click());
        sleep(500);
        onView(withId(R.id.imageView1)).check(matches(isDisplayed()));

        onView(withId(R.id.scrollView2)).perform(swipeUp());//rola a tela do aplicativo pra cima
        sleep(500);
        onView(withId(R.id.imageView7)).check(matches(isDisplayed()));
        onView(withId(R.id.scrollView2)).perform(swipeDown());//rola a tela do  aplicativo pra baixo
        sleep(500);

    }

    public void testz() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//clica no menu superior direito
        onView(withText(R.string.action_logout)).perform(click());//clica no menu configuracoes
        sleep(3000);
    }
}