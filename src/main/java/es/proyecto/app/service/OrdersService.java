package es.proyecto.app.service;

import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.OrderMapper;
import es.proyecto.app.repository.OrderProductRepository;
import es.proyecto.app.repository.OrdersRepository;
import es.proyecto.app.repository.ProductsRepository;
import es.proyecto.app.repository.UsersRepository;
import es.swagger.codegen.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de órdenes en el sistema.
 */
@Slf4j
@Validated
@Transactional
@Service
public class OrdersService {

    private final OrderMapper mapper = OrderMapper.INSTANCE;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderProductRepository orderProductsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository userRepository;

    /**
     * Obtiene todas las órdenes registradas.
     *
     * @return lista de órdenes convertidas a modelo API.
     */
    public List<Orders> getAllOrders() {
        return mapper.toApiDomain(ordersRepository.findAll());
    }

    /**
     * Crea una nueva orden asociada a un usuario existente.
     *
     * @param order objeto {@link Orders} con los datos de la orden a crear.
     * @return {@link HttpStatus#CREATED} si la orden se creó correctamente.
     * @throws RuntimeException si el usuario asociado no se encuentra.
     */
    public HttpStatus createOrder(Orders order) {
        // Buscar el usuario en la BD antes de asignarlo
        UsersEntity user = userRepository.findById(order.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Convertir el DTO en entidad
        OrdersEntity entity = mapper.toEntity(order);
        entity.setUser(user); // Asegurar que el usuario está persistido

        ordersRepository.save(entity);
        return HttpStatus.CREATED;
    }

    /**
     * Obtiene una orden por su identificador.
     *
     * @param idOrder identificador de la orden.
     * @return objeto {@link Orders} si se encuentra, o {@code null} si no existe.
     */
    public Orders getOrderById(Integer idOrder) {
        Optional<OrdersEntity> optionalOrdersEntity = ordersRepository.findById(idOrder);
        return optionalOrdersEntity.map(mapper::toApiDomain).orElse(null);
    }

    /**
     * Elimina una orden existente por su identificador.
     *
     * @param idOrder identificador de la orden a eliminar.
     */
    public void deleteOrder(Integer idOrder) {
        if (ordersRepository.existsById(idOrder)) {
            ordersRepository.deleteById(idOrder);
        }
    }


    /**
     * Actualiza una orden existente.
     *
     * @param idOrder identificador de la orden a actualizar.
     * @param order   objeto {@link Orders} con los datos actualizados.
     * @return {@link HttpStatus#OK} si la actualización fue exitosa,
     *         {@link HttpStatus#NOT_FOUND} si la orden no existe.
     */
    public HttpStatus updateOrder(Integer idOrder, Orders order) {
        Optional<OrdersEntity> existingOrder = ordersRepository.findById(idOrder);
        if(existingOrder.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        order.setIdOrder(idOrder);
        ordersRepository.save(mapper.toEntity(order));
        return HttpStatus.OK;
    }

}

