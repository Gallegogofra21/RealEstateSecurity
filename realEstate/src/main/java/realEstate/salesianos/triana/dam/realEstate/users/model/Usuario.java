package realEstate.salesianos.triana.dam.realEstate.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import realEstate.salesianos.triana.dam.realEstate.models.Inmobiliaria;
import realEstate.salesianos.triana.dam.realEstate.models.Interesa;
import realEstate.salesianos.triana.dam.realEstate.models.Vivienda;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellidos;
    private String direccion;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;
    private String telefono;
    private String avatar;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole rol;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id", foreignKey = @ForeignKey(name = "PK_USER_INMOBILIARIA"), nullable = true)
    private Inmobiliaria inmobiliaria;

    @OneToMany(mappedBy = "propietario")
    private List<Vivienda> viviendas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "interesado")
    private List<Interesa> intereses = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    public Usuario(String nombre, String apellidos) {
    }

    public Usuario(String nombre, String apellidos, String direccion, String email, String telefono, String avatar) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

    // HELPERS

    public void addInmobiliaria(Inmobiliaria i) {
        this.inmobiliaria = i;
        i.getGestores().add(this);
    }

    public void removeInmobiliaria(Inmobiliaria i) {
        i.getGestores().remove(this);
        this.inmobiliaria = null;
    }

    public void nullearPropietarioDeViviendas() {
    }
}
