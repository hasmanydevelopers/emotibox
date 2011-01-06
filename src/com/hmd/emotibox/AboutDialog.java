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
import android.content.ComponentName;
import android.content.pm.PackageInfo;

public class AboutDialog extends AlertDialog.Builder {
    
    public AboutDialog(final Context context) {
        super(context);
        
        this.setTitle("Emotibox v" + getVersionName(context, Emotibox.class) );
        this.setMessage("Emotibox let you pimp your Social Network experience with Unicode characters.\n\n"
            + "Created by:\n has_many :developers"
        );
        this.setCancelable(true);
        this.setIcon(R.drawable.ic_dialog_about);
        
        this.setPositiveButton("Website", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Uri uriUrl = Uri.parse("http://hasmanydevelopers.com/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });

        this.setNegativeButton("Repository", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Uri uriUrl = Uri.parse("http://github.com/hasmanydevelopers/emotibox");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });
    }
    
    public static String getVersionName(Context context, Class cls) 
    {
      try {
        ComponentName comp = new ComponentName(context, cls);
        PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
        return pinfo.versionName;
      } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        return null;
      }
    }

}



