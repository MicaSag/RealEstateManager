package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.threeten.bp.LocalDateTime;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {
    
    // For Debug
    private static final String TAG = Utils.class.getSimpleName();

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.91);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * arrondi à 2 chiffres après la virgules
     *
     * @param euros
     * @return
     */
    public static BigDecimal convertEuroToDollar(BigDecimal euros)
    {
        BigDecimal result = euros.multiply(new BigDecimal(1.09));
        return result.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        int[] networkTypes = {  ConnectivityManager.TYPE_MOBILE,
                                ConnectivityManager.TYPE_WIFI};
        try {
            // Get ConnectivityManager
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // // Look if the connection is a Wifi or Mobile connection
            for (int networkType : networkTypes) {
                // Get ActiveNetworkInfo
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                // see if a network connection is established and Wifi or Mobile connection
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Start an Activity
     *
     * @param  context   : context of the application
     *         className : className of the activity called
     * @return none
     */
    public static void startActivity(Context context, Class className){

        // Create a intent for call Activity
        Intent intent = new Intent(context, className);

        // Call RestaurantCardActivity
        context.startActivity(intent);
    }

    /**
     * Start an Activity with Key
     *
     * @param  context   : context of the application
     *         className : className of the activity called
     *         key       : receiving key of the activity called
     *         keyValue  : sent key content
     * @return none
     */
    public static void startActivity(Context context, Class className, String key, String keyValue){

        // Create a intent for call Activity
        Intent intent = new Intent(context, className);

        // ==> Sends the Restaurant details
        if (!(key == null)) intent.putExtra(key,keyValue);

        // Call RestaurantCardActivity with 1 parameter
        context.startActivity(intent);
    }

    /**
     * Converter a LocalDateTime in String format
     *
     * @param  localDateTime   : date in LocalDateTime format
     * @return date at format jj/mm/ssaa
     */
    public static String fromLocalDateTime(LocalDateTime localDateTime) {
        Log.d(TAG, "fromLocalDateTime() called with: localDateTime = [" + localDateTime.toString() + "]");
        String date = localDateTime.toString();
        return date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);
    }
}
