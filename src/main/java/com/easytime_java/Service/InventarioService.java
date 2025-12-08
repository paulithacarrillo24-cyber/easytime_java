package com.easytime_java.Service;

import com.easytime_java.model.Inventario;
import com.easytime_java.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository repo;

    public InventarioService(InventarioRepository repo) {
        this.repo = repo;
    }

    public List<Inventario> listar() {
        return repo.findAll();
    }

    public Inventario obtenerPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public Inventario guardar(Inventario inv) {
        return repo.save(inv);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    public List<Inventario> buscar(String q) {
        return repo.buscar(q.toLowerCase());
    }
}
