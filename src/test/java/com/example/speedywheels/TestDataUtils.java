package com.example.speedywheels;

import com.example.speedywheels.model.dtos.*;
import com.example.speedywheels.model.entity.Contact;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.enums.*;
import com.example.speedywheels.repository.UserRoleRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataUtils {
    public static CarAddDTO createCarAddDTO(MockMultipartFile picture, BigDecimal price, Integer power) {
        return new CarAddDTO()
                .setBrand("BMW")
                .setModel("X5")
                .setPower(power)
                .setMileage(5000)
                .setProductionDate(LocalDateTime.now())
                .setColor(Color.DARK_BLUE)
                .setLocation("Sofia")
                .setTransmission(TransmissionType.AUTOMATIC)
                .setEuroStandard(EuroStandard.EURO_6)
                .setCubicCapacity(3000)
                .setEngineType(EngineType.PETROL)
                .setPrice(price)
                .setCategory(CarCategory.JEEP)
                .setHas4x4(true)
                .setHasAirConditioner(true)
                .setHasGPS(true)
                .setPhotosUrls(List.of(picture));
    }

    public static MotorcycleAddDTO createMotorcycleAddDTO(MockMultipartFile picture, BigDecimal price, Integer power) {
        return new MotorcycleAddDTO()
                .setBrand("Yamaha")
                .setModel("R1")
                .setPower(power)
                .setMileage(5000)
                .setProductionDate(LocalDateTime.now())
                .setColor(Color.DARK_BLUE)
                .setLocation("Sofia")
                .setTransmission(TransmissionType.AUTOMATIC)
                .setEuroStandard(EuroStandard.EURO_6)
                .setCubicCapacity(3000)
                .setEngineType(EngineType.PETROL)
                .setPrice(price)
                .setCategory(MotorcycleCategory.SPORT)
                .setHasLuggageCase(true)
                .setHasSidecar(true)
                .setHasStabilityControl(true)
                .setPhotosUrls(List.of(picture));
    }

    public static User createUser(PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        return new User()
                .setUsername("testUser")
                .setPassword(passwordEncoder.encode("test"))
                .setFirstName("test")
                .setLastName("User")
                .setAge(22)
                .setProfilePictureUrl("https://bootdey.com/img/Content/avatar/avatar6.png")
                .setBanned(false)
                .setRegisteredOn(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setActive(true)
                .setRoles(userRoleRepository.findAll().stream().collect(Collectors.toSet()))
                .setContact(new Contact().setEmail("test@example.com"));
    }

    public static CarAddDTO createWrongCarAddDTO() {
        return new CarAddDTO()
                .setBrand("")
                .setModel("") // Adding this field as it was missing
                .setPower(-1)
                .setMileage(-100)
                .setProductionDate(LocalDateTime.now().plusDays(1))
                .setColor(null)
                .setLocation("")
                .setTransmission(null)
                .setEuroStandard(null)
                .setCubicCapacity(0)
                .setEngineType(null)
                .setPrice(BigDecimal.valueOf(-1000))
                .setCategory(null)
                .setHas4x4(false)
                .setHasAirConditioner(false)
                .setHasGPS(false)
                .setPhotosUrls(List.of());
    }

    public static CarAddDTO createCarAddDTO(MockMultipartFile picture) {
        return new CarAddDTO()
                .setBrand("BMW")
                .setModel("X5")
                .setPower(300)
                .setMileage(5000)
                .setProductionDate(LocalDateTime.now())
                .setColor(Color.DARK_BLUE)
                .setLocation("Sofia")
                .setTransmission(TransmissionType.AUTOMATIC)
                .setEuroStandard(EuroStandard.EURO_6)
                .setCubicCapacity(3000)
                .setEngineType(EngineType.PETROL)
                .setPrice(BigDecimal.valueOf(20000))
                .setCategory(CarCategory.JEEP)
                .setHas4x4(true)
                .setHasAirConditioner(true)
                .setHasGPS(true)
                .setPhotosUrls(List.of(picture));
    }

    public static MotorcycleAddDTO createMotorcycleAddDTO(MockMultipartFile picture) {
        MotorcycleAddDTO motorcycleAddDTO = new MotorcycleAddDTO()
                .setBrand("Yamaha")
                .setModel("R1")
                .setPower(300)
                .setMileage(5000)
                .setProductionDate(LocalDateTime.now())
                .setColor(Color.DARK_BLUE)
                .setLocation("Sofia")
                .setTransmission(TransmissionType.AUTOMATIC)
                .setEuroStandard(EuroStandard.EURO_6)
                .setCubicCapacity(3000)
                .setEngineType(EngineType.PETROL)
                .setPrice(BigDecimal.valueOf(20000))
                .setCategory(MotorcycleCategory.SPORT)
                .setHasLuggageCase(true)
                .setHasSidecar(true)
                .setHasStabilityControl(true)
                .setPhotosUrls(List.of(picture));
        return motorcycleAddDTO;
    }


    public static MotorcycleAddDTO createWrongMotorcycleAddDTO() {
        MotorcycleAddDTO motorcycleAddDTO = new MotorcycleAddDTO()
                .setBrand("")
                .setPower(-1)
                .setMileage(-100)
                .setProductionDate(LocalDateTime.now().plusDays(1))
                .setColor(null)
                .setLocation("")
                .setTransmission(null)
                .setEuroStandard(null)
                .setCubicCapacity(0)
                .setEngineType(null)
                .setPrice(BigDecimal.valueOf(-1000))
                .setCategory(null)
                .setHasLuggageCase(true)
                .setHasSidecar(true)
                .setHasStabilityControl(true)
                .setPhotosUrls(List.of());
        return motorcycleAddDTO;
    }

    public static UserProfileDTO createUserProfileDTO() {
        UserProfileDTO userProfileDTO = new UserProfileDTO()
                .setFirstName("UpdatedName")
                .setLastName("UpdatedLastName")
                .setContactPhone("1234567890")
                .setCity("UpdatedCity")
                .setSocialMediasGithub("github.com/updated")
                .setSocialMediasTwitter("twitter.com/updated")
                .setSocialMediasInstagram("instagram.com/updated")
                .setSocialMediasFacebook("facebook.com/updated");
        return userProfileDTO;
    }

    public static UserProfileDTO createWrongUserProfileDTO() {
        UserProfileDTO userProfileDTO = new UserProfileDTO()
                .setFirstName("")
                .setLastName("")
                .setContactPhone("")
                .setCity("")
                .setSocialMediasGithub("")
                .setSocialMediasTwitter("")
                .setSocialMediasInstagram("")
                .setSocialMediasFacebook("");
        return userProfileDTO;
    }

    public static UserProfileDTO createUserProfileDTOWithNoSocialMedia() {
        UserProfileDTO userProfileDTO = new UserProfileDTO()
                .setFirstName("UpdatedName")
                .setLastName("UpdatedLastName")
                .setContactPhone("1234567890")
                .setCity("UpdatedCity")
                .setSocialMediasGithub("")
                .setSocialMediasTwitter("")
                .setSocialMediasInstagram("")
                .setSocialMediasFacebook("");
        return userProfileDTO;
    }

    public static MockMultipartFile getMockMultipartFile() throws IOException {
        String imagePath = "src/test/resources/static/images/test.jpeg";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        MockMultipartFile picture1 = new MockMultipartFile(
                "photosUrls",
                "vehicle.jpg",
                "image/jpeg",
                imageBytes);
        return picture1;
    }

    public static CommentAddDTO createCommentAddDTOForCar() {
        return new CommentAddDTO()
                .setUser("testUser")
                .setCommentedAt(LocalDateTime.now())
                .setComment("Great car!");
    }

    public static CommentAddDTO createCommentAddDTOForMotorcycle() {
        return new CommentAddDTO()
                .setUser("testUser")
                .setCommentedAt(LocalDateTime.now())
                .setComment("Great motorcycle!");
    }

    public static ContactDTO createContactDTO() {
        return new ContactDTO()
                .setFullName("Ventsislav Stoyanov")
                .setEmail("ventsislav.stoyanov@example.com")
                .setSubject("Test Subject")
                .setMessage("Test message");
    }

}
