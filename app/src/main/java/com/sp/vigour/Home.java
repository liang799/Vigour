package com.sp.vigour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Home extends Fragment implements View.OnClickListener {
    private Button detailsButt;
    private TextView tips;
    private TextView Vgr_Amount;
    private TextView hide_indicator;
    private TextView toggle_hide;
    private boolean hidden = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tips = view.findViewById(R.id.tips);
        detailsButt = view.findViewById(R.id.details_button);
        detailsButt.setOnClickListener(this);
        Vgr_Amount = view.findViewById(R.id.balance_home);
        hide_indicator = view.findViewById(R.id.hideEye);
        toggle_hide = view.findViewById(R.id.visibilitySwitch);
        toggle_hide.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visibilitySwitch:
                if (hidden == false) {
                    hide_indicator.setVisibility(View.VISIBLE);
                    Vgr_Amount.setText("***");
                    hidden = true;
                } else {
                    hide_indicator.setVisibility(View.GONE);
                    Vgr_Amount.setText("100");
                    hidden = false;
                }
                break;
            case R.id.details_button:
                Navigation.findNavController(v).navigate(R.id.action_home2_to_transactions);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}