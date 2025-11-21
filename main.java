import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {

    static int getNumber(String text, String key) {
        int i = text.indexOf("\"" + key + "\"");
        if (i < 0) return -1;
        int colon = text.indexOf(":", i) + 1;
        while (colon < text.length() && !Character.isDigit(text.charAt(colon))) colon++;
        int start = colon;
        while (colon < text.length() && Character.isDigit(text.charAt(colon))) colon++;
        return Integer.parseInt(text.substring(start, colon));
    }

    static String getValue(String text, int index, String field) {
        int pos1 = text.indexOf("\"" + index + "\"");
        if (pos1 < 0) return null;
        int pos2 = text.indexOf("\"" + field + "\"", pos1);
        if (pos2 < 0) return null;
        int colon = text.indexOf(":", pos2);
        int q1 = text.indexOf("\"", colon + 1);
        int q2 = text.indexOf("\"", q1 + 1);
        return text.substring(q1 + 1, q2);
    }

    static List<BigInteger> multiplyPoly(List<BigInteger> poly, BigInteger root) {
        int size = poly.size();
        List<BigInteger> result = new ArrayList<>(Collections.nCopies(size + 1, BigInteger.ZERO));
        result.set(0, poly.get(0));
        for (int i = 1; i < size; i++) {
            result.set(i, poly.get(i).subtract(root.multiply(poly.get(i - 1))));
        }
        result.set(size, poly.get(size - 1).negate().multiply(root));
        return result;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder inputText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) inputText.append(line);
        String json = inputText.toString();

        int n = getNumber(json, "n");
        int k = getNumber(json, "k");

        List<BigInteger> roots = new ArrayList<>();

        for (int i = 1; i <= k; i++) {
            String baseStr = getValue(json, i, "base");
            String valueStr = getValue(json, i, "value");
            if (baseStr == null || valueStr == null) continue;
            int base = Integer.parseInt(baseStr);
            BigInteger root = new BigInteger(valueStr, base);
            roots.add(root);
        }

        List<BigInteger> poly = new ArrayList<>();
        poly.add(BigInteger.ONE);

        for (BigInteger r : roots) poly = multiplyPoly(poly, r);

        for (int i = 0; i < poly.size(); i++) {
            System.out.print(poly.get(i));
            if (i + 1 < poly.size()) System.out.print(" ");
        }
    }
}
