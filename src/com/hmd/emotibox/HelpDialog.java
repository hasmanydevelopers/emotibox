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
        + "Emotibox let you pimp your Social Network experience with UTF8 characters.\r\n\r\n"
        + "1. Look at the tabs for your favorite character.\r\n\r\n"
        + "2. Tap the char to copy it to the clipboard (don't worry if you see a invalid char, it should appear correctly in the browser.\r\n\r\n"
        + "3. Go to your Social Network app and paste it.\r\n\r\n"
        + "4. Enjoy!";
    
    public HelpDialog(final Context context) {
        super(context);
        
        this.setTitle("Help");
        this.setMessage(INSTRUCTIONS);
        this.setCancelable(true);
        this.setIcon(R.drawable.ic_menu_help);
    }
}
/*

import android.view.LayoutInflater;
import android.view.View;

public class HelpDialog extends AlertDialog.Builder {
    final String INSTRUCTIONS = ""
        + "Emotibox let you pimp your Social Network experience with UTF8 characters.\r\n\r\n"
        + "1. Look at the tabs for your favorite character.\r\n\r\n"
        + "2. Tap the char to copy it to the clipboard (don't worry if you see a invalid char, it should appear correctly in the browser.\r\n\r\n"
        + "3. Go to your Social Network app and paste it.\r\n\r\n"
        + "4. Enjoy!";
    
    public HelpDialog(Context context) {
        super(context);
        
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.help, null);

        TextView title = (TextView) layout.findViewById(R.id.help_title);
        title.setText("Help");
        TextView msg = (TextView) layout.findViewById(R.id.help_message);
        msg.setText(INSTRUCTIONS);

        this.setView(layout);
    }
}
*/
