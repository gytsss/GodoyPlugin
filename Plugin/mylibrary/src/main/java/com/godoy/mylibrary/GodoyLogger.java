package com.godoy.mylibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GodoyLogger {

    static final String LOGTAG = "GodLOG";
    public static Activity mainActivity;

    public interface AlertViewCallBack {
        public void onButtonTapped(int id);
    }

    static GodoyLogger _instance = null;

    public static GodoyLogger getInstance() {
        if (_instance == null)
            _instance = new GodoyLogger();
        return _instance;
    }

    public String getLogtag() {
        return LOGTAG;
    }

    public void showAlertView(String[] strings, final AlertViewCallBack callBack) {
        if (strings.length < 3) {
            Log.i(LOGTAG, "Error - expected at least 3 strings, got " + strings.length);
            return;
        }

        DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Log.i(LOGTAG, "Tapped: " + which);
                callBack.onButtonTapped(which);
            }
        };

        AlertDialog alertDialog = new AlertDialog.Builder(mainActivity)
                .setTitle(strings[0])
                .setMessage(strings[1])
                .setCancelable(false)
                .create();
        alertDialog.setButton(alertDialog.BUTTON_NEUTRAL, strings[2], myClickListener);
        if (strings.length > 3)
            alertDialog.setButton(alertDialog.BUTTON_NEGATIVE, strings[3], myClickListener);
        if (strings.length > 4)
            alertDialog.setButton(alertDialog.BUTTON_POSITIVE, strings[4], myClickListener);
        alertDialog.show();

    }
    public void showAlertBeforeClearingLogs()
    {
        String[] strings = {"Confirmation", "Are you sure that you want to delete logs?", "Yes", "No"};

        showAlertView(strings, new AlertViewCallBack() {
            @Override
            public void onButtonTapped(int id) {
                if(id == DialogInterface.BUTTON_NEUTRAL)
                {
                    clearUnityLogs();
                }
            }
        });
    }

    private ArrayList<String> unityLogs = new ArrayList<>();
    private File logFile;

    public GodoyLogger() {
        logFile = new File(mainActivity.getFilesDir(), "unity_logs.txt");

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                Log.e(LOGTAG, "Error creating logs file", e);
            }
        }
    }

    public void logFromUnity(String log) {
        unityLogs.add(log);

        try {
            FileOutputStream fos = new FileOutputStream(logFile, true);
            fos.write((log + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e(LOGTAG, "Error writing logs file", e);
        }
    }

    public ArrayList<String> getUnityLogs()
    {
        return unityLogs;
    }

    public void clearUnityLogs()
    {
        unityLogs.clear();
        if(logFile.exists())
        {
            logFile.delete();
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                Log.e(LOGTAG, "Error deleting logs file", e);
            }
        }
    }



}
