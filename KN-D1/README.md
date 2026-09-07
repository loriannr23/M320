# TBZ M320 KN-D1 - Java-Beispiele

Dieser Ordner enthaelt zwei getrennte, einfache Java-Beispiele fuer die Kompetenz KN-D1.

## Beispiel 1: Bankkonto-Simulation

Dateien:

- `src\Bank.java`
- `src\Bankkonto.java`
- `src\Main.java`

Starten:

```powershell
javac -d out src\*.java
java -cp out Main
```

## Beispiel 2: Heizungs-/Raum-Simulation

Dateien:

- `src\heizung\Heizung.java`
- `src\heizung\Raum.java`
- `src\heizung\Main.java`

Starten:

```powershell
javac -d out src\heizung\*.java
java -cp out heizung.Main
```

## Gezeigte KN-D1-Anforderungen

- eigene Klassen
- Konstruktoren
- Objekte mit `new` instanziieren
- private Attribute und Datenkapselung
- Getter und Setter
- Methoden mit Parametern
- Kommunikation zwischen Objekten
- sichtbare Zustandsaenderungen
