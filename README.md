# ShippingApplication
Shipping Application REST APIs


The service utilizes in memory databases to store the corresponding objects. The WAR file in target directory needs to be deployed to Apache Tomcat server in order to test and utilize the APIs.

"Goods" APIs
=============
0. Get all Goods http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/goods GET
0. Get Goods by Id http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/goods/{id} GET
0. Create new Goods http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/goods POST
  * Parameters => Goods object => {"id":1,"description":"Hazmat 2","hazmat":true,"maxCountForLimtedQuantity":5}
  * id is automatically generated.
  
"Package" APIs
=============
0. Get all Packages http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/packages GET
0. Get Package by Id http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/packages/{id} GET
0. Create new Package http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/packages POST
  * Parameters => Package object => {"packageId":1, "items":{"3":17,"5":20}}
  * packageId is automatically generated.
  * Items is defined as Map\<Long, Integer> where key define the Goods id and values here is the quantity for the goods in the package. 
0. Assign Goods to existing Package http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/packages/{packageId} PUT
  * Parameters => Package object => {"items":{"3":17,"5":20}}
  * If the same Goods item exist, the new quantity is refllected.
0. Get Limited Quantity Packages http://localhost:8080/ShipLabels-0.0.1-SNAPSHOT/packages/limitedQuantity GET
  * Assumption => All Non-Hazmat Goods are eligible for limited quantity.
  * All hazmat goods have a limited quantity characteristic that indicates how much of it can ship if it is to be shipped as limited quantity. If all the goods in the package are limited quantity or less, then the package can ship as limited quantity.
  * The API returns the list of all package ids which satisfy above mentioned criteria.
