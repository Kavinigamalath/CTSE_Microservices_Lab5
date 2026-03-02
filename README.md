# Microservices Lab

Simple microservices stack: Item, Order, Payment (Spring Boot) behind Spring Cloud Gateway. Docker Compose wires ports 8080–8083.

## Endpoints (via gateway http://localhost:8080)
- GET /items
- POST /items
- GET /items/{id}
- GET /orders
- POST /orders
- GET /orders/{id}
- GET /payments
- POST /payments/process
- GET /payments/{id}

Sample bodies:
- POST /items
```
{ "name": "Headphones", "price": 199.99 }
```
- POST /orders
```
{ "item": "Laptop", "quantity": 2, "customerId": "C001" }
```
- POST /payments/process
```
{ "orderId": 1, "amount": 1299.99, "method": "CARD" }
```

## Build JARs
From repo root:
```
cd item-service && mvn clean package -DskipTests && cd ..
cd order-service && mvn clean package -DskipTests && cd ..
cd payment-service && mvn clean package -DskipTests && cd ..
cd api-gateway && mvn clean package -DskipTests && cd ..
```

## Run with Docker Compose
```
docker-compose build
docker-compose up -d
```
Check containers: `docker ps`
Stop: `docker-compose down`

## Quick curl smoke tests
```
curl http://localhost:8080/items
curl -X POST http://localhost:8080/items -H "Content-Type: application/json" -d "{\"name\":\"Headphones\",\"price\":199.99}"
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d "{\"item\":\"Laptop\",\"quantity\":2,\"customerId\":\"C001\"}"
curl -X POST http://localhost:8080/payments/process -H "Content-Type: application/json" -d "{\"orderId\":1,\"amount\":1299.99,\"method\":\"CARD\"}"
```

## Postman
Import microservices-lab.postman_collection.json and set `base_url` to `http://localhost:8080`.
