# 🛒 Cart API

A simple **Shopping Cart REST API** built with **Spring Boot** that supports full CRUD operations for carts and products.

---

# 🧰 Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* REST APIs
* Maven

---

# 🚀 Features

* Create carts
* Create products
* Add products to cart
* Update item quantity
* Remove items from cart
* Clear cart

---

# 📌 Cart Endpoints

### Base URL
```
/api
```

## Create Cart

Create a new shopping cart.

```
POST /api/carts
```

---

## Add Product to Cart

Add a product to an existing cart.

```
POST /api/carts/add-to-cart/{cartId}
```

### Path Parameters

| Parameter | Description |
| --------- | ----------- |
| cartId    | Cart ID     |

### Request Body

```
{
  "productId": "uuid"
}
```

---

## Get Cart

Fetch cart details along with items.

```
GET /api/carts/{id}
```

### Path Parameters

| Parameter | Description |
| --------- | ----------- |
| id        | Cart ID     |

---

## Update Product Quantity

Update quantity of a product already present in the cart.

```
PUT /api/carts/{cartId}/items/{productId}
```

### Path Parameters

| Parameter | Description |
| --------- | ----------- |
| cartId    | Cart ID     |
| productId | Product ID  |

### Request Body

```
{
  "quantity": 2
}
```

---

## Remove Product from Cart

Remove a specific product from the cart.

```
DELETE /api/carts/{cartId}/items/{productId}
```

### Path Parameters

| Parameter | Description |
| --------- | ----------- |
| cartId    | Cart ID     |
| productId | Product ID  |

---

## Clear Cart

Remove all items from a cart.

```
DELETE /api/carts/{cartId}/items
```

### Path Parameters

| Parameter | Description |
| --------- | ----------- |
| cartId    | Cart ID     |

---

# 📦 Product Endpoints

## Create Product

Create a new product.

```
POST /api/products
```

### Request Body

```
{
  "name": "Product Name",
  "description": "Product description",
  "price": 100.00,
  "stock": 50
}
```
---

# 📦 User Endpoints

## Create User

Create a new User.

```
POST /api/user
```

### Request Body

```
{
  "username": "UserName",
  "password": "Password",
  "role": "ROLE_USER" or "ROLE_ADMIN",
}
```

---

## Get User

Fetch user details

```
GET /api/user/me
```

---

## Login User

Create a new User.

```
POST /api/auth/login
```

### Request Body

```
{
  "username": "UserName",
  "password": "Password",
}
```

---