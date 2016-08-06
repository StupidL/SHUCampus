package me.stupideme.shucampus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.MyUser;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class LoginActivityMaterial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_login_material);

        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.material_log_in);
        login.setListener(new MaterialLoginViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                BmobUser user = new BmobUser();
                String name = registerUser.getEditText().getText().toString();
                String pass = registerPass.getEditText().getText().toString();
                String repass = registerPassRep.getEditText().getText().toString();

                if (!name.isEmpty() && pass.equals(repass) && !pass.isEmpty()) {

                    user.setUsername(name);
                    user.setPassword(pass);
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Intent intent = new Intent(LoginActivityMaterial.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivityMaterial.this.finish();
                            } else {
                                Toast.makeText(LoginActivityMaterial.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("UserName", name);
                    editor.putString("Password", pass);
                    editor.apply();

                } else {

                    if (!pass.equals(repass)) {
                        Toast.makeText(LoginActivityMaterial.this, "Password Error", Toast.LENGTH_SHORT).show();
                    }
                    if (name.isEmpty()) {
                        Toast.makeText(LoginActivityMaterial.this, "Name Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                final String name = loginUser.getEditText().getText().toString();
                final String pwd = loginPass.getEditText().getText().toString();

                BmobUser user = new MyUser();
                user.setUsername(name);
                user.setPassword(pwd);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser myUser, BmobException e) {
                        if (e == null) {
                            SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserName", name);
                            editor.putString("Password", pwd);
                            editor.putBoolean("hasExit", false);
                            editor.apply();

                            Intent intent = new Intent(LoginActivityMaterial.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivityMaterial.this.finish();
                        } else {
                            Toast.makeText(LoginActivityMaterial.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
