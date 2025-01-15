package es.proyecto.app.service;

import es.proyecto.app.entity.CartEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.mapper.CartMapper;
import es.proyecto.app.repository.CartRepository;
import es.proyecto.app.repository.UsersRepository;
import es.proyecto.app.repository.ProductsRepository;
import es.swagger.codegen.models.CartProductResponse;
import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@Transactional
@Service
public class CartService {

    private final CartMapper mapper = CartMapper.INSTANCE;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductsRepository productsRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public List<CartProductResponse> viewCart(Integer userId) {
        List<CartEntity> cartEntities = cartRepository.findByUser_IdUser(userId);
        return cartEntities.stream()
                .map(mapper::toApiDomain)
                .collect(Collectors.toList());
    }

    //TODO: arreglar los métodos de esta clase eliminando las pruebas de control
    public boolean addProductToCart(Integer userId, Integer productId, Integer quantity) {
        logger.info("addProductToCart called with userId: {}, productId: {}, quantity: {}", userId, productId, quantity);

        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(productId, "productId must not be null");

        // Verificar si ya existe un carrito con este usuario y producto
        Optional<CartEntity> optionalCart = cartRepository.findByUser_IdUserAndProduct_IdProduct(userId, productId);
        CartEntity cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            // Actualizar la cantidad si ya existe
            cart.setQuantity(cart.getQuantity() + quantity);
            logger.info("Updated product quantity in cart for userId: {}, productId: {}, new quantity: {}", userId, productId, cart.getQuantity());
        } else {
            // Crear un nuevo registro en el carrito si no existe
            UsersEntity user = usersRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
            ProductsEntity product = productsRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid productId"));

            cart = new CartEntity();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cartRepository.save(cart);
            logger.info("New cart item created for userId: {}, productId: {}, quantity: {}", userId, productId, quantity);
        }

        cartRepository.save(cart);
        logger.info("Product added to cart for userId: {}, productId: {}", userId, productId);
        return true;
    }




    private boolean simulateAddToCartLogic(Integer userId, Integer productId, Integer quantity) {
        // Aquí puedes simular tu lógica real de añadir al carrito
        // Por ahora, simplemente devuelve true o false aleatoriamente
        return Math.random() < 0.5; // Esto simula un fallo aleatorio
    }


    public boolean modifyProductInCart(Integer userId, Integer productId, Integer quantity) {
        Optional<CartEntity> existingCartItem = cartRepository.findByUser_IdUserAndProduct_IdProduct(userId, productId);
        if (existingCartItem.isEmpty()) {
            return false;
        }

        CartEntity cartEntity = existingCartItem.get();
        cartEntity.setQuantity(quantity);
        cartRepository.save(cartEntity);
        return true;
    }

    public boolean removeProductFromCart(Integer userId, Integer productId, Integer quantity) {
        Optional<CartEntity> existingCartItem = cartRepository.findByUser_IdUserAndProduct_IdProduct(userId, productId);
        if (existingCartItem.isPresent()) {
            CartEntity cartEntity = existingCartItem.get();
            if (cartEntity.getQuantity() > quantity) {
                cartEntity.setQuantity(cartEntity.getQuantity() - quantity);
                cartRepository.save(cartEntity);
                return true;
            } else {
                cartRepository.delete(cartEntity);
                return true;
            }
        } else {
            return false;
        }
    }

}
