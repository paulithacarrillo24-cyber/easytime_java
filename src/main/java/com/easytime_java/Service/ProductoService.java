package com.easytime_java.Service;

import com.easytime_java.model.Producto;
import com.easytime_java.repository.ProductoRepository;
import com.easytime_java.exception.BusinessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo){
        this.repo = repo;
    }

    public List<Producto> listar() {
    return repo.findAllWithInventario();
    }

    public Producto obtenerPorId(Integer id) {
    return repo.findById(id)
               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto guardar(Producto p) {
    if (p.getCodigo() == null || p.getCodigo().trim().isEmpty()) {
    throw new BusinessException("El código es obligatorio.");
    }
    String codigoStr = p.getCodigo().trim();
    // Validar que solo tenga dígitos
    if (!codigoStr.matches("\\d+")) {
     throw new BusinessException("El código debe contener solo números.");
    }
    if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
        throw new BusinessException("El nombre es obligatorio.");
    }
    if (p.getDescripcion() == null || p.getDescripcion().trim().isEmpty()) {
        throw new BusinessException("La descripción es obligatoria.");
    }
    // Obligatorios de negocio
    if (p.getCaducidad() == null) {
        throw new BusinessException("La fecha de caducidad es obligatoria.");
    }
    if (p.getPrecio() == null || p.getPrecio().trim().isEmpty()) {
    throw new BusinessException("El precio es obligatorio.");
    }
    String precioStr = p.getPrecio().trim();
    if (!precioStr.matches("\\$?\\d+(\\.\\d+)?")) {
    throw new BusinessException("El precio debe ser un número válido.");
    }
    if (p.getCantidad() == null || p.getCantidad().trim().isEmpty()) {
    throw new BusinessException("La cantidad es obligatoria.");
    }
    if (p.getInventario() == null || p.getInventario().getIdInventario() == null) {
        throw new BusinessException("Debe seleccionar un inventario válido.");
    }

    // Normalizar
    String codigoTrim = p.getCodigo().trim();
    p.setCodigo(codigoTrim);
    p.setNombre(p.getNombre().trim());
    p.setDescripcion(p.getDescripcion().trim());

    // Unicidad de código
    if (p.getIdProducto() == null) {
        if (repo.existsByCodigo(codigoTrim)) {
            throw new BusinessException("Ya existe un producto con el código: " + codigoTrim);
        }
    } else {
        Optional<Producto> otro = repo.findByCodigo(codigoTrim);
        if (otro.isPresent() && !otro.get().getIdProducto().equals(p.getIdProducto())) {
            throw new BusinessException("Ya existe otro producto con el código: " + codigoTrim);
        }
    }

    return repo.save(p);
}

    public Producto actualizar(Integer id, Producto nuevosDatos) {
    Producto p = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    // Validaciones (mismas reglas que guardar)
    if (nuevosDatos.getCodigo() == null || p.getCodigo().trim().isEmpty()) {
        throw new BusinessException("El código es obligatorio.");
    }
    String codigoStr = p.getCodigo().trim();
    // Validar que solo tenga dígitos
    if (!codigoStr.matches("\\d+")) {
        throw new BusinessException("El código debe contener solo números.");
    }
    if (nuevosDatos.getNombre() == null || nuevosDatos.getNombre().trim().isEmpty()) {
        throw new BusinessException("El nombre es obligatorio.");
    }
    if (nuevosDatos.getDescripcion() == null || nuevosDatos.getDescripcion().trim().isEmpty()) {
        throw new BusinessException("La descripción es obligatoria.");
    }
    if (nuevosDatos.getCaducidad() == null) {
        throw new BusinessException("La fecha de caducidad es obligatoria.");
    }
    if (nuevosDatos.getPrecio() == null || p.getPrecio().trim().isEmpty()) {
        throw new BusinessException("El precio es obligatorio.");
    }
    String precioStr = p.getPrecio().trim();
    if (!precioStr.matches("\\$?\\d+(\\.\\d+)?")) {
        throw new BusinessException("El precio debe ser un número válido.");
    }
    if (nuevosDatos.getCantidad() == null || p.getCantidad().trim().isEmpty()) {
        throw new BusinessException("La cantidad es obligatoria.");
    }   
    if (nuevosDatos.getInventario() == null || nuevosDatos.getInventario().getIdInventario() == null) {
        throw new BusinessException("Debe seleccionar un inventario válido.");
    }

    // Unicidad de código en edición
    String codigoTrim = nuevosDatos.getCodigo().trim();
    Optional<Producto> otro = repo.findByCodigo(codigoTrim);
    if (otro.isPresent() && !otro.get().getIdProducto().equals(id)) {
        throw new BusinessException("Ya existe otro producto con el código: " + codigoTrim);
    }

    // Normalizar y aplicar cambios
    p.setCodigo(codigoTrim);
    p.setNombre(nuevosDatos.getNombre().trim());
    p.setDescripcion(nuevosDatos.getDescripcion().trim());
    p.setCaducidad(nuevosDatos.getCaducidad());
    p.setPrecio(nuevosDatos.getPrecio());
    p.setCantidad(nuevosDatos.getCantidad());
    p.setInventario(nuevosDatos.getInventario());

    return repo.save(p);
}

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // dentro de ProductoService
    public List<Producto> listarFiltered(String codigo, String nombre, Integer inventarioId) {
    List<Producto> all = repo.findAll();
    Stream<Producto> s = all.stream();
    if (codigo != null && !codigo.isBlank()) {
        String kc = codigo.trim().toLowerCase();
        s = s.filter(p -> p.getCodigo() != null && p.getCodigo().toLowerCase().contains(kc));
    }
    if (nombre != null && !nombre.isBlank()) {
        String kn = nombre.trim().toLowerCase();
        s = s.filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(kn));
    }
    if (inventarioId != null) {
        s = s.filter(p -> p.getInventario() != null && inventarioId.equals(p.getInventario().getIdInventario()));
    }
    return s.collect(Collectors.toList());
    }
}

