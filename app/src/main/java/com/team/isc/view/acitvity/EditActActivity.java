package com.team.isc.view.acitvity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.widget.MyRadioGroup;
import com.team.isc.util.SPUtil;
import com.team.isc.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActActivity extends AppCompatActivity implements MyRadioGroup.OnCheckedChangeListener {

    private EditText editact_atitle,editact_aplace,editact_amaxnumber,editact_acost,editact_atext,editact_ahost;
    private TextView editact_adatetime;
    private MyRadioGroup editact_radiogroup;
    private RadioButton radioButton1_volunteer,radioButton2_competition,radioButton3_reporting,radioButton4_entertainment,radioButton5_sports,radioButton6_other;
    String atype=null,adatetime;
    Handler handler;
    TimePickerView pvTime ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_act);
        bind();
        init();
        editact_radiogroup.setOnCheckedChangeListener(this);
    }

    void bind(){
        editact_atitle=findViewById(R.id.editact_atitle);
        editact_adatetime=findViewById(R.id.editact_adatetime);
        editact_aplace=findViewById(R.id.editact_aplace);
        editact_amaxnumber=findViewById(R.id.editact_amaxnumber);
        editact_acost=findViewById(R.id.editact_acost);
        editact_atext=findViewById(R.id.editact_atext);
        editact_ahost=findViewById(R.id.editact_ahost);

        editact_radiogroup=findViewById(R.id.editact_radiogroup);
        radioButton1_volunteer=findViewById(R.id.radioButton1_volunteer);
        radioButton2_competition=findViewById(R.id.radioButton2_competition);
        radioButton3_reporting=findViewById(R.id.radioButton3_reporting);
        radioButton4_entertainment=findViewById(R.id.radioButton4_entertainment);
        radioButton5_sports=findViewById(R.id.radioButton5_sports);
        radioButton6_other=findViewById(R.id.radioButton6_other);
    }

    void init(){
        //时间选择器
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.SECOND, 0);
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR),selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030,1,1);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                adatetime = sdf.format(date.getTime());
                editact_adatetime.setText(adatetime);
            }
        })
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                .build();


    }
    public void selectdatetime(View view) {
        hideInput();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public void editActback(View view) {
        finish();
    }

    public void addAct(View view) {
        String atitle=editact_atitle.getText().toString();
//        String adatetime=editact_adatetime.getText().toString();
        String aplace=editact_aplace.getText().toString();
        String amaxnumber=editact_amaxnumber.getText().toString();
        String acost=editact_acost.getText().toString();
        String atext=editact_atext.getText().toString();
        String ahost=editact_ahost.getText().toString();

        if(atitle.isEmpty()||adatetime.isEmpty()||aplace.isEmpty()||amaxnumber.isEmpty()||acost.isEmpty()||atext.isEmpty()||atype.isEmpty()||ahost.isEmpty()){
            Toast.makeText(EditActActivity.this,"请完善信息后再提交",Toast.LENGTH_LONG).show();
            Log.d("ISC","adatetime=="+adatetime);
        }else {
            JSONObject obj=new JSONObject();
            try {
                obj.put("uno", SPUtil.getInt("uno"));
                obj.put("atitle", URLEncoder.encode(atitle,"utf-8"));
                obj.put("adatetime",URLEncoder.encode(adatetime,"utf-8"));
                obj.put("aplace",URLEncoder.encode(aplace,"utf-8"));
                obj.put("amaxnumber",URLEncoder.encode(amaxnumber,"utf-8"));
                obj.put("acost",URLEncoder.encode(acost,"utf-8"));
                obj.put("atext",URLEncoder.encode(atext,"utf-8"));
                obj.put("atype",URLEncoder.encode(atype,"utf-8"));
                obj.put("ahost",URLEncoder.encode(ahost,"utf-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String objstr=obj.toString();
            sendEditActivity(objstr);
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what== Flag.EDITACTACTIVITY_MSG){
                        Toast.makeText(EditActActivity.this, "活动发送成功!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            };
        }
    }

    private void sendEditActivity(String objstr) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/AddActivityServlet").post(RequestBody.create(mediaType,objstr)).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                if (response.isSuccessful()) {
                    Log.d("isc", "AddActivityServletresponseStr==" + responseStr);
                    if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                        Message message=new Message();
                        message.what= Flag.EDITACTACTIVITY_MSG;
                        handler.sendMessage(message);
                    }else {
                        Looper.prepare();
                        Toast.makeText(EditActActivity.this, "服务器错误!请联系管理员或稍后重试！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            }
        });
    }
    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        int id= group.getCheckedRadioButtonId();
        //添加LinearLayout布局后，单选效果会消失，为了保证单选效果，在每次单击按钮前，清空所有选项
        switch (id) {
            case R.id.radioButton1_volunteer:
                atype = "志愿活动";
                break;
            case R.id.radioButton2_competition:
                atype = "比赛";
                break;
            case R.id.radioButton3_reporting:
                atype = "报告汇演/讲座";
                break;
            case R.id.radioButton4_entertainment:
                atype = "娱乐";
                break;
            case R.id.radioButton5_sports:
                atype = "运动";
                break;
            case R.id.radioButton6_other:
                atype = "其它";
                break;
        }
    }

        void clearAllRadioButton(){
            radioButton1_volunteer.setChecked(false);
            radioButton2_competition.setChecked(false);
            radioButton3_reporting.setChecked(false);
            radioButton4_entertainment.setChecked(false);
            radioButton5_sports.setChecked(false);
            radioButton6_other.setChecked(false);
        }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
