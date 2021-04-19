package au.com.agl.kotlincats.presentation

import android.util.Log
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerApi
import au.com.agl.kotlincats.data.OwnerNetworkRepository
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.domain.MainFacade
import au.com.agl.kotlincats.domain.MainUseCases
import au.com.agl.kotlincats.domain.model.DataLine
import au.com.agl.kotlincats.presentation.model.DisplayLine
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

interface MainPresenting {
    fun viewLoaded()
    fun viewUpdated()
}

class MainPresenter(val display: MainDisplay): MainPresenting {

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val activeClient = OkHttpClient().newBuilder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    init {
    }

    override fun viewLoaded() {

        //**** Should be in Service *****************
        val retrofit = Retrofit.Builder()
            .client(activeClient)
            .baseUrl("https://agl-developer-test.azurewebsites.net/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()))
            .build()
        val api = retrofit.create(OwnerApi::class.java)
        val repository: OwnerRepository = OwnerNetworkRepository(api)

        val facade: MainFacade = MainUseCases(repository)
        facade.loadGroupedCats(object: Callback<List<DataLine>> {
            override fun onSuccess(data: List<DataLine>) {
                // TODO: display visually
                Log.d(MainActivity::class.java.simpleName, data.toString())
                display.showList( translateToDisplay(data) )
            }

            override fun onError(error: Throwable) {
                Log.e(MainActivity::class.java.simpleName, error.message ?: "Unknown error")
                display.showError(error.message ?: "Unknown error")
            }
        })
        //*******************************************
    }

    override fun viewUpdated() {

    }

    private fun translateToDisplay(oldData: List<DataLine>): List<DisplayLine> {
        val resultValue: MutableList<DisplayLine> = mutableListOf()

        val dataComparator = Comparator { item1: DataLine, item2: DataLine ->
            if (item1.gender == item2.gender) {
                return@Comparator item1.petName?.compareTo(item2.petName ?: "") ?: "".compareTo(item2.petName ?: "")
            } else {
                return@Comparator item1.gender?.compareTo(item2.gender ?: "") ?: "".compareTo(item2.gender ?: "")
            }
        }

        val sortedData = oldData.sortedWith(dataComparator)

        var activeGroup: String? = null
        sortedData.forEach { dataItem ->
            if (dataItem.gender != activeGroup) {
                activeGroup = dataItem.gender
                resultValue.add( DisplayLine(dataItem.gender, null) )
            }
            resultValue.add( DisplayLine(null, dataItem.petName) )
        }

        return resultValue
    }
}