package org.testTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ArgumentManager {

    private Boolean appendOption = false;
    private Boolean fullStatistic = false;
    private Boolean shortStatistic = false;
    private ArrayList<String> inputFiles = new ArrayList<>();
    private String outputFilePrefix = "";
    private String outputFilePath = "";

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public String getOutputFilePrefix() {
        return outputFilePrefix;
    }

    public void setOutputFilePrefix(String outputFilePrefix) {
        this.outputFilePrefix = outputFilePrefix;
    }

    public ArrayList<String> getInputFiles() {
        return inputFiles;
    }

    public Boolean getShortStatistic() {
        return shortStatistic;
    }

    public void setShortStatistic(Boolean shortStatistic) {
        this.shortStatistic = shortStatistic;
    }

    public Boolean getFullStatistic() {
        return fullStatistic;
    }

    public void setFullStatistic(Boolean fullStatistic) {
        this.fullStatistic = fullStatistic;
    }

    public Boolean getAppendOption() {
        return appendOption;
    }

    public void setAppendOption(Boolean appendOption) {
        this.appendOption = appendOption;
    }

    /**
     * Метод для обработки аргументов командной строки.
     * Повторяющиеся входные файлы не считываются многократно.
     * В случае, если заданы некорректный префикс или директория
     */
    public void parseArgs(String [] args) {

        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-a")) {
                setAppendOption(true);

            } else if (args[i].equals("-p")&& args.length>2) {
                setOutputFilePrefix(args[i + 1]);
                try {
                    Paths.get(outputFilePrefix);
                } catch (InvalidPathException ex){
                    System.out.println("Invalid prefix: " + outputFilePrefix);
                    setOutputFilePrefix("");
                }
            } else if (args[i].equals("-o")&& args.length>2) {
                setOutputFilePath(args[i + 1] + File.separator);
                try {
                    Paths.get(outputFilePath);
                } catch (InvalidPathException ex){
                    System.out.println("Invalid output file path: " + outputFilePath);
                    setOutputFilePath("");
                }

            } else if (args[i].equals("-f")) {
                setFullStatistic(true);

            } else if (args[i].equals("-s")) {
                setShortStatistic(true);

            } else if (args[i].endsWith(".txt") && !inputFiles.contains(args[i])) {
                try {
                    Paths.get(args[i]);
                    inputFiles.add(args[i]);
                } catch (InvalidPathException ex){
                    System.out.println("Invalid input file: " + args[i]);
                }
            }
            else if (args.length>2 &&!args[i - 1].equals("-o") && !args[i - 1].equals("-p")){
                System.out.println("Argument \"" + args[i] + "\" is not valid");
            }
        }
    }
}
