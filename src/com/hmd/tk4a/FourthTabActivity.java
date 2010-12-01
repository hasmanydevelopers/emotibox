package com.hmd.tk4a;

import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TableRow;
import android.graphics.Typeface;
import android.widget.TableLayout;
import android.text.ClipboardManager;
import android.widget.TableLayout.LayoutParams;

public class FourthTabActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fourth_tab);
        
        String chars[] = {
            "\u262F", "\u263E", "\u261D", "\u2656", "\u273D",
            "\u271D", "\u2604", "\u261F", "\u265F", "\u273A",
            "\u2625", "\u2702", "\u270D", "\u2655", "\u2735",
            "\u2645", "\u2647", "\u2646", "\u2659", "\u265F",
            "\u2654", "\u2655", "\u2656", "\u2657", "\u2658",
            "\u265A", "\u265B", "\u265C", "\u265D", "\u265E",
            "\u00F7", "\u2030", "\u221A", "\u2260", "%",
            "\u02DA", "\u02C6", "\u02DC", "\u02D8", "\u00AF",
            "\u2211", "\u00BA", "\u00AA", "\u203D", "?",
        };
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSans.ttf");
        TableLayout table = (TableLayout) findViewById(R.id.table4);
        
        TableRow row = new TableRow(this);
        for (int i = 0; i < chars.length; i++){
            if ((i % 5 == 0) | (i == 0)){
                row = new TableRow(this);
                table.addView(row, new TableLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }
            Button button = new Button(this);
            button.setHeight(55);
            button.setWidth(55);
            button.setText(chars[i]);
            button.setTag(chars[i]);
            button.setTextSize(25);
            button.setTypeface(typeface);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    String ch = v.getTag().toString();
                    Toast.makeText(getApplicationContext(), "Copied to Clipboard: " + ch, Toast.LENGTH_LONG).show();
                    cm.setText(ch);
                }
            });
            row.addView(button);
        }
    }
}
