package br.com.fullStack.education.M1S11.datasource.repositories;

import br.com.fullStack.education.M1S11.datasource.entities.CadernoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CadernoRepository extends JpaRepository<CadernoEntity, Long> {
    List<CadernoEntity> findAllByUsuarioId(Long idUsuario);

    Optional<CadernoEntity> findByUsuarioIdAndId(Long usuarioId, Long id);

    Optional<CadernoEntity> findFirstByUsuarioId(Long usuarioId);

}
