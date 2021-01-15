package com.svs.riakotlin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svs.riakotlin.R
import com.svs.riakotlin.models.DataMod
import kotlinx.android.synthetic.main.adapter_item.view.*


class ListAdapter(var listData: ArrayList<DataMod>, val mContext: Context,
                  val listener: (DataMod, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DefaultViewHOlder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_NORMAL
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is DefaultViewHOlder){
            holder.bind(listData[position], mContext, listener)
        }

    }


   inner class DefaultViewHOlder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(resp: DataMod, mContext: Context, listener: (DataMod, Int) -> Unit)= with(itemView) {

            Log.e("aksjlak","${resp}")

            tvDesc.text = resp.description
            tvTitle.text = resp.title

            itemView.setOnClickListener {
                listener(resp,0)
            }
        }
    }


    fun updateList(list:ArrayList<DataMod>){
        this.listData=list
        notifyDataSetChanged()
    }
    
    companion object{
        const val TYPE_NORMAL=1
    }

}