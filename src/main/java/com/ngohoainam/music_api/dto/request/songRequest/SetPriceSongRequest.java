package com.ngohoainam.music_api.dto.request.songRequest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class SetPriceSongRequest {
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.01",message = "Price must be greater than 0")
    private BigDecimal price;
}
