package de.hrw.verteiltesystemepraktikum.order;

import de.hrw.verteiltesystemepraktikum.product.Product;
import de.hrw.verteiltesystemepraktikum.question.Question;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    public Order(String products, String quantity, Integer total, Date date) {
        this.products = products;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Order)) {
            return false;
        }
        Order other = (Order) o;
        boolean authorEquals = (this.products == null && other.products == null) ||
                (this.products != null && this.products.equals(other.products));
        boolean emailEquals = (this.quantity == null && other.quantity == null) ||
                (this.quantity != null && this.quantity.equals(other.quantity));
        boolean subjectEquals = (this.total == null && other.total == null) ||
                (this.total != null && this.total.equals(other.total));
        boolean contentEquals = (this.date == null && other.date == null) ||
                (this.date != null && this.date.equals(other.date));
        return authorEquals && emailEquals && subjectEquals && contentEquals;
    }

}