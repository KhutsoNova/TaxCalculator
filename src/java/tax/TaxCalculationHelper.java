package tax;

import java.text.DecimalFormat;

/**
 * A Helper class that will handle the backend tax calculations logic.
 *
 * @author Khutso Mothapo
 */
public class TaxCalculationHelper {
    double taxableAmount, taxBandAmount, computedTaxBandAmount, rebateAmount, primaryRebateAmount;
    double secondaryRebateAmount, tertiaryRebateAmount, taxCredits, additionalDependentsAmount;
    boolean isTaxThresholdIndividual;  
    
    /**
     * This method calculates the individual's PAYE before Tax Credits.
     * 
     * @param age The age of the indivdiual.
     * @param taxableIncomeType The type/form (Monthly/Annually) of grosstaxableIncome income provided.
     * @param grossTaxableIncome  The individual's taxable income.
     * @param taxYear The tax year.
     * @return returns a double
     */
    public double getPAYEbeforeCredits(String age, String taxableIncomeType, double grossTaxableIncome, String taxYear) {       
        try {
            if (taxableIncomeType.equals("monthly")) {
                grossTaxableIncome = grossTaxableIncome * 12;
            }
            //Precheck to see if the indivdiual qualifies within the Tax Threshold
            boolean isTaxThresholdIndividual = isTaxThreshholdIndividual(age, grossTaxableIncome, taxYear);
            
            if (!isTaxThresholdIndividual) {
               if (taxYear.equals("2017")) {
                    taxableAmount = getIndivdiualsAndSpecialTrusts("2017", grossTaxableIncome) - getTaxRebateAmount("2017", age);  
               } else {
                  taxableAmount = getIndivdiualsAndSpecialTrusts("2018", grossTaxableIncome) - getTaxRebateAmount("2018", age);  
               }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxableAmount;
    }
    
    /**
     * This method checks if the individual qualifies within the Tax Threshhold brackets.
     * 
     * @param age The age of the indivdiual.
     * @param grossTaxableIncome The individual's taxable income.
     * @param year The tax year.
     * @return a boolean value 
     */   
    public boolean isTaxThreshholdIndividual (String age, double grossTaxableIncome, String year) {             
        try {            
            if (year.equals("2017")) {
                //below age 65
                if (age.equals("a") && grossTaxableIncome <= 75_000.0) {               
                    isTaxThresholdIndividual = true; 
                //Age 65 to 75
                } else if (age.equals("b") && grossTaxableIncome <= 116_150.0) {
                    isTaxThresholdIndividual = true;
                //Age 75 and over
                } else if (age.equals("c") && grossTaxableIncome <= 129_850.0) {
                    isTaxThresholdIndividual = true;
                }  
            } else {
                //below age 65
                if (age.equals("a") && grossTaxableIncome <= 75_750.0) {               
                    isTaxThresholdIndividual = true; 
                //Age 65 to 75
                } else if (age.equals("b") && grossTaxableIncome <= 117_300.0) {
                    isTaxThresholdIndividual = true;
                //Age 75 and over
                } else if (age.equals("c") && grossTaxableIncome <= 131_150.0) {
                    isTaxThresholdIndividual = true;
                } 
            }                    
        } catch (Exception e) {
            e.printStackTrace();
        }                       
        return isTaxThresholdIndividual;
    }
    
    /**
     * This method calculates which Tax Bracket/Band the Individual falls under. 
     * 
     * @param year The tax year.
     * @param grossTaxableIncome The individual's taxable income.
     * @return a double value
     */
    public double getIndivdiualsAndSpecialTrusts(String year, double grossTaxableIncome) {       
        try {
            if (year.equals("2017")) {
                //Tax year 2017
                if (grossTaxableIncome <= 188_000.0) {
                    taxBandAmount = grossTaxableIncome * (18.0/100.0);                   
                } else if (grossTaxableIncome >= 188_001.0 && grossTaxableIncome <= 293_600.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 188_000.0, 26.0, 33_840.0);
                } else if (grossTaxableIncome >= 293_601.0 && grossTaxableIncome <= 406_400.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 293_600.0, 31.0, 61_296.0);
                } else if (grossTaxableIncome >= 406_401.0 && grossTaxableIncome <= 550_100.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 406_400.0, 36.0, 96_264.0);
                } else if (grossTaxableIncome >= 550_101.0 && grossTaxableIncome <= 701_300.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 550_100.0, 39.0, 147_996.0);
                } else {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 701_300.0, 41.0, 206_964.0);
                }
            } else {
                //Tax year 2018
                if (grossTaxableIncome <= 189_880.0) {
                    taxBandAmount = grossTaxableIncome * (18.0/100.0);  
                } else if (grossTaxableIncome >= 189_881.0 && grossTaxableIncome <= 296_540.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 189_880.0, 26.0, 34_178.0);
                } else if (grossTaxableIncome >= 296_541.0 && grossTaxableIncome <= 410_460.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 296_540.0, 31.0, 61_910.0);
                } else if (grossTaxableIncome >= 410_461.0 && grossTaxableIncome <= 555_600.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 410_460.0, 36.0, 97_225.0);
                } else if (grossTaxableIncome >= 555_601.0 && grossTaxableIncome <= 708_310.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 555_600.0, 39.0, 149_475.0);
                } else if (grossTaxableIncome >= 708_311.0 && grossTaxableIncome <= 1_500_000.0) {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 708_310.0, 41.0, 209_032.0);
                } else {
                    taxBandAmount = getTaxBandAmount(grossTaxableIncome, 1_500_000.0, 45.0, 533_625.0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxBandAmount;
    }
    
    /**
     * This method calculates the individual's tax bracket/band amount.
     * 
     * @param grossTaxableIncome The individual's taxable income.
     * @param previousTaxBandAmount The preceding/previous tax bracket/band's maximum value.
     * @param currentTaxBandPercentage The current tax bracket/band's percentage.
     * @param currentTaxBandAmount The current tax band's amount
     * @return a double value
     */
    public double getTaxBandAmount(Double grossTaxableIncome, Double previousTaxBandAmount, Double currentTaxBandPercentage, Double currentTaxBandAmount) {        
       try {
           double grossMinusPreviousBandAmount = grossTaxableIncome - previousTaxBandAmount;
           double grossAmountMinusBandAmountPercentage = grossMinusPreviousBandAmount * (currentTaxBandPercentage/100.0);
           computedTaxBandAmount = grossAmountMinusBandAmountPercentage + currentTaxBandAmount;
       } catch (Exception e) {
           e.printStackTrace();
       }       
      return computedTaxBandAmount;  
    }
    
    /**
     * This method sets the tax rebate amount values.
     * 
     * @param taxYear he tax year.
     */
    public void setRebateAmounts(String taxYear) {
        if (taxYear.equals("2017")){
            primaryRebateAmount = 13_500;
            secondaryRebateAmount = primaryRebateAmount + 7_407;
            tertiaryRebateAmount = primaryRebateAmount + secondaryRebateAmount + 2_466; 
        } else {
            primaryRebateAmount = 13_635;
            secondaryRebateAmount = primaryRebateAmount + 7_479;
            tertiaryRebateAmount = primaryRebateAmount + secondaryRebateAmount + 2_493; 
        }
    }
    
    /**
     * This method calculates the Individual's Rebate Amount
     * 
     * @param taxYear The tax year.
     * @param age The individual's age.
     * @return a double value
     */
    public double getTaxRebateAmount(String taxYear, String age){       
        try {            
            if (taxYear.equals("2017")) {
                setRebateAmounts(taxYear);          
                switch (age) {
                    //Below age 65
                    case "a":
                        rebateAmount = primaryRebateAmount;
                    break;
                    //Age 65 to 175
                    case "b":
                        rebateAmount = secondaryRebateAmount;
                    break;
                    //Age 75 and Over
                    default :
                       rebateAmount = tertiaryRebateAmount; 
                    break;
                }
            } else {
                setRebateAmounts(taxYear);
                switch (age) {
                    //Below age 65
                    case "a":
                        rebateAmount = primaryRebateAmount;
                    break;
                    //Age 65 to 175
                    case "b":
                        rebateAmount = secondaryRebateAmount;
                    break;
                    //Age 75 and Over
                    default :
                       rebateAmount = tertiaryRebateAmount; 
                    break;
                }               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rebateAmount;
    }
    
    /**
     * This method sets the tax credit amount and additional dependents amount depending on the tax year.
     * 
     * @param taxYear The tax year
     */
    public void setTaxCreditAmount (String taxYear) {
        if (taxYear.equals("2017")) {
            taxCredits = 286;
            additionalDependentsAmount = 192;
        } else {
            taxCredits = 303;
            additionalDependentsAmount = 204;
        }
    }
    
    /**
     * This method checks whether an individual has a medical aid, and then calculates
     * the tax credits the individual qualifies for, based on the information provided.
     * 
     * @param taxYear The tax year.
     * @param hasMedicalAid Boolean value that checks whether the Individual has a medical aid.
     * @param numberOfDependents The number of dependents on the medical aid besides the main member.
     * @return a double value
     */
    public double getMedicalAidCredits (String taxYear, String hasMedicalAid, int numberOfDependents){             
        if (hasMedicalAid.equals("yes")) {            
            if (taxYear.equals("2017")) {
                setTaxCreditAmount(taxYear); 
                if (numberOfDependents == 1) {
                    taxCredits = taxCredits * 2;
                } else if (numberOfDependents > 1) {
                    taxCredits = (taxCredits * 2) + (additionalDependentsAmount * numberOfDependents);
                }
            } else {
                setTaxCreditAmount(taxYear); 
                if (numberOfDependents == 1) {
                    taxCredits = taxCredits * 2;
                } else if (numberOfDependents > 1) {
                    taxCredits = taxCredits * 2 + (additionalDependentsAmount * (numberOfDependents - 1));
                }
            }
        }
        return taxCredits;    
    }
    
    /**
     * This method formats the calculated PAYE value into the format e.g 55,000.00
     * 
     * @param income The individual's PAYE value/ income after tax.
     * @return 
     */
    public String decimalFormnatter (double income) {
        String formattedNumber = String.format("%.2f", income);
        
        String [] splittedNum = formattedNumber.split("\\.");
        String decimalNum="";
        
        if (splittedNum.length == 2) {
            decimalNum="." + splittedNum[1];
        }
        
        Double inputDouble=Double.parseDouble(formattedNumber);
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String output = myFormatter.format(inputDouble);
  
        return output+decimalNum;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
