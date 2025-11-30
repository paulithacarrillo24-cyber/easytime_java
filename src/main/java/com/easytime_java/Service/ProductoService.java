package com.easytime_java.Service;

import com.easytime_java.model.Producto;
import com.easytime_java.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo){
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto obtenerPorId(Integer id) {
    return repo.findById(id)
               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
  }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Producto actualizar(Integer id, Producto nuevosDatos) {
        Producto p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        p.setCodigo(nuevosDatos.getCodigo());
        p.setNombre(nuevosDatos.getNombre());
        p.setDescripcion(nuevosDatos.getDescripcion());
        p.setCaducidad(nuevosDatos.getCaducidad());
        p.setPrecio(nuevosDatos.getPrecio());
        p.setCantidad(nuevosDatos.getCantidad());
        p.setInventario(nuevosDatos.getInventario());

        return repo.save(p);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}