package au.com.agl.kotlincats.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.adapter.RecyclerAdapter
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerApi
import au.com.agl.kotlincats.data.OwnerNetworkRepository
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.domain.MainFacade
import au.com.agl.kotlincats.domain.MainUseCases
import au.com.agl.kotlincats.domain.PetOwnerData
import au.com.agl.kotlincats.domain.SortedPetOwnerData
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
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
        val api = retrofit.create(OwnerApi::class.java)
        val repository: OwnerRepository = OwnerNetworkRepository(api)
        val recyclerView = findViewById<View>(R.id.recycler) as RecyclerView

        val facade: MainFacade = MainUseCases(repository)
            facade.loadGroupedCats(object : Callback<List<PetOwnerData>> {
            override fun onSuccess(data: List<PetOwnerData>) {
                // TODO: display visually
                //Sort gender with relation to pet names
                val comparatorValue = Comparator { firstItem: PetOwnerData, secondItem: PetOwnerData ->
                    if (firstItem.gender == secondItem.gender) {
                        return@Comparator firstItem.petName.compareTo(secondItem.petName ?: "") ?: "".compareTo(secondItem.petName ?: "")
                    }
                    else {
                        return@Comparator firstItem.gender.compareTo(secondItem.gender ?: "") ?: "".compareTo(secondItem.gender ?: "")
                    }
                }
                val sortedPetOwnerData: MutableList<SortedPetOwnerData> = mutableListOf()
                val sortedData = data.sortedWith(comparatorValue)
                //this variable is used to ignore repeated respective gender values getting attached to each an every pet
                var ignoreRepeatedGenderValue: String? = null
                sortedData.forEach { sortItems ->
                    if (sortItems.gender != ignoreRepeatedGenderValue) {
                        ignoreRepeatedGenderValue = sortItems.gender
                        sortedPetOwnerData.add( SortedPetOwnerData(sortItems.gender, "") )
                    }
                    sortedPetOwnerData.add( SortedPetOwnerData("", sortItems.petName) )
                }
                val adapter = RecyclerAdapter(sortedPetOwnerData)
                // Attached the adapter to the recyclerview to populate items
                recyclerView.adapter = adapter
                // Set layout manager to position the items
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                Log.d(MainActivity::class.java.simpleName, data.toString())
            }

            override fun onError(error: Throwable) {
                Log.e(MainActivity::class.java.simpleName, error.message ?: "Unknown error")
            }
        })
    }
}
