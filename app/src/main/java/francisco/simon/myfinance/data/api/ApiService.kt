package francisco.simon.myfinance.data.api

import francisco.simon.myfinance.data.dto.AccountDto
import francisco.simon.myfinance.data.dto.CategoryDto
import francisco.simon.myfinance.data.dto.TransactionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): Response<List<TransactionDto>>

    @GET("accounts")
    suspend fun getAccounts(): Response<List<AccountDto>>
}