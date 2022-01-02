package net.kigawa;

import java.util.Iterator;
import java.util.List;

public class StringUtil {
    public static StringBuffer insertSymbol(StringBuffer sb, String symbol, List<String> stringList) {
        Iterator<String> iterator = stringList.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) sb.append(symbol);
        }
        return sb;
    }

    public static String insertSymbol(String symbol, List<String> stringList) {
        return insertSymbol(new StringBuffer(), symbol, stringList).toString();
    }
}
