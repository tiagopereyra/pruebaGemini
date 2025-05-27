package ar.edu.utn.frba.dds.models.services;

import ar.edu.utn.frba.dds.models.DTOs.PeticionCrearHechoDTO;
import ar.edu.utn.frba.dds.models.DTOs.PeticionModificarHechoDTO;
import ar.edu.utn.frba.dds.models.entities.*; // Importa todas tus entidades
import ar.edu.utn.frba.dds.models.repositories.HechoRepository;
// Si necesitas buscar Categorias existentes en lugar de crearlas siempre:
// import ar.edu.utn.frba.dds.models.repositories.CategoriaRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FuenteDinamicaService {

    private final HechoRepository hechoRepository;
    private final GestionMultimediaService gestionMultimediaService;
    // private final CategoriaRepository categoriaRepository; // Opcional

    // Inyectar dependencias vía constructor
    public FuenteDinamicaService(HechoRepository hechoRepository,
                                 GestionMultimediaService gestionMultimediaService) {
        // CategoriaRepository categoriaRepository) { // Opcional
        this.hechoRepository = hechoRepository;
        this.gestionMultimediaService = gestionMultimediaService;
        // this.categoriaRepository = categoriaRepository; // Opcional
    }

    /**
     * Permite a un contribuyente (registrado o anónimo) subir un nuevo hecho.
     * Los hechos subidos por usuarios registrados podrán ser modificados posteriormente.
     * Los hechos subidos por anónimos no podrán ser editados.
     * Todos los hechos subidos iniciarán en estado de revisión PENDIENTE.
     * @param peticion DTO con los datos para crear el hecho.
     * @return El Hecho creado y guardado.
     * @throws IllegalArgumentException si los datos de la petición son inválidos.
     */
    public Hecho subirHecho(PeticionCrearHechoDTO peticion) {
        if (peticion == null) {
            throw new IllegalArgumentException("La petición para crear el hecho no puede ser nula.");
        }

        // 1. Crear/Obtener Entidades relacionadas
        Ubicacion ubicacion = new Ubicacion(peticion.getLatitudUbicacion(), peticion.getLongitudUbicacion());

        // Para Categoria: o la creas siempre, o la buscas/creas.
        // Opción A: Crear siempre nueva
        Categoria categoria = new Categoria(peticion.getNombreCategoria());
        // Opción B: Buscar o crear (necesitaría CategoriaRepository)
        // Categoria categoria = categoriaRepository.buscarPorNombre(peticion.getNombreCategoria())
        // .orElseGet(() -> categoriaRepository.guardar(new Categoria(peticion.getNombreCategoria())));

        // 2. Procesar Multimedia
        Multimedia contenidoMultimedia = gestionMultimediaService.procesarYGuardar(
                peticion.getTipoMultimedia(),
                peticion.getDatosMultimedia()
        );

        // 3. Crear Contribuyente si aplica
        Contribuyente contribuyente = null;
        if (peticion.getNombreContribuyente() != null && !peticion.getNombreContribuyente().trim().isEmpty()) {
            contribuyente = new Contribuyente(
                    peticion.getNombreContribuyente(),
                    peticion.getApellidoContribuyente(),
                    peticion.getFechaNacimientoContribuyente()
            );
        }

        // 4. Determinar TipoDeFuente
        TipoDeFuente origen = (contribuyente != null) ? TipoDeFuente.CONTRIBUYENTE : TipoDeFuente.MANUAL; // O un TipoDeFuente.ANONIMO específico

        // 5. Crear Hecho
        Hecho nuevoHecho;
        if (contribuyente != null) {
            nuevoHecho = new Hecho(
                    peticion.getTitulo(),
                    peticion.getDescripcion(),
                    categoria,
                    peticion.getFechaAcontecimiento(),
                    contribuyente,
                    origen,
                    ubicacion
            );
        } else {
            nuevoHecho = new Hecho(
                    peticion.getTitulo(),
                    peticion.getDescripcion(),
                    categoria,
                    peticion.getFechaAcontecimiento(),
                    origen, // Origen indicará si es anónimo o no
                    ubicacion
            );
        }
        // Asignar multimedia (asumiendo que Hecho tiene un setter o se pasa en constructor más completo)
        // nuevoHecho.setContenidoMultimedia(contenidoMultimedia); // Necesitarás este setter en Hecho.java

        // 6. Crear y asociar Revisión inicial
        RevisionHecho revisionInicial = new RevisionHecho(); // Por defecto estado PENDIENTE
        nuevoHecho.setRevision(revisionInicial); // Necesitarás este setter en Hecho.java

        // 7. Guardar Hecho (el repositorio se encarga de generar el ID si es necesario)
        return hechoRepository.guardar(nuevoHecho);
    }

    /**
     * Permite a un contribuyente registrado modificar un hecho que haya subido.
     * La modificación solo es posible dentro de una semana desde la fecha de carga original.
     * La modificación reinicia el estado de revisión del hecho a PENDIENTE.
     * @param idHecho El ID del hecho a modificar.
     * @param peticion DTO con los nuevos datos para el hecho.
     * @param contribuyenteEditor Objeto Contribuyente que intenta la edición.
     * En un sistema real, este provendría de la sesión del usuario autenticado.
     * @return El Hecho modificado y guardado.
     * @throws SecurityException si el usuario no es el contribuyente original, si el hecho fue subido anónimamente,
     * o si el plazo de edición ha expirado.
     * @throws IllegalArgumentException si el hecho no se encuentra o la petición es inválida.
     */
    public Hecho modificarHecho(String idHecho, PeticionModificarHechoDTO peticion, Contribuyente contribuyenteEditor) {
        if (idHecho == null || peticion == null || contribuyenteEditor == null) {
            throw new IllegalArgumentException("ID del hecho, petición de modificación y contribuyente editor no pueden ser nulos.");
        }

        Optional<Hecho> hechoOptional = hechoRepository.buscarPorId(idHecho);
        if (!hechoOptional.isPresent()) {
            throw new IllegalArgumentException("Hecho con ID " + idHecho + " no encontrado.");
        }

        Hecho hechoAModificar = hechoOptional.get();

        // 1. Verificar si el hecho fue subido por un contribuyente registrado
        if (hechoAModificar.getContribuyente() == null) {
            throw new SecurityException("Este hecho fue subido anónimamente y no puede ser modificado.");
        }

        // 2. Verificar que el editor sea el contribuyente original.
        //    Esto requiere que la clase Contribuyente tenga un método equals() bien implementado
        //    o que se comparen campos significativos.
        if (!hechoAModificar.getContribuyente().equals(contribuyenteEditor)) {
            // Para una comparación más robusta, si Contribuyente tuviera un ID único, se compararía eso.
            // Por ahora, dependemos de .equals() o una lógica de comparación manual.
            // Ejemplo de comparación manual (si .equals no está bien definido para esto):
            // if (!hechoAModificar.getContribuyente().getNombre().equals(contribuyenteEditor.getNombre()) || ...)
            throw new SecurityException("No tiene permisos para modificar este hecho. No es el contribuyente original.");
        }

        // 3. Verificar plazo de una semana para la edición desde la fecha de carga ORIGINAL
        long diasDesdeCarga = ChronoUnit.DAYS.between(hechoAModificar.getFechaCarga(), LocalDate.now());
        if (diasDesdeCarga > 7) {
            throw new SecurityException("El plazo para modificar el hecho (1 semana desde su carga original) ha expirado.");
        }

        // 4. Actualizar campos del Hecho
        hechoAModificar.setTitulo(peticion.getTitulo());
        hechoAModificar.setDescripcion(peticion.getDescripcion());

        // Actualizar Categoria (creando una nueva o buscando/creando)
        Categoria nuevaCategoria = new Categoria(peticion.getNombreCategoria());
        hechoAModificar.setCategoria(nuevaCategoria); // Necesitarás setters en Hecho.java

        hechoAModificar.setFechaAcontecimiento(peticion.getFechaAcontecimiento());

        Ubicacion nuevaUbicacion = new Ubicacion(peticion.getLatitudUbicacion(), peticion.getLongitudUbicacion());
        hechoAModificar.setUbicacion(nuevaUbicacion);

        // 5. Actualizar Multimedia
        Multimedia nuevoContenidoMultimedia = gestionMultimediaService.procesarYGuardar(
                peticion.getTipoMultimedia(),
                peticion.getDatosMultimedia()
        );
        // hechoAModificar.setContenidoMultimedia(nuevoContenidoMultimedia); // Setter en Hecho.java

        // 6. Reiniciar la revisión a PENDIENTE
        RevisionHecho revisionModificacion = hechoAModificar.getRevision();
        if (revisionModificacion == null) { // Debería existir si fue creado por subirHecho
            revisionModificacion = new RevisionHecho();
        }
        revisionModificacion.setEstado(EstadoRevision.PENDIENTE);
        revisionModificacion.setAdministrador(null); // Limpiar revisor anterior
        revisionModificacion.setFechaRevisado(null); // Limpiar fecha de revisión anterior
        revisionModificacion.setSugerencia(null);   // Limpiar sugerencias anteriores
        // Podría ser útil un campo en RevisionHecho para indicar que es una revisión de una modificación
        // revisionModificacion.setHechoAnteriorID(hechoAModificar.getIdentificador()); // Si se versionaran los hechos
        hechoAModificar.setRevision(revisionModificacion);

        // 7. Guardar el hecho actualizado
        return hechoRepository.guardar(hechoAModificar); // El método guardar actualiza si el ID existe
    }

    /**
     * Obtiene los hechos que han sido subidos a través de esta fuente dinámica
     * y que (idealmente) están aprobados.
     * @return Lista de hechos.
     */
    public List<Hecho> obtenerHechosDeFuenteDinamica() {
        return hechoRepository.buscarTodos().stream()
                .filter(hecho -> hecho.getOrigen() == TipoDeFuente.CONTRIBUYENTE || hecho.getOrigen() == TipoDeFuente.MANUAL)
                // Podrías añadir un filtro para que solo devuelva los aprobados:
                // .filter(hecho -> hecho.getRevision() != null && hecho.getRevision().getEstado() == EstadoRevision.APROBADO)
                .filter(hecho -> !hecho.isEliminado()) // No mostrar hechos marcados como eliminados
                .collect(Collectors.toList());
    }
}