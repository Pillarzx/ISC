package com.team.isc.view.acitvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team.isc.R;
import com.team.isc.model.Activity;

public class ActinfoActivity extends AppCompatActivity {

    TextView acttitle,acttext,acthost,actdatetime,acttype,actplace,actmaxnumber,actcurrentnumber,actcost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actinfo);
        bind();
        init();
    }

    void bind(){
        acttitle=findViewById(R.id.acttitle);
        acttext=findViewById(R.id.acttext);
        acthost=findViewById(R.id.acthost);
        actdatetime=findViewById(R.id.actdatetime);
        acttype=findViewById(R.id.acttype);
        actplace=findViewById(R.id.actplace);
        actmaxnumber=findViewById(R.id.actmaxnumber);
        actcost=findViewById(R.id.actcost);
        actcurrentnumber=findViewById(R.id.actcurrentnumber);
    }
    void init(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Activity activity=(Activity) bundle.getSerializable("activity");
        Log.d("ISC","init()=="+activity.toString());
        acttitle.setText(activity.getAtitle());
        acttext.setText(activity.getAtext());
        acthost.setText(activity.getAhost());
        actdatetime.setText(activity.getAdatetime());
        Log.d("ISC","Actinfo actdatetime="+activity.getAdatetime());
        acttype.setText(activity.getAtype());
        Log.d("ISC","Actinfo acttype="+activity.getAtype());
        actplace.setText(activity.getAplace());
        actmaxnumber.setText(activity.getAmaxnumber()+"");
        actcost.setText(activity.getAcost()+"");
        actcurrentnumber.setText("1");//当前报名人数
    }
    public void back(View view){
        finish();
    }
    public void join(View view){

    }
}
