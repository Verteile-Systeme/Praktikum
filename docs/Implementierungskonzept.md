


# Verteilte Systeme: Praktikum
## Erläuterung des Implementierungskonzepts 
Ausgewähltes REST-API Design: rest-api-design-02

Zur Realisierung der REST-API wurde die Programmiersprache JAVA in Kombination mit dem Spring-Boot Framework verwendet. Folgende Abhängigkeiten des Frameworks wurden genutzt:
* spring-boot-starter-data-jpa
* spring-boot-starter-web
* spring-boot-starter-test
* spring-boot-starter-validation
* postgres

Zusätzlich wurden die folgenden Plugins zu Convenience Zwecken, zum schreiben von Unit Tests und zum Erzeugen von Testdaten genutzt:
* j-unit
* lombok
* javafaker

Wir haben uns für die Umsetzung mit Java entschieden, da beide Teammitglieder keine Erfahrung mit Python gemacht haben und gerne unsere Kenntnisse mit Java vertiefen wollten.
Das Open Source-Framework Spring (https://spring.io) ist eine der bekanntesten Lösungen wenn es um die Implementierung von Webanwendungen mit Java geht. Mithilfe der Quickstart Guides bietet es einen schnellen und einfachen Einstieg, bietet eine gut verständliche und  umfangreiche Dokumentation.


## Teststrategie

Die Anwendung wurde getestet in dem für die jeweiligen Endpoints für die Controller Unit-Tests mit JUnit geschreiben wurden.
Zusätzlich wurden Integrationstests mit dem Tool WebMcvTest des Spring-Frameworks verwendet.
Für einige Endpunkte wurden auch Akzeptanztests geschrieben um allen Anforderungen der Vorgabe zu entsprechen.

In der [README](../README.md) finden Sie eine Anleitung wie man alle Tests ausführt.