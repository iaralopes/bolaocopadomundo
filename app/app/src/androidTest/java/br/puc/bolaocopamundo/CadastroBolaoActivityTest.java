package br.puc.bolaocopamundo;


import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.puc.bolaocopamundo.activity.CadastroBolaoActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CadastroBolaoActivityTest {

    @Rule
    public final ActivityTestRule<CadastroBolaoActivity> cadastro = new ActivityTestRule<>(CadastroBolaoActivity.class);

    @Test
    public void cadastroBolao() {
        onView(withId(R.id.nomeBolao)).perform(typeText("Bolao do 11"), closeSoftKeyboard());
        onView(withId(R.id.numeroGanhadores)).perform(typeText("oi"), closeSoftKeyboard());
        onView(withId(R.id.button_cadastrar)).perform(scrollTo()).perform(click());
    }
}
