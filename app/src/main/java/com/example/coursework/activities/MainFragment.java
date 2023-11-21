package com.example.coursework.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.coursework.HikeAdapter;
import com.example.coursework.R;
import com.example.coursework.database.HikeDatabase;
import com.example.coursework.models.Hike;

import java.util.List;

public class MainFragment extends Fragment implements HikeAdapter.OnDeleteClickListener {
    private HikeDatabase hikeDatabase;

    List<Hike> hikes;
    private RecyclerView recyclerView;

    private HikeAdapter hikeAdapter;

    private Button deleteAll;

    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "hike_db")
                .allowMainThreadQueries()
                .build();

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        hikes = hikeDatabase.hikeDao().getAllHikes();
        hikeAdapter = new HikeAdapter(getContext(),hikes, this);
        recyclerView.setAdapter(hikeAdapter);
        deleteAll = v.findViewById(R.id.deleteAllButton);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hikeDatabase.hikeDao().deleteAllHike();
                hikes.removeAll(hikes);
                hikeAdapter.notifyDataSetChanged();
            }
        });
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