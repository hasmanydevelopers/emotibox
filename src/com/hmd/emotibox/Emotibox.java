package com.hmd.emotibox;

import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TabHost;
import android.content.Intent;
import android.widget.TextView;
//import android.app.TabActivity;

import android.widget.Toast;
import android.widget.Button;
import android.widget.TableRow;
import android.graphics.Typeface;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.text.ClipboardManager;
import android.app.Dialog;
import android.app.AlertDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.res.Resources;
import android.widget.TabHost.TabSpec;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.util.Log;

import android.graphics.Color;
import android.view.Gravity;

import android.database.Cursor;


public class Emotibox extends Activity{

    private static final int MAX_TAB = 4;
    
    private static final int MAJOR_MOVE = 60;
    private static final int ANIM_DURATION = 400;
    
    final static public int ABOUT_DIALOG = 0x10000000;
    final static public int HELP_DIALOG = 0x20000000;
    
    private PanelSwitcher panel;
    private TextView tabLeft;
    private TextView tabCenter;
    private TextView tabRight;
    private DbAdapter mDbHelper;
    
    String mostUsed[] = {"", "", "", "", ""};
    Button btnMostUsed[] = {null, null, null, null, null};
    Typeface typeface; 
    TableLayout[] tableLay;
    
    String chars[][] = { 
        {
            "\u2665", "\u263A", "\u2639", "\u263B", "\u2620",
            "\u266A", "\u266B", "\u2601", "\u2602", "\u2603", 
            "\u263C", "\u263E", "\u2729", "\u2708", "\u2709",
            "\u00AE", "\u00A9", "\u2122", "\u2627", "\u2624",
            "\u2642", "\u2640", "\u262F", "\u262E", "\u267A",
            "\u2622", "\u2623", "\u2707", "\u262A", "\u262D",
        },
        {
            "\u2714", "\u2716", "\u261F", "\u261D", "\u270C",
            "\u270D", "\u270F", "\u270E", "\u2710", "\u2026",
            "\u2702", "\u2704", "\u2626", "\u2628", "\u2765",
            "\u2611", "\u2612", "\u261B", "\u261A", "\u2660",
            "\u2720", "\u262C", "\u262B", "\u2604", "\u2735",
            "\u273E", "\u273D", "\u2740", "\u273F", "\u2742",
            "\u2723", "\u2749", "\u2741", "\u2746", "\u273A",
        },
        {
            "\u2648", "\u2649", "\u264A", "\u264B", "\u264C",
            "\u264D", "\u264E", "\u264F", "\u2650", "\u2651",
            "\u2652", "\u2653", "\u221E", "\u203D", "\u2206",
            "\u0153", "\u00C6", "\u00E6", "\u03C0", "\u00DF",
            "\u2127", "\u03A9", "\u00E7", "\u222B", "\u00B5",
            "\u2022", "\u00B6", "\u2202", "\u00A7", "\u00E5",
            "\u00AB", "\u00BB", "\u00A1", "\u00BF", "\u25CA",
        },
        {
            "\u265C", "\u265F", "\u265E", "\u265D", "\u265B",
            "\u2656", "\u2659", "\u2658", "\u2657", "\u2655",
            "\u2654", "\u265A", "\u2645", "\u2647", "\u2646",
            "\u2264", "\u2265", "\u2260", "\u2248", "\u2211",
            "\u2030", "\u221A", "\u00F8", "\u00BA", "\u00AA",
            "\u2680", "\u2681", "\u2682", "\u2683", "\u2684",
            "\u2685", "\u260A", "\u260B", "\u260C", "\u260D",
        }
    };
     

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        typeface = Typeface.createFromAsset(getAssets(),"fonts/DejaVuSans.ttf");
        tableLay = new TableLayout[MAX_TAB];
        tableLay[0] = (TableLayout) findViewById(R.id.table1);
        tableLay[1] = (TableLayout) findViewById(R.id.table2);
        tableLay[2] = (TableLayout) findViewById(R.id.table3);
        tableLay[3] = (TableLayout) findViewById(R.id.table4);
        
