package com.example.realestate.service;

import com.example.realestate.domain.House;
import com.example.realestate.repository.HouseRepo;
import com.example.realestate.repository.UserRepo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "houses")
public class HouseService {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private HouseRepo houseRepo;


    public House saveHouse(House house, MultipartFile multipartFile) throws IOException{
        String imagePath=saveImage(multipartFile);
        House hous=new House();
        hous.setHouseType(house.getHouseType());
        hous.setLocation(house.getLocation());
        hous.setSize(house.getSize());
        hous.setPrice(house.getPrice());
        hous.setStatus(house.getStatus());
        hous.setAvailability(house.getAvailability());
        hous.setImagePath(imagePath);
        return houseRepo.save(hous);

    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName= UUID.randomUUID().toString()+""+file.getOriginalFilename();
        Path imagesDirectory= Paths.get(uploadPath);
        if(!Files.exists(imagesDirectory)){
            Files.createDirectories(imagesDirectory);
        }
        Path filePath=imagesDirectory.resolve(fileName);
        Files.copy(file.getInputStream(),filePath);
        return fileName;
    }
    @Cacheable(key = "'all'")
    public List<House> getAllHouse(){
        List<House> allhouses=houseRepo.findAll();
        return allhouses;
    }
    public House findHouse(int id){
        House house=houseRepo.findById(id).orElse(null);
        if(house!=null){
            return house;
        }
        return null;
    }
    public boolean deleteHouse(int id){
        House house=houseRepo.findById(id).orElse(null);
        if(house!=null){
            houseRepo.delete(house);
            return true;
        }
        else{
            return  false;
        }
    }
    public House updateHouse(House updatedHouse){
        House existingHouse = houseRepo.findById(updatedHouse.getId()).orElse(null);
        if (existingHouse != null) {
            existingHouse.setHouseType(updatedHouse.getHouseType());
            existingHouse.setLocation(updatedHouse.getLocation());
            existingHouse.setSize(updatedHouse.getSize());
            existingHouse.setPrice(updatedHouse.getPrice());
            existingHouse.setStatus(updatedHouse.getStatus());
            existingHouse.setAvailability(updatedHouse.getAvailability());

            return houseRepo.save(existingHouse);
        }
        return null;
    }
    public House findByLocation(String location){
        House house=houseRepo.findByLocation(location).orElse(null);
        if(house!=null){
            return house;
        }
        return  null;
    }
}
