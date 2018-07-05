package br.puc.bolaocopamundo;


import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.puc.bolaocopamundo.activity.LoginActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> login = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginIntoApp() {
        onView(withId(R.id.et_username)).perform(typeText("tis"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("tis"), closeSoftKeyboard());
        onView(withId(R.id.bt_login)).perform(click());
        onView(withText("Login realizado com sucesso.")).check(matches(isDisplayed()));
    }
}
