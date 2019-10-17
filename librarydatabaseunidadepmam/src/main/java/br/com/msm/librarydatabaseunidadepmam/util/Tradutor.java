package br.com.msm.librarydatabaseunidadepmam.util;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.msm.themes.interfaces.Translation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import static com.msm.themes.util.Util.Tag;


public class Tradutor {

    Context ctx;
    String texto;

    public Tradutor(Context ctx, String texto) {
        this.ctx = ctx;
        this.texto = texto;
    }

    public void setCallback(final Translation callback) {
        Locale ling = Locale.getDefault();
        String idioma = ling.getLanguage();
        String query = null;
        try {
            query = URLEncoder.encode(texto, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://mymemory.translated.net/api/get?q=" + query + "&langpair=en|" + idioma;

        Ion.with(ctx).load(url).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (e == null) {
                    Tag(ctx, result.toString());
                    try {
                        callback.textTranslation(result.get("responseData").getAsJsonObject().get("translatedText").getAsString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        callback.textTranslation("erro");
                    }
                } else {
                    Tag(ctx, e.toString());
                    callback.textTranslation("erro");

                }

            }
        });

    }

}
