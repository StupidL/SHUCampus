package me.stupideme.shucampus.presenter;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.stupideme.shucampus.model.LoginModel;
import me.stupideme.shucampus.view.LoginView;

/**
 * Created by StupidL on 2016/12/6.
 */

public class LoginPresenter {

    private static LoginPresenter INSTANCE;
    private LoginView mView;
    private LoginModel mModel;

    private LoginPresenter(LoginView view) {
        mView = view;
        mModel = LoginModel.getInstance();
    }

    public static LoginPresenter getInstance(LoginView view) {
        if (INSTANCE == null) {
            synchronized (LoginPresenter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginPresenter(view);
                }
            }
        }
        return INSTANCE;
    }

    public void signIn(String name, String pwd) {
        mView.trySignIn();
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(pwd);
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    mView.signInSuccess();
                } else {
                    mView.signInFailed();
                }
            }
        });
    }

    public void signUp(final String name, final String pwd, String rePwd) {
        mView.trySignUp();
        if (pwd.equals(rePwd)) {
            BmobUser user = new BmobUser();
            user.setUsername(name);
            user.setPassword(pwd);
            user.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        mModel.saveUserInfo(name, pwd);
                        mView.signUpSuccess();
                    } else {
                        mView.signUpFailed();
                    }
                }
            });

        } else {
            mView.signUpFailed();
        }
    }

    public void autoSignIn() {
        String[] info = mModel.getUserInfo().split(",");
        String name = info[0];
        String pwd = info[1];
        signIn(name, pwd);
    }

}
