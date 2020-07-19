package com.example.administrator.coolweather;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        // 初始化控件
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        // 设置点击事件监听器
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();//.trim（）删除两边的空格
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {//TextUtils.isEmpty（）输入框是空值或者你就敲了几下空格键该方法都会返回true
                    ArrayList<User> data = mDBOpenHelper.getAllData();//data为获取的user表内的user信息
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {//遍历比较
                        User user = data.get(i);//获取data里的第i个user信息
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {//将信息与输入的信息进行对比
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
