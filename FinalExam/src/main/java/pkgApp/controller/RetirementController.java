package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Adding an entry in the hashmap for each TextField control I want to validate
		// with a regular expression
		// "\\d*?" - means any decimal number
		// "\\d*(\\.\\d*)?" means any decimal, then optionally a period (.), then
		// decmial
		// Whole number from 0-40
		hmTextFieldRegEx.put(txtYearsToWork, "^([0-9]|[1-3][0-9]|40)$");
		// Decimal number between 0 and 10% (0.00-0.10) 
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "^(0*(\\.(0[0-9]*|10*))?)$");
		// Whole number from 0-20
		hmTextFieldRegEx.put(txtYearsRetired, "^([0-9]|1[0-9]|20)$");
		// Decimal number between 0 and 10% (0.00-0.10)
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "^(0*(\\.(0[0-9]*|10*))?)$");
		// Whole number from 2642-10000
		hmTextFieldRegEx.put(txtRequiredIncome, "^(264[2-9]|26[5-9][0-9]|2[7-9][0-9]{2}|[3-9][0-9]{3}|10000)$");
		// Whole number from 0-2642
		hmTextFieldRegEx.put(txtMonthlySSI, "^([0-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|"
				+ "99[0-9]|1[0-9]{3}|2[0-5][0-9]{2}|26[0-3][0-9]|264[0-2])$");

		// Check out these pages (how to validate controls):
		// https://stackoverflow.com/questions/30935279/javafx-input-validation-textfield
		// https://stackoverflow.com/questions/40485521/javafx-textfield-validation-decimal-value?rq=1
		// https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
		// There are some examples on how to validate / check format

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					// If newPropertyValue = true, then the field HAS FOCUS
					// If newPropertyValue = false, then field HAS LOST FOCUS
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}

		//
		// TODO: Validate Working Annual Return %, accept only numbers and decimals
		// TODO: Validate Years retired, accepted only decimals
		// TODO: Validate Retired Annual Return %, accept only numbers and deciamls
		// TODO: Validate Required Income, accept only decimals
		// TODO: Validate Monthly SSI, accept only decimals
	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");

		// disable read-only controls
		txtSaveEachMonth.setDisable(true);
		txtWhatYouNeedToSave.setDisable(true);

		// Clear, enable txtYearsToWork
		txtYearsToWork.clear();
		txtYearsToWork.setDisable(false);
		
		// Clear, enable txtAnnualReturnWorking
		txtAnnualReturnWorking.clear();
		txtAnnualReturnWorking.setDisable(false);
		
		// Clear, enable txtYearsRetired
		txtYearsRetired.clear();
		txtYearsRetired.setDisable(false);
		
		// Clear, enable txtAnnualReturnRetired
		txtAnnualReturnRetired.clear();
		txtAnnualReturnRetired.setDisable(false);
		
		// Clear, enable txtRequiredIncome
		txtRequiredIncome.clear();
		txtRequiredIncome.setDisable(false);
				
		// Clear, enable txtMonthlySSI
		txtMonthlySSI.clear();
		txtMonthlySSI.setDisable(false);
				
		// TODO: Clear, enable the rest of the input controls. Hint! You already have a
		// HashMap of all the input controls....!!!!
	}

	@FXML
	public void btnCalculate() {
		
		String a = txtYearsToWork.getText();
		String b = txtAnnualReturnWorking.getText();
		String c = txtYearsRetired.getText();
		String d = txtAnnualReturnRetired.getText();
		String e = txtRequiredIncome.getText();
		String f = txtMonthlySSI.getText();
		
		int aint = Integer.parseInt(a);
		double bdouble = Double.parseDouble(b);
		int cint = Integer.parseInt(c);
		double ddouble = Double.parseDouble(d);
		double edouble = Double.parseDouble(e);
		double fdouble = Double.parseDouble(f);
				
		Retirement retire = new Retirement(aint, bdouble, cint, ddouble, edouble, fdouble);

		System.out.println("calculating");

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);
		
		
		// TODO: Calculate txtWhatYouNeedToSave value...
		// TODO: Then calculate txtSaveEachMonth, using amount from txtWhatYouNeedToSave
		// as input
		
		
	}
}
