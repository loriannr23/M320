package footballd3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Small JSON parser for the fields returned by the football API. */
final class JsonParser {
    private final String json;
    private int position;

    private JsonParser(String json) {
        this.json = json;
    }

    static Object parse(String json) {
        if (json == null) {
            throw new IllegalArgumentException("JSON darf nicht null sein.");
        }
        JsonParser parser = new JsonParser(json);
        Object value = parser.readValue();
        parser.skipWhitespace();
        if (parser.position != json.length()) {
            throw parser.error("Zusätzliche Zeichen nach dem JSON-Wert");
        }
        return value;
    }

    private Object readValue() {
        skipWhitespace();
        if (position >= json.length()) {
            throw error("Unerwartetes Ende der JSON-Antwort");
        }

        char current = json.charAt(position);
        if (current == '{') {
            return readObject();
        }
        if (current == '[') {
            return readArray();
        }
        if (current == '"') {
            return readString();
        }
        if (current == 't') {
            readLiteral("true");
            return Boolean.TRUE;
        }
        if (current == 'f') {
            readLiteral("false");
            return Boolean.FALSE;
        }
        if (current == 'n') {
            readLiteral("null");
            return null;
        }
        if (current == '-' || isDigit(current)) {
            return readNumber();
        }
        throw error("Unerwartetes Zeichen '" + current + "'");
    }

    private Map<String, Object> readObject() {
        expect('{');
        skipWhitespace();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        if (take('}')) {
            return object;
        }

        while (true) {
            skipWhitespace();
            if (position >= json.length() || json.charAt(position) != '"') {
                throw error("Ein Objekt-Schlüssel muss ein String sein");
            }
            String key = readString();
            skipWhitespace();
            expect(':');
            object.put(key, readValue());
            skipWhitespace();
            if (take('}')) {
                return object;
            }
            expect(',');
        }
    }

    private List<Object> readArray() {
        expect('[');
        skipWhitespace();
        List<Object> array = new ArrayList<Object>();
        if (take(']')) {
            return array;
        }

        while (true) {
            array.add(readValue());
            skipWhitespace();
            if (take(']')) {
                return array;
            }
            expect(',');
        }
    }

    private String readString() {
        expect('"');
        StringBuilder value = new StringBuilder();
        while (position < json.length()) {
            char current = json.charAt(position++);
            if (current == '"') {
                return value.toString();
            }
            if (current == '\\') {
                if (position >= json.length()) {
                    throw error("Unvollständige Escape-Sequenz");
                }
                char escaped = json.charAt(position++);
                switch (escaped) {
                    case '"': value.append('"'); break;
                    case '\\': value.append('\\'); break;
                    case '/': value.append('/'); break;
                    case 'b': value.append('\b'); break;
                    case 'f': value.append('\f'); break;
                    case 'n': value.append('\n'); break;
                    case 'r': value.append('\r'); break;
                    case 't': value.append('\t'); break;
                    case 'u': value.append(readUnicodeCharacter()); break;
                    default: throw error("Unbekannte Escape-Sequenz");
                }
            } else if (current < 0x20) {
                throw error("Steuerzeichen in einem String");
            } else {
                value.append(current);
            }
        }
        throw error("String wurde nicht abgeschlossen");
    }

    private char readUnicodeCharacter() {
        if (position + 4 > json.length()) {
            throw error("Unvollständiges Unicode-Zeichen");
        }
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int digit = Character.digit(json.charAt(position++), 16);
            if (digit < 0) {
                throw error("Ungültiges Unicode-Zeichen");
            }
            value = value * 16 + digit;
        }
        return (char) value;
    }

    private BigDecimal readNumber() {
        int start = position;
        take('-');
        if (take('0')) {
            if (position < json.length() && isDigit(json.charAt(position))) {
                throw error("Eine Zahl darf keine führende Null haben");
            }
        } else {
            readDigits();
        }

        if (take('.')) {
            readDigits();
        }
        if (take('e') || take('E')) {
            if (!take('+')) {
                take('-');
            }
            readDigits();
        }

        try {
            return new BigDecimal(json.substring(start, position));
        } catch (NumberFormatException exception) {
            throw error("Ungültige Zahl");
        }
    }

    private void readDigits() {
        int start = position;
        while (position < json.length() && isDigit(json.charAt(position))) {
            position++;
        }
        if (start == position) {
            throw error("Ziffer erwartet");
        }
    }

    private void readLiteral(String literal) {
        if (!json.regionMatches(position, literal, 0, literal.length())) {
            throw error("Ungültiger JSON-Wert");
        }
        position += literal.length();
    }

    private boolean take(char expected) {
        if (position < json.length() && json.charAt(position) == expected) {
            position++;
            return true;
        }
        return false;
    }

    private void expect(char expected) {
        if (!take(expected)) {
            throw error("'" + expected + "' erwartet");
        }
    }

    private void skipWhitespace() {
        while (position < json.length() && Character.isWhitespace(json.charAt(position))) {
            position++;
        }
    }

    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException(message + " (Position " + position + ")");
    }
}
