package com.subhamgupta.androidtask.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhamgupta.androidtask.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AudioRecorder extends Fragment {
    private boolean isRecording = false;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    String filename;
    private static File mFileName = null;
    private FloatingActionButton record;
    private FloatingActionButton stop;
    private goToFiles nextPage;
    private Chronometer chrono;
    private TextView filenameText;

    public AudioRecorder() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_audio_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        record = view.findViewById(R.id.fabrecord);
        nextPage = (goToFiles) getContext();
        stop = view.findViewById(R.id.fabstop);
        chrono  = view.findViewById(R.id.chronometer);
        filenameText = view.findViewById(R.id.textname);
        chrono.setOnChronometerTickListener(chronometer -> {
            long time = SystemClock.elapsedRealtime() - chronometer.getBase();
            int h   = (int)(time /3600000);
            int m = (int)(time - h*3600000)/60000;
            int s= (int)(time - h*3600000- m*60000)/1000 ;
            String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
            chronometer.setText(t);
        });
        checkPermissions();
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("00:00:00");

        record.setOnClickListener(view1 -> {
            if(isRecording) {
                //Stop Recording
                stopRecording();

                // Change button image and set Recording state to false
                record.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic, null));
                isRecording = false;
            } else {
                //Check permission to record audio
                if(checkPermissions()) {
                    //Start Recording
                    startRecording();

                    // Change button image and set Recording state to false
                    record.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_stop_24, null));
                    isRecording = true;
                }
            }
        });
        stop.setOnClickListener(view1 -> {
            nextPage.onGo("go");
        });



    }
    public interface goToFiles {
        void onGo(String msg);
    }
    private void stopRecording() {
        //Stop Timer, very obvious
        chrono.stop();

        //Change text on page to file saved
        filenameText.setText("Recording Stopped, File Saved : " + filename);

        //Stop media recorder and set it to null for further use to record new audio
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    private void startRecording()  {

        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
        Date now = new Date();
        filename = formatter.format(now) + ".3gp";
        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        filenameText.setText("Recording, File Name : " + filename);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(path+"/"+filename);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
        }
        mRecorder.start();
    }

    public void playAudio() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(String.valueOf(mFileName));
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }
    private boolean checkPermissions() {
        //Check permission
        if (ActivityCompat.checkSelfPermission(getContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            return true;
        } else {
            //Permission not granted, ask for permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    public void pausePlaying() {
        mPlayer.release();
        mPlayer = null;
    }
}