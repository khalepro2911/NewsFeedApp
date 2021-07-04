package com.example.http;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.ViewHolder>{
    private ArrayList<Website>websites;
    private Context context;
    public WebAdapter(ArrayList<Website>websites, Context context){
        this.websites = websites;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.rowlayout,parent,false);
        return new ViewHolder(itemView,context,websites);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnView.setText(websites.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return websites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView btnView;
        
        private ArrayList<Website> websites;
        private Context context;
        public ViewHolder(View itemView, Context context, ArrayList<Website> websites){
            super(itemView);
            btnView = itemView.findViewById(R.id.btn);
            this.context = context;
            this.websites = websites;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            Intent intent = new Intent(context,WebViewActivity.class);
            intent.putExtra("URL_NAME",websites.get(getAbsoluteAdapterPosition()).getURL());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
