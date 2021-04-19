package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.domain.model.DataLine

class MainUseCases(private val ownerRepository: OwnerRepository): MainFacade {
    override fun loadGroupedCats(callback: Callback<List<DataLine>>) {
        ownerRepository.get(object: Callback<List<Owner>> {
            override fun onSuccess(data: List<Owner>) {
                // TODO: manipulate data as appropriate and feed into callback

                callback.onSuccess( translateData(data) )
            }

            override fun onError(error: Throwable) {
                callback.onError(error)
            }
        })
    }

    private fun translateData(oldData: List<Owner>): List<DataLine> {
        val resultValue: MutableList<DataLine> = mutableListOf()

        oldData.forEach { ownerData ->
            ownerData.pets?.forEach { petData ->
                if (petData.type == "Cat") {
                    val newIem = DataLine(ownerData.gender, petData.name)
                    resultValue.add(newIem)
                }
            }
        }

        return resultValue
    }
}
