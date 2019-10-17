package br.com.msm.librarydatabaseunidadepmam.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static com.msm.themes.util.Util.Tag;


public class Image {

    public static String MyPrefUploadImg = "MyPrefUploadImg";



    public  static File getFileImageCache(Context ctx, String uid) {
        // Get the data from an ImageView as bytes

        return new File(ctx.getCacheDir(), uid + "img.jpg");


    }

    public static boolean getMyPrefUploadImg(Context ctx) {

        SharedPreferences sharedpreferences = ctx.getSharedPreferences(MyPrefUploadImg, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("isUpload", false);

    }

    public static void setMyPrefUploadImg(Context ctx, boolean isUpload) {


        SharedPreferences sharedpreferences = ctx.getSharedPreferences(MyPrefUploadImg, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isUpload", isUpload);
        editor.commit();
    }

    public  static boolean saveImageCache(Context ctx,Bitmap bitmap, File file) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            Tag(ctx, "sucesso ao criar arquvio png");
            return  true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Tag(ctx, "sucesso ao criar arquvio png" + e.getMessage());
            return false;
        }

    }

}
