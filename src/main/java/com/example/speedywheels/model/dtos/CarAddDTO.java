package com.example.speedywheels.model.dtos;

import com.example.speedywheels.model.enums.*;
import com.example.speedywheels.validation.vehiclePicturesValidator.ValidPhotos;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CarAddDTO {

    @NotBlank(message = "{car.add.brandError}")
    private String brand;

    @NotBlank(message = "{car.add.modelError}")
    private String model;

    @Min(value = 1, message = "{car.add.powerError}")
    private int power;

    @Min(value = 0, message = "{car.add.mileageError}")
    private int mileage;

    @NotNull(message = "{car.add.productionDateError}")
    @PastOrPresent(message = "{car.add.productionDateErrorPastOrPresent}")
    private LocalDateTime productionDate;

    @NotNull(message = "{car.add.colorError}")
    private Color color;

    @NotBlank(message = "{car.add.locationError}")
    private String location;

    @NotNull(message = "{car.add.transmissionError}")
    private TransmissionType transmission;

    @NotNull(message = "{car.add.euroStandardError}")
    private EuroStandard euroStandard;

    @Min(value = 1, message = "{car.add.cubicCapacityError}")
    private int cubicCapacity;

    @NotNull(message = "{car.add.engineTypeError}")
    private EngineType engineType;

    @NotNull(message = "{car.add.priceErrorNotNull}")
    @Min(value = 1, message = "{car.add.priceError}")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "{car.add.categoryError}")
    private CarCategory category;

    private boolean has4x4;

    private boolean hasAirConditioner;

    private boolean hasGPS;

}
