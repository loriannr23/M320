# KN-D2: HAT-Beziehungen im Flugsystem

Das Beispiel bildet Flüge, Passagiere, Flugzeuge und einen Zeitplan ab. Es zeigt
unterschiedliche Arten von HAT-Beziehungen sowie Delegation.

## Beziehungen

- **Aggregation (lose Beziehung):** Ein `Zeitplan` verwaltet mehrere `Flug`-Objekte.
  Die Flüge werden ausserhalb erstellt und dem Zeitplan mit `addFlug` übergeben.
  Sie können deshalb auch ohne diesen Zeitplan existieren.
- **Zweiseitige Aggregation:** Ein `Flug` kennt seine `Passagier`-Objekte und ein
  `Passagier` kennt seine gebuchten Flüge. `addPassagier` und `removePassagier`
  halten beide Seiten der Beziehung konsistent.
- **Aggregation:** Ein `Flug` erhält sein `Flugzeug` im Konstruktor. Das Flugzeug
  kann unabhängig vom Flug bestehen und auch für einen anderen Flug verwendet werden.
- **Komposition (starke Abhängigkeit):** Ein `Flugzeug` erzeugt seinen `Sitzplan`
  selbst. Der Sitzplan erzeugt wiederum seine `Sitz`-Objekte. Diese Teile werden
  nicht von aussen eingesetzt und gehören zu ihrem jeweiligen Ganzen.
- **Delegation:** `Zeitplan.getTotalPassagiere()` berechnet die Summe nicht aus den
  Passagierlisten selbst, sondern delegiert die Abfrage mit
  `flug.getAnzahlPassagiere()` an jeden Flug. `Flugzeug.getAnzahlSitze()` delegiert
  entsprechend an den Sitzplan.

Alle Listen werden nur als unveränderbare Ansichten zurückgegeben. Änderungen
laufen dadurch über die vorgesehenen Methoden und die Beziehungen bleiben konsistent.

## Ausführen

Im Ordner `KN-D2`:

```text
javac -encoding UTF-8 -d out src/flightsystem/*.java
java -cp out flightsystem.Main
```
