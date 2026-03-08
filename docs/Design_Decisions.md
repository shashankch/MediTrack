# Design Decisions

## Overview

MediTrack follows object-oriented principles with design patterns for maintainability and extensibility.

## Key Decisions

- **Layered Architecture**: Entities, Services, Utilities.
- **Dependency Injection**: Services injected into Main for loose coupling.
- **Observer Pattern**: For appointment notifications.
- **Factory Pattern**: For bill creation.
- **Strategy Pattern**: For billing strategies.
- **In-Memory Data Store**: With CSV persistence for simplicity.

## UML Class Diagram

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }

    class PatientService {
        +addPatient(String, int): Patient
        +updatePatient(String, String, int): Patient
        +deletePatient(String): Patient
        +listPatients()
        +getPatient(String): Patient
    }

    class DoctorService {
        +addDoctor(String, int, Specialization, double): Doctor
        +updateDoctor(String, String, int, Specialization, double): Doctor
        +deleteDoctor(String): Doctor
        +listDoctors()
        +getDoctor(String): Doctor
        +averageDoctorFee(): double
    }

    class AppointmentService {
        +createAppointment(String, String): Appointment
        +cancelAppointment(String): boolean
        +listAppointments()
        +getAppointment(String): Appointment
        +appointmentsPerDoctor(): Map
    }

    class BillingService {
        +generateStandardBill(String): BillSummary
        +generateDiscountBill(String): BillSummary
    }

    class SearchService {
        +globalSearch(String): List<Searchable>
    }

    class NotificationService {
        +addObserver(Observer)
        +removeObserver(Observer)
        +notifyObservers(Appointment)
    }

    class DataStore~T~ {
        +save(String, T)
        +get(String): T
        +update(String, T): T
        +delete(String): T
        +all(): List~T~
    }

    class IdGenerator {
        +nextPatient(): String
        +nextDoctor(): String
        +nextAppointment(): String
        +nextBill(): String
    }

    class CSVUtil {
        +loadData(DoctorService, PatientService, AppointmentService)
        +saveData(...)
    }

    class AppointmentObserver {
        +update(Appointment)
    }

    class BillFactory {
        +createStandard(String, double): Bill
        +createSenior(String, double): Bill
    }

    class BillingStrategy {
        +calculate(double): double
    }

    class StandardBillingStrategy {
        +calculate(double): double
    }

    class SeniorBillingStrategy {
        +calculate(double): double
    }

    Main --> PatientService
    Main --> DoctorService
    Main --> AppointmentService
    Main --> BillingService
    Main --> SearchService
    Main --> NotificationService

    AppointmentService --> PatientService
    AppointmentService --> DoctorService
    AppointmentService --> NotificationService

    BillingService --> AppointmentService
    BillingService --> DoctorService

    SearchService --> PatientService
    SearchService --> DoctorService
    SearchService --> AppointmentService

    PatientService --> DataStore
    DoctorService --> DataStore
    AppointmentService --> DataStore

    PatientService --> IdGenerator
    DoctorService --> IdGenerator
    AppointmentService --> IdGenerator
    BillingService --> IdGenerator

    NotificationService --> AppointmentObserver

    BillingService --> BillFactory
    BillFactory --> BillingStrategy
    BillingStrategy <|-- StandardBillingStrategy
    BillingStrategy <|-- SeniorBillingStrategy

    class Patient {
        -id: String
        -name: String
        -age: int
        +getId(): String
        +getName(): String
        +getAge(): int
        +setName(String)
        +setAge(int)
        +matches(String): boolean
    }

    class Doctor {
        -id: String
        -name: String
        -age: int
        -specialization: Specialization
        -fee: double
        +getId(): String
        +getName(): String
        +getAge(): int
        +getSpecialization(): Specialization
        +getFee(): double
        +setName(String)
        +setAge(int)
        +setSpecialization(Specialization)
        +setFee(double)
        +matches(String): boolean
    }

    class Appointment {
        -id: String
        -patientId: String
        -doctorId: String
        -status: AppointmentStatus
        +getId(): String
        +getPatientId(): String
        +getDoctorId(): String
        +getStatus(): AppointmentStatus
        +cancel()
        +getSummary(): String
        +matches(String): boolean
    }

    class Bill {
        -id: String
        -amount: double
        -paid: boolean
        +getId(): String
        +getAmount(): double
        +pay()
    }

    class BillSummary {
        -id: String
        -amount: double
        +getId(): String
        +getAmount(): double
    }

    PatientService --> Patient
    DoctorService --> Doctor
    AppointmentService --> Appointment
    BillingService --> Bill
    BillingService --> BillSummary

    class Specialization {
        <<enumeration>>
        CARDIOLOGY
        DERMATOLOGY
        ...
    }

    class AppointmentStatus {
        <<enumeration>>
        SCHEDULED
        CANCELLED
    }

    Doctor --> Specialization
    Appointment --> AppointmentStatus
```
