package com.example.car_rental;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void>
{
    String data,data_1;
    String dataParsed = "";
    String CurrentDate = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://worldtimeapi.org/api/timezone/Europe/Tirane");

            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while(line != null)
            {

                line = bufferedReader.readLine();
                data = data + line;
            }

            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();


            JsonObject jsonObject = jsonParser.parse(data.replace("null","")).getAsJsonObject();
            CurrentDate = jsonObject.get("datetime").toString();
            CurrentDate = (CurrentDate.split("T"))[0].replace("\"","");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Cart.data = CurrentDate ;
    }
}
