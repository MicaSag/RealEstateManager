package com.openclassrooms.realestatemanager.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
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

        // Call RestaurantCardActivity with 3 parameters
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

        // Call RestaurantCardActivity with 3 parameters
        context.startActivity(intent);
    }
}
