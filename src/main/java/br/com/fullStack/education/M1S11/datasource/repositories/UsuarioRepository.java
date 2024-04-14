package br.com.fullStack.education.M1S11.datasource.repositories;

import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNomeUsuario(String nomeUsuario); //query que busca os usuarios pelo nome de usuario
}
