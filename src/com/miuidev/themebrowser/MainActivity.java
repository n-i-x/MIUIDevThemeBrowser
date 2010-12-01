package com.miuidev.themebrowser;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, RecommendedThemesActivity.class);
        spec = tabHost.newTabSpec("recommendedthemes").setIndicator(getString(R.string.tab_recommended_themes), res.getDrawable(R.drawable.ic_tab_top_picks_unselected)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MostDownloadedThemesActivity.class);
        spec = tabHost.newTabSpec("mostdownloadedthemes").setIndicator(getString(R.string.tab_mostdownloaded_themes)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, AllThemesActivity.class);
        spec = tabHost.newTabSpec("allthemes").setIndicator(getString(R.string.tab_all_themes)).setContent(intent);
        tabHost.addTab(spec);

    }
}

