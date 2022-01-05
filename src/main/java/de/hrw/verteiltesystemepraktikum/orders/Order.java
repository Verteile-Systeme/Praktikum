package de.hrw.verteiltesystemepraktikum.orders;


import de.hrw.verteiltesystemepraktikum.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity(
        name = "order"
)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "products_id")
    @NotEmpty
    private Product products;

    public Product getProducts() {
        return products;
    }

    @NotEmpty
    private Double quantity;

    @NotEmpty
    private Double total;

    @NotEmpty
    @DateTimeFormat
    private Date date;

    public Order(
            Long id,
            Product products,
            Double quantity,
            Double total,
            Date date
    ) {
        this.id = id;
        this.products = products;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

}
