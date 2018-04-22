/* 
 * JavaScript file that handles the front-end input fields validation
 * 
 * @author Khutso Mothapo
 */
var errorMessage="";

//This function calls all the other defined functions below and builds up the alert message if one or more of the other functions return a false value.
function validate() {             
    errorMessage="";               
    var taxableIncomeValidation = validateTaxableIncome();
    var numberOfDependentsValidation = validateMedicalAidNumberOfDependents();
    var medicalAidContributionValidation = validateMedicalAidContributionAmount();
                                
    if (taxableIncomeValidation && numberOfDependentsValidation && medicalAidContributionValidation) {                                    
        return (true);
    } else {                  
        alert(errorMessage);
        return (false);
    }
}

//This function validates the 'Taxable Income' field.
function validateTaxableIncome() {
    var taxableIncome = document.TaxCalculator.taxableIncome.value;
    var regex = /[0-9]|\./;
               
    if (taxableIncome === "" || taxableIncome === "0.00" || taxableIncome === "0") {
        errorMessage += "Please Enter your Taxable Income. \n";
        document.getElementById("taxableIncome").style.backgroundColor="red";
        return false;
    } else if (!regex.test(taxableIncome)) {
        errorMessage += "Please Enter Only a Numberic Value As your Taxable Income. \n";
        document.getElementById("taxableIncome").style.backgroundColor="red";
        return false;
    } else {
        document.getElementById("taxableIncome").style.backgroundColor="";
        return true;
    }
}

//This function validates the 'Number of Dependents' field.
function validateMedicalAidNumberOfDependents() {              
    var optionSelected="";
    var numOfOptions = document.TaxCalculator.hasMedicalAid.length;
    var numberOfDepedents = document.TaxCalculator.numberOfDependents.value;
    var regex = /[0-9]|\./;
               
    for (var index=0; index < numOfOptions; index++) {
        if (document.TaxCalculator.hasMedicalAid[index].selected) {
           optionSelected = document.TaxCalculator.hasMedicalAid[index].value;
           break;
        }      
    }
    
    if (optionSelected === "yes") {
        if (numberOfDepedents === "") {
            errorMessage +="Please Enter the Number of Dependents. \n";
            document.getElementById("numberOfDependents").style.backgroundColor="red";
            return false;
        } else if (!regex.test(numberOfDepedents)) {
            errorMessage +="Please Enter a Numberic Value for Dependents. \n";
            document.getElementById("numberOfDependents").style.backgroundColor="red";
            return false;
        } else {                      
            document.getElementById("numberOfDependents").style.backgroundColor="";                 
            return true; 
        }
    } else {
        document.getElementById("numberOfDependents").style.backgroundColor="";                 
        return true; 
    }
}

//This function validates the 'Medical Aid Contribution(Monthly)' field.
function validateMedicalAidContributionAmount() {              
    var optionSelected="";
    var numOfOptions = document.TaxCalculator.hasMedicalAid.length;
    var medicalAidContribution = document.TaxCalculator.medicalAidContribution.value;
    var regex = /[0-9]|\./;
               
    for (var index=0; index < numOfOptions; index++) {
        if (document.TaxCalculator.hasMedicalAid[index].selected) {
           optionSelected = document.TaxCalculator.hasMedicalAid[index].value;
           break;
        }      
    }
        
    if (optionSelected === "yes") {
        if (medicalAidContribution === "" || medicalAidContribution === "0.00") {
            errorMessage +="Please Enter the Medical Aid Contribution Amount. \n";
            document.getElementById("medicalAidContribution").style.backgroundColor="red";
            return false;
        } else if (!regex.test(medicalAidContribution)) {
            errorMessage +="Please Enter a Numberic Value for the Medical Aid Contribution Amount. \n";
            document.getElementById("medicalAidContribution").style.backgroundColor="red";
            return false;
        } else {                      
            document.getElementById("medicalAidContribution").style.backgroundColor="";                 
            return true; 
        }
    } else {
               document.getElementById("medicalAidContribution").style.backgroundColor="";                 
               return true; 
    }
}

//This function hides the 'Number of Dependents' and 'Medical Aid Contribution(Monthly)' fields if the Medical Aid field is set to 'no'.
function medicalAidFieldsValidation() {
    var optionSelected="";
    var numOfOptions = document.TaxCalculator.hasMedicalAid.length;
                             
    for (var index=0; index < numOfOptions; index++) {
       if (document.TaxCalculator.hasMedicalAid[index].selected) {
          optionSelected = document.TaxCalculator.hasMedicalAid[index].value;
          break;
       }      
    }
               
    if (optionSelected === "no") {
        document.getElementById("NumberOfDependentsTr").style.display = 'none';
        document.getElementById("MedicalAidContributionTr").style.display = 'none';
                
    } else {
        document.getElementById("NumberOfDependentsTr").style.display = '';
        document.getElementById("MedicalAidContributionTr").style.display = '';
    }             
}