package org.testTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    /**
     * Метод для обработки файлов
     */
    public void processFiles(ArgumentManager argumentManager, Statistic intStatistic, Statistic floatStatistic, Statistic stringStatistic) {
/*
* Проверка, существует ли директория, указанная для создания выходных файлов.
* Если директории не существует - происходит создание всей ветви недостающих директорий
*/
        File outputDirectory = new File(argumentManager.getOutputFilePath());
        if (!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
/*
Создание временных файлов результатов
 */
        File intResults = new File(argumentManager.getOutputFilePath() + argumentManager.getOutputFilePrefix() + "integers.txt");
        File floatResults = new File(argumentManager.getOutputFilePath() + argumentManager.getOutputFilePrefix() + "floats.txt");
        File stringResults = new File(argumentManager.getOutputFilePath() + argumentManager.getOutputFilePrefix() + "strings.txt");


        /*
         * Создание BufferedWriter для каждого файла результатов с указанием расположения, префиксом и функцией записи в существующий файл.
         */
        try (
                BufferedWriter intWriter = new BufferedWriter(new FileWriter(intResults, argumentManager.getAppendOption()));
                BufferedWriter floatWriter = new BufferedWriter(new FileWriter(floatResults, argumentManager.getAppendOption()));
                BufferedWriter strWriter = new BufferedWriter(new FileWriter(stringResults, argumentManager.getAppendOption()))
        ) {

        /*
        * Создание списка BufferedReader для каждого из входных файлов. Если файл отсутствует в консоль выводится соответствующее сообщение.
        */
            List<BufferedReader> readers = new ArrayList<>(argumentManager.getInputFiles().size());
            int i = 0;
            for (String inputFile : argumentManager.getInputFiles()){
                try {
                    readers.add(new BufferedReader(new FileReader(inputFile)));
                } catch (Exception e){
                    System.out.println("File not found: " + inputFile);
                }
            }

            /*
            В отсутствие валидных входных файлов программа прекращает свое выполнение.
            На мой взгляд данная проверка имеет место, однако она закомментирована из соображений удобства проверки кода программы.
             */

//            if (readers.isEmpty()){
//                System.out.println("No input files");
//                System.exit(-1);
//            }


            boolean allLineProcessed = false;
            while (!allLineProcessed) {
                allLineProcessed = true; // Маркер, необходимый для прочтения всех файлов.
                for (BufferedReader reader : readers) {
                    String line = reader.readLine();
                    if (line != null) {
                        allLineProcessed = false; // Если в ходе итерации обработана строка - переходим к следующей итерации. Иначе - завершение чтения файлов(выход из цикла).
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue; // Пропуск пустых строк
                        }
                        String type = classifyValue(line); //Определение типа данных, содержащихся в строке
                        switch (type) {                    //Запись строки в файл результата в зависимости от типа данных
                            case "Integer":
                                intWriter.write(line);
                                if (argumentManager.getShortStatistic() || argumentManager.getFullStatistic()){
                                    intStatistic.addValue(line, type, argumentManager.getFullStatistic());
                                }
                                intWriter.newLine();
                                break;
                            case "Float":
                                floatWriter.write(line);
                                if (argumentManager.getShortStatistic() || argumentManager.getFullStatistic()){
                                    floatStatistic.addValue(line, type, argumentManager.getFullStatistic());
                                }
                                floatWriter.newLine();
                                break;
                            case "String":
                                strWriter.write(line);
                                if (argumentManager.getShortStatistic() || argumentManager.getFullStatistic()){
                                    stringStatistic.addValue(line, type, argumentManager.getFullStatistic());
                                }
                                strWriter.newLine();
                                break;
                        }
                    }
                }
            }
/*
* Закрытие всех экземпляров BufferedReader, соответсвующих входным файлам.
*/
            for (BufferedReader reader : readers) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("File handling error");
            e.printStackTrace();
        }
        deleteTemporaryFiles(intResults, floatResults, stringResults);
    }

    /**
     * Метод для определения типа данных текущей строки.
     * Выполняются поочередно проверки:
     *  1.Является ли целым числом?
     *  2.Является ли вещественным числом?
     *  Если не является ни целым, ни вещественным, следовательно является строкой.
     */
    private static String classifyValue(String value) {
        if (value.matches("-?\\d+")) {
            return "Integer";
        } else if (value.matches("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?")) {
            return "Float";
        } else {
            return "String";
        }
    }

    /**
     * Метод для удаления пустых временных файлов
     */
    private static void deleteTemporaryFiles (File... file){
        for (File currentFile : file){
            if (currentFile.exists() && currentFile.length()==0){
                currentFile.delete();
            }
        }
    }
}
