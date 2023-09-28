package exercise.memo_app.AddMemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import exercise.memo_app.databinding.MemoListBinding

class ItemAdapter (val onItemClicked : (MemoModel) -> Unit): ListAdapter<MemoModel, ItemAdapter.ViewHolder>(
    diffUtil
){

    inner class ViewHolder(private val view:MemoListBinding):RecyclerView.ViewHolder(view.root)
    {
        fun bind(memoModel : MemoModel){
            view.titleTextView.text = memoModel.title

            view.root.setOnClickListener{
                onItemClicked(memoModel)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MemoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object{

        val diffUtil = object : DiffUtil.ItemCallback<MemoModel>(){
            override fun areItemsTheSame(oldItem: MemoModel, newItem: MemoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MemoModel, newItem: MemoModel): Boolean {
                return oldItem == newItem
            }

        }
    }



}

