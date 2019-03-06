## Spring boot multiple modules with Flyway migration

This example ilustrates possible solution for using *Flyway* in separate modules with own datasource definition.

The solution disables autoconfiguration from *Spring Boot for Flyway* and then creates own *Flyway* object
to migrate a database 
