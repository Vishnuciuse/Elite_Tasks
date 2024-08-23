package com.example.myapplication.data

data class NetWorkData(
	val routers: List<RoutersItem>
)

data class Fingerprint(
	val vendor: String,
	val model: String
)

data class DevicesItem(
	val ifType: String,
	val name: String,
	val ipAddress: String,
	val fingerprint: Fingerprint,
	val subType: Any,
	val type: Int,
	val deviceId: String
)

data class ConnectedTo(
	val routerId: String,
	val jsonMemberInterface: String,
	val routerMac: String
)

data class RoutersItem(
	val routerName: String,
	val devices: List<DevicesItem>,
	val routerId: String,
	val isp: String,
	val fsanSerialNumber: String,
	val modelNumber: String,
	val type: String,
	val connectedTo: ConnectedTo?=null,
	val macAddr: String,
	val status: String,
	var subRouter: MutableList<RoutersItem> = mutableListOf()
)

