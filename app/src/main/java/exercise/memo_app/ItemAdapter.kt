package exercise.memo_app

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import exercise.memo_app.databinding.ItemListBinding

class ItemAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){


        var editTextView = view.findViewById<EditText>(R.id.editTextView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


    }


}