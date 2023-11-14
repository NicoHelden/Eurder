package be.switchfully.domain.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class ItemDTO {
    private UUID id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private Double price;
    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be a non-negative number")
    private int amount;


    public UUID getId() {
        return id;
    }

    public ItemDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ItemDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemDTO setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return amount == itemDTO.amount && Objects.equals(id, itemDTO.id) && Objects.equals(name, itemDTO.name) && Objects.equals(description, itemDTO.description) && Objects.equals(price, itemDTO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, amount);
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
