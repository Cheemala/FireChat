package com.trackbuzz.firebase.networkapi;

import com.trackbuzz.firebase.model.UsersData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by CheemalaCh on 4/15/2018.
 */

public interface APIServices {

    @GET("users.json")
    Call<UsersData> getUsers();

    /*@FormUrlEncoded
    @POST("updateemp.php")
    Call<AttendenceResponse> postAtendnceDetails(@Field("PGNAME") String reqstIdntfr, @Field("empid") String userEmpId, @Field("latitude") String userLatVal, @Field("longitude") String userLongiVal, @Field("image") String userTakenPic, @Field("location") String userLocAdrs, @Field("Direction") String userTimr);
*/

}
