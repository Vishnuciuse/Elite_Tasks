package com.example.myapplication .task1

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
import com.example.myapplication.task1.subRouterAdapter2

class RouterAdapter2(val data: List<RoutersItem>) :RecyclerView.Adapter<RouterAdapter2.DataViewHolder>() {

    val mainData: MutableList<RoutersItem> = mutableListOf()
    var routerId = ""

    init {
        data.forEach {
            if(it.type=="ROUTER"){
                mainData.add(it)
            }
        }

//            for (m in 0 until data.size) {
//                mainData.add(data[m])
//                data.forEach { s ->
//                    if(s.connectedTo!=null){
//                        if (s.connectedTo.routerId.equals(mainData[m].routerId)){
//                            mainData[m].subRouter.add(s)
//                        }
//                    }
//                }
//            }
//        println("The mainData restructure: "+mainData)

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
//            routerId = itemData.routerId
//            data.forEach {
//                println("each data in main list"+it)
//                if (it.connectedTo!=null){
//                    if (it.connectedTo.routerId.equals("$routerId")){
//                        subData.add(it)
//                    }
//                }
//            }

            itemData?.let {
                val adapter = deviceAdapter2( it.devices,data,itemData.routerId )
                conDeviceRecyclerView.adapter = adapter
            }

        }
    }

}

class deviceAdapter2(
    val deviceData: List<DevicesItem>,
    val data: List<RoutersItem>,
    val pRouterId: String
):RecyclerView.Adapter<deviceAdapter2.DataViewHolder>(){
     val subData : MutableList<RoutersItem> = mutableListOf()

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

            data.forEach {
                if (it.connectedTo!=null){
                    if (it.connectedTo.routerId == pRouterId){
                        subData.add(it)
                    }
                }
            }
            println("check the sub router data -"+subData)

            subData?.let {
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

    inner class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.conDeviceNameTV)
        val spaceConstraintView: ConstraintLayout = itemView.findViewById(R.id.spaceConstraintView)
        val bottomView: View = itemView.findViewById(R.id.conBottomView)
        val subRecyclerView: RecyclerView = itemView.findViewById(R.id.subRecyclerView)

        fun bind(itemData: DevicesItem) {
            textView.text = "${itemData.name}"
            spaceConstraintView.visibility = View.GONE
        }
    }



}

