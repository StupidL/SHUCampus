package me.stupideme.shucampus.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.presenter.LoginPresenter;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter mPresenter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mPresenter = LoginPresenter.getInstance(this);

        Intent intent = getIntent();
        boolean auto = false;
        if (intent != null) {
            String where = intent.getStringExtra("WhereFrom");
            if (where.equals("MainActivity"))
                auto = true;
        }
        if (!auto)
            mPresenter.autoSignIn();

        MaterialLoginView view = (MaterialLoginView) findViewById(R.id.material_log_in);
        view.setListener(new MaterialLoginViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                String name = registerUser.getEditText().getText().toString();
                String pass = registerPass.getEditText().getText().toString();
                String repass = registerPassRep.getEditText().getText().toString();
                mPresenter.signUp(name, pass, repass);
            }

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String user = loginUser.getEditText().getText().toString();
                String pwd = loginPass.getEditText().getText().toString();
                mPresenter.signIn(user, pwd);
            }
        });

        mDialog = new ProgressDialog(this);

    }


    @Override
    public void trySignIn() {
        mDialog.setMessage("Trying... Please wait.");
        mDialog.show();
    }

    @Override
    public void signInFailed() {
        mDialog.dismiss();
        showToast("Sign in failed!");
    }

    @Override
    public void signInSuccess() {
        mDialog.dismiss();
        navigateToMainPage();
    }

    @Override
    public void trySignUp() {
        mDialog.setMessage("Trying... Please wait.");
        mDialog.show();
    }

    @Override
    public void signUpFailed() {
        mDialog.dismiss();
        showToast("Sign up failed!");
    }

    @Override
    public void signUpSuccess() {
        mDialog.dismiss();
        navigateToMainPage();
    }

    private void navigateToMainPage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
