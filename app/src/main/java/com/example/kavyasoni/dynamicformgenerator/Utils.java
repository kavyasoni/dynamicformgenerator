package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by kavyasoni on 27/10/15.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static String parseFileToString(Context context, String filename) {
        try {
            InputStream stream = context.getAssets().open(filename);
            int size = stream.available();

            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();

            return new String(bytes);

        } catch (IOException e) {
            Log.i(TAG, "IOException: " + e.getMessage());
        }
        return null;
    }

    // Don't delete! Even if not used!
    public static String convertToJSON(Object object) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        final Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(object);
        Log.i(TAG, "convertToJSON : " + jsonString);
        return jsonString;
    }

    // Don't delete! Even if not used!
    public static <T> T convertToObject(String responseString, Type typeOfT) {
        Log.i(TAG, "convertToObject : " + responseString);
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(responseString, typeOfT);
    }

    // Don't delete! Even if not used!
    public static <T> T convertToObject(String responseString, Class<T> classOfT) {
        Log.i(TAG, "convertToObject : " + responseString);
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(responseString, classOfT);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes
                        .getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        gsonBuilder
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(
                            FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes
                                .getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                });
        return gsonBuilder;
    }
}
