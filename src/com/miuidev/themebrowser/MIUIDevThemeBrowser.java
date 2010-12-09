/**
 *  This file is part of MIUIDevThemeBrowser.
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

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

import com.github.droidfu.DroidFuApplication;

public class MIUIDevThemeBrowser extends DroidFuApplication {

        public static final String BASEURL = "http://themes.miui-themes.com/";
        public static final String FEATURED_THEMES_JSON = BASEURL + "featured.json";
        public static final String ALL_THEMES_JSON = BASEURL + "allthemes.json";
        public static final File SDCARD = Environment.getExternalStorageDirectory();
        public static final StatFs SDCARD_STAT = new StatFs(SDCARD.getAbsolutePath());

}
