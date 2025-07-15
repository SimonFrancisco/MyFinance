package francisco.simon.core.data.network.api

import francisco.simon.core.data.network.dto.CategoryDto
import francisco.simon.core.data.network.dto.account.AccountByIdDto
import francisco.simon.core.data.network.dto.account.AccountDto
import francisco.simon.core.data.network.dto.account.AccountUpdateRequestDto
import francisco.simon.core.data.network.dto.transactions.AddTransactionDto
import francisco.simon.core.data.network.dto.transactions.EditTransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionDto
import francisco.simon.core.data.network.dto.transactions.TransactionResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): Response<List<CategoryDto>>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): Response<List<TransactionDto>>

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") transactionId: Int
    ): Response<TransactionDto>

    @POST("transactions")
    suspend fun addTransaction(
        @Body addTransactionDto: AddTransactionDto
    ): Response<TransactionResponseDto>

    @PUT("transactions/{id}")
    suspend fun editTransaction(
        @Path("id") transactionId: Int,
        @Body editTransactionDto: EditTransactionDto
    ): Response<TransactionDto>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") transactionId: Int
    ): Response<Unit>

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