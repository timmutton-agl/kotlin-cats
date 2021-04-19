package au.com.agl.kotlincats.presentation

import au.com.agl.kotlincats.presentation.model.DisplayLine

interface MainDisplay {
    fun showList(newData: List<DisplayLine>)
    fun showError(newMessage: String)
}