package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.data.model.Owner

class MainUseCases(private val ownerRepository: OwnerRepository): MainFacade {
    override fun loadGroupedCats(callback: Callback<Any>) {
        ownerRepository.get(object: Callback<List<Owner>> {
            override fun onSuccess(data: List<Owner>) {
                // TODO: manipulate data as appropriate and feed into callback
                callback.onSuccess(data)
            }

            override fun onError(error: Throwable) {
                callback.onError(error)
            }
        })
    }
}
