package kh.edu.rupp.seavphov.api;


import kh.edu.rupp.seavphov.model.ApiResponse
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.model.Carousel
import kh.edu.rupp.seavphov.model.LoginResponse
import kh.edu.rupp.seavphov.model.Notification
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // DUMMY API
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

    // Real API
    @GET("books")
    suspend fun loadAllSeavphov(): ApiResponse<ArrayList<Book>>

    @GET("books/{bookId}")
    suspend fun loadBookById(@Path("bookId") bookId: String): ApiResponse<Book>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("gmail") gmail: String,
        @Field("password") password: String
    ): ApiResponse<LoginResponse>

}
