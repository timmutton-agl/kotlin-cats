package au.com.agl.kotlincats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.presentation.List.MainAdapter
import au.com.agl.kotlincats.presentation.model.DisplayLine
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainDisplay {

    private var viewAdapter: MainAdapter? = null
    val presenter: MainPresenting

    init {
        presenter = MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        presenter.viewLoaded()
    }

    //Display interface
    override fun showList(newData: List<DisplayLine>) {
        Log.d(MainActivity::class.java.simpleName, newData.toString())
        viewAdapter = MainAdapter(newData, catsList)
        catsList.adapter = viewAdapter
    }

    override fun showError(newMessage: String) {
        viewAdapter = null
        catsList.adapter = viewAdapter
        Toast.makeText(this, newMessage, Toast.LENGTH_LONG).show()
    }

    //Private functions
    private fun setupUI() {
        val layoutManager = LinearLayoutManager(this)
        catsList.layoutManager = layoutManager
        catsList.adapter = null
    }
}
