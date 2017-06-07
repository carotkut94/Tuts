package com.example.rajora_sd.tuts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();


        if (askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 12) && askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 13)) {
            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            if (f.exists()) {
                for (File L : getListFiles(f)) {
                    Log.e("Files", L.getName());
                    File to  = new File(f, L.getName().substring(0, L.getName().lastIndexOf('.'))+ ".rev_tuts");
                    L.renameTo(to);
                    Toast.makeText(this, L.getName(), Toast.LENGTH_LONG).show();
                    //L.delete();
                }
            }
            else{
                Toast.makeText(this, "No Such File", Toast.LENGTH_LONG).show();
            }
        }

    }

    List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    public boolean askPermission(String permission, int reqCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, reqCode);
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
