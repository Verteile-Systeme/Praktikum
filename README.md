# Verteilte Systeme - Praktikum 

## Realisierung des BambooKeys Designs

Dieses Repository hält den Quellcode für das Praktikum des Moduls
Verteilte Systeme an der Hochschule Ruhrwest für das WiSe 21/22.

Folgende Studierende arbeiten an dem Projekt:

* [Dimitrios Barkas](https://github.com/dimibarkas)
* [Tim Cichon](https://github.com/dmticich)

## Entwicklungsumgebung

Für die Ausführung wird eine lokale Postgres-Instanz benötigt.
Diese kann z.B. mithilfe des folgenden Befehls gestartet werden.

Benötigte Software:

* [Docker](https://www.docker.com)

```bash
$ docker run -d \
--name postgresVS \
-e POSTGRES_DB=postgreVS \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-v /var/lib/db \
postgres:13.2
```

Die API-Dokumentation mittels Swagger wird unter folgendem Pfad bereit gestellt:
http://localhost:8080/swagger-ui/

