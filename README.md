# MediTrack - Clinic & Appointment Management System

MediTrack is a Java-based modular application for managing clinic appointments, patients, doctors, billing, and notifications. It demonstrates object-oriented design patterns, concurrency, streams, cloning, data persistence with CSV, and observer notifications.

## Features

- Patient, Doctor, and Appointment CRUD operations
- Billing with standard and senior discounts
- Global search across entities
- Analytics (e.g., appointments per doctor, average doctor fees)
- Observer pattern for appointment notifications
- Intelligent recommendations (simulated)
- CSV data import/export

## Setup

### Prerequisites

- JDK 17 or higher
- Terminal/Command Prompt

### Installation

1. Clone or download the project.
2. Follow the detailed setup instructions in [docs/Setup_Instructions.md](docs/Setup_Instructions.md).

### Quick Start

```bash
# Compile
javac -d out $(find src -name "*.java")

# Run without data
java -cp out com.health.meditrack.Main

# Run with CSV data loading
java -cp out com.health.meditrack.Main --loadData
```

## Usage

The application provides a menu-driven interface:

1. **Patient Management**: Add, update, delete, list patients.
2. **Doctor Management**: Add, update, delete, list doctors.
3. **Appointment Management**: Create, cancel, list appointments.
4. **Billing**: Generate bills for appointments.
5. **Search**: Global search by name or age.
6. **Analytics**: View appointments per doctor.
7. **Recommendation**: Get Specialization by Symptoms.
8. **CSV Save/Load**: Import/export data.

### Manual Tests

Run the test suite:

```bash
java -cp out com.health.meditrack.test.TestRunner
```

## Demo Instructions

1. Start the application.
2. Add a patient and doctor.
3. Create an appointment.
4. Generate a bill.
5. Observe notifications in the console.
6. Run analytics.

### Sample Run Outputs

#### Starting the Application

```
===============================
        MediTrack - Clinic & Appointment Management System
===============================
MAIN MENU
1 Patient Management
2 Doctor Management
3 Appointment Management
4 Billing
5 Search
6 Analytics
7 Recommendation
8 Save/Load CSV
0 Exit
```

#### Adding a Patient

```
--- Patient Management ---
1 Add Patient
2 Update Patient
3 Delete Patient
4 List Patients
0 Back
1
Name: John Doe
Age: 30
Patient created with ID: P001
```

#### Listing Patients

```
--- Patient List ---
PatientID: P001 | Name: John Doe | Age: 30
```

#### Creating an Appointment

```
--- Appointment Management ---
1 Create Appointment
2 Cancel Appointment
3 List Appointments
0 Back
1
Patient ID: P001
Doctor ID: D001
Appointment created with ID: A001
```

#### Notifications

```
Observer registered: AppointmentObserver
Appointment confirmed for doctor Dr. Smith
```

#### Billing

```
--- Billing ---
1 Generate Standard Bill
2 Generate Discount Bill
0 Back
1
Appointment ID: A001
Bill generated: ₹1200.0
```

#### Analytics

```
Appointments per doctor: {Dr. Smith (D001)=1}
```

## Architecture

- **Entities**: Patient, Doctor, Appointment, Bill.
- **Services**: CRUD operations with dependency injection.
- **Patterns**: Observer for notifications, Factory for bills, Strategy for billing.
- **Data**: In-memory with CSV persistence.

## Documentation

- [Setup Instructions](docs/Setup_Instructions.md)
- [JVM Report](docs/JVM_Report.md)
- [Design Decisions](docs/Design_Decisions.md)

## Generating JavaDoc

To generate HTML documentation:

```bash
javadoc -d docs/javadoc -sourcepath src -subpackages com.health.meditrack
```

Open `docs/javadoc/index.html` in a browser.
