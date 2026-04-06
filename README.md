# 🛒 Cart API

Its a SpringBoot based CartApi with features:
- **⚡ User Management**
- **⚡ JWT Authorization & Roles**
- **⚡ Product Creation & Management**
- **⚡ Cart Creation & Management**
- **⚡ Order Creation**
- **⚡ Checkout**
- **⚡ Payment Service Integration**

---

## All About Endpoints

### PUBLIC:
1. login user
- login user with credentials
- `POST /api/auth/login`

2. refresh token
- generate new access-token with refresh token
- `POST /api/auth/refresh-token`

3. logout
- logout user
- `POST /api/auth/logout`

4. register user
- create a new user
- `POST /api/user`

5. Stripe Webhook
- receives payment confirmation from stripe payment gateway
- `POST /api/order/stripe-webhook`


### PROTECTED:
6. admin route
- a simple admin route (does nothing)
- `GET /api/admin/hello`

### PRIVATE:
7. create cart
- creates a new cart
- `POST /api/carts`

8. add to cart
- add a product to the cart (add twice for increasing qty)
- `POST /api/carts/add-to-cart/{cartId}`

9. get cart
- get cart by id
- `GET /api/carts/{id}`

10. update quantity
- update quantity of a product
- `PUT /api/carts/{cartId}/items/{productId}`

11. remove product from cart
- remove product from cart
- `DELETE /api/carts/{cartId}/items/{productId}`

12. clear cart
- empty your cart
- `DELETE /api/carts/{cartId}/items`

13. create product
- create a new product
- `POST /api/products`

14. get user
- get logged in user
- `GET /api/user/me`

15. checkout
- checkout with your cart
- `POST /api/order/checkout`

16. Get Orders
- get all orders of the current user
- `GET /api/order`

17. GET Order
- get order by id
- `GET /api/order/{orderId}`
