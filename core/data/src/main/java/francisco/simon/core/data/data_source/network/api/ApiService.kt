package francisco.simon.core.data.data_source.network.api

import francisco.simon.core.data.dto.AccountByIdDto
import francisco.simon.core.data.dto.AccountDto
import francisco.simon.core.data.dto.AccountUpdateRequestDto
import francisco.simon.core.data.dto.CategoryDto
import francisco.simon.core.data.dto.TransactionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Requests to server
 * @author Simon Francisco
 */
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

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") accountId: Int
    ): Response<AccountByIdDto>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") accountId: Int,
        @Body updateAccountBody: AccountUpdateRequestDto
    ): Response<AccountDto>
}