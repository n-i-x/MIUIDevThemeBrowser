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
	
    private String theme_name;
    private String theme_url;
    private String theme_author;
    private String theme_preview_url;
    private long theme_size;
    private String theme_version;
    private String[] theme_screenshot_urls;
   
    public String getThemeName() {
        return theme_name;
    }
    public void setThemeName(String theme_name) {
        this.theme_name = theme_name;
    }
    public String getThemeURL() {
        return theme_url;
    }
    public void setThemeURL(String theme_url) {
        this.theme_url = theme_url;
    }
    public String getThemeAuthor() {
        return theme_author;
    }
    public void setThemeAuthor(String theme_author) {
        this.theme_author = theme_author;
    }
    public String getThemePreviewURL() {
        return theme_preview_url;
    }
    public void setThemePreviewURL(String theme_preview_url) {
        this.theme_preview_url = theme_preview_url;
    }
    public long getThemeSize() {
        return theme_size;
    }
    public void setThemeSize(long theme_size) {
        this.theme_size = theme_size;
    }
    public String getThemeVersion() {
        return theme_version;
    }
    public void setThemeVersion(String theme_version) {
        this.theme_version = theme_version;
    }
    public String[] getThemeScreenshotURLs() {
        return theme_screenshot_urls;
    }
    public void setThemeScreenshotURLs(String[] theme_screenshot_urls) {
        this.theme_screenshot_urls = theme_screenshot_urls;
    }
}
