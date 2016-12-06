package me.stupideme.shucampus.view;

/**
 * Created by StupidL on 2016/12/6.
 */

public interface LoginView {
    void trySignIn();

    void signInFailed();

    void signInSuccess();

    void trySignUp();

    void signUpFailed();

    void signUpSuccess();
}
