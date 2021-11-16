package de.hrw.verteiltesystemepraktikum.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private long id;

    @NotNull(message = "{name.product.not.null}")
    @NotEmpty(message = "{name.product.not.empty}")
    private String name;

    @NotNull(message = "{brand.product.not.null}")
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
