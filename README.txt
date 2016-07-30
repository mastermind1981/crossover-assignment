Backend :

1. Spring Boot
2. Hibernate
3. MySQL [username : root , password : root] File : /src/main/resources/application.properties
4. Rest Services

Three Controllers are added which will cater Customer, Product and SaleOrder requests respectively. In case of any Exception global exception
handler is configured.

1. CustomerController
2. ProductController
3. SaleOrderController

Controllers will be communicating to Services.All buisness logic resides in service layers.

1. CustomerService
2. ProductService
3. SaleOrderService

All services will be commnucating to DAO layer. 

1. CustomerCreditRepository
2. CustomerRepository
3. ProductQuantityRepository
4. OrderLinesRepository
5. ProductRepository
6. SaleOrderRepository


FrontEnd :

Three singleton services are added along with the DTO objects. Services will be using RestTemplate for communicating with Back end services over the http.

1. CustomerService
2. ProductService
3. SaleOrderService

Video file can be open in VLC. There were some problem in Frontend code so I have fixed them as well. [Data rendering problem in table]
