package de.hrw.verteiltesystemepraktikum.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hrw.verteiltesystemepraktikum.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The data class represents a Product with a name, brand, new and old Price which can be stored in a Database.
 *
 * @author Dimitrios Barkas
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String brand;

    private Integer newPrice;

    private Integer oldPrice;

    public Product(
            String name,
            String brand,
            Integer newPrice,
            Integer oldPrice
    ) {
        this.name = name;
        this.brand = brand;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
    }


}
