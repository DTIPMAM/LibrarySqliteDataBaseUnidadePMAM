package br.com.msm.librarydatabaseunidadepmam.util;


import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;


public class Util {



    public static MaterialDialog Progress(Context ctx) {

        MaterialDialog p = new MaterialDialog.Builder(ctx)
                .content("Aguarde...")
                .progress(true, 0).build();
        return p;
    }

    public static boolean isOnline() {

        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53); //www.google.com
                    //  SocketAddress sockaddr = new InetSocketAddress("45.40.132.20", 53); //pmam.online

                    sock.connect(sockaddr, timeoutMs);
                    sock.close();

                    return true;
                } catch (IOException e) { return false; }
            }
        };
        AsyncTask<Void, Void, Boolean> ret = task.execute();
        try {
            return ret.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return  false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  false;
        }

    }

}

