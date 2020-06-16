package com.example.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private String filename = "history.txt";
    private RadioGroup radioGroupMode;
    private RadioButton radioFTC;
    private RadioButton radioCTF;
    private TextView input;
    private TextView output;
    private Button convertBtn;
    private TextView history;

    public String convertedDouble(double n) {
        String s = String.format("%.1f", n);
        return s;
    }

    public String FaToCe(double f) {
        double c = (f - 32.0) * 5.0 / 9.0;
        return convertedDouble(c);
    }

    public String CeToFa(double c) {
        double f = (c * 9.0 / 5.0) + 32.0;
        return convertedDouble(f);
    }

    private void saveData(String x, String y) {
        String mode;
        if(radioFTC.isChecked())
            mode = "F to C: ";
        else
            mode = "C to F: ";
        String existdata = readData();
        String newdata = x + " -> " + y + "\n";
        String data = mode + newdata + existdata;
        try {
            FileOutputStream out = this.openFileOutput(filename, MODE_PRIVATE);
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String readData() {
        try {
            FileInputStream in = this.openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroupMode = (RadioGroup) this.findViewById(R.id.RadioGroup);
        radioFTC = (RadioButton) this.findViewById(R.id.FTCRadio);
        radioFTC.setChecked(true);
        radioCTF = (RadioButton) this.findViewById(R.id.CTFRadio);
        input = (TextView) this.findViewById(R.id.input);
        output = (TextView) this.findViewById(R.id.output);
        convertBtn = (Button) this.findViewById(R.id.ConvertBtn);
        history = (TextView) this.findViewById(R.id.history);
        history.setText(readData());

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double in = Double.parseDouble(input.getText().toString());;
                String result;
                if(radioFTC.isChecked()) {
                    result = FaToCe(in);
                }
                else {
                    result = CeToFa(in);
                }
                output.setText(result);
                saveData(convertedDouble(in), result);
                history.setText(readData());
            }
        });
    }
}
