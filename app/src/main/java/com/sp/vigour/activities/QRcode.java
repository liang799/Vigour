package com.sp.vigour.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sp.vigour.Addhelper;
import com.sp.vigour.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QRcode extends AppCompatActivity {
    Button btScan;
    Addhelper helper = new Addhelper(this);
    Addhelper db = new Addhelper(this);
    private String today;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd LLL");
    ImageButton close;
    ArrayList<Integer> historyCrypto = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qrcode);

        close = findViewById(R.id.imageButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btScan = findViewById(R.id.bt_scan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        QRcode.this
                );

                // Set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");

                //Set beep
                intentIntegrator.setBeepEnabled(true);

                //Locked orientation
                intentIntegrator.setOrientationLocked(true);

                //set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);

                //Initiate scan
                intentIntegrator.initiateScan();

            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        if(intentResult.getContents() !=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    QRcode.this
            );
            builder.setTitle("Result");

            builder.setMessage(intentResult.getContents());

            Cursor cursor = helper.getdata();
            cursor.moveToLast();
            String latestday = helper.getDate(cursor);

            today = simpleDateFormat.format(new Date());

            if(intentResult.getContents().equals("ok"))
            {
                Log.d("qr","it recognises the text");
            }
            if (helper.checkForTables() == false) {
                //create new row
                helper.insert(String.valueOf(Math.round(0)), today, 5);
            } else if(!today.equals(latestday)) {

                //reset steps and create new row
                //steps = 0;

                //helper.getCoin(c);

                int size = historyCrypto.size()-1;
                int usercrypto = historyCrypto.get(size);
                helper.updateBal(usercrypto + 5, today);

            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Dismiss dialog
                    dialogInterface.dismiss();
                }
            });
            //Show alert dialog
            builder.show();
        }else {
            Toast.makeText(getApplicationContext(), "OOPS You didnt scan anything", Toast.LENGTH_SHORT).show();
        }
    }
    private void storeDatainArray() {
        helper.close();
        Cursor cursor = helper.getdata();

        historyCrypto.clear();

        int i = 0;

        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){

                historyCrypto.add(cursor.getInt(4));

                i++;

            }
            helper.close();
        }
    }
}
