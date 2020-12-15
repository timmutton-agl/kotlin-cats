package au.com.agl.kotlincats.data

import android.util.Log
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.model.Owner
import retrofit2.Call
import retrofit2.Response

class OwnerNetworkRepository(private val ownerApi: OwnerApi): OwnerRepository {
    override fun get(callback: Callback<List<Owner>>) {
        ownerApi.getOwners().enqueue (object: retrofit2.Callback<List<Owner>>{
            override fun onResponse(call: Call<List<Owner>>, response: Response<List<Owner>>) {
                if(response.isSuccessful){
                    response.body()?.let(callback::onSuccess)
                } else {
                    Log.e(OwnerNetworkRepository::class.java.simpleName, "${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Owner>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}