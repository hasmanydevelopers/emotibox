package com.hmd.emotibox;

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
import android.view.Menu;
import android.view.MenuInflater;

public class SecondTabActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.second_tab);
        
        String chars[] = {
            "\u2609", "\u2607", "\u2608", "\u2621", "\u2720",
            "\u260A", "\u260B", "\u260C", "\u260D", "\u2641",
            "\u2707", "\u2622", "\u2623", "\u2723", "\u2721",
            "\u261E", "\u261C", "\u271C", "\u271B", "\u2765",
            "\u2648", "\u2649", "\u264A", "\u264B", "\u264C",
            "\u264D", "\u264E", "\u264F", "\u2650", "\u2651",
            "\u2652", "\u2653", "\u262C", "\u262B", "\u2628",
            "\u2627", "\u2626", "\u2701", "\u2703", "\u2704",
            "\u270E", "\u2710", "\u2742", "\u2749", "\u2746",
        };
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSans.ttf");
        TableLayout table = (TableLayout) findViewById(R.id.table2);
        
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
                    Toast.makeText(SecondTabActivity.this, "Copied to Clipboard: " + ch, Toast.LENGTH_LONG).show();
                    cm.setText(ch);
                }
            });
            row.addView(button);
        }
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
}