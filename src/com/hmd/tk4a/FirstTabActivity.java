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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.ImageView;

public class FirstTabActivity extends Activity {
    static final int DIALOG_ABOUT_ID = 1;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.first_tab);
        
        String chars[] = {
            "\u2665", "\u2708", "\u263A", "\u266C", "\u2611",
            "\u2660", "\u260E", "\u263B", "\u266B", "\u2612", 
            "\u2664", "\u2624", "\u2639", "\u266A", "\u2640",
            "\u2729", "\u2709", "\u2620", "\u2714", "\u2642",
            "\u2605", "\u2707", "\u267A", "\u2716", "\u2668",
            "\u2766", "\u2601", "\u270C", "\u265B", "\u2741",
            "\u262A", "\u2602", "\u270F", "\u265D", "\u2740",
            "\u262D", "\u2603", "\u261B", "\u265E", "\u273F",
            "\u262E", "\u263C", "\u261A", "\u2658", "\u273E",
        };
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSans.ttf");
        TableLayout table = (TableLayout) findViewById(R.id.table1);
        
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
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                showDialog(DIALOG_ABOUT_ID);
                break;
            case R.id.quit:
                onQuit();
                break;
        }
        return true;
    }
    
    public void onQuit(){
        this.finish();
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
            case DIALOG_ABOUT_ID:
                /*dialog = new Dialog(getApplicationContext());
                
                dialog.setContentView(R.layout.about);
                dialog.setTitle("About TK4A");
                
                TextView text = (TextView) dialog.findViewById(R.id.about_text);
                text.setText("Hello, this is a custom dialog!");
                ImageView image = (ImageView) dialog.findViewById(R.id.about_icon);
                image.setImageResource(R.drawable.ic_dialog_about);
                */
                dialog = null;
                Toast.makeText(getApplicationContext(), "Holy shit", Toast.LENGTH_LONG).show();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }
}
