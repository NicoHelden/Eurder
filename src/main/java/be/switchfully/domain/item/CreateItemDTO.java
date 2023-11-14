package be.switchfully.domain.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateItemDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private int amount;


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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
