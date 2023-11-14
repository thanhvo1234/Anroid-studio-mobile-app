package com.example.coursework;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.activities.MainFragment;
import com.example.coursework.database.HikeDatabase;
import com.example.coursework.models.Hike;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class EditFragment extends Fragment {

    private View v;

    private Button edit;

    private String name, location, date, parking, length, difficulty, description;

    private int idHike;

    private EditText nameHike, locationHike, lengthHike, descriptionHike;

    private TextView dateHike;

    private RadioButton yes, no;

    private EditText difficultyHike;

    Calendar calendar;

    String[] levelItem = {"EXTREME HIGH", "HIGH", "MEDIUM", "LOW"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterLevels;

    HikeDatabase hikeDatabase;
    List<Hike> hikeList;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edit, container, false);

        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "hike_db")
                .allowMainThreadQueries()
                .build();

        nameHike = v.findViewById(R.id.nameEditHikeText);
        locationHike = v.findViewById(R.id.nameEditLocationText);
        lengthHike = v.findViewById(R.id.lengthEditHikeText);
        dateHike = v.findViewById(R.id.dateEditHikeText);
        difficultyHike = v.findViewById(R.id.difficultyEditLevelText);
        descriptionHike = v.findViewById(R.id.descriptionEditText);
        yes = v.findViewById(R.id.radioEditButton1);
        no = v.findViewById(R.id.radioEditButton2);

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dateHike.setOnClickListener(view ->{
            new DatePickerDialog(getActivity(),date
                    ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        autoCompleteTextView = v.findViewById(R.id.difficultyEditLevelText);
        adapterLevels = new ArrayAdapter<String>(getContext(), R.layout.list_level, levelItem);
        autoCompleteTextView.setAdapter(adapterLevels);

        edit = v.findViewById(R.id.editButton);

        getDataHike();
        setDataHike();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageData();

            }
        });
        return v;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main,fragment);
        fragmentTransaction.commit();
    }
    private void storageData(){
        name = nameHike.getText().toString();
        location = locationHike.getText().toString();
        length = lengthHike.getText().toString();
        date = dateHike.getText().toString();
        difficulty = difficultyHike.getText().toString();
        description = descriptionHike.getText().toString();

        if (yes.isChecked()){
            parking = "Yes";
        } else {
            parking = "No";
        }
        if (name.isEmpty()|| location.isEmpty() || date.isEmpty() || length.isEmpty() || difficulty.isEmpty()){
            Toast.makeText(getContext(), "Fill all update ?", Toast.LENGTH_SHORT).show();
        }else {
            Hike hike = new Hike(idHike, name, location, date, parking, length, difficulty, description);
            hikeDatabase.hikeDao().updateHike(hike);
            replaceFragment(new MainFragment());
        }

    }


    private void getDataHike(){
        idHike = getArguments().getInt("idHike");
        name = getArguments().getString("nameHike");
        location = getArguments().getString("locationHike");
        date = getArguments().getString("dateHike");
        parking = getArguments().getString("parkingHike");
        length = getArguments().getString("lengthHike");
        difficulty = getArguments().getString("difficultyHike");
        description = getArguments().getString("descriptionHike");

        if(parking.equals("Yes")){
            yes.setChecked(true);
        } else {
            no.setChecked(true);
        }

    }

    private void setDataHike(){
        nameHike = v.findViewById(R.id.nameEditHikeText);
        nameHike.setText(name);

        locationHike = v.findViewById(R.id.nameEditLocationText);
        locationHike.setText(location);

        dateHike = v.findViewById(R.id.dateEditHikeText);
        dateHike.setText(date);

        lengthHike = v.findViewById(R.id.lengthEditHikeText);
        lengthHike.setText(length);

        difficultyHike = v.findViewById(R.id.difficultyEditLevelText);
        difficultyHike.setText(difficulty);

        descriptionHike = v.findViewById(R.id.descriptionEditText);
        descriptionHike.setText(description);


        yes = v.findViewById(R.id.radioEditButton1);
        no = v.findViewById(R.id.radioEditButton2);

        AutoCompleteTextView autoCompleteTextView = v.findViewById(R.id.difficultyEditLevelText);
        adapterLevels = new ArrayAdapter<String>(getContext(), R.layout.list_level, levelItem);
        autoCompleteTextView.setAdapter(adapterLevels);


    }
    private void updateLabel(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateHike.setText(dateFormat.format(calendar.getTime()));
        date = dateHike.getText().toString();
    }


}