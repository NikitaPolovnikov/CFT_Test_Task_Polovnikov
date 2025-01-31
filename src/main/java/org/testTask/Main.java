package org.testTask;



public class Main {

        public static void main(String[] args) {
/*
Создание экземпляра класса ArgumentManager и выполнение метода parseArgs для обработки аргументов командной строки.
*/
            ArgumentManager argumentManager  = new ArgumentManager();
            argumentManager.parseArgs(args);
/*
Создание экземпляров класса Statistic для целых и вещественных чисал, а также для строк.
 */
            Statistic intStatistic = new Statistic();
            Statistic floatStatistic = new Statistic();
            Statistic stringStatistic = new Statistic();
/*
Обработка файлов.
 */
            FileProcessor fileProcessor = new FileProcessor();
            fileProcessor.processFiles(argumentManager, intStatistic, floatStatistic, stringStatistic);

/*
Вывод в консоль статистики, если это необходимо
*/
            if (argumentManager.getShortStatistic()){
                intStatistic.printShortStat("Integer");
                floatStatistic.printShortStat("Float");
                stringStatistic.printShortStat("String");
            }
            if (argumentManager.getFullStatistic()){
                intStatistic.printFullStat("Integer");
                floatStatistic.printFullStat("Float");
                stringStatistic.printFullStat("String");
            }

        }

    }
