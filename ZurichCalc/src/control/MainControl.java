package control;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Calc;
import model.DbHelper;

public class MainControl implements Initializable {

	@FXML
	private Button btBerechnung;
	@FXML
	private ChoiceBox<String> cbStufe;
	@FXML
	private ChoiceBox<String> cbGruppe;
	@FXML
	private ChoiceBox<String> cbDiffGruppe;
	@FXML
	private ChoiceBox<String> cbDiffStufe;
	@FXML
	private TextField tbAnpProz;
	@FXML
	private TextArea taErgebnis;
	@FXML
	private TextField tbAnzWochenStd;
	@FXML
	private TextField tfUeberz;
	@FXML
	private CheckBox checkStunden;
	@FXML
	private CheckBox checkProz;
	@FXML
	private CheckBox checkDiff;
	@FXML
	private CheckBox check14;

	private double jahresbrutto;
	private double jahresbruttoV;
	private double monatsbrutto;
	private double monatsbruttoLim;
	private double monatsbruttoV;

	private double anzWoStd;
	private double anzProz;

	private String stufe;
	private String stufeV;

	NumberFormat nf = NumberFormat.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		cbGruppe.setItems(FXCollections.observableArrayList("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"));
		cbStufe.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8"));
		cbDiffGruppe
				.setItems(FXCollections.observableArrayList("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"));
		cbDiffStufe.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8"));

	}

	@FXML
	private void calcAll() {

		taErgebnis.clear();

		// Definieren der gewählten Stufe
		stufenDefinition();

		// Berechnung Monatsbrutto
		calcMonatsbrutto();

		// Wochenstunden ausgewählt
		if (checkStunden.isSelected()) {
			calcWochenStunden();
		}

		// Prozent ausgewählt
		if (checkProz.isSelected()) {
			calcProzErh();
		}

		// Vergleich ausgewählt
		if (checkDiff.isSelected()) {
			calcDiff();
		}
	}

	private void stufenDefinition() {
		// Definieren der gewählten Stufe
		stufe = cbGruppe.getSelectionModel().getSelectedItem();
		stufe = stufe + "/" + cbStufe.getSelectionModel().getSelectedItem();

		// Definieren der Vergleichs Stufe
		stufeV = cbDiffGruppe.getSelectionModel().getSelectedItem();
		stufeV = stufeV + "/" + cbDiffStufe.getSelectionModel().getSelectedItem();

	}

	private void calcMonatsbrutto() {

		Calc calcu = new Calc();

		jahresbrutto = getStufeValue(stufe);

		if (check14.isSelected()) {
			monatsbrutto = calcu.calc14(jahresbrutto);
			taErgebnis.setText("Ihre Auswahl -> Stufe " + stufe + "\n" + "Jahresbrutto KV: " + nf.format(jahresbrutto)
					+ "\n" + "Monatsbrutto KV bei 14 Gehältern: " + nf.format(monatsbrutto) + "\n"
					+ "Monatsbrutto inkl. Überzahlung: "
					+ nf.format(monatsbrutto + Double.valueOf(tfUeberz.getText().replaceAll(",", "."))));
		}
		if (!check14.isSelected()) {
			monatsbrutto = calcu.calc15(jahresbrutto);
			taErgebnis.setText("Ihre Auswahl -> Stufe " + stufe + "\n" + "Jahresbrutto KV: " + nf.format(jahresbrutto)
					+ "\n" + "Monatsbrutto KV bei 15 Gehältern: " + nf.format(monatsbrutto) + "\n"
					+ "Monatsbrutto inkl. Überzahlung: " + nf.format(monatsbrutto + Double.valueOf(tfUeberz.getText().replaceAll(",", "."))));
		}

	}

	private void calcProzErh() {

		Calc calcu = new Calc();
		double kvErh = 0.0;

		anzProz = Double.valueOf(tbAnpProz.getText().replaceAll(",", "."));

		if (!checkStunden.isSelected()) {
			kvErh = calcu.calcErhoehung(monatsbrutto, anzProz);
			double diff = kvErh - monatsbrutto;
			taErgebnis.setText(taErgebnis.getText() + "\n" + "Erhöhung bei " + anzProz + "% " + nf.format(kvErh)
					+ " dies sind um " + nf.format(diff) + " mehr" + "\n");
		} else {
			kvErh = calcu.calcErhoehung(monatsbruttoLim, anzProz);
			double diffLim = kvErh - monatsbruttoLim;
			taErgebnis.setText(taErgebnis.getText() + "\n" + "Erhöhung bei " + anzProz + "% " + nf.format(kvErh)
					+ " dies sind um " + nf.format(diffLim) + " mehr" + "\n");
		}

		if (Double.valueOf(tfUeberz.getText().replaceAll(",", ".")) > 0.0) {
			calcProzUeberz(kvErh);

		}

	}

	// Berechnung der prozentuellen Erhöhung bei Überbezahlung
	private void calcProzUeberz(double kvErh) {

		Calc calcu = new Calc();
		
		anzProz = Double.valueOf(tbAnpProz.getText().replaceAll(",", "."));
		System.out.println(monatsbrutto);

		double ueberzahlung = Double.valueOf(tfUeberz.getText().replaceAll(",", "."));
		double erhoehungUeberz = calcu.calcErhoehung(ueberzahlung, anzProz);
		double diff = erhoehungUeberz - ueberzahlung;
		double monatnachErh = monatsbrutto + (kvErh - monatsbrutto) + erhoehungUeberz;
		taErgebnis.setText(taErgebnis.getText() + "Erhöhung der Überzahlung " + anzProz + "% "
				+ nf.format(erhoehungUeberz) + " dies sind um " + nf.format(diff) + " Euro mehr" + "\n"
				+ "Monatsbrutto nach Erhöhung: " + nf.format(monatnachErh));
	}

	private void calcDiff() {

		Calc calcu = new Calc();

		jahresbruttoV = getStufeValue(stufeV);
		

		if (!checkStunden.isSelected()) {

			if (check14.isSelected()) {
				monatsbruttoV = calcu.calc14(jahresbruttoV);
			} else {
				monatsbruttoV = calcu.calc15(jahresbruttoV);
			}

			double diffJ = jahresbruttoV - jahresbrutto;
			double diffM = monatsbruttoV - monatsbrutto;

			taErgebnis.setText(taErgebnis.getText() + "\n" + "Ihre Auswahl -> Stufe Vergleich " + stufe + " - " + stufeV
					+ "\n" + "Jahresbrutto Vergleich: " + nf.format(jahresbruttoV) + " --> Jahresbrutto Differenz: "
					+ nf.format(diffJ) + "\n" + "Monatsbrutto Vergleich: " + nf.format(monatsbruttoV)
					+ " --> Monatsbrutto Differenz: " + nf.format(diffM) + "\n" + "inkl. Überzahlung: "
					+ nf.format(monatsbruttoV + Double.valueOf(tfUeberz.getText())) + "\n");
		}

		if (checkStunden.isSelected()) {

			jahresbrutto = calcu.calcStunden(Double.valueOf(tbAnzWochenStd.getText()), jahresbrutto);
			jahresbruttoV = calcu.calcStunden(Double.valueOf(tbAnzWochenStd.getText()), jahresbruttoV);

			if (check14.isSelected()) {
				monatsbruttoV = calcu.calc14(jahresbruttoV);
			} else {
				monatsbruttoV = calcu.calc15(jahresbruttoV);
			}

			double diffJ = jahresbruttoV - jahresbrutto;
			double diffM = monatsbruttoV - monatsbruttoLim;

			taErgebnis.setText(taErgebnis.getText() + "\n" + "Ihre Auswahl -> Stufe Vergleich " + stufe + " - " + stufeV
					+ "\n" + "Jahresbrutto auf Basis Vollzeit: " + nf.format(jahresbruttoV)
					+ " --> Jahresbrutto Differenz: " + nf.format(diffJ) + "\n" + "Monatsbrutto Vergleich: "
					+ nf.format(monatsbruttoV) + " --> Monatsbrutto Differenz: " + nf.format(diffM) + "\n"
					+ "inkl. Überzahlung: " + nf.format(monatsbruttoV + Double.valueOf(tfUeberz.getText().replaceAll(",", "."))) + "\n");
		}
	}

	private void calcWochenStunden() {

		Calc calcu = new Calc();

		anzWoStd = Double.valueOf(tbAnzWochenStd.getText());
		monatsbruttoLim = calcu.calcStunden(anzWoStd, monatsbrutto);
		taErgebnis.setText(taErgebnis.getText() + "\n" + "" + "Monatsbrutto bei " + anzWoStd + " Std. "
				+ nf.format(monatsbruttoLim) + "\n" + "inkl. Überzahlung: "
				+ nf.format(monatsbruttoLim + Double.valueOf(tfUeberz.getText().replaceAll(",", "."))));

	}

	private double getStufeValue(String stufe) {

		double jahresbrutto = 0.0;
		DbHelper db = new DbHelper();
		Calendar cal = Calendar.getInstance();
		
		try {
			db.openConn();
			jahresbrutto = db.getValue(stufe, cal.get(Calendar.YEAR));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return jahresbrutto;
	}

}
