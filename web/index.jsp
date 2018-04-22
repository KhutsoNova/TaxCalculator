<%-- 
    Document   : index
    Created on : 20 Apr 2018, 18:30:00 PM
    Author     : Khutso Mothapo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="tax.TaxCalculationHelper"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tax Calculator</title>
        <link href="css/index.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/index.js"></script>
    </head>
    <% 
        TaxCalculationHelper taxHelper = new TaxCalculationHelper();
        String taxYear = null, ageCategory = null, taxableIncomeType = null, hasMedicalAid = null;
        double taxableIncome = 0.0, payeBeforeTaxCredit = 0.0, taxCredits = 0.0, payeDueAfterTaxCreditDeductions=0.0, netCashAfterPayeDue =0.0, netCashAfterPayeDueAndMedicalAidContribution = 0.0, medicalAidConribution = 0.0;
        int numberOfDependents = 0;
                                      
        if (request.getParameter("taxableIncome") != null) {                   
            taxYear = request.getParameter("taxYear");
            ageCategory = request.getParameter("age");
            taxableIncomeType = request.getParameter("taxableIncomeType");         
            taxableIncome = Double.parseDouble(request.getParameter("taxableIncome"));                
            payeBeforeTaxCredit = taxHelper.getPAYEbeforeCredits(ageCategory, taxableIncomeType, taxableIncome, taxYear);                   
            hasMedicalAid = request.getParameter("hasMedicalAid");

            if (hasMedicalAid.equals("yes")) {                      
                numberOfDependents = Integer.parseInt(request.getParameter("numberOfDependents"));
                medicalAidConribution = Double.parseDouble(request.getParameter("medicalAidContribution"));
                taxCredits = taxHelper.getMedicalAidCredits(taxYear, hasMedicalAid, numberOfDependents);
                payeDueAfterTaxCreditDeductions = (payeBeforeTaxCredit / 12) - taxCredits;                   
                netCashAfterPayeDue = taxableIncomeType.equals("annually") ? taxableIncome / 12 : taxableIncome;
                netCashAfterPayeDue = netCashAfterPayeDue - payeDueAfterTaxCreditDeductions;
                netCashAfterPayeDueAndMedicalAidContribution = netCashAfterPayeDue - medicalAidConribution;
            } else {                      
               payeDueAfterTaxCreditDeductions =  payeBeforeTaxCredit / 12;
               netCashAfterPayeDue = taxableIncomeType.equals("annually") ? taxableIncome / 12 : taxableIncome;
               netCashAfterPayeDue = netCashAfterPayeDue - payeDueAfterTaxCreditDeductions;
               netCashAfterPayeDueAndMedicalAidContribution = netCashAfterPayeDue;
            }                                                     
        }              
    %>
    <body onload="medicalAidFieldsValidation()">
     <div class="container">
        <header>
            <h1>Tax Calculator</h1>
        </header>
        <form method="post" action="index.jsp" name="TaxCalculator">
            <div class = "heading1">
            <h4>Please Provide Your Details Below</h4>
            </div>
            <div class="tableInfo">
            <table border="0" style="margin-top:-50px">   
                <tr><td><label id="caption">Tax Year</label></td><td>
                    <label id="lblSelect"> 
                    <select name="taxYear" id="taxYear">                       
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                    </select>
                    </label>    
                </td></tr>
                <br>
                <tr><td><label id="caption">Age Category</label></td><td>
                    <label id="lblSelect">  
                    <select name="age" id="age">                       
                        <option value="a">Below Age 65</option>
                        <option value="b">Age 65 to 75</option>
                        <option value="c">Age 75 and Over</option>
                    </select>
                    </label> 
                </td></tr>
                <br>
                <tr><td><label id="caption">Taxable Income Type</label></td><td>
                    <label id="lblSelect">    
                    <select name="taxableIncomeType" id="taxableIncomeType" title="Select Taxable Income Type">                       
                        <option value="monthly">Monthly</option>
                        <option value="annually">Annually</option>
                    </select>
                    </label> 
                </td></tr>
                <br>
                <tr>                  
                    <td><label id="caption">Taxable Income</label></td><td><div class="taxableIncome">R <input type="text" name="taxableIncome" id="taxableIncome" value="0.00"></div></td></tr><br>
                <br>
                <tr><td><label>Are you on a Medical Aid? </label></td><td>
                    <label id="lblSelect">    
                    <select name="hasMedicalAid" id="hasMedicalAid" onclick="medicalAidFieldsValidation()">                       
                        <option value="no">No</option>
                        <option value="yes">Yes</option>
                    </select>
                    </label>  
                    </td></tr>     
                <br>
                <tr id="NumberOfDependentsTr">                  
                <td><label id="caption">Number of Dependents</label></td><td><div class="numberOfDependents"><input type="text" name="numberOfDependents" id="numberOfDependents" value="0"></div></td></tr><br>
                <br>
                <tr id="MedicalAidContributionTr">
                    <td><label id="caption">Medical Aid Contribution(Monthly)</label></td><td>R <input type="text" name="medicalAidContribution" id="medicalAidContribution" value="0.00"></td></tr><br>          
            </table>
                <Button class="button" onClick="return validate();">Calculate Tax</button>
            </div>
            <h3> Tax Calculation Totals </h3>
            <div class = "tableCalcualtionTotals">
            <table border="0" style="margin-bottom:10px">
                <tr>
                <% String sPayeBeforeTaxCreditMonthly = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(payeBeforeTaxCredit / 12) : "0.00"; %> 
                <td><label id="caption">Monthly PAYE Before Tax Credit</label></td><td>R<input type="text" name="mp" id="outputValues"  value ="<% out.println(sPayeBeforeTaxCreditMonthly);%>"></td></tr><br>
                <% String sPayeBeforeTaxCreditAnnually = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(payeBeforeTaxCredit) : "0.00"; %> 
                <td><label id="caption">Annual PAYE Before Tax Credit</label></td><td>R<input type="text" name="ap" id="outputValues"  value="<% out.println(sPayeBeforeTaxCreditAnnually);%>"></td></tr><br>
                <% String sTaxCredits = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(taxCredits) : "0.00"; %> 
                <td><label id="caption">Tax Credits</label></td><td>R<input type="text" name="tc" id="outputValues"  value="<% out.println(sTaxCredits);%>"></td></tr><br>
                <% String sPayeDueAfterTaxCreditDeductions = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(payeDueAfterTaxCreditDeductions) : "0.00"; %> 
                <td><label id="caption">PAYE Due After Tax Credit Deductions (Monthly)</label></td><td>R<input type="text" name="ptcd" id="outputValues"  value="<% out.println(sPayeDueAfterTaxCreditDeductions);%>"></td></tr><br>
                <% String sNetCashAfterPayeDue = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(netCashAfterPayeDue) : "0.00"; %> 
                <td><label id="caption">Net Cash Pay After PAYE Due (Monthly)</label></td><td>R<input type="text" name="npd" id="outputValues"  value="<% out.println(sNetCashAfterPayeDue);%>"></td></tr><br>
                <% String sNetCashAfterPayeDueAndMedicalAidContribution = request.getParameter("taxableIncome") != null ? taxHelper.decimalFormnatter(netCashAfterPayeDueAndMedicalAidContribution) : "0.00"; %> 
                <td><label id="caption">Net Cash Pay After PAYE Due and Medical Aid Contribution(Monthly)</label></td><td>R<input type="text" name="npdm" id="outputValues" value="<% out.println(sNetCashAfterPayeDueAndMedicalAidContribution);%>"></td></tr><br>
            </table>
            </div>
            </form>
            <footer><h4>Copyright &copy; 2018 Tax Calculator, Website by Khutso Mothapo, All rights reserved &reg<h4></footer>
    </div>
    </body>
</html>
