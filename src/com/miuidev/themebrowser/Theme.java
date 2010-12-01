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

public class Theme {
	   
    private String themeName;
    private String themeAuthor;
    private String themeVersion;
    private String themeSize;
    private String themeURL;
    private String themePreviewURL;
   
    public String getThemeName() {
        return themeName;
    }
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
    public String getThemeAuthor() {
        return themeAuthor;
    }
    public void setThemeAuthor(String themeAuthor) {
        this.themeAuthor = themeAuthor;
    }
    public String getThemeVersion() {
        return themeVersion;
    }
    public void setThemeVersion(String themeVersion) {
        this.themeVersion = themeVersion;
    }
    public String getThemeSize() {
        return themeSize;
    }
    public void setThemeSize(String themeSize) {
        this.themeSize = themeSize;
    }
    public String getThemeURL() {
        return themeURL;
    }
    public void setThemeURL(String themeURL) {
        this.themeURL = themeURL;
    }
    public String getThemePreviewURL() {
        return themePreviewURL;
    }
    public void setThemePreviewURL(String themePreviewURL) {
        this.themePreviewURL = themePreviewURL;
    }
}
