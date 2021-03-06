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
import android.view.MenuItem;
import android.view.MenuInflater;
import android.app.Dialog;
import android.app.AlertDialog;

import com.hmd.emotibox.AboutDialog;

public class FourthTabActivity extends Activity {
    final static public int ABOUT_DIALOG = 0x10000000;
    final static public int HELP_DIALOG = 0x20000000;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fourth_tab);
        
        String chars[] = {
            "\u265C", "\u265F", "\u265E", "\u265D", "\u265B",
            "\u2656", "\u2659", "\u2658", "\u2657", "\u2655",
            "\u2654", "\u265A", "\u2645", "\u2647", "\u2646",
            "\u2264", "\u2265", "\u2260", "\u2248", "\u2211",
            "\u2030", "\u221A", "\u00F8", "\u00BA", "\u00AA",
            "\u2680", "\u2681", "\u2682", "\u2683", "\u2684",
            "\u2685", "\u260A", "\u260B", "\u260C", "\u260D",
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
                    Toast.makeText(FourthTabActivity.this, "Copied: " + ch, Toast.LENGTH_SHORT).show();
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
                showDialog(ABOUT_DIALOG);
                break;
            case R.id.help:
                showDialog(HELP_DIALOG);
                break;
            case R.id.quit:
                onQuit();
        }
        return true;
    }
    
    public void onQuit(){
        this.finish();
    }
    
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder;
        switch (id) {
            case ABOUT_DIALOG:
                builder = new AboutDialog(FourthTabActivity.this);
                break;
            case HELP_DIALOG:
                builder = new HelpDialog(FourthTabActivity.this);
                break;
            default:
                builder = null;
        }
        return builder.create();
    }
}
