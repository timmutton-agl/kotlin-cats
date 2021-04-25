package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback

//Will make use of manipulated owner-pet relation list
interface MainFacade {
    fun loadGroupedCats(callback: Callback<List<PetOwnerData>>)
}