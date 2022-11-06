package com.cindytech.isecure.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cindytech.isecure.MainActivity;
import com.cindytech.isecure.R;
import com.cindytech.isecure.databinding.FragmentHomeBinding;
import com.cindytech.isecure.db.HomeDB;
import com.cindytech.isecure.db.SettingsDB;
import com.cindytech.isecure.model.Model;
import com.cindytech.isecure.ui.settings.SettingsFragment;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeDB homedb;
    DayNightSwitch dayNightSwitch;
    View backgroundView;
    TextView tv_day_night;
    Button btn_arm, btn_disarm, btn_status, btn_enable, btn_disable, btn_time;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Background VIEW
        backgroundView = binding.getRoot();

        //Initialize DB with current context
        homedb = new HomeDB(HomeFragment.this);

        //TEXTVIEW DAY-NIGHT
        tv_day_night = binding.tvDayNight.findViewById(R.id.tv_day_night);
        tv_day_night.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        SettingsFragment sf = new SettingsFragment();



        //BUTTON ARM
        //ARM Button Action
        btn_arm = binding.btnArm.findViewById(R.id.btn_arm);
        btn_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //Get data from DB
                    Model model = model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getArm();
                    //When permission is granted
                    //Create methodLog.d("ARM", "Pippo");
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //Disarm Button Action
        btn_disarm = binding.btnDisarm.findViewById(R.id.btn_disarm);
        btn_disarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getDisarm();
                    //When permission is granted
                    //Create method
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });



        //DAY-NIGHT SWITCH
        dayNightSwitch = binding.dayNightSwitch.findViewById(R.id.day_night_switch);
        dayNightSwitch.setDuration(450);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean isNight) {
                if(isNight)
                {
                    //Toast.makeText(MainActivity.this,"Night mode Selected",Toast.LENGTH_SHORT).show();
                   // root.setAlpha(1f);
                    backgroundView.setBackgroundColor(Color.BLACK);
                    tv_day_night.setTextColor(Color.YELLOW);
                    tv_day_night.setText("NIGHT");
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                            PackageManager.PERMISSION_GRANTED){
                        //When permission is granted
                        //Create method
                        //Get data from DB
                        Model model = getModel();
                        String sPhone = model.getNumber();
                        String command = model.getNight();
                        sendMessage(sPhone, command);
                    } else {
                        //When permission is not granted
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                    }
                }
                else {
                    //Toast.makeText(MainActivity.this,"Day mode Selected",Toast.LENGTH_SHORT).show();
                    //root.setAlpha(0f);
                    backgroundView.setBackgroundColor(Color.WHITE);
                    tv_day_night.setTextColor(Color.DKGRAY);
                    tv_day_night.setText("DAY");
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) ==
                            PackageManager.PERMISSION_GRANTED){
                        //When permission is granted
                        //Create method
                        //Get data from DB
                        Model model = getModel();
                        String sPhone = model.getNumber();
                        String command = model.getDay();
                        sendMessage(sPhone, command);
                    } else {
                        //When permission is not granted
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                    }
                }
            }
        });


        //Status Button Action
        btn_status = binding.btnStatus.findViewById(R.id.btn_status);
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getStatus();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //ENABLE Button Action
        btn_enable = binding.btnEnable.findViewById(R.id.btn_enable);
        btn_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getEnable();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //DISABLE Button Action
        btn_disable = binding.btnDisable.findViewById(R.id.btn_disable);
        btn_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getDisable();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //TIME Button Action
        btn_time = binding.btnTime.findViewById(R.id.btn_time);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getTime();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        return root;
    }


    //Get data from DB
    public Model getModel(){
        //get data from DB
        Model model = new Model();
        Cursor res = homedb.uploadSettings();
        while (res.moveToNext()) {

            model.setNumber(res.getString(1));
            model.setArm(res.getString(2));
            model.setDisarm(res.getString(3));
            model.setNight(res.getString(4));
            model.setDay(res.getString(5));
            model.setStatus(res.getString(6));
            model.setEnable(res.getString(7));
            model.setDisable(res.getString(8));
            model.setTime(res.getString(9));
            Log.d("ARM", "time from get Model"+model.getTime());
            model.setPassword(res.getString(10));
        }
        return model;
    }


    private void sendMessage(String sPhone, String command) {
        //check condition
        if(!sPhone.equals("") && !command.equals("")){
            //When both edit text value not equal to blank
            //Initialize sms manager
            SmsManager smsManager = SmsManager.getDefault();
            //Send text message
            smsManager.sendTextMessage(sPhone, null, command, null, null);
            //Display toast
            Toast.makeText(getActivity().getApplicationContext(), "SMS sent succesfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Enter value first!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //Get data from DB
            Model model = getModel();
            String sPhone = model.getNumber();
            String command = model.getDisarm();
            //When perission is granted
            sendMessage(sPhone, command);
        } else {
            //When permission is denied
            //Display toast
            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Permission Denied ....!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}