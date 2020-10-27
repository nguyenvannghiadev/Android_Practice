package com.android.haule.eventbusexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

/**
 * Created by Hau Le on 2018-10-18.
 */
public class FirstFragment extends Fragment {
    private TextView textView;
    private Button btn;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        textView = view.findViewById(R.id.tv);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
//                user = new User("ABCDEF", "56463754");
//                ((SecondActivity) Objects.requireNonNull(getActivity())).textView
//                        .setText("Username: " + user.getUsername() + " Password: " + user.getPassword());
//                textView.setText("Username: " + user.getUsername() + " Password: " + user.getPassword());
                EventBus.getDefault().postSticky(new UserEvent(new User("ABCD", "74567845")));
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        //get data from second activity
        user = ((SecondActivity) Objects.requireNonNull(getActivity())).user;
        if(user != null){
            textView.setText("Username: " + user.getUsername() + " Password: " + user.getPassword());
        }
    }

        @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onUser(UserEvent event){
        textView.setText("Username: " + event.getUser().getUsername() + " Password: " + event.getUser().getPassword());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
