package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
