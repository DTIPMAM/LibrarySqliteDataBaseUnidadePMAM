package br.com.msm.librarydatabaseunidadepmam.util;


import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Util {



    public static MaterialDialog Progress(Context ctx) {

        MaterialDialog p = new MaterialDialog.Builder(ctx)
                .content("Aguarde...")
                .progress(true, 0).build();
        return p;
    }

    public static boolean isOnline() {
        int timeOut = 3000;
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    return InetAddress.getByName("google.com");
                } catch (UnknownHostException e) {
                    return null;
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {

            e.printStackTrace();
        }
        return inetAddress!=null && !inetAddress.equals("");
    }

}

