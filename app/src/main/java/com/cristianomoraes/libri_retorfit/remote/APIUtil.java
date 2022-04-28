package com.cristianomoraes.libri_retorfit.remote;

public class APIUtil {

    public APIUtil() {
    }

    public static final String API_URL = "http://10.107.144.17:3000";

    public static RouterInterface getUsuarioInterface(){

        return RetrofitClient.getClient(API_URL)
                .create(RouterInterface.class);

    }

}







