package com.muhammadyaseenfatimamazharsarfarz.voicerecorderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;


public class RecordingFragment extends Fragment  implements onSelectListener{
    View view;
    private RecyclerView recyclerView;
    private ArrayList<File> fileList;
    RecAdapter recAdapter;
    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/VRecoder");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recording, container, false);
        recyclerView = view.findViewById(R.id.recyler_View);

        displayFile();
        return view;

    }

    private void displayFile() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        fileList=new ArrayList<>();
        fileList.addAll(findFile(path));
        recAdapter=new RecAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(recAdapter);

    }
    public ArrayList<File> findFile(File file){
        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=file.listFiles();
        if(files!=null){
            for (File singlefile : files) {
                if (singlefile.getName().toLowerCase().endsWith("amr")) {
                    arrayList.add(singlefile);
                }

            }
        }
        return arrayList;

    }

    @Override
    public void OnSelected(File file) {
        Uri uri= FileProvider.getUriForFile(getContext(),
                getContext().getApplicationContext().getPackageName()+".provider",file);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"audio/x-wax");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getContext().startActivity(intent);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            displayFile();
        }
    }
}