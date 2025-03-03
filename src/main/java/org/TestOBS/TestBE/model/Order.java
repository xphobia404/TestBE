package org.TestOBS.TestBE.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order") // Gunakan plural untuk menghindari konflik dengan SQL keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idItem", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idInventory", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private int price;

    // Menghitung total harga secara otomatis
    public void calculatePrice() {
        if (item != null) {
            this.price = item.getItemPrice() * this.qty;
        }
    }
}
