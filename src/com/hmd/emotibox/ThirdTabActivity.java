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

public class ThirdTabActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.third_tab);
        
        String chars[] = {
            "\u00A9", "\u00AE", "\u2122", "\u2026", "\u221E",
            "\u00A5", "\u20AC", "\u00A3", "\u0192", "$",
            "\u2264", "\u2265", "\u2211", "\u00AB", "\u00BB",
            "\u00E7", "\u222B", "\u00B5", "\u25CA", "\u0131",
            "\u2206", "\u03A9", "\u2248", "*", "\u00A7",
            "\u2022", "\u00B6", "\u00AC", "\u2020", "&",
            "\u00A1", "\u00BF", "\u00F8", "\u00E5", "\u2202",
            "\u0153", "\u00C6", "\u00E6", "\u03C0", "\u00DF",
        };
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSans.ttf");
        TableLayout table = (TableLayout) findViewById(R.id.table3);
        
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
                    Toast.makeText(ThirdTabActivity.this, "Copied to Clipboard: " + ch, Toast.LENGTH_LONG).show();
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
