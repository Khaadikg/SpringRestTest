package peaksoft.springrestapi.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
//    User-(id, email, password, firstName, lastName, createdDate, studyFormat, course, group)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    @Enumerated
    private StudyFormat studyFormat; // null if it's role not student
    @CreatedDate
    private LocalDate createdDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;

    /*BLOCK OF GROUPS FIELDS*/
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "group_id")
    private Group group;
    @Transient
    private Long groupId;

    /*BLOCK OF COURSE FIELDS*/
    @OneToOne(cascade = {CascadeType.ALL})
    private Course course;
    @Transient
    private long courseId;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return authorities;
    }

    @Override
    public String getPassword() {
//        return this.getPassword();
        return password;
    }

    @Override
    public String getUsername() {
//        return this.getUsername();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

