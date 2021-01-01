package com.systemvx.androiddevtest

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.systemvx.androiddevtest.generated.callback.OnClickListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class registerActivity extends Activity  {
    private Button register;
    private EditText id;
    private EditText pwd_1;
    private EditText pwd_2;
    private EditText email;
    private String result;
    private String username;
    private String pwd1;
    private String e_mail;
    private String pwd2;
    private int ResultCode=2;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener((View.OnClickListener) this);
        id = (EditText) findViewById(R.id.input_identity_text);
        pwd_1 = (EditText) findViewById(R.id.password_edit);
        pwd_2 = (EditText) findViewById(R.id.password_edit_1);
        email = (EditText) findViewById(R.id.email_edit);
    }

    @Override
    public void onClick(final View v) {
        new Thread(){public void run() {

            switch (v.getId()) {
                case R.id.register_button:
                    username = id.getText().toString().trim();
                    e_mail = email.getText().toString().trim();
                    pwd1 = pwd_1.getText().toString().trim();
                    pwd2 = pwd_2.getText().toString().trim();
                    if(!pwd1.equals(pwd2)){
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "两次输入密码不一致，请重新输入！", 1).show();
                            }
                        });
                    }else {
                        try {
                            //设置路径
                            String path="url"+username+"&password="+ pwd1+"&email="+e_mail+"";
                            //创建URL对象
                            URL url=new URL(path);
                            //创建一个HttpURLconnection对象
                            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                            //设置请求方法
                            conn.setRequestMethod("POST");
                            //设置请求超时时间
                            conn.setReadTimeout(5000);
                            //conn.setConnectTimeout(5000);
                            //Post方式不能设置缓存，需要手动设置
                            //conn.setUseCaches(false);
                            //准备要发送的数据
                            String data ="id="+ URLEncoder.encode(username,"utf-8")+"&password"+URLEncoder.encode(pwd1,"utf-8")+"&email"+URLEncoder.encode(e_mail,"utf-8");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//使用的是表单请求类型
                            conn.setRequestProperty("Content-Length", data.length()+"");
                            conn.setDoOutput(true);
                            //连接
                            // conn.connect();
                            //获得返回的状态码
                            conn.getOutputStream().write(data.getBytes());
                            int code=conn.getResponseCode();
                            if(code==200){
                                //获得一个文件的输入流
                                InputStream inputStream= conn.getInputStream();
                                result = StringTools.readStream(inputStream);
                                //更新UI
                                showToast(result);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        };}.start();
    }
    public void showToast(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(), "注册成功", 1).show();
                    Intent intent=new Intent();
                    intent.putExtra("id", username);
                    intent.putExtra("password", pwd1);
                    setResult(ResultCode, intent);
                    finish();
                }
            }
        });
    }
}


