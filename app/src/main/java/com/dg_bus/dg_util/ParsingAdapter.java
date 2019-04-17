package com.dg_bus.dg_util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dg_bus.R;


import java.util.ArrayList;

public class ParsingAdapter extends RecyclerView.Adapter<ParsingAdapter.ViewHolder> {
    //데이터 배열 선언
    private ArrayList<ItemObject> mList;
    //private Instant GlideApp;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_release, texView_director;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_release = itemView.findViewById(R.id.textView_release);
            texView_director = itemView.findViewById(R.id.textView_director);
        }
    }

    //생성자
    public ParsingAdapter(ArrayList<ItemObject> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ParsingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParsingAdapter.ViewHolder holder, int position) {

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_release.setText(String.valueOf(mList.get(position).getRelease()));
        holder.texView_director.setText(String.valueOf(mList.get(position).getDirector()));


        GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
                .override(300,400)
                .into(holder.imageView_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
