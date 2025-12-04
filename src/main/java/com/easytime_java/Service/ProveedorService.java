package com.easytime_java.Service;

import com.easytime_java.model.Proveedor;
import com.easytime_java.repository.ProveedorRepository; // Asegúrate de importar tu repositorio
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public List<Proveedor> listar() {
        return repository.findAll();
    }

    public Proveedor guardar(Proveedor proveedor) {
        return repository.save(proveedor);
    }

    public Optional<Proveedor> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
