package es.proyecto.app.service;

import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.mapper.ProductMapper;
import es.proyecto.app.repository.ProductsRepository;
import es.swagger.codegen.models.Products;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Users para la gestión de usuarios en el sistema.
 */
@Validated
@Transactional
@Service
public class ProductsService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    @Autowired
    private ProductsRepository repository;

    /**
     * Obtiene todos los productos registrados.
     *
     * @return lista de productos convertidos a modelo API.
     */
    public List<Products> getAllProducts() {
        return mapper.toApiDomain(repository.findAll());
    }

    /**
     * Crea un nuevo producto.
     *
     * @param product objeto {@link Products} con los datos del producto a crear.
     */
    public void createProduct(Products product) {
        ProductsEntity entity = mapper.toEntity(product);
        repository.save(entity);
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param idProduct identificador del producto.
     * @return objeto {@link Products} si existe, o {@code null} si no se encuentra.
     */
    public Products geProductById(Integer idProduct) {
        Optional<ProductsEntity> optionalProductEntity = repository.findById(idProduct);
        return optionalProductEntity.map(mapper::toApiDomain).orElse(null);
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param idProduct identificador del producto a eliminar.
     */
    public void deleteProduct(Integer idProduct) {
        if (repository.existsById(idProduct)) {
            repository.deleteById(idProduct);
        }
    }

    /**
     * Actualiza un producto existente.
     *
     * @param idProduct identificador del producto a actualizar.
     * @param product   objeto {@link Products} con los datos actualizados.
     * @return {@link HttpStatus#OK} si la actualización fue exitosa,
     *         {@link HttpStatus#NOT_FOUND} si el producto no existe.
     */
    public HttpStatus updateProduct(Integer idProduct, Products product) {

        Optional<ProductsEntity> existingProduct = repository.findById(idProduct);
        if(existingProduct.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        product.setIdProduct(idProduct);
        repository.save(mapper.toEntity(product));
        return HttpStatus.OK;
    }

}


