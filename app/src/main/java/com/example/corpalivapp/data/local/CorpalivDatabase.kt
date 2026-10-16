package com.example.corpalivapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.corpalivapp.data.model.Colaboracion
import com.example.corpalivapp.data.model.Producto
import com.example.corpalivapp.data.model.Programa

@Database(
    entities = [
        Programa::class,
        Producto::class,
        Colaboracion::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CorpalivDatabase : RoomDatabase() {

    abstract fun programaDao(): ProgramaDao

    abstract fun productoDao(): ProductoDao

    abstract fun colaboracionDao(): ColaboracionDao

    companion object {
        @Volatile
        private var INSTANCE: CorpalivDatabase? = null

        fun obtenerInstancia(context: Context): CorpalivDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    CorpalivDatabase::class.java,
                    "corpaliv_database"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}