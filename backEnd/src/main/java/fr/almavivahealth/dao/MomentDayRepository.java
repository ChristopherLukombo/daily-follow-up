package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.MomentDay;

@Repository
public interface MomentDayRepository extends JpaRepository<MomentDay, Long>{

}
