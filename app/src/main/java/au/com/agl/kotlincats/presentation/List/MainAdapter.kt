package au.com.agl.kotlincats.presentation.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.presentation.model.DisplayLine

class MainAdapter(private val listData: List<DisplayLine>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> {
                val viewItem = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_group_cell, parent, false)
                return MainListGroupHolder(viewItem)
            }
            1 -> {
                val viewItem = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_line_cell, parent, false)
                return MainListItemHolder(viewItem)
            }
            else -> {
                val viewItem = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_line_cell, parent, false)
                return MainListItemHolder(viewItem)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as? MainListGroupHolder)?.let {
            holder.updateView(listData[position])
        }

        (holder as? MainListItemHolder)?.let {
            holder.updateView(listData[position])
        }
    }

    override fun getItemCount(): Int {

        return listData.count()
    }

    override fun getItemViewType(position: Int): Int {

        return if (listData[position].group != null) {
            0
        } else {
            1
        }
    }
}