        //override the default gestureListener of the panelSwitcher
        panel = (PanelSwitcher) findViewById(R.id.tabcontent);
        panel.setGestureDetector( new GestureDetector(Emotibox.this, new GestureDetector.SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                int dx = (int) (e2.getX() - e1.getX());

                // don't accept the fling if it's too short
                // as it may conflict with a button push
                if (Math.abs(dx) > MAJOR_MOVE && Math.abs(velocityX) > Math.abs(velocityY)) {
                    if (velocityX > 0) {
                        moveRight();
                    } else {
                        moveLeft();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }));
        
        //set the title bar
        tabLeft = (TextView) findViewById(R.id.tab_left);
        tabLeft.setTypeface(typeface);
        tabLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveRight();
            }
        });
                
        tabCenter = (TextView) findViewById(R.id.tab_center);
        tabCenter.setTypeface(typeface);
        tabCenter.setText( chars[0][0] );
        
        tabRight = (TextView) findViewById(R.id.tab_right);
        tabRight.setTypeface(typeface);
        tabRight.setText( chars[1][0] + chars[2][0] + chars[3][0] );
        tabRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLeft();
            }
        });
        
        mDbHelper = new DbAdapter(this);
        mDbHelper.open();
        
        // set the content of all pages in the panelSwitcher
        for(int i=0; i < MAX_TAB; i++){ 
        
            TableLayout table = tableLay[i];
            
            TableRow row = new TableRow(this);
            for (int j = 0; j < chars[i].length; j++){
                if ((j % 5 == 0) | (j == 0)){
                    row = new TableRow(this);
                    table.addView(row, new TableLayout.LayoutParams(
                            LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT));
                }
                
                Button button = new Button(this);
                button.setHeight(50);
                button.setWidth(55);
                button.setText(chars[i][j]);
                button.setTag(chars[i][j]);
                button.setTextSize(25);
                button.setTypeface(typeface);
                button.setTextColor(Color.WHITE );
                button.setBackgroundResource(R.drawable.btn_bg_black);
                button.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL );
                
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        String strChar = v.getTag().toString();
                        int charCode = str2int(strChar);
                        Toast.makeText(Emotibox.this, "Copied: " + strChar, Toast.LENGTH_SHORT).show();
                        cm.setText(strChar);
                        String strBack = int2str(charCode);
                        Log.d("Emotibox", "Updating unicode char " + charCode);
                        Log.d("Emotibox", "Converting back to str " + strBack);
                        mDbHelper.updateRecord(charCode);
                        updateMostUsed();
                    }
                });
                
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        String ch = v.getTag().toString();
                        Intent i = new Intent(android.content.Intent.ACTION_MAIN);
                        i.addCategory(android.content.Intent.CATEGORY_LAUNCHER);
//                        i.setType("plain/text");
//                        i.putExtra(android.content.Intent.EXTRA_TEXT, ch); 
                        startActivity(Intent.createChooser(i, "Send the character using:"));
                        return true;
                    }
                });
                
                row.addView(button);
            }
            
            row = new TableRow(this);
            if (i == 0){
                row.setPadding( 0, 10, 0, 10 );
                for (int x = 0; x < 5; x++){
                    Button button = new Button(this);
                    button.setHeight(50);
                    button.setWidth(55);
                    button.setTextSize(25);
                    button.setTypeface(typeface);
                    button.setTextColor(Color.WHITE );
                    button.setBackgroundResource(R.drawable.btn_bg_orange);
                    button.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL );
                    
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            String strChar = v.getTag().toString();
                            int charCode = str2int(strChar);
                            Toast.makeText(Emotibox.this, "Copied: " + strChar, Toast.LENGTH_SHORT).show();
                            cm.setText(strChar);
                            String strBack = int2str(charCode);
                            Log.d("Emotibox", "Updating unicode char " + charCode);
                            Log.d("Emotibox", "Converting back to str " + strBack);
                            mDbHelper.updateRecord(charCode);
                            updateMostUsed();
                        }
                    });
                    
                    btnMostUsed[x] = button;
                    row.addView(btnMostUsed[x]);
                }
                table.addView(row, new TableLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }
            
            
        }
        
        updateMostUsed();
        
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
                builder = new AboutDialog(this);
                break;
            case HELP_DIALOG:
                builder = new HelpDialog(this);
                break;
            default:
                builder = null;
        }
        return builder.create();
    }
    
    private void setTabTitle( int idx ){
        switch(idx){
        case 0:
            tabLeft.setText( "" );
            tabCenter.setText( chars[0][0] );
            tabRight.setText( chars[1][0] + chars[2][0] + chars[3][0] );
            break;
        case 1:
            tabLeft.setText( chars[0][0] );
            tabCenter.setText( chars[1][0] );
            tabRight.setText( chars[2][0] + chars[3][0] );
            break;
        case 2:
            tabLeft.setText( chars[0][0] + chars[1][0] );
            tabCenter.setText( chars[2][0] );
            tabRight.setText(  chars[3][0] );
            break;
        case 3:
            tabLeft.setText( chars[0][0] + chars[1][0] + chars[2][0] );
            tabCenter.setText( chars[3][0] );
            tabRight.setText( "" );
            break;
        }
    }
    
    private int str2int(String strChar){
        return (int) strChar.charAt(0);
    }
    
    private String int2str(int charCode){
        return Character.toString((char) charCode);
    }
    
    private void updateMostUsed(){
        Log.d("Emotibox", "Fetching most used");
        Cursor c = mDbHelper.fetchMostUsed();
        if (c != null) {
            int i = 0;
            int count = c.getCount();
            boolean remain = true;
            Log.d("Emotibox", "Processing " + count + " records");
            if (count > 0){
                c.moveToFirst();
                while (remain){
                    Log.d("Emotibox --MostUsed", "Key: "+ c.getInt(0) + " - Value: " + c.getInt(1));
                    mostUsed[i] = int2str(c.getInt(0));
                    remain = c.moveToNext();
                    i++;
                }
            }
        }
        c.close();
        for (int x = 0; x < 5; x++){
            btnMostUsed[x].setText(mostUsed[x]);
            btnMostUsed[x].setTag(mostUsed[x]);
            if (mostUsed[x] == ""){
                btnMostUsed[x].setClickable(false);
            }
        }
    }
    
    private void moveRight(){
        panel.moveRight();
        setTabTitle( panel.getCurrentIndex() );
    }
    
    private void moveLeft(){
        panel.moveLeft();
        setTabTitle( panel.getCurrentIndex() );
    }

}

