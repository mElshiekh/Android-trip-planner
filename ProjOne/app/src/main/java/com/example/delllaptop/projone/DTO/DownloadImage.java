package com.example.delllaptop.projone.DTO;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.delllaptop.projone.SQLAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dell Laptop on 3/11/2018.
 */

public class DownloadImage extends AsyncTask<String, Void, Integer> {

    String webLink;
    Context cont;
    Trip trip;
    String url;
    SharedPreferences saving;
    SharedPreferences.Editor edit;

    public DownloadImage(String webLink, Context cont,Trip trip) {
        this.webLink = webLink;
        this.cont = cont;
        this.trip = trip;
        saving=cont.getSharedPreferences("imgurl",0);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        Log.i("doinback", "2na Guwa il Asymc Task !!!!");
        URL url;
        HttpURLConnection httpURLConnection;
        String point=null;
        String inputString;
        try {
            Log.i("7parseUrl", "7nbd2 ngeeb il JSON YA RAB !!!!");
            url = new URL(webLink);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            Log.i("parseUrl", "7ngeeb il JSON mn il URL !!!!");
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
                Log.i("json", inputString+"bgeeb il Json");
            }
            Log.i("afterJsonParsing", builder.toString());
            String arrayString = builder.toString();
            JSONObject jsonObject = new JSONObject(arrayString);
            JSONArray array = jsonObject.getJSONArray("routes");
            JSONObject routes = array.getJSONObject(0);
            JSONObject overview_polyline = routes.getJSONObject("overview_polyline");
            point = overview_polyline.getString("points");
            ///////////////////
            Bitmap img = null;
            HttpURLConnection connection;
            InputStream is;
            String urlstr = "https://maps.googleapis.com/maps/api/staticmap?size=600x300&center="+trip.getSlat()+
            ","+trip.getSlong()+"|"+trip.getElat()+","+trip.getElong()+
                    "&path=fillcolor:red7Ccolor:red%7Cenc:"+point;
            URL urlimg = new URL(urlstr+"&key=AIzaSyCw_w2LB0TTREWDbUotVkgzHNjDGHTSqHk");
            Log.i("overview_polyline", overview_polyline.toString());
            Log.i("try", urlimg+"");
            connection = (HttpURLConnection) urlimg.openConnection();
            is = connection.getInputStream();
            Log.i("try","0 fkdkfjsdfsdfdsfdsfsdfdsf0000000000000000000");
            img = BitmapFactory.decodeStream(is);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    ,"imge"+trip.getId());
            FileOutputStream out = new FileOutputStream(file);
            Log.i("try","222222222222222222222222222222222222222");
            img.compress(Bitmap.CompressFormat.PNG, 90, out);
            Log.i("try","33333333333333333333333333333333333333");
            out.flush();
            Log.i("try","4444444444444444444444444444444444444444");

            Log.i("urlstr",urlstr);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
/*
        if(s!= null) {
            Toast.makeText(cont, s+"2y 7agga", Toast.LENGTH_LONG).show();
//            Picasso.with(cont)
//                    .load("https://maps.googleapis.com/maps/api/staticmap?size=400x300&center="+url.getStart_lat()+
//                            ","+url.getStart_long()+"|"+url.getEnd_lat()+","+url.getEnd_long()+
//                            "&zoom=11&path=fillcolor:red7Ccolor:red%7Cenc:"+s+"&key=AIzaSyCw_w2LB0TTREWDbUotVkgzHNjDGHTSqHk");

        }else {
            Toast.makeText(cont," il S fady", Toast.LENGTH_LONG).show();
        }*/

    }
}