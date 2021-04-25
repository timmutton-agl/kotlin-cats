package au.com.agl.kotlincats.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.domain.SortedPetOwnerData
import kotlinx.android.synthetic.main.item_gender.view.*
import kotlinx.android.synthetic.main.item_pets.view.*

class RecyclerAdapter(private val list: List<SortedPetOwnerData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //Will return holder : If gender value is available then will return Gender Holder, else return Pet Holder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> {
                val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_gender, parent, false)
                return GenderHolder(viewItem)
            }
            1 -> {
                val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_pets, parent, false)
                return PetHolder(viewItem)
            }
            else -> {
                val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_pets, parent, false)
                return PetHolder(viewItem)
            }
        }
    }

    // Will involve populating data into the item through holder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //Attach respective list values to respective holder

        (holder as? GenderHolder)?.let {
            holder.genData(list[position])
        }

        (holder as? PetHolder)?.let {
            holder.petData(list[position])
        }

    }

    // Will return the total count of items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (list[position].genGroup != "") {
            0
        } else {
            1
        }
    }
}

//Holder to hold gender values
class GenderHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

    private var gender: TextView = v.gender
    var sortedPetOwnerData: SortedPetOwnerData? = null

    fun genData(sortedGender: SortedPetOwnerData) {
        sortedPetOwnerData = sortedGender
        gender.text = " * " +sortedPetOwnerData?.genGroup
    }
}

//Holder to hold Pet values
class PetHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

    private var petNames: TextView = v.pets
    var sortedPetOwnerData: SortedPetOwnerData? = null

    fun petData(sortedPetNames: SortedPetOwnerData) {
        sortedPetOwnerData = sortedPetNames
        petNames.text = " - " +sortedPetOwnerData?.petGroup
    }
}