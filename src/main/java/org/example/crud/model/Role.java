    package org.example.crud.model;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.security.core.GrantedAuthority;

    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Getter
    @Setter
    @Table(name = "roles")
    public class Role implements GrantedAuthority {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @ManyToMany(mappedBy = "roles")
        private Set<UserProfile> accounts = new HashSet<>();

        @Override
        public String getAuthority() {
            return name;
        }
    }
