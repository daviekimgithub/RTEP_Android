package com.example.rtepandroid.retrofit

import com.example.rtepandroid.models.Department
import com.example.rtepandroid.models.Employee
import com.example.rtepandroid.models.Employee1
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoints {

//    @GET("/api/users/")
//    fun getUsersWithEmail(@Query("filters[email][\$eq]") email: String): Call<List<UserGet>>

    @GET("/api/departments/")
    fun getDepartments(): Call<List<Department>>

    @POST("/api/employees/")
    fun createEmployee(@Body employee: Employee): Call<Employee>


//    @FormUrlEncoded
//    @POST("/api/users/")
//    fun postUser(
//        @Field("email") email:String,
//        @Field("password") password:String,
//        @Field("username") username:String
//    ): retrofit2.Call<UserPost>
//
//    @FormUrlEncoded
//    @POST("/api/users/")
//    fun postUpdatedProfile(
//        @Field("email") email:String,
//        @Field("fullname") fullname:String,
//        @Field("gender") gender:String,
//        @Field("imageurl") imageurl:String,
//        @Field("phoneNumber") phoneNumber:String,
//        @Field("username") username:String
//    ): retrofit2.Call<UserFillProfile>
//
//
//    @FormUrlEncoded
//    @POST("/api/auth/local")
//    fun postLogin(
//        @Field("identifier") identifier:String,
//        @Field("password") password:String
//    ): retrofit2.Call<Login>
//
//
//

//    @GET("/api/hotels/{pk}/")
//    fun getAHotel(@Path("pk") pk: String): Call<Hotel>
//    // confirming guest existance in LoginActivity
//    @GET("/api/guests/")
//    fun getGuests(): Call<List<Guest>>
//
//    @GET("/api/guests/{pk}/")
//    fun getAGuest(@Path("pk") pk: String): Call<Guest>
//
//    @GET("/api/rooms/")
//    fun getRooms(): Call<List<Room>>
//
//    @GET("/api/rooms/{pk}/")
//    fun getARoom(@Path("pk") pk: String): Call<Room>
//
//    //checking any existence of room and hotel in reservation
//    @GET("/api/reservations/")
//    fun checkRoomOccupied():Call<List<Rerservation>>
//



//    @POST("api/guests/")
//    suspend fun postMyGuest(@Body requestBody: RequestBody):Response<ResponseBody>


//    @FormUrlEncoded
//    @POST("/api/guests/")
//    fun postGuest(
//        @Field("id") id:String,
//        @Field("first_name") first_name:String,
//        @Field("last_name") last_name:String,
//        @Field("id_no") id_no:String,
//        @Field("phone_no") phone_no:String,
//        @Field("gender") gender:String,
//        @Field("email") email:String,
//        @Field("password") password:String,
//        @Field("created_at") created_at:String
//
//    ): Call<Guest>
//    // to check guest while posting guest in Register Activity
//    @GET("/api/guests/")
//    fun checkGuestExistance():Call<List<Guest>>
//
//    @FormUrlEncoded
//    @POST("/api/reservations/")
//    fun postReservation(
//        @Field("attended") attended: Boolean,
//        @Field("cancelled") cancelled: Boolean,
//        @Field("checkin_date") checkin_date: Any,
//        @Field("checkout_date") checkout_date: Any,
//        @Field("created_at") created_at: String,
//        @Field("expected_checkin_date") expected_checkin_date: String,
//        @Field("guest") guest: String,
//        @Field("hotel") hotel: String,
//        @Field("id") id: String,
//        @Field("room") room: String
//    ): Call<Reservation>

}