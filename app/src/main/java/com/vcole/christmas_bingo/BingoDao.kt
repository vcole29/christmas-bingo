import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BingoDao {
    @Query("SELECT * FROM bingo_words WHERE theme = :themeName")
    suspend fun getWordsByTheme(themeName: String): List<BingoWord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<BingoWord>)
}