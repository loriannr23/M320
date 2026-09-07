# Ticketsystem – Modul 320

Java-Lösung mit abstrakter Oberklasse und polymorph verarbeiteten Ticketarten.

## Annahmen

- Standardrabatt: 10 %
- Backstage-Zuschlag: CHF 50
- Catering-Zuschlag: CHF 30
- Beim Gruppenticket ist `basisPreis` der Preis pro Person; der Endpreis gilt für
  `maxPersonen` und erhält anschließend den Gruppenrabatt.
- Ein Ticket ist nur bis einschließlich Eventdatum und nur vor seiner Entwertung gültig.

Die Werte lassen sich in den Klassen einfach ändern.

## Kompilieren und starten (PowerShell)

```powershell
javac -d out src/ticketsystem/*.java
java -cp out ticketsystem.Main
```
