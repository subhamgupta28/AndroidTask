package com.subhamgupta.androidtask.adapters;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.subhamgupta.androidtask.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.subhamgupta.androidtask.TimeAgo;


import java.io.File;


public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {
    private static File[] allFiles;
    private TimeAgo timeAgo;
    private onItemListClick onItemListClick;
    public FileAdapter(File[] allFiles, onItemListClick onItemListClick) {
        this.allFiles = allFiles;
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public FileAdapter.FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        timeAgo = new TimeAgo();
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.FileHolder holder, int position) {
        holder.fileName.setText(allFiles[position].getName());



        holder.fileDuration.setText(getDuration(allFiles[position], holder.fileDate.getContext()));
        holder.fileDate.setText(timeAgo.getTimeAgo(allFiles[position].lastModified()));
        //holder.playImg.setOnClickListener(view -> playAudio(audioFiles.get(position).getFilePath()));
    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public String getDuration(File file, Context context){
        Uri uri = Uri.parse(file.getPath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context,uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int dur = Integer.parseInt(durationStr);
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        String songTime = String.format("%02d:%02d:%02d", hrs,  mns, scs);
        return songTime;
    }
    public class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fileName;
        TextView fileDuration;
        TextView fileDate;
        ImageView playImg;
        public FileHolder(@NonNull View itemView) {
            super(itemView);
            fileDate = itemView.findViewById(R.id.filedate);
            fileName = itemView.findViewById(R.id.filename);
            fileDuration = itemView.findViewById(R.id.fileduration);
            playImg = itemView.findViewById(R.id.playimg);
           itemView.setOnClickListener(this::onClick);
        }
        @Override
        public void onClick(View v) {
            Log.e(allFiles[getAdapterPosition()].toString(),String.valueOf(getAdapterPosition()));
            onItemListClick.onClickListener(allFiles[getAdapterPosition()], getAdapterPosition());
        }

    }
    public interface onItemListClick {

        void onClickListener(File file, int position) ;


    }
}
