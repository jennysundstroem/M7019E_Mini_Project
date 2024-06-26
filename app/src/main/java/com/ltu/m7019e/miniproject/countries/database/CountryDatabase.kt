package com.ltu.m7019e.miniproject.countries.database
import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.model.CountryConverters

@Database(entities = [Country::class], version = 1, exportSchema = false)
@TypeConverters(CountryConverters::class)
abstract class CountryDatabase : RoomDatabase (){

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var Instance: CountryDatabase? = null

        fun getDatabase(context: Context): CountryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {

                Room.databaseBuilder(context, CountryDatabase::class.java, "country_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .addTypeConverter(CountryConverters())
                    .build()
                    .also { Instance = it }
            }
        }
    }
}