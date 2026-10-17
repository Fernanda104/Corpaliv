package com.example.corpalivapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.corpalivapp.data.model.Colaboracion
import com.example.corpalivapp.data.model.Producto
import com.example.corpalivapp.data.model.Programa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    cargarProgramasIniciales(database)
                                    cargarProductosIniciales(database)
                                }
                            }
                        }
                    })
                    .build()

                INSTANCE = instancia
                instancia
            }
        }

        private suspend fun cargarProgramasIniciales(
            database: CorpalivDatabase
        ) {
            val programas = listOf(
                Programa(
                    nombre = "Programa de inclusión",
                    descripcion = "Iniciativa orientada a promover " +
                            "la participación y la inclusión social."
                ),
                Programa(
                    nombre = "Programa de apoyo familiar",
                    descripcion = "Iniciativa de acompañamiento " +
                            "y orientación para las familias."
                )
            )

            programas.forEach { programa ->
                database.programaDao().insertar(programa)
            }
        }

        private suspend fun cargarProductosIniciales(
            database: CorpalivDatabase
        ) {
            val productos = listOf(
                Producto(
                    nombre = "Producto de muestra 1",
                    descripcion = "Descripción pendiente de completar.",
                    precio = 0.0,
                    disponible = true
                ),
                Producto(
                    nombre = "Producto de muestra 2",
                    descripcion = "Descripción pendiente de completar.",
                    precio = 0.0,
                    disponible = true
                )
            )

            productos.forEach { producto ->
                database.productoDao().insertar(producto)
            }
        }
    }
}