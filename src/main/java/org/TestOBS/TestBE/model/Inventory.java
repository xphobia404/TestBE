package org.TestOBS.TestBE.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idItem", nullable = false)
    private Item item;

    private int quantity;
    private String type; // "T" for Top Up, "W" for Withdrawal
}
