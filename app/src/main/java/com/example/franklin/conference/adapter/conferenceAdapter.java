package com.example.franklin.conference.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.franklin.conference.Data.ConferenceData;
import com.example.franklin.conference.R;

import java.util.List;

/**
 * Created by Franklin on 2018/3/31.
 */

public class conferenceAdapter extends RecyclerView.Adapter<conferenceAdapter.ViewHolder> {

    private Context mContext;
    private List<ConferenceData.Results> myconferencelist;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView conferenceImage;
        TextView conferenceName;

        public ViewHolder (View view){
            super(view);
            cardView = (CardView)view;
            conferenceImage = (ImageView) view.findViewById(R.id.meeting_image);
            conferenceName = (TextView) view.findViewById(R.id.meeting_name);
        }
    }
    public conferenceAdapter(List<ConferenceData.Results> meetingList){
        myconferencelist = meetingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.meeting_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConferenceData.Results conf = myconferencelist.get(position);
        holder.conferenceName.setText(conf.name);
        Glide.with(mContext).load(conf.imge).into(holder.conferenceImage);

    }
    @Override
    public int getItemCount(){
        return myconferencelist.size();
    }
}

