# Shop Management API

### System Description
The application server allows managing products and orders.
The web interface (API), as well as the objects used for communication, are defined in the provided specification.

The API specification is available at: https://app.swaggerhub.com/apis-docs/JAHICJAKUB/Street_of_Code_Spring_Boot_zadanie/1.0.0

The system supports creating and removing products. It also allows updating existing products, as well as increasing the stock quantity of products.

The system supports creating and deleting orders. Products available in stock can primarily be added to orders. In case of insufficient stock, the system will return an `error code 400`.

The system supports paying for an order. Once an order is paid, its status changes to `paid`. Products cannot be added to a paid order.

### Automated Tests
The system includes automated integration tests located in the `src/test/java` directory.
These tests verify the functionality of the API. There is no need to add or modify any tests.

If all tests pass, the application has been correctly implemented.
