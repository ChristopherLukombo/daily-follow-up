package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
