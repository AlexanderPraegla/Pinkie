package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.TokenResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TokenApi {


    @FormUrlEncoded
    @Headers({"Cache-Control: no-cache",
            "Content-Type: application/x-www-form-urlencoded"})
    @POST("auth/token")
    Call<TokenResult> refreshToken(@Field("grant_type") String grantType,
                                   @Field("client_id") String clientId,
                                   @Field("client_secret") String clientSecret,
                                   @Field("scope") String scope,
                                   @Field("refresh_token") String refreshToken);

    @FormUrlEncoded
    @Headers({"Cache-Control: no-cache",
            "Content-Type: application/x-www-form-urlencoded"})
    @POST("auth/token")
    Call<TokenResult> getToken(@Field("grant_type") String grantType,
                               @Field("client_id") String clientId,
                               @Field("client_secret") String clientSecret,
                               @Field("scope") String scope);

}
