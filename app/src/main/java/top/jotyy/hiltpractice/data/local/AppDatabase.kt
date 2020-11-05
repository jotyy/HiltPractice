package top.jotyy.hiltpractice.data.local

import androidx.room.Database
import top.jotyy.hiltpractice.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase {

    abstract fun userDao(): UserDao
}
