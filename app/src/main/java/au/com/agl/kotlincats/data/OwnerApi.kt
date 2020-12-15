package au.com.agl.kotlincats.data

import au.com.agl.kotlincats.data.model.Owner
import retrofit2.Call
import retrofit2.http.GET

interface OwnerApi {
    @GET("people.json")
    fun getOwners(): Call<List<Owner>>
}