package be.switchfully.domain.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class ItemDTO {
    private final UUID id;
    @NotBlank(message = "Name cannot be blank")
    private final String name;
    private final String description;
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private final Double price;
    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be a non-negative number")
    private final int amount;

    public ItemDTO(UUID id, String name, String description, Double price, int amount) {
        this.id = id;
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
