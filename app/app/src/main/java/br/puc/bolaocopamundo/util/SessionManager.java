package br.puc.bolaocopamundo.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;

import java.util.HashMap;

import br.puc.bolaocopamundo.activity.LoginActivity;
import br.puc.bolaocopamundo.entity.Pessoa;

public class SessionManager {

    /*
        Attributes
     */

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    // Constantes
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_EMAIL = "email";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /*
        Methods
     */

    public void createLoginSession(String id, String name, String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NOME, name);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        LoginManager.getInstance().logOut();

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /*
        Getters
     */

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    public Long getId(){
        return Long.parseLong(pref.getString(KEY_ID, null));
    }

    public String getNome(){
        return pref.getString(KEY_NOME, null);
    }

    public String getEmail(){
        return pref.getString(KEY_EMAIL, null);
    }

}