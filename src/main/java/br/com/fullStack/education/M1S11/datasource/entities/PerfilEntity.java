package br.com.fullStack.education.M1S11.datasource.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "perfil")
public class PerfilEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id")
    private Long id;

    private String nomePerfil;

    @Override
    public String getAuthority() {
        return this.nomePerfil;
    }
}
