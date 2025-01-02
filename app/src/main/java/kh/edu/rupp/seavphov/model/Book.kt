package kh.edu.rupp.seavphov.model;

data class Book(
    val title: String,
    var price: String,
    var imgUrl: String,
    var condition: String? = null
)
