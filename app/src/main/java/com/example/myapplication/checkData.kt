package com.example.myapplication

data class Data(
	val id: String? = null,
	val type: String? = null
)

data class IncludedItem(
	val attributes: Attributes? = null,
	val id: String? = null,
	val type: String? = null
)

data class Relationships(
	val author: Author? = null
)

data class DataItem(
	val relationships: Relationships? = null,
	val attributes: Attributes? = null,
	val id: String? = null,
	val type: String? = null
)

data class CheckData(
	val data: List<DataItem?>? = null,
	val included: List<IncludedItem?>? = null
)

data class Author(
	val data: Data? = null
)

data class Attributes(
	val created: String? = null,
	val title: String? = null,
	val body: String? = null,
	val updated: String? = null,
	val gender: String? = null,
	val name: String? = null,
	val age: Int? = null
)

