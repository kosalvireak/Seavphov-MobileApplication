package kh.edu.rupp.seavphov.model

data class BookDetail(
    val title: String,
    var description: String,
    var author: String,
    var category: String,
    var condition: String,
    var location: String,
    var imgUrl: String,
)
