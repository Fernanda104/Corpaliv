package com.example.corpalivapp.notificaciones

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.corpalivapp.R

// Notificación local que confirma que CORPALIV recibió la solicitud de colaboración.
object NotificacionColaboracion {

    private const val ID_CANAL = "solicitudes_colaboracion"
    private const val ID_NOTIFICACION = 1

    // Android 8+ exige un canal. Crearlo de nuevo no duplica nada.
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val canal = NotificationChannel(
            ID_CANAL,
            "Solicitudes de colaboración",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        canal.description = "Confirma que CORPALIV recibió tu solicitud"

        val administrador = context.getSystemService(NotificationManager::class.java)
        administrador.createNotificationChannel(canal)
    }

    // Antes de Android 13 el permiso se concede al instalar.
    fun tienePermiso(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true
        }
        val estado = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        )
        return estado == PackageManager.PERMISSION_GRANTED
    }

    // El permiso se revisa en la primera línea; lint no lo detecta porque está en otra función.
    @SuppressLint("MissingPermission")
    fun mostrarSolicitudRecibida(context: Context, tipo: String, fecha: String) {
        if (!tienePermiso(context)) {
            return
        }
        val mensaje = "CORPALIV recibió tu solicitud de $tipo. Te contactaremos el $fecha."

        val constructor = NotificationCompat.Builder(context, ID_CANAL)
        constructor.setSmallIcon(R.drawable.ic_launcher_foreground)
        constructor.setContentTitle("Solicitud recibida")
        constructor.setContentText(mensaje)
        constructor.setStyle(NotificationCompat.BigTextStyle().bigText(mensaje))
        constructor.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        constructor.setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(ID_NOTIFICACION, constructor.build())
    }
}