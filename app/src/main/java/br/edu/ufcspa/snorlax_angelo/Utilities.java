package br.edu.ufcspa.snorlax_angelo;

import android.os.Environment;
import android.os.StatFs;

/**
 * Created by icaromsc on 29/03/2017.
 */

public class Utilities {


    /**
     * @return Number of Mega bytes available on External storage
     */
    public static long getAvailableSpaceInMB(){
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace/SIZE_MB;
    }

    public static String getPhoneModel(){
        return android.os.Build.MODEL;
    }

    public static String getAndroidVersion(){
        return android.os.Build.VERSION.RELEASE;
    }





}
