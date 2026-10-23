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
                    nombre = "Velas aromáticas artesanales",
                    descripcion = "Velas de cera de soya elaboradas en el taller de Alba Lab.",
                    precio = 6990.0
                ),
                Producto(
                    nombre = "Jabones naturales",
                    descripcion = "Jabones hechos a mano con aceites esenciales.",
                    precio = 3990.0
                ),
                Producto(
                    nombre = "Caja de regalo corporativa",
                    descripcion = "Set de productos del taller para regalos de empresa o eventos.",
                    precio = 19990.0
                ),
                Producto(
                    nombre = "Tarjetas y papelería artesanal",
                    descripcion = "Tarjetas de saludo y libretas hechas con papel reciclado.",
                    precio = 2490.0,
                    disponible = false
                ),
                Producto(
                    nombre = "Servicio de catering inclusivo",
                    descripcion = "Coffee break y colaciones para reuniones, preparadas por el equipo de Alba Lab.",
                    precio = 0.0
                ),
                Producto(
                    nombre = "Taller de sensibilización para empresas",
                    descripcion = "Capacitación sobre inclusión laboral de personas con discapacidad, como parte de la RSE.",
                    precio = 0.0
                )
            )

            productos.forEach { producto ->
                database.productoDao().insertar(producto)
            }
        }
    }
}