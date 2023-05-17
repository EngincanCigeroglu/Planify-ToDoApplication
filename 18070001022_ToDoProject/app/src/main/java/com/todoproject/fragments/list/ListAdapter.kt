package com.todoproject.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.todoproject.R
import com.todoproject.database.models.MyData
import com.todoproject.database.models.Priority
import com.todoproject.databinding.CardLayoutBinding
import java.text.SimpleDateFormat

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<MyData>()

    class MyViewHolder(private val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTextView = binding.titleTxt
        val descriptiontext = binding.descriptionTxt
        val priority = binding.priorityText
        val cardview = binding.CardListView
        val mydate = binding.dateText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.mydate.text = currentItem.date
        holder.titleTextView.text = currentItem.title
        holder.descriptiontext.text = currentItem.description
        holder.cardview.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        val priorityColor =
            when (currentItem.priority)
                {
                Priority.HIGH -> ContextCompat.getColor(holder.itemView.context, R.color.red)
                Priority.MEDIUM -> ContextCompat.getColor(holder.itemView.context, R.color.yellow)
                Priority.LOW -> ContextCompat.getColor(holder.itemView.context, R.color.green)
                /*Priority.LOW -> holder.priority.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))  */
            }
            holder.priority.setTextColor(priorityColor)

        when (currentItem.priority) {
            Priority.HIGH -> holder.priority.text = "High"
            Priority.MEDIUM -> holder.priority.text = "Medium"
            Priority.LOW -> holder.priority.text = "Low"
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(myData: List<MyData>)
    {
        val appDiffUtil = AppDiffUtil(dataList, myData)
        val diffUtilResult = DiffUtil.calculateDiff(appDiffUtil)
        this.dataList = myData
        diffUtilResult.dispatchUpdatesTo(this)
    }


/*

    class MViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: MyData) {
            binding.myData = toDoData
            binding.executePendingBindings()
        }

   var dataList = emptyList<MyData>()



        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(
            parent
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(toDoData: List<MyData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }*/
}