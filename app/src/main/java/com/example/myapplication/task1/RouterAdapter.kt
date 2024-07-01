package com.example.myapplication .task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.DevicesItem
import com.example.myapplication.data.RoutersItem

class RouterAdapter(val data: List<RoutersItem>,val isSubRouter: Boolean,val pRouterId:String) :RecyclerView.Adapter<RouterAdapter.DataViewHolder>() {

    val mainData: MutableList<RoutersItem> = mutableListOf()
    var routerId = ""

    init {
        if (isSubRouter==true){
                data.forEach {
                    println("each data in main list"+it)
                    if (it.connectedTo!=null){
                        if (it.connectedTo.routerId.equals("$pRouterId")){
                            mainData.add(it)
                        }
                    }
                }
        }else{
            data.forEach {
                if (it.type=="ROUTER"){
                    mainData.add(it)
                }
            }

        }

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemData = mainData[position]
        holder.bind(itemData,data)
        println("check connected to data"+mainData[position].connectedTo)
//        if(itemData.connectedTo!!.routerId == itemData.routerId){
//
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.router_layout, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainData.size
    }

    inner class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.routerNameTV)
        private val conDeviceRecyclerView: RecyclerView = itemView.findViewById(R.id.devicesConnectedRV)

        fun bind(itemData: RoutersItem, data: List<RoutersItem>) {
            textView.text = "${itemData.routerName}"
            conDeviceRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            val subData : MutableList<RoutersItem> = mutableListOf()
            routerId = itemData.routerId
//            data.forEach {
//                println("each data in main list"+it)
//                if (it.connectedTo!=null){
//                    if (it.connectedTo.routerId.equals("$routerId")){
//                        subData.add(it)
//                    }
//                }
//            }

            itemData?.let {
                val adapter = deviceAdapter( it.devices,data,routerId)
                conDeviceRecyclerView.adapter = adapter
            }

        }
    }

}

class deviceAdapter(
    val deviceData: List<DevicesItem>,
    val subData: List<RoutersItem>,
    val pRouterId: String
):RecyclerView.Adapter<deviceAdapter.DataViewHolder>(){
//     val subData : MutableList<RoutersItem> = mutableListOf()

    init {
//        mainData.forEach{ m ->
////            println(it.routerId+" check connected to "+it.connectedTo?.routerId)
////                println(m.routerId+" check connected1 to "+m.connectedTo!!.routerId)
//                mainData.forEach{
//                    if(it.connectedTo!=null){
//                        if(m.routerId == it.connectedTo.routerId){
//                            subData.add(it)
//                        }
//                    }
//                }
//        }
//
//        println("init the device id exist -"+subData)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemData = deviceData[position]
        holder.bind(itemData)

        holder.subRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)

        if(position == deviceData.size-1){
            println("check the sub router data -"+subData)
            subData?.let {
                val adapter = RouterAdapter(subData!!, true,pRouterId)
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

    inner class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.conDeviceNameTV)
        val bottomView: View = itemView.findViewById(R.id.conBottomView)
        val subRecyclerView: RecyclerView = itemView.findViewById(R.id.subRecyclerView)

        fun bind(itemData: DevicesItem) {
            textView.text = "${itemData.name}"
        }
    }



}

