package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import fr.almavivahealth.enums.Action;

/**
 * A DTO for the PatientHistory entity.
 */
public class PatientHistoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    private PatientDTO patient;
    
    private String modifiedBy;
    
    private LocalDateTime modifiedDate;
    
    private Action action;
    
    public PatientHistoryDTO() {
    	// Empty constructor needed for Jackson.
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(final PatientDTO patient) {
		this.patient = patient;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(final Action action) {
		this.action = action;
	}
	
}
