package org.example.crud.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crud.model.UserProfile;
import org.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            setDefaultTargetUrl("/api/users");
        } else {
            String name = authentication.getName();
            Optional<UserProfile> user = userRepository.findByUsername(name);
            if (user.isPresent()) {
                String redirectUrl = "/api/users";
                response.sendRedirect(request.getContextPath() + redirectUrl);
                return;
            } else {
                throw new RuntimeException("User not found with id: " + name);
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
