package com.easytime_java.Service;

import com.easytime_java.model.Servicio;
import com.easytime_java.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository repo;

    public ServicioService(ServicioRepository repo) {
        this.repo = repo;
    }

    public List<Servicio> listar() {
        return repo.findAll();
    }

    public Servicio obtenerPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
    }

    public Servicio guardar(Servicio servicio) {
        return repo.save(servicio);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
