package org.TestOBS.TestBE.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "intventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventory;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item idItem;

    private int quantity;
    private String type; // "T" for Top Up, "W" for Withdrawal
}
