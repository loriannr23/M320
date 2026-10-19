package stocksystem;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final NumberFormat CHF = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("de-CH"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Portfolio portfolio = erstelleBeispielPortfolio();
        List<StockExchange> boersen = Arrays.asList(new ZuercherBoerse(), new LondonBoerse());

        System.out.println("=== Aktienportfolio ===");
        System.out.println("Die angezeigten Kurse sind virtuelle Beispielkurse in CHF.");

        boolean weiter = true;
        while (weiter) {
            StockExchange boerse = waehleBoerse(scanner, boersen);
            if (boerse == null) {
                break;
            }

            System.out.println("\nAusgewählt: " + boerse.getName());
            System.out.println("1. Wert des Portfolios berechnen");
            System.out.println("2. Kurs einer Aktie abfragen");
            System.out.println("3. An dieser Börse kotierte Aktien anzeigen");
            System.out.println("0. Beenden");
            int auswahl = leseZahl(scanner, "Ihre Wahl: ");

            switch (auswahl) {
                case 1:
                    zeigePortfolioWert(portfolio, boerse);
                    break;
                case 2:
                    frageKursAb(scanner, boerse);
                    break;
                case 3:
                    zeigeKotierteAktien(boerse);
                    break;
                case 0:
                    weiter = false;
                    break;
                default:
                    System.out.println("Bitte wählen Sie eine Zahl aus dem Menü.");
            }
            System.out.println();
        }

        System.out.println("Programm beendet.");
    }

    private static Portfolio erstelleBeispielPortfolio() {
        return new Portfolio(Arrays.asList(
                new Position(new Aktie("NESN", "Nestlé"), 10),
                new Position(new Aktie("ABBN", "ABB"), 8),
                new Position(new Aktie("SREN", "Swiss Re"), 4),
                new Position(new Aktie("AZN", "AstraZeneca"), 3),
                new Position(new Aktie("ULVR", "Unilever"), 5)
        ));
    }

    private static StockExchange waehleBoerse(Scanner scanner, List<StockExchange> boersen) {
        while (true) {
            System.out.println("Wählen Sie eine Börse:");
            for (int i = 0; i < boersen.size(); i++) {
                System.out.println((i + 1) + ". " + boersen.get(i).getName());
            }
            System.out.println("0. Beenden");
            int auswahl = leseZahl(scanner, "Ihre Wahl: ");
            if (auswahl == 0) {
                return null;
            }
            if (auswahl >= 1 && auswahl <= boersen.size()) {
                return boersen.get(auswahl - 1);
            }
            System.out.println("Ungültige Auswahl. Bitte versuchen Sie es erneut.\n");
        }
    }

    private static void zeigePortfolioWert(Portfolio portfolio, StockExchange boerse) {
        System.out.println("\nPortfolio an " + boerse.getName() + ":");
        for (Position position : portfolio.getPositionen()) {
            Optional<BigDecimal> kurs = boerse.getKurs(position.getAktie().getSymbol());
            if (kurs.isPresent()) {
                BigDecimal positionswert = kurs.get().multiply(BigDecimal.valueOf(position.getAnzahl()));
                System.out.println("- " + position.getAktie().getSymbol() + " (" + position.getAnzahl()
                        + " Stück): " + CHF.format(positionswert));
            } else {
                System.out.println("- " + position.getAktie().getSymbol() + " ist an dieser Börse nicht kotiert.");
            }
        }
        System.out.println("Portfoliowert der kotierten Positionen: " + CHF.format(portfolio.berechneWert(boerse)));
        if (!portfolio.getNichtKotiertePositionen(boerse).isEmpty()) {
            System.out.println("Hinweis: Nicht kotierte Positionen sind im Total nicht enthalten.");
        }
    }

    private static void frageKursAb(Scanner scanner, StockExchange boerse) {
        System.out.print("Aktien-Symbol (z. B. NESN): ");
        String symbol = scanner.nextLine().trim();
        Optional<BigDecimal> kurs = boerse.getKurs(symbol);
        if (kurs.isPresent()) {
            System.out.println(symbol.toUpperCase(Locale.ROOT) + ": " + CHF.format(kurs.get()));
        } else {
            System.out.println("Kein Kurs für " + symbol + " an " + boerse.getName() + " gefunden.");
        }
    }

    private static void zeigeKotierteAktien(StockExchange boerse) {
        System.out.println("\nKotierte Aktien:");
        for (Aktie aktie : boerse.getKotierteAktien()) {
            System.out.println("- " + aktie.getSymbol() + " – " + aktie.getName()
                    + ": " + CHF.format(boerse.getKurs(aktie.getSymbol()).get()));
        }
    }

    private static int leseZahl(Scanner scanner, String aufforderung) {
        while (true) {
            System.out.print(aufforderung);
            String eingabe = scanner.nextLine().trim();
            try {
                return Integer.parseInt(eingabe);
            } catch (NumberFormatException exception) {
                System.out.println("Bitte geben Sie eine ganze Zahl ein.");
            }
        }
    }
}
