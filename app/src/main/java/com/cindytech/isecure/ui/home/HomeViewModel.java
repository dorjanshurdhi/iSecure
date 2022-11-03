package com.cindytech.isecure.ui.home;

import static android.app.PendingIntent.getActivity;
import static com.cindytech.isecure.R.id.navigation_home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cindytech.isecure.MainActivity;
import com.cindytech.isecure.R;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        //DayNightSwitch dayNightSwitch = getActivity().findViewById(R.id.day_night_switch).setOnClickListener(this);
    }


    public LiveData<String> getText() {
        return mText;
   }
}