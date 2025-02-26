package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/get")
    public Order getOrder(@RequestParam("id") Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new RuntimeException("Something went wrong");
    }

    @GetMapping("/searchByDate")
    public List<Order> searchOrdersByDate(@RequestParam("date") String date) {
        try {
            List<Order> allOrders = orderRepository.findAll(); 
            return allOrders.stream()
                    .filter(order -> order.getOrderDate().equals(date)) 
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        try {
            if (order.getOrderId() != null && orderRepository.existsById(order.getOrderId())) {
                throw new RuntimeException("Order with this ID already exists");
            }
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    @DeleteMapping("/delete")
    public String deleteOrder(@RequestParam("id") Long id) {
        try {
            orderRepository.deleteById(id);
            return "Deleted successfully";
        } catch (Exception e) {
            return "Something went wrong"; 
        }
    }
}
