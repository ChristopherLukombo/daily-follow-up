package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.DiabeticMenu;

@Repository
public interface DiabeticMenuRepository extends JpaRepository<DiabeticMenu, Long> {

}
