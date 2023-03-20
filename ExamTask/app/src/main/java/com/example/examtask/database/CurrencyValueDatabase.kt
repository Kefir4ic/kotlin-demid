package com.example.examtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.examtask.generateDefaultValues

@Database(entities = [CurrencyValue::class], version=1, exportSchema = false)
abstract class CurrencyValueDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: CurrencyValueDatabase? = null


        fun getInstance(context: Context) : CurrencyValueDatabase {
            synchronized(this) {
                var instance = INSTANCE
                // если бд н существует, то создаем ее
                if (instance == null) {
                    instance = createInstance(context)
                    INSTANCE = instance
                }
                return instance!!
            }
        }

        private fun createInstance(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CurrencyValueDatabase::class.java,
                "currency_value_db")
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Thread(Runnable { prepopulateDb(context, getInstance(context)) }).start()
                    }
                })
                .build()

        private fun prepopulateDb(context: Context, instance: CurrencyValueDatabase) {
            val defaultValuesString: String =
                context.assets.open("values.json").bufferedReader().use { it.readText() }

            val defaultValues = generateDefaultValues(defaultValuesString)
            defaultValues.forEach  { value -> instance.getCurrencyValueDatabaseDao().insert(value) }
        }
    }

    abstract fun getCurrencyValueDatabaseDao(): CurrencyValueDao

}