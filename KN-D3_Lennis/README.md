# KN-D3: Wetter-App mit Delegation

## Beschreibung

Konsolen-App: Der Benutzer gibt einen Ort ein, die App zeigt die aktuelle Temperatur
und den Wind an. Die Daten kommen von der freien Wetter-API
[Open-Meteo](https://open-meteo.com) (kein API-Key nötig). Eine leere Eingabe beendet
die App.

```text
=== Wetter-App ===
Ort (leer = Ende): Z123
Fehler: Nur Buchstaben, Leerzeichen, . und - erlaubt.
Ort (leer = Ende): Zürich
Zürich: 11.8 °C, Wind 14.8 km/h
```

## Aufbau

| Klasse | Aufgabe |
|---|---|
| `Main` | startet nur die App |
| `Konsole` | User-Input: Eingabe lesen, prüfen, Ergebnis ausgeben |
| `WetterService` | Logik: Ort → Koordinaten → Wetter |
| `WetterApi` | Aufruf der fremden Schnittstelle (HTTP + JSON lesen) |
| `Wetter` | Daten-Objekt mit Ort, Temperatur, Wind |
| `UngueltigeEingabeException` | eigene Exception bei falscher Eingabe |

## Delegation

Jede Klasse erledigt nur ihre eigene Aufgabe und gibt den Rest weiter:

- `Konsole` holt das Wetter nicht selbst, sondern delegiert an `service.holeWetter(ort)`.
- `WetterService` stellt keine HTTP-Anfragen, sondern delegiert an
  `api.sucheKoordinaten(ort)` und `api.holeAktuellesWetter(breite, laenge)`.

## Kommunikation der Objekte

Die Objekte rufen Methoden auf und geben dabei Werte mit:

1. `Konsole` → `WetterService.holeWetter("Zürich")`
2. `WetterService` → `WetterApi.sucheKoordinaten("Zürich")` → gibt `{47.37, 8.55}` zurück
3. `WetterService` → `WetterApi.holeAktuellesWetter(47.37, 8.55)` → gibt `{11.8, 14.8}` zurück
4. `WetterService` erzeugt `new Wetter("Zürich", 11.8, 14.8)` und gibt es an `Konsole` zurück
5. `Konsole` gibt das `Wetter`-Objekt aus

Die Objekte werden in `Main` erstellt und per Konstruktor übergeben
(`new Konsole(new WetterService(new WetterApi()))`).

## Trennung

- **User-Input:** nur in `Konsole` (`Scanner`, `System.out`)
- **Logik:** nur in `WetterService`, ohne Ein-/Ausgabe und ohne HTTP
- **Fremde API:** nur in `WetterApi`, ohne Logik

## Validierung

`Konsole.pruefeOrt` prüft die Eingabe und wirft bei einem Fehler eine
`UngueltigeEingabeException`:

- weniger als 2 oder mehr als 50 Zeichen
- andere Zeichen als Buchstaben, Leerzeichen, `.` oder `-` (z. B. Zahlen);
  `St. Gallen` ist also erlaubt

Findet die API den Ort nicht, wirft `WetterService` dieselbe Exception. Die `Konsole`
fängt sie ab, zeigt die Meldung und fragt erneut.

## Ausführen

Im Ordner `KN-D3_Lennis` (Internetverbindung nötig):

```text
javac -encoding UTF-8 -d out src/wetter/*.java
java -cp out wetter.Main
```
