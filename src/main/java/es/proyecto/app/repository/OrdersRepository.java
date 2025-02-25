package es.proyecto.app.repository;

import es.proyecto.app.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

    }
