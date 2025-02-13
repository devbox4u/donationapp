# Donation App

Das Projekt implementiert (aktuell) Backendservices für das digitale Spenden.

### Aufbau
Das Datenmodell beinhaltet 3 Entitäten: Donor (Spender), Organization (Organisation) und Donation (Spenden).
Für jede Entität wurde entsprechend per Spring Data JPA Entitätsklassen und CRUD-Repositories erstellt.
Eine Basisklasse Audit fügt den Datensätzen ein Erstellungsdatum hinzu.
Hinzu kommen für die Abbildung der Businesslogik einzelne Services hinzu sowie die per OpenAPI beschriebenen Rest-Controller.

Erste Unit-Tests sind ebenfalls implementiert. 

Es wurde auch eine deklarative Pipeline konfiguriert und per Github und Amazon EC2 getestet.

### Zur Pipeline
Die Pipeline ist so konfiguriert, dass Jenkins den Job auf allen verfügbaren Knoten ausführt.
Es bestehen 3 Schritte:
1. Der Code wird aus dem Github-Repository ausgecheckt. Dabei wurden zuvor die (Token)-Credentials in Jenkins hinterlegt. Diese werden hier zur Authentifizierung dynamisch genutzt.
2. Dann wird der Code durch Maven gebaut und die Unit-Tests werden durchlaufen.
3. Ein Code Quality Check wird zuletzt durchgeführt.

### Ausblick
Aufgrund des sehr kleinen Zeitfensters für das Aufsetzen des Projektes sind noch weitere Arbeiten notwendig:
- Die Businesslogik müsste (entsprechend der Anforderungen) weiter angepasst werden.
- Die Code Quality Checks müssten untersucht und behoben werden - insbesondere im Zusammenspiel mit Lombok.
- Mit Hilfe von Spring Security wird dann auch die API abgesichert und eine Benutzerverwaltung hinzugefügt.
- Ein React Frontend (mit Nutzung von MUI) müsste nach barrierefreien Gesichtspunkten implementiert werden.
