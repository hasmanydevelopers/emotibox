package com.hmd.tk4a;

import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TabHost;
import android.content.Intent;
import android.widget.TextView;
import android.app.TabActivity;
import android.content.res.Resources;

import com.hmd.tk4a.FirstTabActivity;
import com.hmd.tk4a.SecondTabActivity;
import com.hmd.tk4a.ThirdTabActivity;

public class Tk4a extends TabActivity {
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, FirstTabActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("first");
        spec.setIndicator("One", res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, SecondTabActivity.class);
        spec = tabHost.newTabSpec("second");
        spec.setIndicator("Two", res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ThirdTabActivity.class);
        spec = tabHost.newTabSpec("third");
        spec.setIndicator("Three", res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
