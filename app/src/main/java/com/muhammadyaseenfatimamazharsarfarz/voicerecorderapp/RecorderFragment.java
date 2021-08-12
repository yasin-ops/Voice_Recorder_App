package com.muhammadyaseenfatimamazharsarfarz.voicerecorderapp;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;


public class RecorderFragment extends Fragment {
   View view;
    ImageButton btnRecord;
    Chronometer timeRec;
    GifImageView gifView;
    private static  String fileName;
    private static MediaRecorder mediaRecorder;
    private TextView txtRecordStatus;
    boolean isRecording;
    private AdView adView;
    File path=new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/VRecoder");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recorder, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRecord=view.findViewById(R.id.btnRecord);
        timeRec=view.findViewById(R.id.timeRec);
        gifView=view.findViewById(R.id.gifView);
        adView=view.findViewById(R.id.adView);
        txtRecordStatus=view.findViewById(R.id.txtRecordStatus);
        askRunTimePermission();

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        isRecording=false;
        SimpleDateFormat format=new SimpleDateFormat("yyyyMM_HHmmss", Locale.getDefault());
        String date=format.format(new Date());
        fileName=path+"/recording_"+date+".amr";
        if(!path.exists()){
            path.mkdirs();
        }
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRecording){
                    try {
                        startRecording();
                        gifView.setVisibility(View.VISIBLE);
                        timeRec.setBase(SystemClock.elapsedRealtime());
                        timeRec.start();
                        txtRecordStatus.setText("Recording ......");
                        btnRecord.setImageResource(R.drawable.ic_stop);
                        isRecording=true;

                    }catch (Exception e){
                        e.getStackTrace();
                        Toast.makeText(getContext(), "could not Recorded", Toast.LENGTH_SHORT).show();
                    }

                }else if(isRecording){
                    try {
                        stopRecording();
                        gifView.setVisibility(View.GONE);
                        timeRec.setBase(SystemClock.elapsedRealtime());
                        timeRec.stop();
                        txtRecordStatus.setText("");
                        btnRecord.setImageResource(R.drawable.ic_record);
                        isRecording=false;

                    }catch (Exception e){
                        e.getStackTrace();
                        Toast.makeText(getContext(), "could not Recorded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
 public void startRecording(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    try {
        mediaRecorder.prepare();

    } catch (IOException e) {
        e.printStackTrace();
    }
    mediaRecorder.start();
}
public void  stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
}
    private void askRunTimePermission() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }
}