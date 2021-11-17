package de.hrw.verteiltesystemepraktikum.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


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
