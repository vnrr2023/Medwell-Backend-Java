# Appointment API Documentation

## Base URL
All endpoints are prefixed with `/appointment`.

## Authentication
All endpoints require a JWT token. The `user_id` is extracted from the token and passed as a `@RequestAttribute("user_id")`. If an endpoint has `@RequestAttribute("user_id")`, it means a valid JWT token must be provided.

## Endpoints

### 1. Create Appointment (Patient)
**Endpoint:** `POST /appointment/patient/create`

**Authentication:** JWT token required

**Request Body:**
```json
{
    "service_id": 1,
    "doctor_id": 2,
    "slot_id": 3
}
```

**Response:**
- Success: `201 Created`
- Failure: `400 Bad Request`

---

### 2. Get Previous Appointments
#### For Doctor
**Endpoint:** `GET /appointment/doctor/previous-appointments`

**Authentication:** JWT token required

**Query Parameters:**
- `page` (optional, default: 0)

**Response:**
```json
{
    "appointments": [...],
    "currentPage": 0,
    "totalPages": 3,
    "hasNext": true,
    "nextPage": 1
}
```

#### For Patient
**Endpoint:** `GET /appointment/patient/previous-appointments`

**Authentication:** JWT token required

**Query Parameters:**
- `page` (optional, default: 0)

**Response:**
(Same as doctor's response format)

---

### 3. Get Upcoming Appointments
#### For Doctor
**Endpoint:** `GET /appointment/doctor/upcoming-appointments`

**Authentication:** JWT token required

**Query Parameters:**
- `date` (YYYY-MM-DD, required)
- `time` (optional)

**Response:**
```json
{
    "appointments": [...]
}
```

#### For Patient
**Endpoint:** `GET /appointment/patient/upcoming-appointments`

**Authentication:** JWT token required

**Query Parameters:**
- `date` (YYYY-MM-DD, required)
- `time` (optional)

**Response:**
(Same as doctor's response format)

---

### 4. Get Appointment Calendar
#### For Patient
**Endpoint:** `GET /appointment/patient/appointment-calendar`

**Authentication:** JWT token required

**Query Parameters:**
- `year` (integer, required)
- `month` (integer, required)

**Response:**
```json
[
    { "date": "2025-03-16", "appointments": [...] }
]
```

#### For Doctor
**Endpoint:** `GET /appointment/doctor/appointment-calendar`

**Authentication:** JWT token required

**Query Parameters:**
- `year` (integer, required)
- `month` (integer, required)

**Response:**
(Same as patient's response format)





# Doctor Profile API Documentation

## Base URL
All endpoints are prefixed with `/doctor/data`.

## Authentication
All endpoints requiring `@RequestAttribute("user_id")` need a valid JWT token.

## Endpoints

### 1. Get Doctor Profile
#### For Doctor
**Endpoint:** `GET /doctor/data/profile`

**Authentication:** JWT token required

**Response:**
```json
{
    "id": 1,
    "name": "Dr. John Doe",
    "profilePic": "url_to_profile_pic",
    "profileQr": "url_to_qr_code",
    "education": "MBBS, MD",
    "phoneNumber": "1234567890",
    "speciality": "Cardiology",
    "verified": true
}
```

#### For Patients (Viewing a Doctor's Profile)
**Endpoint:** `GET /doctor/data/profile/{doctorId}`

**Response:**
```json
{
    "id": 1,
    "name": "Dr. John Doe",
    "profilePic": "url_to_profile_pic",
    "profileQr": "url_to_qr_code",
    "education": "MBBS, MD",
    "phoneNumber": "1234567890",
    "speciality": "Cardiology",
    "verified": true
}
```

---

### 2. Get Doctor Addresses
#### For Doctor
**Endpoint:** `GET /doctor/data/addresses`

**Authentication:** JWT token required

**Response:**
```json
[
    {
        "id": 1,
        "address": "123 Main St, City, Country",
        "clinicName": "ABC Clinic"
    }
]
```

#### For Patients (Viewing a Doctor's Addresses)
**Endpoint:** `GET /doctor/data/addresses/{doctorId}`

**Response:**
(Same as doctor's response format)

---

### 3. Get Doctor Services
#### For Doctor
**Endpoint:** `GET /doctor/data/services`

**Authentication:** JWT token required

**Response:**
```json
[
    {
        "id": 1,
        "serviceName": "General Checkup",
        "price": 50.00
    }
]
```

#### For Patients (Viewing a Doctor's Services)
**Endpoint:** `GET /doctor/data/services/{doctorId}`

**Response:**
(Same as doctor's response format)

