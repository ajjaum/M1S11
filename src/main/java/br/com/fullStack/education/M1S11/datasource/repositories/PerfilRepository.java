package br.com.fullStack.education.M1S11.datasource.repositories;

import br.com.fullStack.education.M1S11.datasource.entities.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
    Optional<PerfilEntity> findByNomePerfil(String nomePerfil); //query que busca os usuarios pelo nome de usuario
}
