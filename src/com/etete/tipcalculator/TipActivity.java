package com.etete.tipcalculator;

import java.text.NumberFormat;
import java.math.BigDecimal;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class TipActivity extends Activity {
    private double currentBill = 0.0; 
    private EditText etBill;
    private TextView tvTip;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        etBill = (EditText) findViewById(R.id.bill);
        tvTip = (TextView) findViewById(R.id.amount);
        etBill.setOnKeyListener(
                // If user starts to type anything, then hide our previous (or any) tip field
                new OnKeyListener() {                    
                    @Override
                    public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                        tvTip.setVisibility(View.INVISIBLE);
                        return false;
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip, menu);
        return true;
    }
    
    public void onCalcTip(View v) {        
        double theAmount = -1.0;
        boolean validAmount = true;
        
        try {
            theAmount = Double.parseDouble(etBill.getText().toString());
        }
        catch (NumberFormatException e) {
            validAmount = false;
        }
        if (validAmount) {
            double pct = 0.0;
            switch (v.getId()) {
            case R.id.tenpct:
                pct = 10.0;
                break;
            case R.id.fifteenpct:
                pct = 15.0;
                break;
            case R.id.twentypct:
                pct = 20.0;
                break;
            default:
                pct = -1.0;
            }
            if (pct >= 0) {
                tvTip.setVisibility(View.VISIBLE);
                currentBill = theAmount * pct / 100.0;
                final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
                final String val = currencyInstance.format(currentBill);
                final String tipis = getString(R.string.tipis);
                tvTip.setText(tipis + String.format("%1$10s", val));
            }
        }
        else {
            Toast.makeText(this, getString(R.string.validAmount), Toast.LENGTH_LONG).show();
        }
    }
    
}
