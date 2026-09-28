# KN-Q2 – Dokumentation und Code-Konventionen

**Kriterium Q2 (\*\*)**

> Mein Code verwendet ein Software-Dokumentationswerkzeug (JavaDoc).
> Mein Code ist kommentiert und entspricht den Code-Konventionen.

## Umsetzung

Der Code von Niveau 2 (`KN-D2/src/flightsystem/`, 7 Klassen) ist vollständig mit
JavaDoc kommentiert: Klassen, Konstruktoren und Methoden mit `@param`, `@return`
und `@throws`.

## Doku generieren

```powershell
javadoc -encoding UTF-8 -d KN-D2/doc KN-D2/src/flightsystem/*.java
```

Prüfen mit `-Xdoclint:all` – läuft ohne Warnung und ohne Fehler durch.

## Was kommentiert wird

Der Code sagt, *wie* etwas passiert – der Kommentar sagt, *warum*.

- **Öffentliche Schnittstellen:** Was die Methode tut, was sie erwartet, was sie
  zurückgibt, wann sie fehlschlägt.
- **Entscheidungen und Annahmen:** z. B. warum `Flug.addPassagier` eine Doppelbuchung
  still ignoriert, aber bei ausgebuchtem Flug eine Ausnahme wirft; warum
  `Zeitplan.findByStartZeit` beide Zeitgrenzen einschliesst.
- **Beziehungen:** `Flugzeug` besitzt seinen `Sitzplan` (Komposition), ein `Flug`
  kennt seine Passagiere nur (Aggregation).

Nicht kommentiert wird, was der Code schon sagt (`// setzt den Namen`). Getter haben
nur eine `@return`-Zeile.

## Code-Konventionen

| Element | Regel | Beispiel |
|---|---|---|
| Paket | klein | `flightsystem` |
| Klasse | PascalCase | `Sitzplan` |
| Methode | camelCase, Verb zuerst | `addPassagier()` |
| Variable | camelCase, sprechend | `anzahlReihen` |
| Konstante | `UPPER_SNAKE_CASE` | `MAX_SITZE` |

Dazu: 4 Leerzeichen Einrückung, Klammern auch bei einzeiligen `if`, eine öffentliche
Klasse pro Datei, Attribute `private` mit Gettern.
