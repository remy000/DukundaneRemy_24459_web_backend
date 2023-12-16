package com.example.realestate.controller;

import com.example.realestate.domain.House;
import com.example.realestate.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/houses")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @PostMapping("/saveHouse")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>saveHouse(@ModelAttribute House house, @RequestParam("file") MultipartFile file){
        try{
            houseService.saveHouse(house,file);
            return new ResponseEntity<>("House added successfull", HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("House not added" +ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @GetMapping("/getAllHouses")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    public ResponseEntity<?>allHouses(){
        List<House> allHouses=houseService.getAllHouse();
        if(allHouses!=null){
            List<House> houseResponses = allHouses.stream().peek(house -> {
                String imageUrl = "http://localhost:8080/images/" + house.getImagePath(); // Replace with your domain
                house.setImagePath(imageUrl);
                // Construct response object
            }).toList();
            return new ResponseEntity<>(houseResponses,HttpStatus.OK);

        }  else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getOneHouse/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?>oneHouse(@PathVariable("id") Integer id){
        House house=houseService.findHouse(id);
        if(house!=null){
            String imageUrl = "http://localhost:8080/images/" + house.getImagePath();
            house.setImagePath(imageUrl);
            return new ResponseEntity<>(house,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateHouse/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateHouse(@PathVariable("id") Integer id, @RequestBody House updatedHouse) {
        House house = houseService.findHouse(id);
        if (house != null) {
            house.setHouseType(updatedHouse.getHouseType());
            house.setLocation(updatedHouse.getLocation());
            house.setSize(updatedHouse.getSize());
            house.setPrice(updatedHouse.getPrice());
            house.setStatus(updatedHouse.getStatus());
            house.setAvailability(updatedHouse.getAvailability());

            House updated = houseService.updateHouse(house);
            if (updated != null) {
                return new ResponseEntity<>("House Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("House not updated", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("House not found", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteHouse/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteHouse(@PathVariable ("id") Integer id){

        boolean deleted=houseService.deleteHouse(id);
        if(deleted){
            return new ResponseEntity<>("House deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("House not deleted",HttpStatus.BAD_REQUEST);
        }
    }
}
