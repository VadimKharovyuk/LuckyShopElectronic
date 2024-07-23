package com.example.luckyshop.service;
import com.example.luckyshop.model.ActivationToken;
import com.example.luckyshop.model.User;
import com.example.luckyshop.repository.ActivationTokenRepository;
import com.example.luckyshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ActivationTokenRepository activationTokenRepository;




    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void registerUser(User user) {
        // Проверка существования пользователя с таким же email или username
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Устанавливаем пароль и роль по умолчанию
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER); // Устанавливаем роль по умолчанию
        user.setBlocked(true); // Устанавливаем пользователя в заблокированный по умолчанию

        // Сохранение пользователя
        User savedUser = userRepository.save(user);

        // Создание активационного токена
        String token = UUID.randomUUID().toString();
        ActivationToken activationToken = new ActivationToken();
        activationToken.setToken(token);
        activationToken.setUser(savedUser);
        activationTokenRepository.save(activationToken);

        // Отправка письма с активацией
        emailService.sendVerificationEmail(user.getEmail(), token);
    }


    public boolean activateUser(String token) {
        // Проверка токена
        ActivationToken activationToken = activationTokenRepository.findByToken(token);

        if (activationToken != null && !activationToken.isUsed()) {
            User user = activationToken.getUser();
            user.setBlocked(false); // Разблокировка пользователя
            userRepository.save(user);

            activationToken.setUsed(true); // Отметка токена как использованного
            activationTokenRepository.save(activationToken);

            return true;
        }

        return false;
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
