# KN-V3: Aktienportfolio mit Börsen-Interface

## Bezug zur Aufgabe

Die Lösung setzt die im Auftrag vorgeschlagene Idee **Portfolio von Aktien** um.
KN-V3 verlangt ein Programm zu den Lernzielen; es setzt keinen Code aus einer
älteren Aufgabe voraus. Das Beispiel ist daher eigenständig.

## Programm

Das Konsolenmenü lässt die Benutzerin oder den Benutzer eine Börse auswählen und
danach den Portfoliowert abfragen, den Kurs eines Symbols nachschlagen oder die an
der Börse kotierten Aktien anzeigen. Es gibt zwei Börsen mit unterschiedlichen
Aktienlisten. Die Kurse sind festgelegte virtuelle Beispielkurse in CHF und keine
Live-Börsendaten.

Das Portfolio enthält Beispielpositionen für Nestlé, ABB, Swiss Re, AstraZeneca und
Unilever. Wenn eine Aktie an der ausgewählten Börse nicht kotiert ist, wird sie
sichtbar als nicht verfügbar ausgewiesen und nicht in die Summe aufgenommen. Die
Ausgabe bezeichnet die Summe deshalb ausdrücklich als Wert der kotierten Positionen.

## Interfaces und Polymorphismus

`StockExchange` legt den Vertrag für Börsen fest: Name, kotierte Aktien und Kursabfrage.
`ZuercherBoerse` und `LondonBoerse` implementieren denselben Vertrag mit eigenen
Aktienlisten und Preisen.

`Portfolio.berechneWert(StockExchange boerse)` ist nicht an eine konkrete Börsenklasse
gebunden. Es ruft `getKurs` über das Interface auf. Das konkrete Objekt entscheidet,
welcher Kurs geliefert wird. Das ist Polymorphismus: Derselbe Aufruf funktioniert mit
verschiedenen Implementierungen.

In `Main` stehen beide Börsen in einer `List<StockExchange>`. Das Menü verwendet
jeweils den gewählten Eintrag über den gemeinsamen Interface-Typ. Eine weitere Börse
kann ergänzt werden, indem eine neue Klasse `StockExchange` implementiert und dem
Menü hinzugefügt wird. Die Berechnung im Portfolio muss dafür nicht geändert werden.

### Weshalb ein Interface statt einer Börsen-Vererbung?

Das Portfolio braucht nur die Fähigkeit, Kurse abzufragen. Es muss nicht wissen, wie
Zürich oder London die Kurse intern speichern. Eine gemeinsame konkrete Oberklasse
würde Implementierungsdetails unnötig festlegen. Das Interface hält die Kopplung
klein und erlaubt weitere Börsen oder eine Test-Börse mit denselben Operationen.

## Klassenüberblick

- `Aktie`: Symbol und lesbarer Name.
- `Position`: eine Aktie und die gehaltene Stückzahl.
- `Portfolio`: unveränderliche Liste von Positionen und Berechnung des Börsenwerts.
- `StockExchange`: Interface für Börsen.
- `Boerse`: gemeinsame interne Kursverwaltung der Beispielbörsen.
- `ZuercherBoerse`, `LondonBoerse`: konkrete Börsen mit unterschiedlichen Kursdaten.
- `Main`: Konsolenmenü und Beispielportfolio.

## Starten

Im Ordner `KN-V3`:

```text
javac -encoding UTF-8 -d out src/stocksystem/*.java
java -cp out stocksystem.Main
```

Java 8 oder neuer wird benötigt.

## Beispielablauf

1. Zürich auswählen.
2. „Wert des Portfolios berechnen“ auswählen. NESN, ABB und SREN werden bewertet;
   die Londoner Positionen werden als nicht kotiert angezeigt.
3. Zurück zur Börsenauswahl gehen und London auswählen.
4. Den Kurs `AZN` abfragen oder den Portfoliowert berechnen.

## Erweiterungsmöglichkeiten

- Eine neue Börse wie New York mit eigener Aktienliste als weitere Interface-
  Implementierung ergänzen.
- Kursdaten statt aus einer Beispieldatenstruktur aus einer Datei oder einer
  geeigneten Kursdaten-API laden.
- Mehrere Währungen mit expliziten Wechselkursen berücksichtigen. In diesem
  Lernbeispiel sind sämtliche Beispielkurse in CHF, damit die Portfoliosumme ohne
  zusätzliche Wechselkurslogik verständlich bleibt.
