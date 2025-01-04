package kh.edu.rupp.seavphov.api;


import kh.edu.rupp.seavphov.model.ApiResponse
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.model.Carousel
import kh.edu.rupp.seavphov.model.Notification
import retrofit2.http.GET;

interface ApiService {
    @GET("carousel.json")
    suspend fun loadCarousel(): ApiResponse<Carousel>

    @GET("books.json")
    suspend fun loadBooks(): List<Book>

    @GET("bookDetail.json")
    suspend fun loadBookDetail(): ApiResponse<BookDetail>

    @GET("NewestAddition.json")
    suspend fun loadNewestAddition(): ApiResponse<ArrayList<Book>>

    @GET("ThisWeekHighlight.json")
    suspend fun loadThisWeekHighlight(): ApiResponse<ArrayList<Book>>

    @GET("BooksList.json")
    suspend fun loadBooksList(): ApiResponse<ArrayList<Book>>

    @GET("Notifications.json")
    suspend fun loadNotificationsList(): ApiResponse<ArrayList<Notification>>
}
