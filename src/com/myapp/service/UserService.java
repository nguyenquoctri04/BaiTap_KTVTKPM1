package src.com.myapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private final Map<String, String> users = new HashMap<>();

    public UserService() {
        // Sample users for demonstration purposes
        users.put("admin", "password123");
        users.put("user", "userpass");
    }

    public String authenticate(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            // Generate a simple token (in production, use JWT or a secure method)
            return UUID.randomUUID().toString();
        }
        return null;
    }
}
