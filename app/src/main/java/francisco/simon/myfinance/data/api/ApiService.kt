package francisco.simon.myfinance.data.api

import francisco.simon.myfinance.data.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDto>>
}