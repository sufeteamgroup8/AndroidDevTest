package com.systemvx.androiddevtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.systemvx.androiddevtest.utils.DialogUtil;
import com.systemvx.androiddevtest.utils.HttpUtil;
import java.util.HashMap;

public class registerActivity extends Activity  {
    private String username;
    private String pwd1;
    private String e_mail;
    private String pwd2;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.register_button);
        EditText id = findViewById(R.id.input_identity_text);
        username= id.getText().toString();
        EditText pwd_1 = findViewById(R.id.password_edit);
        pwd1= pwd_1.toString();
        EditText pwd_2 = findViewById(R.id.password_edit_1);
        pwd2= pwd_2.toString();
        EditText email = findViewById(R.id.email_edit);
        e_mail= email.getText().toString();

        register.setOnClickListener(v -> {

            if(validate())
            {
                try {
                    addUser(username,pwd1,e_mail);

                } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

   );

    }

    private void addUser(String id, String pwd1, String e_mail) {
        //Map封装
        HashMap<String, String> map = new HashMap<>();
        map.put("User_id", id);
        map.put("User_key", pwd1);
        map.put("User_email",e_mail);
        String url = HttpUtil.BASE_URL + "register";
        new HttpUtil().postRequest(url, map);
    }

    private boolean validate() {
        if (!pwd1.equals(pwd2)) {
            DialogUtil.showDialog(this, "密码不一致", false);
            return false;
        }
        if (username.equals("")) {
            DialogUtil.showDialog(this, "请填写用户名", false);
            return false;
        }
        if (pwd1.equals("") & pwd2.equals("")) {
            DialogUtil.showDialog(this, "请填写密码", false);
            return false;
        }
        if (e_mail.equals("")) {
            DialogUtil.showDialog(this, "请填写邮箱", false);
            return false;
        }
        return true;

    }}


