/**
 * This file is part of MIUIDevThemeBrowser.
 *
 *  MIUIDevThemeBrowser is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MIUIDevThemeBrowser is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MIUIDevThemeBrowser.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Copyright (c) 2010 miui-dev.com, All rights reserved.
 *  Author: n_i_x (nix@miui-dev.com)
 *  Website: http://www.miui-dev.com
 */

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

