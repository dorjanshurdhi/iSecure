package com.cindytech.isecure.ui.settings;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cindytech.isecure.R;
import com.cindytech.isecure.databinding.FragmentSettingsBinding;
import com.cindytech.isecure.db.SettingsDB;
import com.cindytech.isecure.model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    final private static String ID = "1";

    //references to buttons and other controls on the layout
    Button btn_save;
    EditText et_number, et_arm, et_disarm, et_night, et_day, et_status, et_enable, et_disable, et_time, et_password;
    SettingsDB settingsDB ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        settingsDB = new SettingsDB(SettingsFragment.this);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //List all components
        btn_save = binding.btnSave.findViewById(R.id.btn_save);
        et_number = binding.etNumber.findViewById(R.id.et_number);
        et_arm = binding.etArm.findViewById(R.id.et_arm);
        et_disarm = binding.etDisarm.findViewById(R.id.et_disarm);
        et_night = binding.etNight.findViewById(R.id.et_night);
        et_day = binding.etDay.findViewById(R.id.et_day);
        et_status = binding.etStatus.findViewById(R.id.et_status);
        et_enable = binding.etEnable.findViewById(R.id.et_enable);
        et_disable = binding.etDisable.findViewById(R.id.et_disable);
        et_time = binding.etTime.findViewById(R.id.et_time);
        et_password = binding.etPassword.findViewById(R.id.et_password);

        Cursor res = settingsDB.uploadSettings();

        if (res != null) {
            while (res.moveToNext()) {

                Model model = new Model();

                model.setId(1);
                model.setNumber(res.getString(1));
                model.setArm(res.getString(2));
                model.setDisarm(res.getString(3));
                model.setNight(res.getString(4));
                model.setDay(res.getString(5));
                model.setStatus(res.getString(6));
                model.setEnable(res.getString(7));
                model.setDisable(res.getString(8));
                model.setTime(res.getString(9));
                model.setPassword(res.getString(10));

                et_number.setText(model.getNumber());
                et_arm.setText(model.getArm());
                et_disarm.setText(model.getDisarm());
                et_status.setText(model.getStatus());
                et_enable.setText(model.getEnable());
                et_disable.setText(model.getDisable());
                et_night.setText(model.getNight());
                et_day.setText(model.getDay());
                et_time.setText(model.getTime());
                et_password.setText(model.getPassword());
            }
        }

        //button listeners
        btn_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String number = et_number.getText().toString();
                 String arm = et_arm.getText().toString();
                 String disarm = et_disarm.getText().toString();
                 String night = et_night.getText().toString();
                 String day = et_day.getText().toString();
                 String status = et_status.getText().toString();
                 String enable = et_enable.getText().toString();
                 String disable = et_disable.getText().toString();

                 @SuppressLint("SimpleDateFormat") String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
                 String time = "TIME " +currentTime;

                 //String time = et_time.getText().toString();
                 String password = et_password.getText().toString();

                 Model model;
                 try {
                     model = new Model(1, number, arm, disarm, night, day, status, enable, disable, time, password);
                     Log.d("info NUMBER", model.getNumber());
                     if (settingsDB.firstLogin() == true) {
                         settingsDB.addOne(model);
                         Toast.makeText(getActivity().getApplicationContext(), model.toString(), Toast.LENGTH_SHORT).show();
                         Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                     } else {
                         Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                         settingsDB.updateModel(model);
                     }
                 } catch (Exception e) {
                     Toast.makeText(getActivity().getApplicationContext(), "ERROR CREATING MODEL", Toast.LENGTH_SHORT).show();
                     model = new Model(-1, "error", "error", " error", "error", "error", "error", "error", "error", "error", "error");
                 }
             }
         });
       return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}