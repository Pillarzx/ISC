package com.team.isc.view.userinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.common.MainApplication;
import com.team.isc.presenter.UserInfo;
import com.team.isc.util.SPUtil;

public class SetRealnameActivity extends AppCompatActivity {

    private EditText etrealname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_realname);
        etrealname=findViewById(R.id.setrealname);
    }
    public void back(View v){
        finish();
    }
    public void setRealname(View view){
        String realname=etrealname.getText().toString();
        UserInfo userInfo=new UserInfo();
        userInfo.setRealname(SPUtil.getString("username"),realname);
        if(realname.equals(SPUtil.getString("realname"))){
            Toast.makeText(SetRealnameActivity.this,"已保存",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            etrealname.setText("");
            Toast.makeText(SetRealnameActivity.this,"请重试",Toast.LENGTH_SHORT).show();
        }
    }
}
