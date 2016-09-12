package com.fabianmainz.fractionscalculator;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int calcMode = 0;
    private TextView buttonChangeMode;

    private int selectedField = 0;
    private TextView[] field = new TextView[7];
    private TextView[] numbers = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AssetManager am = getApplicationContext().getAssets();

        Typeface font = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "SourceSansPro-Semibold.ttf"));


        buttonChangeMode = (TextView) findViewById(R.id.buttonCalcMode);
        buttonChangeMode.setClickable(true);
        buttonChangeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcMode<3) {
                    calcMode++;
                } else {
                    calcMode = 0;
                }
                updateCalcMode();
            }
        });

        for (int a = 1; a <= 6; a++) {
            int resID = getResources().getIdentifier("field"+a, "id", getPackageName());
            Log.d("asd", "a. "+a);
            field[a] = (TextView) findViewById(resID);
            field[a].setClickable(true);
            field[a].setTypeface(font);
            if (a < 5) {
                final int f = a;
                field[a].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectField(f);
                    }
                });
            }
        }

        for (int a = 0; a < 10; a++) {
            int resID = getResources().getIdentifier("button"+a, "id", getPackageName());
            numbers[a] = (TextView) findViewById(resID);
            numbers[a].setClickable(true);
            numbers[a].setTypeface(font);
            final int f = a;
            numbers[a].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickNumber(f);
                }
            });
        }

    }

    private void reset() {
        selectedField = 0;
        for (int i = 1; i < field.length; i++) {
            field[i].setText(getResources().getString(R.string.placeholder));
            field[i].setBackground(getResources().getDrawable(R.drawable.placeholder));
            if (i>4) {
                field[i].setBackground(getResources().getDrawable(R.drawable.transparent));
                field[i].setText("?");
            }
        }
        calcMode = 0;
    }

    private void updateCalcMode() {
        switch (calcMode) {
            case 0:
                buttonChangeMode.setText("*");
                break;
            case 1:
                buttonChangeMode.setText("/");
                break;
            case 2:
                buttonChangeMode.setText("+");
                break;
            case 3:
                buttonChangeMode.setText("-");
                break;
        }
        updateResult();
    }

    private void selectField(int f) {
        if (f == selectedField) {
            return;
        }
        if (selectedField > 0) {
            if (field[selectedField].getText().equals(getResources().getString(R.string.placeholder))) {
                field[selectedField].setBackground(getResources().getDrawable(R.drawable.placeholder));
            } else {
                field[selectedField].setBackground(getResources().getDrawable(R.drawable.transparent));
            }
        }

        selectedField = f;
        field[selectedField].setBackground(getResources().getDrawable(R.drawable.selected));
        updateResult();
    }

    private void clickNumber(int number) {
        if (selectedField==0) {
            return;
        }
        if (field[selectedField].getText().equals(getResources().getString(R.string.placeholder))) {
            field[selectedField].setText(String.valueOf(number));
        } else {
            field[selectedField].setText(field[selectedField].getText()+String.valueOf(number));
        }
        updateResult();
    }

    private void updateResult() {
        for (int a = 1; a <= 4; a++) {
            if (field[a].getText().equals(getResources().getString(R.string.placeholder)) || field[a].getText().length()<1) {
                return;
            }
        }

        Bruch a = new Bruch(Integer.parseInt(field[1].getText().toString()), Integer.parseInt(field[2].getText().toString()));
        Bruch b = new Bruch(Integer.parseInt(field[3].getText().toString()), Integer.parseInt(field[4].getText().toString()));

        Bruch result = null;
        switch (calcMode) {
            case 0:
                result = a.multiply(b);
                break;
            case 1:
                result = a.divide(b);
                break;
            case 2:
                result = a.add(b);
                break;
            case 3:
                result = a.subtract(b);
                break;
        }

        result = result.simplifiy();

        field[5].setText(""+result.getZaehler());
        field[6].setText(""+result.getNenner());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
