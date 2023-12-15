/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.dinemaster.service;

import java.util.*;
import com.example.dinemaster.model.*;
import com.example.dinemaster.repository.*;
import com.example.dinemaster.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChefJpaService implements ChefRepository {
    @Autowired
    private ChefJpaRepository chefJpaRepository;
    @Autowired
    private RestaurantJpaService restaurantJpaService;

    @Override
    public ArrayList<Chef> getChefs() {
        return (ArrayList<Chef>) chefJpaRepository.findAll();
    }

    @Override
    public Chef getChefById(int chefId) {
        try {
            return chefJpaRepository.findById(chefId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Chef addChef(Chef chef) {

        int restaurantId = chef.getRestaurant().getId();
        Restaurant restaurant = restaurantJpaService.getRestaurantById(restaurantId);
        chef.setRestaurant(restaurant);
        chefJpaRepository.save(chef);
        return chef;
    }

    @Override
    public Chef updateChef(int chefId, Chef chef) {
        try {
            Chef existChef = chefJpaRepository.findById(chefId).get();
            if (chef.getFirstName() != null) {
                existChef.setFirstName(chef.getFirstName());
            }
            if (chef.getLastName() != null) {
                existChef.setLastName(chef.getLastName());
            }
            if (chef.getExpertise() != null) {
                existChef.setExpertise(chef.getExpertise());
            }
            if (chef.getExperienceYears() != 0) {
                existChef.setExperienceYears(chef.getExperienceYears());
            }
            if (chef.getRestaurant() != null) {
                int restaurantId = chef.getRestaurant().getId();
                Restaurant restaurant = restaurantJpaService.getRestaurantById(restaurantId);
                existChef.setRestaurant(restaurant);

            }
            chefJpaRepository.save(existChef);
            return existChef;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteChef(int chefId) {
        try {
            chefJpaRepository.deleteById(chefId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Restaurant getRestaurantOfChef(int chefId) {
        try {
            return chefJpaRepository.findById(chefId).get().getRestaurant();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}