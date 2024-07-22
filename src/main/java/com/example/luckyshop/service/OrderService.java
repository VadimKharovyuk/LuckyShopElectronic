package com.example.luckyshop.service;

import com.example.luckyshop.model.Order;
import com.example.luckyshop.model.OrderItem;
import com.example.luckyshop.model.User;
import com.example.luckyshop.repository.OrderRepository;
import com.example.luckyshop.repository.OrderItemRepository;
import com.example.luckyshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    private  final  OrderItemRepository orderItemRepository;


    private  final  UserRepository userRepository;

    // Создание нового заказа
    public Order createOrder(User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setStatus(Order.Status.Pending);
        order.setDateCreate(LocalDateTime.now());
        order.setTotalPrice(calculateTotalPrice(orderItems));

        return orderRepository.save(order);
    }

    // Получение заказа по ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Получение всех заказов пользователя
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // Обновление статуса заказа
    public Order updateOrderStatus(Long orderId, Order.Status status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    // Добавление элемента в заказ
    public Order addOrderItem(Long orderId, OrderItem orderItem) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            orderItem.setOrder(order); // Устанавливаем заказ для элемента
            order.getOrderItems().add(orderItem); // Добавляем элемент в список
            order.setTotalPrice(calculateTotalPrice(order.getOrderItems())); // Пересчитываем общую цену
            orderRepository.save(order); // Сохраняем изменения
            return order;
        }
        return null;
    }

    // Удаление элемента из заказа
    public Order removeOrderItem(Long orderId, Long orderItemId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            Optional<OrderItem> orderItemOpt = orderItemRepository.findById(orderItemId);
            if (orderItemOpt.isPresent()) {
                OrderItem orderItem = orderItemOpt.get();
                order.getOrderItems().remove(orderItem); // Удаляем элемент из списка
                orderItemRepository.delete(orderItem); // Удаляем элемент из базы данных
                order.setTotalPrice(calculateTotalPrice(order.getOrderItems())); // Пересчитываем общую цену
                orderRepository.save(order); // Сохраняем изменения
                return order;
            }
        }
        return null;
    }


    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
