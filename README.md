QA_H2014
========

 Gestion des patients en milieu hospitalier

Compile and start project
=========================
```
mvn clean compile exec:java
```

Valid request example
=====================
POST at http://localhost:8080/patient/0/prescriptions/
```json
 { intervenant: "000000",
   date: "2001-07-04T12:08:56",
   renouvellements: 0,
   din: "02240541"
 }
 ```
