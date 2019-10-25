# 接口文档 V1 

> 后端接口统一采用RESTful风格  
## 用户验证  
所有的请求都使用JWT，通过cookie在header中添加token。

## 接口返回数据格式  
  
Key | Type  | Description  
---- | ----  | -----------  
status | boolean | 表示接口是否调用成功  
msg | string | 错误信息  
data | object | 接口返回的数据  

## 用户
### 注册
```
POST /api/v1/user  
``` 
**Request Data**   
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
account | object | Yes | - | 用户的所有信息，详情见`Account Data`

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
token  | string | JWT
username | string | 用户标识id  

**Account Data**

Key | Type  | Description  
---- | ----  | -----------  
username | string | 用户标识id
email | string | 用户邮箱
firstName | string | 名
lastName | string | 性
status | string | 权限
addr1 | string | 地址1
addr2 | string | 地址2
city | string | 城市
state | string | 状态
zip | string | 邮编
country | string | 国家
phone | string | 手机
favouriteCategoryId | string | 最喜欢的种类
languagePreference | string | 语言喜好
listOption | boolean | list选择
bannerOption | boolean | banner选择
bannerName | string | banner名

### 登入

```
POST /api/v1/session  
``` 
**Request Data**   
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
username | string | - | - | 用户标识id
password | string | - | - | 用户密码

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
token  | string | JWT
username | string | 登入的username

### 登出

```
DELETE /api/v1/session  
``` 
**Request Data**   
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
username | string | Yes | - | 用户标识id

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
token | string | 空JWT
## 用户信息  
### 用户详细信息    
```
GET /api/v1/user/detailinfo  
```
**Request Data**   
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
username | string | Yes | - | 要查询的用户的username


**Response Data**    
  
Key | Type  | Description  
---- | ----  | -----------  
account | object | 用户账户，内容详情见`Account Data`


```
PUT /api/v1/user/detailinfo  
```
**Request Data**   
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
username | string | Yes | - | 用户标识id


**Response Data**    
  
Key | Type  | Description  
---- | ----  | -----------  
token | string | JWT
account | object | 用户账户，内容详情见`Account Data`

## 目录  
### Category  
```
GET /api/v1/category  
```  
**Request Data**  
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
id | string | Yes | - | category标识id  

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
productList | list | product列表，内容详情见`Product Data`

**Product Data**

Key | Type  | Description   
---- | ----  | -----------   
productId | string | product标识  
categoryId | string | product所属category
name | string | product名   
description | string | product描述

### Product  
```
GET /api/v1/product  
```  
**Request Data**  
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
id | string | Yes | - | product标识id  

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
product | object | 内容详情见`Product Data`
itemList | list | item列表，内容详情见`Item Data`

**Item Data**

Key | Type  | Description   
---- | ----  | -----------   
itemId | string | item标识  
productId | string | item所属product
listPrice | int | list价格   
unitcost | int | unit价格
supplierId | int | 供应商id
status | int | 状态
attribute1 | int | 地址1
attribute2 | int | 地址2
attribute3 | int | 地址3
attribute4 | int | 地址4
attribute5 | int | 地址5
product | object | 内容详情见`Product Data`
quantity | int | 库存数量

### Item  
```
GET /api/v1/item  
```  
**Request Data**  
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
id | string | Yes | - | item标识id  

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
product | object | item所属product，内容详情见`Product Data`
time | object | 查询到的item，内容详情见`Item Data`

## 购物车
### Cart
```
GET /api/v1/user/cart 
```  
**Request Data**  
  
Key | Type | Mandatory | Default | Description  
---- | ---- | --------- | -------- | -----------  
 

**Response Data**   
  
Key | Type  | Description  
---- | ----  | -----------  
cart | object | 购物车，详情见`Cart Data`

**Cart Data**

Key | Type  | Description  
---- | ----  | -----------  

