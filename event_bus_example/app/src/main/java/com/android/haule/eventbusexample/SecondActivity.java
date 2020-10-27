package com.android.haule.eventbusexample;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondActivity extends AppCompatActivity {
    public TextView textView;
    public User user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.tv_text);
//        if(getIntent().getSerializableExtra("User") != null){
//            user = (User) getIntent().getSerializableExtra("User");
//        }
//        if(user!=null){
//            textView.setText("Username: " + user.getUsername() + " Password: " + user.getPassword());
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void OnUser(UserEvent event){
        textView.setText("Username: " + event.getUser().getUsername() + " Password: " + event.getUser().getPassword());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
