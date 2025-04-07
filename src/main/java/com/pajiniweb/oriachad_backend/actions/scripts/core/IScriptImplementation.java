package com.pajiniweb.oriachad_backend.actions.scripts.core;

public interface IScriptImplementation {


	boolean run(Object[] args) throws Exception;

	void register();

	String getCode();

	Object getStudentCardData(Long idStudent, Long idYear);

	Object getClassCardData(Long idClass, Long idYear);

	Object getLevelCardData(Long idLevel, Long idYear);

	Object getSubjectCardData(Long idSubject, Long idYear);

	Object getSpecialityCardData(Long idSpecialty, Long idLevel, Long idYear);

	Object getEstablishmentCardData(Long idEstablishment, Long idYear);

	Object getGuardianCardData(Long idStudent, Long idYear);

	Boolean hasStudentCardData();

	Boolean hasClassCardData();

	Boolean hasLevelCardData();

	Boolean hasEstablishmentCardData();

	Boolean hasGuardianCardData();

	Boolean hasSpecialtyCardData();
	Boolean  hasSubjectCardData();
}
