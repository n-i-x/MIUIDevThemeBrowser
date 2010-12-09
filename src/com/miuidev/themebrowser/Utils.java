package com.miuidev.themebrowser;

import android.content.Context;

public class Utils {

    public static String formatSize(Context mContext, long sizeInBytes) {

    	String[] sizes = mContext.getResources().getStringArray(R.array.sizes);
    	String readableFileSize = null;
    	
    	for(String size : sizes) {
    		if (sizeInBytes < 1024.0) {
    			readableFileSize = Long.toString(sizeInBytes) + size;
    			break;
    		}
    		
    		sizeInBytes /= 1024.0;
    	}
    	
    	return readableFileSize;
    }
}
