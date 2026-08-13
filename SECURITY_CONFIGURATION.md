# Local security configuration

Database-backed examples read `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` from environment variables. Never commit real credentials; keep them in the process environment or an untracked IDE run configuration.

## Database integration tests

Database integration tests are disabled by default in `CodeSpringEx00`, `CodeSpringEx02`, `CodeSpringEx03`, and `CodeSpringEx04`. Run them only in a configured environment:

```powershell
mvn -B clean test -Pdb-integration
```

The Oracle JDBC driver must also be supplied locally. This repository does not add or redistribute it, and no Oracle test was performed during this audit.

## Dependency and security baseline

- Java 8-compatible bytecode on JDK 17
- Spring Framework 5.3.39; Spring Security 5.8.16 where used
- SLF4J 1.7.36 with reload4j 1.2.26
- MyBatis 3.5.19 / MyBatis-Spring 1.3.3; HikariCP 2.7.9
- Jackson 2.17.3, Gson 2.11.0, Commons FileUpload 1.6.0 where used

Log4j 1 API usages were migrated to SLF4J. Uploads use normalized generated names, a 10 MiB application limit, and an `UPLOAD_DIR` root-path check. Error pages no longer expose exception messages or stack traces.

Spring 6 and newer MyBatis-Spring/Hikari major versions require a coordinated Java/Jakarta/container migration and are outside this compatibility pass.

## Deferred external decisions and compatibility work

Vendored UI assets in `CodeSpringEx02` and `CodeSpringEx03` include old jQuery 3.1.0, Raphael 2.2.0, and the legacy SB Admin 2 bundle. They must be upgraded and browser-regression-tested together; replacing individual files without rebuilding the bundle may break the example UI. No repository-level license was selected because ownership and upstream teaching-material terms require external confirmation.

OWASP Dependency-Check was attempted, but its vulnerability-data update did not complete. Maven dependency resolution and update analysis, secret scanning, dangerous Java API scanning, dynamic SQL scanning, upload-path review, JSP output review, and build verification were performed instead.
## Jakarta migration

All modules target Java 17, Spring Framework 6.2.19, Jakarta Servlet 6.0/JSP 3.1, and Tomcat 10.1.57. Database modules use Oracle ojdbc11 directly; the obsolete log4jdbc proxy was removed. Hikari starts with zero idle connections so UI/static-resource checks can run without contacting Oracle. Actual database behavior still requires the explicit db-integration profile and a real Oracle environment.
