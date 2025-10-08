
# Internet Shop

Java spring boot web application


## Features

- Ability to add items to the cart
- Admin Tab
- Account management
- Different specs for each type of product
- API
- Swagger for better API debugging
- Logging
- JWT


## API Reference

#### Get item

```http
  GET /api/products/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item you need |

 #### Return data
| Parameter    | Type      | Description                                 |
|:-------------|:----------|:--------------------------------------------|
| `id`         | `integer` | Id of item you requested                    |
| `price`      | `float`   | Price of item you requested                 |
| `Item specs` | `Object`  | Item specs (Clock Speed, Form factor, etc.) |

#### The rest of documentation is done using Swagger.

## Authors

- [@BlackyCGS](https://www.github.com/BlackyCGS)

