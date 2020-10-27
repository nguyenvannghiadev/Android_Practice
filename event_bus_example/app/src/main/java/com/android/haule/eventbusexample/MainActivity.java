package com.android.haule.eventbusexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private Button btnClickMe;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClickMe = findViewById(R.id.btn_click_me);
        textView = findViewById(R.id.tv_activity);
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new UserEvent(new User("Hau", "334")));
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("User", new User("Hau", "334"));
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }

//    @SuppressLint("SetTextI18n")
//    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
//    public void onUser(ReturnEvent event){
//        textView.setText("Username: " + event.getUser().get + " Password: " + event.getUser().password);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
}
