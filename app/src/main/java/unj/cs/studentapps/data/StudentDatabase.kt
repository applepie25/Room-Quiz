package unj.cs.studentapps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 2)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object{
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context):StudentDatabase {
            return INSTANCE?: Room.databaseBuilder(context.applicationContext, StudentDatabase::class.java, "studentdb")
                .fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}