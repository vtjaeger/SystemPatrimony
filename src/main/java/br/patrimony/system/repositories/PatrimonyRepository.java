package br.patrimony.system.repositories;

import br.patrimony.system.models.Patrimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
}
