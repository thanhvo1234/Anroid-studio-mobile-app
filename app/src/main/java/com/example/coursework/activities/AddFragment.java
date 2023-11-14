package com.example.coursework.activities;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.example.coursework.R;
import com.example.coursework.database.HikeDatabase;
import com.example.coursework.models.Hike;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFragment extends Fragment {
    private EditText nameHikeTxt, nameLocationTxt, lengthHikeTxt, descriptionTxt, difficultyTxt;

    private TextView dateHikeTxt;

    private RadioButton button1, button2;

    private String nameHike, locationHike, dateHike, parking, lengthHike, difficultyLevel, description;

    private HikeDatabase hikeDatabase;
    private Button addDetailsButton;


    Calendar calendar;


    private View v;

    String[] levelItem = {"EXTREME HIGH", "HIGH", "MEDIUM", "LOW"};

    private EditText level;

    ArrayAdapter<String> adapterLevels;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add, container, false);



        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "hike_db")
                .allowMainThreadQueries()
                .build();


        nameHikeTxt = v.findViewById(R.id.nameHikeText);
        nameLocationTxt = v.findViewById(R.id.nameLocationText);
        dateHikeTxt = v.findViewById(R.id.dateHikeText);
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

        dateHikeTxt.setOnClickListener(view ->{
            new DatePickerDialog(getActivity(),date
                    ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        lengthHikeTxt = v.findViewById(R.id.lengthHikeText);

        descriptionTxt = v.findViewById(R.id.descriptionText);

        AutoCompleteTextView autoCompleteTextView = v.findViewById(R.id.difficultyLevelText);
        adapterLevels = new ArrayAdapter<String>(getContext(), R.layout.list_level, levelItem);
        autoCompleteTextView.setAdapter(adapterLevels);

        button1 = v.findViewById(R.id.radioButton1);
        button2 = v.findViewById(R.id.radioButton2);



        addDetailsButton = v.findViewById(R.id.addButton);
        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDetails();
            }
        });
        return v;

    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateHikeTxt.setText(dateFormat.format(calendar.getTime()));
        dateHike = dateHikeTxt.getText().toString();
    }



    private void addDetails() {

            level = getView().findViewById(R.id.difficultyLevelText);

            nameHike = nameHikeTxt.getText().toString();
            locationHike = nameLocationTxt.getText().toString();
            dateHike = dateHikeTxt.getText().toString();
            lengthHike = lengthHikeTxt.getText().toString();
            difficultyLevel = level.getText().toString();
            description = descriptionTxt.getText().toString();

            if (button1.isChecked() == true ){
                parking = "Yes";

            } else if (button2.isChecked() == true) {
                parking = "No";
            }

            if( validate()){
                Hike hike = new Hike();

                hike.nameHike = nameHike;
                hike.locationHike = locationHike;
                hike.dateHike = dateHike;
                hike.lengthHike = lengthHike;
                hike.difficultyLevel = difficultyLevel;
                hike.description = description;
                hike.parking = parking;


                long id = hikeDatabase.hikeDao().insertHike(hike);
                Toast.makeText(getContext(),"Add Done " + id, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(),"Fill out requirement field", Toast.LENGTH_SHORT).show();
            }
        }


        private boolean validate(){
         if(nameHike.equals("")|| locationHike.equals("") ||dateHike.equals("") || lengthHike.equals("") || difficultyLevel.equals("")){
             return false;
         } return true;
        }

}