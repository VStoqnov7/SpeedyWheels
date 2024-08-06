package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.model.enums.*;
import com.example.speedywheels.model.view.*;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.ModelAttributeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CarServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarServiceImpl carService;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    private CarAddDTO carAddDTO;
    private Car car;
    private User user;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        user = new User()
                .setUsername("testUser")
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("password")
                .setModified(LocalDateTime.now());

        MultipartFile photo1 = mock(MultipartFile.class);
        MultipartFile photo2 = mock(MultipartFile.class);

        carAddDTO = new CarAddDTO()
                .setModel("X5")
                .setProductionDate(LocalDateTime.now())
                .setPhotosUrls(List.of(photo1, photo2));

        car = new Car();
        car.setModel("X5");
        car.setPrice(BigDecimal.valueOf(80000.00));
        car.setProductionDate(LocalDateTime.now());
        car.setPhotosUrls(List.of("uploaded_photo1_url", "uploaded_photo2_url"));
        car.setRegisteredOn(LocalDateTime.now());
        car.setOwner(user);
        car.setBrand("BMW");
        car.setPower(500);
        car.setMileage(1000);
        car.setColor(Color.BLACK);
        car.setLocation("Sofia");
        car.setTransmission(TransmissionType.AUTOMATIC);
        car.setEuroStandard(EuroStandard.EURO_6);
        car.setCubicCapacity(3000);
        car.setEngineType(EngineType.DIESEL);

        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUser");
        lenient().when(cloudinaryService.saveImage(photo1)).thenReturn("uploaded_photo1_url");
        lenient().when(cloudinaryService.saveImage(photo2)).thenReturn("uploaded_photo2_url");
    }

    @Test
    void saveCar() {
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(carAddDTO, Car.class)).thenReturn(car);

        carService.saveCar(carAddDTO, userDetails);
        verify(userService).saveCurrentUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(1, capturedUser.getMyCars().size());

        Car capturedCar = capturedUser.getMyCars().get(0);

        assertEquals(car.getBrand(), capturedCar.getBrand());
        assertEquals(car.getModel(), capturedCar.getModel());
        assertEquals(car.getPrice(), capturedCar.getPrice());
        assertEquals(car.getProductionDate(), capturedCar.getProductionDate());
        assertEquals(car.getPhotosUrls(), capturedCar.getPhotosUrls());
        assertEquals(car.getRegisteredOn(), capturedCar.getRegisteredOn());
        assertEquals(car.getOwner(), capturedCar.getOwner());
    }
    @Test
    void theMostExpensiveCar() {
        Car expensiveCar = new Car();
        expensiveCar.setPhotosUrls(List.of("photo1", "photo2"));

        TheMostExpensiveVehicleView view = new TheMostExpensiveVehicleView();
        view.setPhotosUrls(List.of("photo1", "photo2"));

        when(carRepository.findMostExpensiveCar()).thenReturn(List.of(expensiveCar));
        when(modelMapper.map(expensiveCar, TheMostExpensiveVehicleView.class)).thenReturn(view);

        TheMostExpensiveVehicleView result = carService.theMostExpensiveCar();

        assertNotNull(result);
        assertEquals(view.getPhotosUrls(), result.getPhotosUrls());
    }

    @Test
    void theMostPowerfulCar() {
        Car powerfulCar = new Car();
        powerfulCar.setId(1L);
        powerfulCar.setBrand("Tesla");
        powerfulCar.setModel("Model S");
        powerfulCar.setPhotosUrls(List.of("photo1", "photo2"));
        powerfulCar.setHas4x4(true);
        powerfulCar.setHasAirConditioner(true);
        powerfulCar.setHasGPS(true);
        powerfulCar.setPower(1000);
        powerfulCar.setPrice(BigDecimal.valueOf(150000));
        powerfulCar.setProductionDate(LocalDateTime.now());

        TheMostPowerfulCarView powerfulCarView = new TheMostPowerfulCarView()
                .setId("1")
                .setBrand("Tesla")
                .setModel("Model S")
                .setPhotosUrls(List.of("photo1", "photo2"))
                .setHas4x4(true)
                .setHasAirConditioner(true)
                .setHasGPS(true);

        when(carRepository.findMostPowerfulCars()).thenReturn(List.of(powerfulCar));
        when(modelMapper.map(powerfulCar, TheMostPowerfulCarView.class)).thenReturn(powerfulCarView);

        TheMostPowerfulCarView result = carService.theMostPowerfulCar();

        assertNotNull(result);
        assertEquals(powerfulCarView.getId(), result.getId());
        assertEquals(powerfulCarView.getBrand(), result.getBrand());
        assertEquals(powerfulCarView.getModel(), result.getModel());
        assertEquals(powerfulCarView.getPhotosUrls(), result.getPhotosUrls());
        assertEquals(powerfulCarView.isHas4x4(), result.isHas4x4());
        assertEquals(powerfulCarView.isHasAirConditioner(), result.isHasAirConditioner());
        assertEquals(powerfulCarView.isHasGPS(), result.isHasGPS());

        verify(carRepository).findMostPowerfulCars();
        verify(modelMapper).map(powerfulCar, TheMostPowerfulCarView.class);
    }

    @Test
    void findLatestCars() {
        Car latestCar = new Car();
        latestCar.setModel("Latest");
        latestCar.setProductionDate(LocalDateTime.now());
        latestCar.setPrice(BigDecimal.valueOf(50000.00));
        VehicleView vehicleView = new VehicleView()
                .setModel("Latest")
                .setProductionDate(ModelAttributeUtil.formatDate(latestCar.getProductionDate()))
                .setPrice(ModelAttributeUtil.formatPrice(latestCar.getPrice()))
                .setType("car");

        when(carRepository.findLatestCars()).thenReturn(List.of(latestCar));
        when(modelMapper.map(latestCar, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = carService.findLatestCars();

        assertFalse(result.isEmpty());
        assertEquals(vehicleView.getModel(), result.get(0).getModel());
        assertEquals(vehicleView.getPrice(), result.get(0).getPrice());
        assertEquals(vehicleView.getProductionDate(), result.get(0).getProductionDate());
    }

    @Test
    void findById() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        Car result = carService.findById(1L);

        assertNotNull(result);
        assertEquals(car.getModel(), result.getModel());
    }

    @Test
    void findCarOwner() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        User result = carService.findCarOwner(1L);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void availableCars() {
        when(carRepository.count()).thenReturn(1L);

        boolean result = carService.availableCars();

        assertTrue(result);
    }

    @Test
    void testRemoveCarFromFavorites() {
        UserFavoriteCars favoriteCar = new UserFavoriteCars();
        favoriteCar.setCar(car);
        user.getFavoriteCars().add(favoriteCar);
        when(userService.findAll()).thenReturn(List.of(user));

        carService.removeCarFromFavorites(car);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveCurrentUser(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(0, updatedUser.getFavoriteCars().size());
    }

    @Test
    void saveVehicle() {
        Vehicle vehicle = new Car();
        when(modelMapper.map(vehicle, Car.class)).thenReturn(car);

        carService.saveVehicle(vehicle);

        verify(carRepository).save(car);
    }

    @Test
    void findJeeps() {
        // Arrange
        Car jeep1 = new Car();
        jeep1.setModel("Jeep1");
        jeep1.setCategory(CarCategory.JEEP);
        jeep1.setProductionDate(LocalDateTime.of(2022, 5, 15, 0, 0));

        Car jeep2 = new Car();
        jeep2.setModel("Jeep2");
        jeep2.setCategory(CarCategory.JEEP);
        jeep2.setProductionDate(LocalDateTime.of(2023, 8, 10, 0, 0));

        VehicleView jeepView1 = new VehicleView();
        jeepView1.setModel("Jeep1");
        jeepView1.setProductionDate(ModelAttributeUtil.formatDate(jeep1.getProductionDate()));
        jeepView1.setType("car");

        VehicleView jeepView2 = new VehicleView();
        jeepView2.setModel("Jeep2");
        jeepView2.setProductionDate(ModelAttributeUtil.formatDate(jeep2.getProductionDate()));
        jeepView2.setType("car");

        when(carRepository.findByCategory(CarCategory.JEEP)).thenReturn(List.of(jeep1, jeep2));
        when(modelMapper.map(jeep1, VehicleView.class)).thenReturn(jeepView1);
        when(modelMapper.map(jeep2, VehicleView.class)).thenReturn(jeepView2);

        // Act
        List<VehicleView> result = carService.findJeeps();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Jeep1", result.get(0).getModel());
        assertEquals("Jeep2", result.get(1).getModel());
        assertEquals(ModelAttributeUtil.formatDate(jeep1.getProductionDate()), result.get(0).getProductionDate());
        assertEquals(ModelAttributeUtil.formatDate(jeep2.getProductionDate()), result.get(1).getProductionDate());
        assertEquals("car", result.get(0).getType());
        assertEquals("car", result.get(1).getType());

        verify(carRepository).findByCategory(CarCategory.JEEP);
        verify(modelMapper).map(jeep1, VehicleView.class);
        verify(modelMapper).map(jeep2, VehicleView.class);
    }

    @Test
    void testFindMyFavoriteCars() {
        Car favoriteCar = new Car();
        favoriteCar.setModel("X5");
        favoriteCar.setPrice(BigDecimal.valueOf(80000.00));
        favoriteCar.setProductionDate(LocalDateTime.now());

        UserFavoriteCars favoriteCarEntity = new UserFavoriteCars();
        favoriteCarEntity.setCar(favoriteCar);
        favoriteCarEntity.setAddedToFavorite(LocalDateTime.now());

        user.setFavoriteCars(Collections.singletonList(favoriteCarEntity));

        VehicleView vehicleView = new VehicleView();
        vehicleView.setModel("X5");
        vehicleView.setPrice(ModelAttributeUtil.formatPrice(favoriteCar.getPrice()));
        vehicleView.setProductionDate(ModelAttributeUtil.formatDate(favoriteCar.getProductionDate()));
        vehicleView.setAddedToFavorite(favoriteCarEntity.getAddedToFavorite());
        vehicleView.setType("car");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(favoriteCar, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = carService.findMyFavoriteCars("testUser");

        assertFalse(result.isEmpty());
        assertEquals(vehicleView, result.get(0));

        verify(modelMapper).map(favoriteCar, VehicleView.class);
    }

    @Test
    void findMyCars() {
        VehicleView vehicleView = new VehicleView()
                .setModel("X5")
                .setPrice(ModelAttributeUtil.formatPrice(car.getPrice()))
                .setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()))
                .setType("car");
        user.setMyCars(Collections.singletonList(car));
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(car, VehicleView.class)).thenReturn(vehicleView);

        List<VehicleView> result = carService.findMyCars("testUser");

        assertFalse(result.isEmpty());
        assertEquals(vehicleView.getModel(), result.get(0).getModel());
        assertEquals(vehicleView.getPrice(), result.get(0).getPrice());
    }

    @Test
    void refreshCar() {
        Car car = new Car();
        car.setRegisteredOn(LocalDateTime.of(2020, 1, 1, 0, 0));

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        carService.refreshCar(1L);

        verify(carRepository).findById(1L);
        verify(carRepository).save(car);
        assertTrue(car.getRegisteredOn().isAfter(LocalDateTime.of(2020, 1, 1, 0, 0)));
    }

    @Test
    void createCarProfileView() {
        car.setRegisteredOn(LocalDateTime.of(2023, 8, 3, 12, 0));

        CarProfileView carProfileView = new CarProfileView();
        carProfileView.setModel("X5");
        carProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(car.getRegisteredOn()));
        carProfileView.setPrice(ModelAttributeUtil.formatPrice(car.getPrice()));

        when(modelMapper.map(car, CarProfileView.class)).thenReturn(carProfileView);

        CarProfileView result = carService.createCarProfileView(car);

        assertNotNull(result);
        assertEquals(car.getModel(), result.getModel());
        assertEquals(ModelAttributeUtil.formatDate(car.getRegisteredOn()), result.getRegisteredOn());
        assertEquals(ModelAttributeUtil.formatPrice(car.getPrice()), result.getPrice());

        verify(modelMapper).map(car, CarProfileView.class);
    }


    @Test
    void addFavoriteCar() {

        carService.addFavoriteCar(car, user);

        assertEquals(1, user.getFavoriteCars().size());

        UserFavoriteCars favoriteCar = user.getFavoriteCars().get(0);
        assertNotNull(favoriteCar);
        assertEquals(car, favoriteCar.getCar());
        assertEquals(user, favoriteCar.getUser());
        assertNotNull(favoriteCar.getAddedToFavorite());
    }

    @Test
    void deleteFavoriteCar() {

        UserFavoriteCars favoriteCar = new UserFavoriteCars()
                .setCar(car)
                .setAddedToFavorite(LocalDateTime.now())
                .setUser(user);

        user.setFavoriteCars(new ArrayList<>(List.of(favoriteCar)));

        carService.deleteFavoriteCar(car, user);

        assertTrue(user.getFavoriteCars().isEmpty());
    }

    @Test
    void deleteCar() {
        user.getMyCars().add(car);
        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        doNothing().when(carRepository).deleteById(anyLong());

        carService.deleteCar(user, 1L, car);

        assertFalse(user.getMyCars().contains(car));
        verify(carRepository).deleteById(1L);
    }
}