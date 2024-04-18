package com.example.teleconsultationbackend.Service;

public interface Consultation1Service {
    void addConsultationStatusWaitinghelper(Long patientId, Long depId);

    void setStatusToAcceptedHelper(Long doctorId, Long patientId);
}
