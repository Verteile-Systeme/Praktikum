package de.hrw.verteiltesystemepraktikum.order;

import de.hrw.verteiltesystemepraktikum.product.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String products;

    private String quantity;

    private Integer total;

    @Temporal(TemporalType.DATE)
    private Date date;

    //void addToOrder();

}