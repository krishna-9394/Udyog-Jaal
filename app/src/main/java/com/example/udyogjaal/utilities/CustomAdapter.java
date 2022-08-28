package com.example.udyogjaal.utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.udyogjaal.R;
import com.example.udyogjaal.activities.MainActivity;
import com.example.udyogjaal.databinding.SeekersProfileBinding;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    Context context;
    ArrayList<SeekersProfile> list;

    public CustomAdapter(Context context, ArrayList<SeekersProfile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seekers_profile, parent, false);
            return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SeekersProfile seekers = list.get(position);
        Log.v("messaging", ""+seekers.toString());
        holder.name.setText(seekers.getName());
        holder.experience.setText(seekers.getExperience()+" Years");
        String field = "";
        for (int i = 0; i < 7; i++) {
            if(seekers.getField_status().get(i)) {
                String name = seekers.getField_name().get(i);
                if(i==6) field = field + R.string.name+". ";
                else field = field + seekers.getField_name().get(i)+", ";
            }
        }
        holder.field.setText(field);
        Glide.with(context)
                .load(seekers.getImageUrl())
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, field, experience;
        private ImageView profile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.seekers_name);
            experience = itemView.findViewById(R.id.seekers_experience);
            field = itemView.findViewById(R.id.seekers_field);
            profile = itemView.findViewById(R.id.seekersImage);
        }
    }
}
