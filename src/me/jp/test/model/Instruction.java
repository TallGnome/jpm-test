package me.jp.test.model;

import me.jp.test.types.InstructionType;
import java.time.LocalDate;

/**
 * Represents a client instruction
 * @author Athanasios.Doulgeris
 *
 */
public class Instruction {

    private String entity;
    private InstructionType type;
    private double agreedFx;
    private Currency currency;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    private int units;
    private double pricePerUnit;

    public Instruction(String entity, InstructionType type, double agreedFx, Currency currency,
                       LocalDate instructionDate, LocalDate settlementDate, int units, double pricePerUnit) {
        super();
        this.entity = entity;
        this.type = type;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.settlementDate = handleSettlementDate(settlementDate);
    }

    private LocalDate handleSettlementDate(LocalDate settlementDate) {
        int openingDayDiff = this.currency.getOpeningDay().compareTo(settlementDate.getDayOfWeek());
        int closingDayDiff = this.currency.getClosingDay().compareTo(settlementDate.getDayOfWeek());

        if(closingDayDiff < 0 && openingDayDiff < 0){
            return settlementDate.plusDays(7 - Math.abs(openingDayDiff));
        } else if(openingDayDiff > 0 && closingDayDiff != 0) {
            return settlementDate.plusDays(openingDayDiff);
        }
        return settlementDate;
    }

    public double getAmountInUSD() {
        return this.agreedFx * this.pricePerUnit * this.units;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(double agreedFx) {
        this.agreedFx = agreedFx;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(LocalDate instructionDate) {
        this.instructionDate = instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
