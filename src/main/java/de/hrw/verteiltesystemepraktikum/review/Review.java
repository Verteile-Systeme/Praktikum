package de.hrw.verteiltesystemepraktikum.review;

import de.hrw.verteiltesystemepraktikum.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publisher;

    private Integer starRating;

    private String text;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public void assignProduct(Product product) {
        this.product = product;
    }
}
