package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.models.Hike;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ContactViewHolder> {
    private List<Hike> hikes;

    private OnDeleteClickListener onDeleteClickListener;

    private Context context;


    public interface OnDeleteClickListener {
        void onDeleteClick(Hike hike);

        void onItemClick(Hike hike);
    }

    public HikeAdapter(Context context,List<Hike> hikes, OnDeleteClickListener onDeleteClickListener ) {
        this.hikes = hikes;
        this.onDeleteClickListener = onDeleteClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hikeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_icon, parent, false);

        return new ContactViewHolder(hikeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.hikeTxt.setText(hike.getNameHike());

        holder.deleteHike.setOnClickListener(view -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(hike);
            }
        });

        holder.moreHike.setOnClickListener(view -> {

        });

        holder.layout.setOnClickListener(view -> {
            onDeleteClickListener.onItemClick(hike);
        });
    }

    @Override
    public int getItemCount() {
        if(hikes != null){
            return hikes.size();
        }
        return 0;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView hikeTxt;

        private Button deleteHike, moreHike;

        private LinearLayout layout;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeTxt = itemView.findViewById(R.id.nameHikeView);
            deleteHike = itemView.findViewById(R.id.deleteButton);
            moreHike = itemView.findViewById(R.id.moreButton);
            layout = itemView.findViewById(R.id.card_hike);

        }
    }




}
