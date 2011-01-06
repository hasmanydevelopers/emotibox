package com.hmd.emotibox;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button; 
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class HelpDialog extends AlertDialog.Builder {
    final String INSTRUCTIONS = ""
        + "1. Look up in the tabs for your favorite character.\n\n"
        + "2. Tap the character to copy it to the clipboard. Don't worry if it is not shown, it should appear properly in your browser.\n\n"
        + "3. Go to any Social Network app and paste it.\n\n"
        + "Also the most used characters will be available at the bottom of the first tab.";
    
    public HelpDialog(final Context context) {
        super(context);
        
        this.setTitle("Help");
        this.setMessage(INSTRUCTIONS);
        this.setCancelable(true);
        this.setIcon(R.drawable.ic_menu_help);
    }
}
