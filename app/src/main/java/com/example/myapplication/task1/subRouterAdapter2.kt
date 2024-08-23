package com.example.myapplication.task1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.DevicesItem
import com.example.myapplication.data.RoutersItem
import com.google.android.material.divider.MaterialDivider

class subRouterAdapter2(val data: List<RoutersItem>,val pRouterId: String = "") : RecyclerView.Adapter<subRouterAdapter2.DataViewHolder>() {

    val subRouterData: MutableList<RoutersItem> = mutableListOf()


    init {
        data.forEach {
            if (it.connectedTo!=null){
                if (it.connectedTo.routerId == pRouterId){
                    subRouterData.add(it)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemData = subRouterData[position]
        if (position == subRouterData.size-1){
            holder.bind(itemData,data,true)
            holder.conBottomMainView.visibility = View.GONE
        }else{
            holder.bind(itemData,data,false)
        }
        println("check connected to data"+subRouterData[position].connectedTo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.router_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subRouterData.size
    }

    inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.routerNameTV)
        private val connectionMainView: ConstraintLayout = itemView.findViewById(R.id.connectionMainView)
         val conBottomMainView: View = itemView.findViewById(R.id.conBottomMainView)
        private val conDeviceRecyclerView: RecyclerView = itemView.findViewById(R.id.devicesConnectedRV)

        fun bind(itemData: RoutersItem, data: List<RoutersItem>,isLastItem:Boolean) {
            textView.text = "${itemData.routerName}"
            conDeviceRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            connectionMainView.visibility = View.VISIBLE
            val subData : MutableList<RoutersItem> = mutableListOf()
//            val routerId = itemData.routerId

//            data.forEach {
//                println("each data in main list"+it)
//                if (it.connectedTo!=null){
//                    if (it.connectedTo.routerId.equals("$routerId")){
//                        subData.add(it)
//                    }
//                }
//            }


            itemData?.let {
                val adapter = subDeviceAdapter2( it.devices,data,itemData.routerId,isLastItem)
                conDeviceRecyclerView.adapter = adapter
            }

        }
    }

}

class subDeviceAdapter2(
    val deviceData: List<DevicesItem>,
    val data: List<RoutersItem>,
    val pRouterId: String,
    val isLastItem: Boolean
): RecyclerView.Adapter<subDeviceAdapter2.DataViewHolder>(){
     val subData : MutableList<RoutersItem> = mutableListOf()


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemData = deviceData[position]
        holder.bind(itemData)

        holder.subRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)

        if(position == deviceData.size-1){

            data.forEach {
                if (it.connectedTo!=null){
                    if (it.connectedTo.routerId == pRouterId){
                        subData.add(it)
                    }
                }
            }

            println("check the sub router data -"+subData)
            subData.let {
                val adapter = subRouterAdapter2(data,pRouterId)
                holder.subRecyclerView.adapter = adapter
            }
            if (subData.size==0){
                holder.bottomView.visibility = View.GONE
            }
        }else{
            holder.bottomView.visibility = View.VISIBLE
        }

        println("check the device position ${position} equals device list position - 1 ${deviceData.size-1}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.connect_device_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return deviceData.size
    }

    inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.conDeviceNameTV)
        val bottomView: View = itemView.findViewById(R.id.conBottomView)
        val conSubRouterCL: ConstraintLayout = itemView.findViewById(R.id.conSubRouterCL)
        val subRecyclerView: RecyclerView = itemView.findViewById(R.id.subRecyclerView)
        val longLineView: View = itemView.findViewById(R.id.longLineView)

        init {
            if (isLastItem){
                longLineView.visibility = View.GONE
            }
        }

        fun bind(itemData: DevicesItem) {
            textView.text = "${itemData.name}"
            conSubRouterCL.setBackgroundColor(Color.BLACK)
        }
    }



}

