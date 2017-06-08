package com.example.rajora_sd.tuts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rajora_sd.tuts.utils.EncryptionUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import lib.folderpicker.FolderPicker;

public class MainActivity extends AppCompatActivity {

    EncryptionUtils encryptionUtils;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encryptionUtils = new EncryptionUtils();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Encrypting the selected folder files..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        if (!preferences.contains("KEY")) {
            startActivity(new Intent(this, RegisterKeyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            this.finish();
        }
        if (askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 12) && askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 13))
            setupData();
    }

    public void setupData() {
        Intent intent = new Intent(this, FolderPicker.class);
        intent.putExtra("Tuts", "Select folder");
        startActivityForResult(intent, 236);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 236 && resultCode == Activity.RESULT_OK) {
            String folderLocation = intent.getExtras().getString("data");
            File files = new File(folderLocation);
            new doEncryptFiles().execute(files);
            Log.e("Cache Path", getCacheDir().getAbsolutePath());
            Log.e("folderLocation", folderLocation);
        }
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


    public class doEncryptFiles extends AsyncTask<File, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected Boolean doInBackground(File... files) {
            for (File file : files[0].listFiles()) {
                Log.e("File Path", file.getAbsolutePath());
                try {
                    encryptionUtils.encryptedWrite(file, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
