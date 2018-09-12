package me.jp.test;

import me.jp.test.model.Currency;
import me.jp.test.model.Instruction;
import me.jp.test.reports.InstructionsReportService;
import me.jp.test.types.InstructionType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static Scanner systemScanner = new Scanner( System.in );
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

        System.out.println("Please enter the absoluter path for the currencies file (.csv)");
        String currenciesFile = systemScanner.nextLine();
        Map<String, Currency> currenciesMap = populateCurrenciesMap(currenciesFile);

        System.out.println("Please enter the absoluter path for the instructions file (.csv)");
        String instructionsFile = systemScanner.nextLine();
        List<Instruction> instructionsInput = populateInstructionsList(instructionsFile, currenciesMap, formatter);


        InstructionsReportService instructionsReportService = new InstructionsReportService(instructionsInput);
        for(Map.Entry<LocalDate, Double> incomingDailyAmount : instructionsReportService.getAmountSettledByDateAndType(InstructionType.SELL).entrySet()){
            if(incomingDailyAmount.getValue() != 0){
                System.out.println("Amount of incoming in USD settled for date "
                        + incomingDailyAmount.getKey().format(formatter)
                        + " is: "
                        + incomingDailyAmount.getValue());
            }
        }

        for(Map.Entry<LocalDate, Double> outgoingDailyAmount : instructionsReportService.getAmountSettledByDateAndType(InstructionType.BUY).entrySet()){
            if(outgoingDailyAmount.getValue() != 0) {
                System.out.println("Amount of outgoing in USD settled for date "
                        + outgoingDailyAmount.getKey().format(formatter)
                        + " is: "
                        + outgoingDailyAmount.getValue());
            }
        }

        System.out.println("\n\n Entity rankings by amount for incoming instructions: ");
        List<Instruction> incomingInstructionsSorted = instructionsReportService
                .getInstructionsSortedByAmountAndType(InstructionType.SELL);
        for(int i = 0; i < incomingInstructionsSorted.size(); i++){
            System.out.println((i+1) + ". "
                    + incomingInstructionsSorted.get(i).getEntity());
        }

        System.out.println("\n\n Entity rankings by amount for outgoing instructions: ");
        List<Instruction> outgoingInstructionsSorted = instructionsReportService
                .getInstructionsSortedByAmountAndType(InstructionType.BUY);
        for(int i = 0; i < outgoingInstructionsSorted.size(); i++){
            System.out.println((i+1) + ". "
                    + outgoingInstructionsSorted.get(i).getEntity());
        }
    }

    private static Map<String, Currency> populateCurrenciesMap(String file){
        Map<String, Currency> map = new HashMap<String, Currency>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] currency = line.split(",");
                map.put(currency[0], new Currency(currency[0],
                        DayOfWeek.valueOf(currency[1]),
                        DayOfWeek.valueOf(currency[2])));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static List<Instruction> populateInstructionsList(String file, Map<String, Currency> currencies,
                                                              DateTimeFormatter formatter){
        List<Instruction> list = new ArrayList<Instruction>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] instruction = line.split(",");
                list.add(new Instruction(instruction[0], InstructionType.valueOf(instruction[1]),
                        Double.parseDouble(instruction[2]), currencies.get(instruction[3]),
                        LocalDate.parse(instruction[4], formatter), LocalDate.parse(instruction[5], formatter),
                        Integer.parseInt(instruction[6]), Double.parseDouble(instruction[7])));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
