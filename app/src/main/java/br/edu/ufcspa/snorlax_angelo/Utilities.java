package br.edu.ufcspa.snorlax_angelo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by icaromsc on 29/03/2017.
 *
 * Classe com métodos diversos para ulitização no app
 *
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
    /**
     * @return Smartphone Model
     */
    public static String getPhoneModel(){
        return android.os.Build.MODEL;
    }



    /**
     * @return Android Smartphone Version
     */
    public static String getAndroidVersion(){
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * método para verificar se um serviço esta em andamento no Android
     * @param serviceClass
     * @param ct
     * @return boolean se o serviço esta rodando
     */
    public static boolean isMyServiceRunning(Class<?> serviceClass,Context ct) {
        ActivityManager manager = (ActivityManager) ct.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Método que verifica conexão com internet
     * @param ct
     * @return boolean há conexão com internet
     */

    public static boolean isOnline(Context ct) {
        ConnectivityManager cm =
                (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    /**
     * @print app Keyhash
     */
    public static void getHash(Context ct){
        try {
            PackageInfo info = ct.getPackageManager().getPackageInfo(
                    "br.edu.ufcspa.snorlax_angelo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }





}
