package com.easytime_java.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.easytime_java.model.Servicio;
import com.easytime_java.repository.ServicioRepository;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los servicios.
 * Proporciona métodos para listar todos los servicios y aplicar filtros
 * por nombre y rango de fechas de registro.
 */
@Service
public class ServiciosService {

    private final ServicioRepository repo;

    /* Constructor del servicio de servicios. */
    public ServiciosService(ServicioRepository repo) {
        this.repo = repo;
    }

    /* Obtiene todos los servicios registrados en la base de datos */
    public List<Servicio> listarTodos() {
        return repo.findAll();
    }

    /* Aplica filtros por nombre y rango de fechas de registro */
    public List<Servicio> filtrarServicios(String nombre, LocalDate desde, LocalDate hasta) {
        List<Servicio> todos = repo.findAll();

        return todos.stream()
            .filter(s -> nombre == null || s.getNOM_SERV().toLowerCase().contains(nombre.toLowerCase()))
            .filter(s -> {
                if (desde != null && hasta != null) {
                    return !s.getCREATED_AT().toLocalDate().isBefore(desde) &&
                           !s.getCREATED_AT().toLocalDate().isAfter(hasta);
                } else if (desde != null) {
                    return !s.getCREATED_AT().toLocalDate().isBefore(desde);
                } else if (hasta != null) {
                    return !s.getCREATED_AT().toLocalDate().isAfter(hasta);
                }
                return true;
            })
            .collect(Collectors.toList());
    }
}