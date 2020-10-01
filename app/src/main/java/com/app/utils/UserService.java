package com.app.utils;

import com.app.model.Access;
import com.app.model.User;
import com.app.report.TypeReport;
import com.app.report.UserReport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users")
    Call<List<User>> getAll();

    @GET("users/{id}")
    Call<User> getById(@Path("id") Integer id);

    @POST("users")
    Call<User> add(@Body User user);

    @PUT("users")
    Call<List<User>> update(@Body User user);

    @DELETE("users/{id}")
    Call<List<User>> delete(@Path("id") Integer id);

    @GET("access/{id}")
    Call<List<Access>> getAccess(@Path("id") Integer id);

    @GET("report/type-access-quantity")
    Call<List<TypeReport>> getTypeAccessQuantity();

    @GET("report/user-access-quantity")
    Call<List<UserReport>> getUserAccessQuantity();

}
