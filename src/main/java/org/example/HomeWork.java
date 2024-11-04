package org.example;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class HomeWork {

    /**
     * Журнал не содержит внутренних противоречий (например, первая запись «DEL X» не может встретиться раньше первой записи «BID X» и т.д.). В последней строке входных данных записано слово «QUIT».
     */
    private static final Map<String, Func<BigDecimal, Integer, Treap<BigDecimal>, BigDecimal>> actions = Map.of(
            "BID", (key, count, treap) -> {
                var node = treap.getByKey(key);
                if (null != node) {
                    node.value += 1;
                } else {
                    treap.add(key);
                }
                return BigDecimal.ZERO;
            },
            "DEL", (key, count, treap) -> {
                treap.getByKey(key).value -= 1;
                return BigDecimal.ZERO;
            },
            "SALE", (key, count, treap) -> {
                var bidCount = treap.split(key.subtract(BigDecimal.valueOf(0.001)))[1].statistic.sumValue;
                return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(bidCount > count ? count : bidCount));
            }

    );

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        var profit = BigDecimal.ZERO;
        var p = Pattern.compile("(\\w+)\\s(\\d+\\.?\\d+)\\s?(\\d+)?");
        var treap = new Treap<BigDecimal>();

        for (String s : actionList) {
            var matcher = p.matcher(s);
            matcher.find();
            profit = profit.add(
                    actions.get(matcher.group(1))
                            .proceed(
                                    new BigDecimal(matcher.group(2)),
                                    Integer.valueOf(Optional.ofNullable(matcher.group(3)).orElse("0")),
                                    treap
                            )
            );
        }

        return profit.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        return null;
    }


}
