# Donation App

Das Projekt implementiert Backendservices für das digitale Spenden.

Das Datenmodell beinhaltet 3 Entitäten: Donor (Spender), Organization (Organisation) und Donation (Spenden).
Für jede Entität wurde entsprechend per Spring Data JPA Entitätsklassen und CRUD-Repositories erstellt. 
Hinzu kommen für die Abbildung der Businesslogik einzelne Services hinzu sowie per OpenAPI beschriebene Rest-Controller.
Eine Basisklasse Audit fügt den Datensätzen ein Erstellungsdatum hinzu.

Erste Unit-Tests sind ebenfalls implementiert. 

Es wurde auch eine deklarative Pipeline konfiguriert und per Github und Amazon EC2 getestet.

Ausblick:
- Aufgrund der kurzen Zeit müsste die Businesslogik weiter angepasst werden.
- Die Code Quality Checks müssten untersucht werden - insbesondere im Zusammenspiel mit Lombok.
- Ein React Frontend (mit Nutzung von MUI) müsste nach barrierefreien Gesichtspunkten implementiert werden