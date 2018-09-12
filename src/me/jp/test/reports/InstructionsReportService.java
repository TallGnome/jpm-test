package me.jp.test.reports;

import me.jp.test.model.Instruction;
import me.jp.test.types.InstructionType;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InstructionsReportService {

    private List<Instruction> instructions;
    private List<LocalDate> distinctDates;

    public InstructionsReportService(List<Instruction> instructions){
        this.instructions = instructions;
        this.distinctDates = this.instructions.stream()
                .filter(distinctByKey(Instruction::getSettlementDate))
                .map(instruction -> instruction.getSettlementDate())
                .collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Map<LocalDate, Double> getAmountSettledByDateAndType(InstructionType type){
        Map<LocalDate, Double> dailyAmountsMap = new HashMap<LocalDate, Double>();
        for(LocalDate date : distinctDates){
            dailyAmountsMap.put(date, this.instructions
                    .stream()
                    .filter(instruction -> instruction.getSettlementDate().isEqual(date) && instruction.getType().equals(type))
                    .mapToDouble(Instruction::getAmountInUSD)
                    .sum());
        }
        return dailyAmountsMap;
    }

    public List<Instruction> getInstructionsSortedByAmountAndType(InstructionType type){
        return this.instructions
                .stream()
                .filter(instruction -> instruction.getType().equals(type))
                .sorted(Comparator.comparing(Instruction::getAmountInUSD).reversed())
                .collect(Collectors.toList());
    }
}
