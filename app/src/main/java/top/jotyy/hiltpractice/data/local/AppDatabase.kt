package top.jotyy.hiltpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import top.jotyy.hiltpractice.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
