package kh.edu.rupp.seavphov.api;


import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.model.Carousel
import retrofit2.http.GET;

interface ApiService {
    @GET("carousel.json")
    suspend fun loadCarousel(): Carousel

    @GET("books.json")
    suspend fun loadBooks(): List<Book>

    @GET("bookDetail.json")
    suspend fun loadBookDetail() : BookDetail
}
