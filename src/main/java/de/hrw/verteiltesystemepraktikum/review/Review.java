package de.hrw.verteiltesystemepraktikum.review;

import de.hrw.verteiltesystemepraktikum.product.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @JoinColumn(name = "product_id")
    @NotEmpty
    private Product product;

    public Review(String publisher, Integer starRating, String text) {
        this.publisher = publisher;
        this.starRating = starRating;
        this.text = text;
    }

    public Review(String publisher, Integer starRating, String text, Product product) {
        this.publisher = publisher;
        this.starRating = starRating;
        this.text = text;
        this.product = product;
    }
}
