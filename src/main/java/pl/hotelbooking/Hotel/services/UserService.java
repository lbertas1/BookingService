package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.domain.User;
import pl.hotelbooking.Hotel.repository.RoomRepository;
import pl.hotelbooking.Hotel.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }
}
