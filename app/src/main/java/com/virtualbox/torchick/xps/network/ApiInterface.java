package com.virtualbox.torchick.xps.network;


import com.virtualbox.torchick.xps.model.Publikasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Torchick on 27/03/2017.
 */

public interface ApiInterface {
    @GET("/sidewi/trickster/index.php")
    Call<List<Publikasi>> getPublikasi(@Query("r") String address);
}
