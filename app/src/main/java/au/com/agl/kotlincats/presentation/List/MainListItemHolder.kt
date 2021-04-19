package au.com.agl.kotlincats.presentation.List

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.agl.kotlincats.presentation.model.DisplayLine
import kotlinx.android.synthetic.main.view_line_cell.view.*

class MainListItemHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

    private var titleText: TextView = v.catName

    var itemData: DisplayLine? = null

    fun updateView(newData: DisplayLine) {

        itemData = newData
        titleText.text = itemData?.name
    }
}
