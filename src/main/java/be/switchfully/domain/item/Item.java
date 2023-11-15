package be.switchfully.domain.item;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private final String description;
    @Column(name = "price", nullable = false)
    private final Double price;
    @Column(name = "amount", nullable = false)
    private final int amount;

    protected Item() {

        this.name = null;
        this.description = null;
        this.price = null;
        this.amount = 0;
    }

    public Item(String name, String description, Double price, int amount) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }


    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public Double getPrice() {
        return price;
    }


    public int getAmount() {
        return amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return amount == item.amount && Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, amount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public void setId(UUID id) {
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IllegalStateException("Cannot change the ID of an existing entity");
        }
    }
    public void updateName(String newName) {
        if (newName != null && !newName.isBlank()) {
            this.name = newName;
        } else {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }

}
