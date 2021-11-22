package de.hrw.verteiltesystemepraktikum.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hrw.verteiltesystemepraktikum.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotEmpty(message = "{name.product.not.empty}")
    private String name;

    @NotEmpty(message = "{brand.product.not.empty}")
    private String brand;

    private Integer newPrice;

    private Integer oldPrice;

//    @JsonIgnore
//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private Set<Review> reviews = new HashSet<>();

    public Product(String name, String brand, Integer newPrice, Integer oldPrice) {
        this.name = name;
        this.brand = brand;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", newPrice=" + newPrice +
                ", oldPrice=" + oldPrice +
                '}';
    }

}
