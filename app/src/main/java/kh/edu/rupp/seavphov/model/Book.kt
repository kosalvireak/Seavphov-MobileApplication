package kh.edu.rupp.seavphov.model;

data class Book(
    var _id: String,
    var title: String,
    var description: String,
    var author: String,
    var category: String,
    var condition: String,
    var location: String,
    var price: String,
    var imgUrl: String,
    var createdAt: String,
    var ownerId: OwnerInfo
)
