package com.sp.vigour.fragments;

import static androidx.navigation.Navigation.findNavController;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ListenableWorker;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.adapters.TransAdapter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.abi.datatypes.Int;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Transactions extends Fragment {
    private RecyclerView recyclerView;
    private Addhelper helper = null;
    private Cursor model = null;
    private TransAdapter adapter = null;
    private TextView balance;
    private ImageView circle;
    Addhelper db;
    SimpleDateFormat simpleDateFormat;
    ArrayList<Integer> historycrypto = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Addhelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transactions, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.historyview);
        circle = v.findViewById(R.id.circle);
        balance = v.findViewById(R.id.userCountry);

        db = new Addhelper(getContext());

        simpleDateFormat = new SimpleDateFormat("dd LLL");

        /* ---- crypto stuff ---- */

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(v).navigate(R.id.action_transactions_to_settings);
            }
        });

        model = helper.getdata();
        model.moveToLast();

        storeDatainArray();
        Integer totcryp = 0;
        for (int i = 0; i< historycrypto.size(); i++){
            totcryp = historycrypto.get(i) +totcryp;
        }
        balance.setText(String.valueOf(totcryp));
        //Log.d("trans1", "todays coins is "+ helper.getCoin(model));

        adapter = new TransAdapter(getActivity(), model, helper);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        return v;
    }

    private void storeDatainArray() {
        db.close();
        Cursor cursor = db.getdata();

        historycrypto.clear();

        int i = 0;

        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){


                historycrypto.add(cursor.getInt(4));

                i++;

            }
            db.close();
        }
    }
}