package br.puc.bolaocopamundo;


import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.puc.bolaocopamundo.activity.CadastroUsuarioActivity;
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
public class CadastroActivityTest {

    @Rule
    public final ActivityTestRule<CadastroUsuarioActivity> cadastro = new ActivityTestRule<>(CadastroUsuarioActivity.class);

    @Test
    public void cadastroApp() {
        onView(withId(R.id.nomePessoa)).perform(typeText("Daniel"), closeSoftKeyboard());
        onView(withId(R.id.emailPessoa)).perform(typeText("danielmop@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.usuarioPessoa)).perform(typeText("daniel"), closeSoftKeyboard());
        onView(withId(R.id.senhaPessoa)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.button_cadastrarPessoa)).perform(click());
        onView(withText("Sucesso ao cadastrar.")).check(matches(isDisplayed()));
    }
}