package com.team.isc.view.acitvity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.presenter.okhttp.PostRequest;

public class RegisterActivity extends Activity {

    private EditText username_register,password_register;
    private Button sign_up_button;

    private void bindView(){
        username_register=findViewById(R.id.username_register);
        password_register=findViewById(R.id.password_register);
        sign_up_button=findViewById(R.id.sign_up_button);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindView();
    }

    public void back(View v){
        finish();
    }

    public void signUp(View v){
        String username=username_register.getText().toString();
        String password=password_register.getText().toString();

        if(username.contains(";")||username.contains("*")||username.contains(" ")||password.contains(";")||password.contains("*")||password.contains(" ")){
            username_register.setText("");
            password_register.setText("");
            Toast.makeText(RegisterActivity.this,"不得含有非法字符“；”、“*”和空格",Toast.LENGTH_SHORT).show();
        }else {
            PostRequest postRequest=new PostRequest();
            postRequest.postRegisterParameter(RegisterActivity.this,username,password);
        }
    }

}
