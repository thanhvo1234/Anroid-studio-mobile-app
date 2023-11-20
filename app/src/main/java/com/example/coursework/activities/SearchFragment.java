package com.example.coursework.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.coursework.EditFragment;
import com.example.coursework.HikeAdapter;
import com.example.coursework.R;
import com.example.coursework.database.HikeDatabase;
import com.example.coursework.models.Hike;

import java.util.List;


public class SearchFragment extends Fragment implements HikeAdapter.OnDeleteClickListener {

    private HikeDatabase hikeDatabase;
    private List<Hike> hikes;
    private HikeAdapter hikeAdapter;
    private RecyclerView recyclerView;
    private EditText editText;
    private String s;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "hike_db")
                .allowMainThreadQueries()
                .build();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button search = view.findViewById(R.id.searchButton);
        editText = view.findViewById(R.id.searchText);

        search.setOnClickListener(view1 -> {
            s = "%" + editText.getText().toString()+"%";
            hikes = hikeDatabase.hikeDao().searchHikes(s);
            hikeAdapter = new HikeAdapter(getContext(),hikes, this);
            recyclerView.setAdapter(hikeAdapter);
            hikeAdapter.notifyDataSetChanged();
        });
        return view;
    }

    public void onDeleteClick(Hike hike){
        hikeDatabase.hikeDao().deleteHike(hike);
        hikes.remove(hike);
        hikeAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Hike hike){
        Bundle bundle = new Bundle();
        bundle.putInt("idHike",hike.hike_id);
        bundle.putString("nameHike",hike.nameHike);
        bundle.putString("locationHike",hike.locationHike);
        bundle.putString("dateHike",hike.dateHike);
        bundle.putString("parkingHike",hike.parking);
        bundle.putString("lengthHike",hike.lengthHike);
        bundle.putString("difficultyHike",hike.difficultyLevel);
        bundle.putString("descriptionHike",hike.description);
        Fragment fragment = new EditFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main,fragment);
        fragmentTransaction.commit();
    }
}