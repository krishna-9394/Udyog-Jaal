package com.example.udyogjaal.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;

public class ProvidersCustomAdapter extends RecyclerView.Adapter<ProvidersCustomAdapter.MyViewHolder>{
    static Context context;
    ArrayList<ProvidersProfile> list;

    public ProvidersCustomAdapter(Context context, ArrayList<ProvidersProfile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProvidersCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.providers_profile, parent, false);
        return new ProvidersCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvidersCustomAdapter.MyViewHolder holder, int position) {
        final ProvidersProfile provider = list.get(position);
        Log.v("messaging", ""+provider.toString());
        holder.name.setText(provider.getName());
        holder.contact.setText(provider.getName());
        String field = "";
        for (int i = 0; i < 7; i++) {
            if(provider.getField_status().get(i)) {
                String name = provider.getField_name().get(i);
                if(i==6) field = field + R.string.name+". ";
                else field = field + provider.getField_name().get(i)+", ";
            }
        }
        holder.field.setText(field);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, field, experience,contact;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.provider_name);
            field = itemView.findViewById(R.id.provider_field);
            contact = itemView.findViewById(R.id.provider_contact);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    phoneCall(contact.getText());
                    return false;
                }
            });
        }
    }
    private static void phoneCall(CharSequence text) {

        String phone = "+91"+text;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
