package au.com.agl.kotlincats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerApi
import au.com.agl.kotlincats.data.OwnerNetworkRepository
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.domain.MainFacade
import au.com.agl.kotlincats.domain.MainUseCases
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://agl-developer-test.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
            .build()
        val api = retrofit.create(OwnerApi::class.java)
        val repository: OwnerRepository = OwnerNetworkRepository(api)

        val facade: MainFacade = MainUseCases(repository)
        facade.loadGroupedCats(object: Callback<Any>{
            override fun onSuccess(data: Any) {
                // TODO: display visually
                Log.d(MainActivity::class.java.simpleName, data.toString())
            }

            override fun onError(error: Throwable) {
                Log.e(MainActivity::class.java.simpleName, error.message ?: "Unknown error")
            }
        })
    }
}
