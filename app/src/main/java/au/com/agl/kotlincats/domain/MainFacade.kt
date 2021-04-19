package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.domain.model.DataLine

interface MainFacade {
    fun loadGroupedCats(callback: Callback<List<DataLine>>)
}