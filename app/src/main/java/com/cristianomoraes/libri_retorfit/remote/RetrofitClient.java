package com.cristianomoraes.libri_retorfit.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient(String url){

        if(retrofit == null){
            /** CRIA E CONFIGURA UM OBJETO DE GSON **/
            Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

            /** CRIA E CONFIGURA O OBJETO DE RETROFIT **/
            retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        }

        return retrofit;

    }

}
