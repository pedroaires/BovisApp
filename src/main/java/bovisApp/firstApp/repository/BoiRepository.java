package bovisApp.firstApp.repository;

import bovisApp.firstApp.model.Boi;
import bovisApp.firstApp.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoiRepository extends JpaRepository<Boi, Long> {
    boolean existsByLoteAndNumero(Lote lote, Integer numero);
}
