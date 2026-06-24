# BFHL REST API (Spring Boot)

A robust and secure REST API built using **Java (Spring Boot)** to process array inputs, categorize elements (even numbers, odd numbers, alphabets, and special characters), calculate statistics, and perform custom string manipulation.

---

## 🚀 Features

- **Classification**: Categorizes elements into even numbers, odd numbers, alphabets (converted to uppercase), and special characters.
- **Aggregations**: Calculates the sum of all numeric values.
- **Custom Logic**: Reverses the order of alphabets and converts them to alternating capitalization (e.g., `aBcd` becomes `DcBa`).
- **Standardized DTOs**: Implemented clean Request and Response DTOs.
- **High Test Coverage**: Comprehensive integration and service-layer test cases.

---

## 🛠️ API Specifications

### 1. POST `/bfhl`
Processes the input data array.

*   **Request Headers**: `Content-Type: application/json`
*   **Request Body**:
    ```json
    {
      "data": ["a", "1", "334", "4", "R", "$"]
    }
    ```
*   **Response Body**:
    ```json
    {
      "is_success": true,
      "user_id": "vaibhav_choudhary_22082005",
      "email": "vaibhav0909.be23@chitkara.edu.in",
      "roll_number": "2310990909",
      "even_numbers": ["4"],
      "odd_numbers": ["1", "334"],
      "alphabets": ["A", "R"],
      "special_characters": ["$"],
      "sum_of_numbers": 339,
      "reversed_alternating_alphabets": "rA"
    }
    ```

### 2. GET `/bfhl`
Returns the operational code.

*   **Response Body**:
    ```json
    {
      "operation_code": 1
    }
    ```

---

## 💻 Local Setup

### Prerequisites
- **Java JDK 17** or higher
- **Maven 3.8+**

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/vaibhavvchoudharyy-gif/bfhl-api.git
   cd bfhl-api
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. The API will be available locally at `http://localhost:8080/bfhl`.

### Running Tests
To execute the automated unit and integration tests:
```bash
mvn test
```

---

## ☁️ Deployment (Hugging Face Spaces - Free, No Card)

This project contains a [Dockerfile](Dockerfile) optimized to deploy to **Hugging Face Spaces** for free without requiring a credit card.

### Deployment Steps:
1. Sign up/log in at **[Hugging Face](https://huggingface.co/)**.
2. Create a new Space at **[huggingface.co/new-space](https://huggingface.co/new-space)**:
   - **Space name**: `bfhl-api`
   - **SDK**: **Docker**
   - **Docker template**: **Blank**
   - **Space hardware**: **CPU basic • Free**
   - **Visibility**: **Public**
3. In your local terminal, add the Hugging Face remote:
   ```bash
   git remote add hf https://huggingface.co/spaces/YOUR_USERNAME/SPACE_NAME
   ```
4. Generate a **Write Token** from your [Hugging Face Access Tokens Settings](https://huggingface.co/settings/tokens).
5. Push the code:
   ```bash
   git push -f hf main
   ```
   *(Use your Hugging Face username and the Write Token as the password when prompted)*
