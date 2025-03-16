# Appointment Management API

This API provides endpoints for patients and doctors to manage appointments, including cancellations, shifting, and actions on shifted appointments.

## Base URL
**Base URL:** `/appointment`

## Endpoints

### 1. Cancel Appointment (Patient)

**URL:** `/appointment/patient/cancel`  
**Method:** `POST`  
**Description:** Allows a patient to cancel an appointment. Cancellation is only permitted at least 3 hours before the appointment time.

#### Request Body:
```json
{
    "appointmentId": "string",
    "date": "string",  
    "time": "string"  
}
```

#### Response:
- **200 OK:** Appointment canceled successfully.
- **400 Bad Request:** Cancellation not allowed within 3 hours of the appointment.

---

### 2. Shift Appointment (Doctor)

**URL:** `/appointment/doctor/shift`  
**Method:** `POST`  
**Description:** Allows a doctor to shift an appointment to a different time slot.

#### Request Body:
```json
{
    "appointmentId": "string",  
    "shiftedSlotId": "string",  
    "message": "string"  
}
```

#### Response:
- **200 OK:** Appointment shift request submitted successfully.

---

### 3. Accept or Reject Shifted Appointment (Patient)

**URL:** `/appointment/patient/action`  
**Method:** `POST`  
**Description:** Allows a patient to accept or reject a shifted appointment.

#### Request Body:
```json
{
    "action": "string",  
    "message": "string",  
    "shiftedAppointmentId": "string"  
}
```

#### Response:
- **200 OK:** Action processed successfully.

---

## Notes
- All request bodies should be sent as JSON.
- Ensure that `appointmentId` and `shiftedAppointmentId` are valid identifiers.
- The API ensures that appointments cannot be canceled within 3 hours of their scheduled time.
- When shifting an appointment, a doctor must specify the `shiftedSlotId` and provide a `message`.
- Patients must respond to a shifted appointment by accepting or rejecting it using the `/appointment/patient/action` endpoint.

For further details or modifications, please contact the API development team.

