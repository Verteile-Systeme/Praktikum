# Verteilte Systeme - Praktikum 

## Realisierung des BambooKeys Designs

Dieses Repository hält den Quellcode für das Praktikum des Moduls
Verteilte Systeme an der Hochschule Ruhrwest für das WiSe 21/22.

Folgende Studierende arbeiten an dem Projekt:

* [Dimitrios Barkas](https://github.com/dimibarkas) 10008891
* [Tim Cichon](https://github.com/dmticich) 100009188

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

## Server starten

Um das Programm auszuführen, wechseln Sie in das Verzeichnis und geben Sie bitte einen der folgenden Kommandos ein:

macOS/Linux:
```
./mvnw spring-boot:run
```

Windows:
```
mvnw spring-boot:run
```

Alternativ lässt sich auch eine JAR Datei über den Befehl bauen:
```
./mvnw clean package 
```

Diese lässt sich dann mit folgendem Befehl ausführen:

```
java -jar target/verteiltesystemepraktikum-0.0.1-SNAPSHOT.jar
```

Der Service ist unter der Adresse http://localhost:8080 erreichbar.

## Tests 

Um alle Tests automatisch auszuführen, geben Sie bitte den folgenden Befehl ein:

```
./mvnw test -Djacoco.skip=true
```

# Dokumentation

Die Dokumentation finden Sie unter [Implementierungskonzept](./docs/Implementierungskonzept.md). 

## Swagger 

Die API-Dokumentation mittels Swagger wird unter folgendem Pfad bereit gestellt:
http://localhost:8080/swagger-ui/

