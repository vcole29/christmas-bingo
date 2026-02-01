import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BingoWord::class], version = 1)
abstract class BingoDatabase : RoomDatabase() {
    abstract fun bingoDao(): BingoDao
}