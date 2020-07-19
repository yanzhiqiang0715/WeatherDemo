package com.example.administrator.coolweather;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private ImageView mIvRegisteractivityBack;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView(){
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword = findViewById(R.id.et_registeractivity_password);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword.getText().toString().trim();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    mDBOpenHelper.add(username, password);//将用户名和密码加入到数据库的表内中
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

