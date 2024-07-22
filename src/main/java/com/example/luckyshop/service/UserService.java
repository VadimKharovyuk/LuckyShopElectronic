package com.example.luckyshop.service;
import com.example.luckyshop.model.User;
import com.example.luckyshop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;



    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }



    @Transactional
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        // Проверка текущего пароля
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // Шифрование и установка нового пароля
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void blockUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
    }


    @Transactional
    public void unblockUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setBlocked(false);
            userRepository.save(user); // Убедитесь, что сохранение происходит внутри транзакции
        });
    }


    public boolean isBlocked(String username) {
        User user = findByUsername(username);
        return user != null && user.isBlocked();
    }


    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
