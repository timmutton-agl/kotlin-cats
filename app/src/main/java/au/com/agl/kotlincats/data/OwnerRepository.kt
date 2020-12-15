package au.com.agl.kotlincats.data

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.model.Owner

interface OwnerRepository {
    fun get(callback: Callback<List<Owner>>)
}