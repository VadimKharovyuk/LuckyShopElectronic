package com.example.luckyshop.controller;

import com.example.luckyshop.model.OrderItem;
import com.example.luckyshop.model.Product;
import com.example.luckyshop.model.User;
import com.example.luckyshop.service.OrderService;
import com.example.luckyshop.service.ProductService;
import com.example.luckyshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private  final  OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/create")
    public String createOrder(@RequestParam Long userId, @RequestParam List<Long> productIds, @RequestParam List<Integer> quantities) {
        User user = userService.findById(userId);
        List<OrderItem> orderItems = IntStream.range(0, productIds.size())
                .mapToObj(i -> {
                    Product product = productService.getProductBid(productIds.get(i));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(quantities.get(i));
                    orderItem.setPrice(product.getPrice());
                    return orderItem;
                }).collect(Collectors.toList());
        orderService.createOrder(user, orderItems);
        return "redirect:/orders";
    }

    @GetMapping
    public String listOrders(Model model) {

        return "order/list";
    }
}
