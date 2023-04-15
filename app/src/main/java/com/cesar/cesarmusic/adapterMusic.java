package com.cesar.cesarmusic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapterMusic extends RecyclerView.Adapter<adapterMusic.ViewHolder> implements View.OnClickListener {

    private int lastPosition = -1;
    private Context context;
    private View.OnClickListener listener;
    private List<String> listMusic;


    public adapterMusic(Context context, List<String> listMusic) {
        this.context = context;
        this.listMusic = listMusic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_music, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        try {
            String song = listMusic.get(position);
            viewHolder.item1.setText(song);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        if (listMusic != null)
            return listMusic.size();
        else return 0;
    }

    public void setDataSet(List<String> listMusic) {
        listMusic = listMusic;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item1;

        public ViewHolder(View v) {
            super(v);
            item1 = (TextView) v.findViewById(R.id.txtMusics);
        }

    }

}

