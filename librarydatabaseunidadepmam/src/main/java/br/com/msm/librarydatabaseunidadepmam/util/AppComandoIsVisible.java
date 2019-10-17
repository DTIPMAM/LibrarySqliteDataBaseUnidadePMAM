package br.com.msm.librarydatabaseunidadepmam.util;

/**
 * Created by MSM on 06/11/2017.
 */

public class AppComandoIsVisible {

    public static boolean isActivityVisible(String nameActivity) {
        if(nameActivity.equals(activity) && activityVisible){
            return true;
        }else {
            return false;
        }

    }

    public static void activityResumed(String nameActivity) {
        activityVisible = true;
        activity = nameActivity;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
    private static String activity;
}
