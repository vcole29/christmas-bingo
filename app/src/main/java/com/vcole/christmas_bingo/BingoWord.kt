import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bingo_words")
data class BingoWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val theme: String,
    val isMarked: Boolean = false
)