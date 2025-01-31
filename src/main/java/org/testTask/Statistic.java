package org.testTask;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Statistic {
    private BigInteger minValue;
    private BigInteger maxValue;
    private BigInteger sum = BigInteger.ZERO;
    private BigInteger currentValue;
    private BigDecimal minValueDec;
    private BigDecimal maxValueDec;
    private BigDecimal sumDec = BigDecimal.ZERO;
    private BigDecimal currentValueDec;
    private long count = 0;

    /**
     * Метод для добавления текущего значения (строки) в статистику в зависимости от типа значения.
     * Данный метод вызывается в случае присутствия аргументов командной строки -f ИЛИ -s;
     * Подсчет количества элементов производится в любом случае.
     * Дополнительное условие соответствует случаю наличия аргумента командной строки -f.
      */
    public void addValue(String line, String type, boolean fullStat) {
        switch (type) {
            case "Integer":
                count++;
                if (fullStat) {
                    currentValue = new BigInteger(line);
                    sum = sum.add(currentValue);
                    minValue = (minValue == null ? currentValue : minValue.min(currentValue));
                    maxValue = (maxValue == null ? currentValue : maxValue.max(currentValue));
                }
                break;
            case "Float":
                count++;
                if (fullStat) {
                    currentValueDec = new BigDecimal(line);
                    sumDec = sumDec.add(currentValueDec);
                    minValueDec = (minValueDec == null ? currentValueDec : minValueDec.min(currentValueDec));
                    maxValueDec = (maxValueDec == null ? currentValueDec : maxValueDec.max(currentValueDec));
                }
                break;
            case "String":
                count++;
                if (fullStat) {
                    currentValue = new BigInteger(String.valueOf(line.length()));
                    minValue = (minValue == null ? currentValue : minValue.min(currentValue));
                    maxValue = (maxValue == null ? currentValue : maxValue.max(currentValue));
                }
                break;
        }
    }

    /**
     * Метод для вывода полной статистики в консоль(-f).
     * Примечание: При расчете среднего значения вещественных чисел округление происходит c помощью RoundingMode.HALF_UP.
     */
    public void printFullStat (String type){
        System.out.println("Full " + type + " Statistic:");
        if (count == 0){
            System.out.println(" No data");
            System.out.println();
        }
        else {
            switch (type){
                case "Integer":
                    System.out.println(" Min value: " + minValue);
                    System.out.println(" Max value: " + maxValue);
                    System.out.println(" Number of integers: " + count);
                    System.out.println(" Sum: " + sum);
                    System.out.println(" Average value: " + sum.divide(BigInteger.valueOf(count)));
                    System.out.println();
                    break;
                case "Float":
                    System.out.println(" Min value: " + minValueDec);
                    System.out.println(" Max value: " + maxValueDec);
                    System.out.println(" Number of floats: " + count);
                    System.out.println(" Sum: " + sumDec);
                    System.out.println(" Average value: " + sumDec.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP));
                    System.out.println();
                    break;
                case "String":
                    System.out.println(" Size of the shortest line: " + minValue);
                    System.out.println(" Size of the largest line: " + maxValue);
                    System.out.println(" Number of strings: " + count);
                    System.out.println();
                    break;
            }
        }
    }

    /**
     * Метод для вывода краткой статистики в консоль(-s).
     */
    public void printShortStat(String type) {
        System.out.println("Short " + type + " Statistic:");
        if (count == 0) {
            System.out.println(" No data");
            System.out.println();
        } else {
            switch (type) {
                case "Integer":
                    System.out.println(" Number of integers: " + count);
                    System.out.println();
                    break;
                case "Float":
                    System.out.println(" Number of floats: " + count);
                    System.out.println();
                    break;
                case "String":
                    System.out.println(" Number of strings: " + count);
                    System.out.println();
                    break;
            }
        }
    }
}


