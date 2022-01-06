package de.hrw.verteiltesystemepraktikum.order;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hrw.verteiltesystemepraktikum.product.Product;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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


    @JsonIgnore
    @ManyToMany(mappedBy = "orderSet")
    private Set<Product> products = new HashSet<>();

    @NotEmpty
    private Double quantity;

    @NotEmpty
    private Double total;

    @NotEmpty
    @DateTimeFormat
    private Date date;

}