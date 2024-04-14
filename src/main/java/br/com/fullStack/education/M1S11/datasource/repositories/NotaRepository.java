package br.com.fullStack.education.M1S11.datasource.repositories;

import br.com.fullStack.education.M1S11.datasource.entities.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findAllByUsuarioId(Long idUsuario);

    Optional<NotaEntity> findByUsuarioIdAndId(Long usuarioId, Long id);
}
