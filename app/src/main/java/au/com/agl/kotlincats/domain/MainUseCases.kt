package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.data.model.Owner

class MainUseCases(private val ownerRepository: OwnerRepository): MainFacade {
    override fun loadGroupedCats(callback: Callback<List<PetOwnerData>>) {
        ownerRepository.get(object: Callback<List<Owner>> {
            override fun onSuccess(data: List<Owner>) {
                // TODO: manipulate data as appropriate and feed into callback
                //Manipulated JSON data to look particularly for pet type : Cat
                //Create a list containing respective cat(s) owner's gender
                val list: MutableList<PetOwnerData> = mutableListOf()
                data.forEach { owners ->
                    owners.pets?.forEach { pets ->
                        if (pets.type == "Cat") {
                            val manipulatedList = PetOwnerData(owners.gender, pets.name)
                            list.add(manipulatedList)
                        }
                    }
                }
                callback.onSuccess(list)
            }

            override fun onError(error: Throwable) {
                callback.onError(error)
            }
        })
    }
}

data class PetOwnerData(val gender:String, val petName: String)
data class SortedPetOwnerData(val genGroup:String, val petGroup: String)
