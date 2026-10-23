# CorpalivApp · Plataforma móvil de vinculación CORPALIV / Alba Lab

Aplicación Android desarrollada para la asignatura **DSY1105 · Desarrollo de Aplicaciones Móviles** (Duoc UC), basada en el caso *"Plataforma Móvil Integrada para la Difusión de Impacto Social y Vinculación Empresarial"* de la **Corporación de Padres y Amigos por el Limitado Visual (CORPALIV)** y su Red Sociolaboral **Alba Lab**.

## Descripción

CORPALIV es una organización sin fines de lucro que promueve la inclusión social y laboral de personas con discapacidad múltiple. Hoy la información para colaborar con la organización está repartida en varios canales, lo que dificulta que el interés de empresas, donantes y voluntarios se convierta en acciones concretas.

CorpalivApp reúne en una sola aplicación la propuesta de valor de la organización. La app permite:

- Conocer los programas de inclusión de CORPALIV.
- Explorar el catálogo de productos y servicios elaborados en Alba Lab.
- Enviar una solicitud de colaboración (donación, alianza, voluntariado, compra de productos o capacitación).
- Contactar directamente al equipo de vinculación.
- Revisar y administrar las solicitudes enviadas desde el teléfono.

## Integrantes

| Nombre | Usuario GitHub |
|---|---|
| Bruno Carrasco | [@Broskyduoc](https://github.com/Broskyduoc) |
| Fernanda Céspedes | [@Fernanda104](https://github.com/Fernanda104) |

## Funcionalidades implementadas

### Interfaz y navegación
- Pantalla de inicio con acceso a las cuatro secciones de la app.
- Navegación entre pantallas con transición animada según la dirección (entrar o volver).
- Botón atrás del sistema integrado con la navegación.
- Tema claro y oscuro con Material Design 3 y fondos institucionales para cada tema.

### Formulario de colaboración con validaciones
- Campos: nombre, organización, correo, tipo de colaboración, cantidad de personas, fecha de contacto y aceptación de contacto.
- Validación en tiempo real por campo, con ícono, mensaje de error animado y teclado adecuado a cada dato.
- Selector de fecha con `DatePickerDialog` y menú desplegable para el tipo de colaboración.
- Confirmación con Snackbar al guardar la solicitud.
- La lógica de validación está separada de la interfaz (`ColaboracionValidador`) y se gestiona desde el ViewModel.

### Catálogo y solicitudes
- Catálogo de productos y servicios con precios en pesos chilenos y tarjetas expandibles animadas.
- Pantalla **Mis solicitudes** con el listado guardado en Room y eliminación con diálogo de confirmación.

### Recursos nativos del dispositivo
- **Notificaciones locales:** se confirma la recepción de la solicitud y se pide el permiso `POST_NOTIFICATIONS` en tiempo de ejecución (Android 13+).
- **Llamada telefónica:** abre el marcador con el número de contacto.
- **Correo electrónico:** abre la app de correo con el asunto ya escrito.
- **Mapas:** abre la ubicación de la sede en Google Maps.

### Animaciones
- `AnimatedContent` para las transiciones entre pantallas.
- `AnimatedVisibility` para los mensajes de error del formulario.
- `animateContentSize` en las tarjetas de producto.
- `animateItem` en las listas.

## Arquitectura

El proyecto sigue el patrón **MVVM** con persistencia local en **Room**:

```
app/src/main/java/com/example/corpalivapp/
├── acciones/          # Intents para llamada, correo y mapa
├── data/
│   ├── local/         # Base de datos Room y DAOs
│   ├── model/         # Entidades: Programa, Producto, Colaboracion
│   └── repository/    # Repositorios que conectan los DAOs con los ViewModels
├── notificaciones/    # Canal y envío de notificaciones locales
├── ui/
│   ├── components/    # Componentes reutilizables (campos, tarjetas, fondo)
│   ├── navigation/    # Navegación animada entre pantallas
│   ├── screens/       # Pantallas de la aplicación
│   └── theme/         # Tema Material 3 claro y oscuro
├── validacion/        # Reglas de validación del formulario
└── viewmodel/         # ViewModels, estado del formulario y factory
```

**Flujo de datos:** `Screen` → `ViewModel` (StateFlow) → `Repository` → `DAO` → `Room`

## Tecnologías

- Kotlin 2.2
- Jetpack Compose + Material Design 3
- Room (persistencia local) con KSP
- ViewModel + StateFlow + Coroutines
- Material Icons Extended
- Git y GitHub (ramas `feature/*` y Pull Requests)

## Requisitos

- Android Studio (versión reciente, compatible con AGP 9.x)
- JDK 17 o superior (incluido en Android Studio)
- Dispositivo o emulador con **Android 7.0 (API 24)** o superior
