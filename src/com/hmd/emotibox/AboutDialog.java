package com.hmd.emotibox;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button; 
import android.widget.TextView;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import android.os.Bundle;
import android.net.Uri;
import android.content.Intent;

public class AboutDialog extends AlertDialog.Builder {
    
    public AboutDialog(final Context context) {
        super(context);
        
        this.setTitle("About Emotibox");
        this.setMessage("has_many :developers");
        this.setCancelable(false);
        this.setIcon(R.drawable.ic_menu_info_details);
        
        this.setPositiveButton("Website", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Uri uriUrl = Uri.parse("http://emotibox.com.ve/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });

        this.setNegativeButton("Repository", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Uri uriUrl = Uri.parse("http://github.com/hmd/emotibox");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });
    }
}


