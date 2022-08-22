package vn.edu.uit.ms.qltt.cuoiky.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.uit.ms.qltt.cuoiky.database.entity.User;
import vn.edu.uit.ms.qltt.cuoiky.database.repository.UserRepository;
import vn.edu.uit.ms.qltt.cuoiky.exception.DataException;
import vn.edu.uit.ms.qltt.cuoiky.repository.IRedisRepo;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IRedisRepo redisRepo;


    @GetMapping(path = "/")
    public Iterable<User> getListUser() {
        LOGGER.info("Execute function get list user");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    String getUser(@PathVariable Integer id) {
        LOGGER.info("Execute function get user with id: {}", id);

        try {
            return redisRepo.getValue(id.toString());
        } catch (DataException.NotFoundException e) {
            LOGGER.warn("Not found {} in redis. Get from database", id);
            User user = userRepository.findById(id).get();
            redisRepo.save(user.getId().toString(), new Gson().toJson(user));
            return new Gson().toJson(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.badRequest().build().toString();
    }

}
