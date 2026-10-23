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

                CoroutineScope(Dispatchers.IO).launch {
                    cargarProgramasIniciales(instancia)
                }

                instancia
            }
        }


        private suspend fun cargarProgramasIniciales(
            database: CorpalivDatabase
        ) {
            val programaInclusion = Programa(
                nombre = "Programa de Inclusión Laboral",
                descripcion = "Brindamos una variedad de mecanismos diseñados " +
                        "específicamente para promover y facilitar la inclusión " +
                        "laboral de personas con discapacidad múltiple. " +
                        "Estos mecanismos están orientados a ayudarlas a " +
                        "integrarse de manera efectiva y exitosa en el mundo " +
                        "laboral, garantizando así que puedan desarrollar sus " +
                        "habilidades y alcanzar su máximo potencial en sus " +
                        "entornos de trabajo."
            )

            val tallerSociolaboral = Programa(
                nombre = "Taller Sociolaboral",
                descripcion = "Nos enfocamos en la formación integral de " +
                        "personas, brindándoles habilidades sociales, " +
                        "emocionales y laborales que les permitan integrarse " +
                        "con éxito al mundo laboral. A través de diversas " +
                        "metodologías, fortalecemos su independencia, autonomía " +
                        "y competencias de empleabilidad. Además de estas " +
                        "habilidades, también nos dedicamos al diseño, " +
                        "fabricación y producción de productos con valor y " +
                        "sentido, fomentando un ambiente de colaboración, " +
                        "aprendizaje y crecimiento mutuo entre beneficiarios " +
                        "y emprendimientos."
            )

            val dao = database.programaDao()

            val programasOficiales = listOf(
                programaInclusion to "Programa de inclusión",
                tallerSociolaboral to "Programa de apoyo familiar"
            )

            programasOficiales.forEach { (oficial, nombreAntiguo) ->
                val existente = dao.obtenerPorNombre(oficial.nombre)
                val antiguo = if (existente == null) {
                    dao.obtenerPorNombre(nombreAntiguo)
                } else {
                    null
                }

                when {
                    existente != null -> {
                        dao.actualizar(
                            existente.copy(
                                descripcion = oficial.descripcion,
                                activo = true
                            )
                        )
                    }

                    antiguo != null -> {
                        dao.actualizar(
                            antiguo.copy(
                                nombre = oficial.nombre,
                                descripcion = oficial.descripcion,
                                activo = true
                            )
                        )
                    }

                    else -> dao.insertar(oficial)
                }
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