# Appointment API Documentation

## Authentication
All endpoints require a JWT token. The `user_id` is extracted from the token and passed as a `@RequestAttribute("user_id")`.

## Endpoints

### 1. Create Appointment (Patient)
**Endpoint:** `POST /patient/create`

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
**Endpoint:** `GET /doctor/previous-appointments`

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
**Endpoint:** `GET /patient/previous-appointments`

**Query Parameters:**
- `page` (optional, default: 0)

**Response:**
(Same as doctor's response format)

---

### 3. Get Upcoming Appointments
#### For Doctor
**Endpoint:** `GET /doctor/upcoming-appointments`

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
**Endpoint:** `GET /patient/upcoming-appointments`

**Query Parameters:**
- `date` (YYYY-MM-DD, required)
- `time` (optional)

**Response:**
(Same as doctor's response format)

---

### 4. Get Appointment Calendar
#### For Patient
**Endpoint:** `GET /patient/appointment-calendar`

**Query Parameters:**
- `year` (integer, required)
- `month` (integer, required)

**Response:**
in postman saved

#### For Doctor
**Endpoint:** `GET /doctor/appointment-calendar`

**Query Parameters:**
- `year` (integer, required)
- `month` (integer, required)

**Response:**
(Same as patient's response format)

