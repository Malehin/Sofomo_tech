# Geolocation Application

This application provides APIs to manage and retrieve geolocation data based on URLs and IP addresses.
Below are the endpoints and instructions for using them.

## Requirements
- Ensure that the application is running locally on port `8080`.
- Use any API client like [Postman](https://www.postman.com/) or [curl](https://curl.se/) to test the endpoints.

---

## Endpoints

### 1. Add Geolocation by URL
- **Description**: Adds geolocation data for a given URL.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/url/add`

#### Request Body
```json
{
  "url": "wp.pl"
}
```


### 2. Add Geolocation by IP
- **Description**: Adds geolocation data for a given IP.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/ip/add`

#### Request Body
```json
{
  "ip": "162.158.112.40"
}
```

### 3. Get Geolocation by URL
- **Description**: Retrieve geolocation data for a given URL.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/url`

#### Request Body
```json
{
  "url": "google.com"
}
```


### 4. Get Geolocation by IP
- **Description**: Retrieve geolocation data for a given IP.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/ip`

#### Request Body
```json
{
  "ip": "8.8.8.9"
}
```

### 5. Delete Geolocation by URL
- **Description**: Delete geolocation data for a given URL.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/url/delete`

#### Request Body
```json
{
  "url": "google.com"
}
```

### 6. Delete Geolocation by IP
- **Description**: Delete geolocation data for a given IP.
- **Method**: `POST`
- **Endpoint**: `http://localhost:8080/api/ip/delete`

#### Request Body
```json
{
  "ip": "8.8.8.9"
}
```