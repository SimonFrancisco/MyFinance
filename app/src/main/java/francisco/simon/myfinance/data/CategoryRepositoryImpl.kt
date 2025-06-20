package francisco.simon.myfinance.data

import android.util.Log
import francisco.simon.myfinance.data.api.ApiService
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    private val categories = MutableStateFlow<List<Category>>(generateCategories())

    override suspend fun getAllCategories(): Flow<List<Category>> {
        try {
            val response = apiService.getCategories()
            categories.update {
                response.body()?.let { listCategories ->
                    listCategories.map { category ->
                        Category(
                            id = category.id,
                            name = category.name,
                            emoji = category.emoji,
                            isIncome = category.isIncome
                        )
                    }
                }!!
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
        return categories
    }

    private fun generateCategories() = buildList<Category> {
        add(
            Category(
                id = 1,
                name = "Аренда квартиры",
                emoji = "\uD83C\uDFE1",
                isIncome = false
            )

        )
        add(
            Category(
                id = 2,
                name = "Одежда",
                emoji = "\uD83D\uDC57",
                isIncome = false
            )
        )
        add(
            Category(
                id = 3,
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            )
        )
        add(
            Category(
                id = 4,
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            )
        )
        add(
            Category(
                id = 5,
                name = "Ремонт квартиры",
                emoji = "РК",
                isIncome = false
            )
        )
        add(
            Category(
                id = 6,
                name = "Продукты",
                emoji = "\uD83C\uDF6D",
                isIncome = false
            )
        )
        add(
            Category(
                id = 7,
                name = "Спортзал",
                emoji = "\uD83C\uDFCB\u200D♂\uFE0F",
                isIncome = false
            )
        )
        add(
            Category(
                id = 8,
                name = "Медицина",
                emoji = "\uD83D\uDC8A",
                isIncome = false
            )
        )

    }

    companion object {
        private const val SIMULATE_LOADING_DELAY = 2000L
    }
}