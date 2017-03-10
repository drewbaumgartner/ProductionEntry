package production.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.*;
import production.util.LocalDateAdapter;

public class ProductionEntry {
	
	private final double RATE_PER_SQ_FT = 1000;
	private final ObjectProperty<LocalDate> date;
	private final StringProperty technician;
	private final DoubleProperty hoursWorked;
	private final StringProperty vehicle;
	private final IntegerProperty milesOut;
	private final IntegerProperty milesIn;
	private final DoubleProperty totalProduction;
	private final StringProperty productUsed;
	private final StringProperty productionType;
	private final DoubleProperty materialStart;
	private final DoubleProperty materialEnd;
	private final DoubleProperty squareFeetTreated;
	private final DoubleProperty rate;
	private final DoubleProperty materialNeeded;
	private final DoubleProperty materialUsed;
	private final DoubleProperty materialVariance;
	private final IntegerProperty milesDriven;
	private final DoubleProperty productionPerHour;
	
	public ProductionEntry()
	{
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.technician = new SimpleStringProperty("");
		this.hoursWorked = new SimpleDoubleProperty(0);
		this.vehicle = new SimpleStringProperty("");
		this.milesOut = new SimpleIntegerProperty(0);
		this.milesIn = new SimpleIntegerProperty(0);
		this.totalProduction = new SimpleDoubleProperty(0);
		this.productUsed = new SimpleStringProperty("");
		this.productionType = new SimpleStringProperty("");
		this.materialStart = new SimpleDoubleProperty(0);
		this.materialEnd = new SimpleDoubleProperty(0);
		this.squareFeetTreated = new SimpleDoubleProperty(0);
		this.rate = new SimpleDoubleProperty(0);
		this.materialNeeded = new SimpleDoubleProperty(0);
		this.materialUsed = new SimpleDoubleProperty(0);
		this.materialVariance = new SimpleDoubleProperty(0);
		this.milesDriven = new SimpleIntegerProperty(0);
		this.productionPerHour = new SimpleDoubleProperty(0);
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {return date.get();}
	public void setDate(LocalDate date) {this.date.set(date);}
	public ObjectProperty<LocalDate> dateProperty() {return date;}
	
	public String getTechnician() {return technician.get();}
	public void setTechnician(String technician) {this.technician.set(technician);}
	public StringProperty technicianProperty() {return technician;}
	
	public double getHoursWorked() {return hoursWorked.get();}
	public void setHoursWorked(double hours) {this.hoursWorked.set(hours);}
	public DoubleProperty hoursWorkedProperty() {return hoursWorked;}
	
	public String getVehicle() {return vehicle.get();}
	public void setVehicle(String vehicle) {this.vehicle.set(vehicle);}
	public StringProperty vehicleProperty() {return vehicle;}
	
	public int getMilesOut() {return milesOut.get();}
	public void setMilesOut(int miles) {this.milesOut.set(miles);}
	public IntegerProperty milesOutProperty() {return milesOut;}
	
	public int getMilesIn() {return milesIn.get();}
	public void setMilesIn(int miles) {this.milesIn.set(miles);}
	public IntegerProperty milesInProperty() {return milesIn;}
	
	public double getTotalProduction() {return totalProduction.get();}
	public void setTotalProduction(double production) {this.totalProduction.set(production);}
	public DoubleProperty totalProductionProperty() {return totalProduction;}
	
	public String getProductUsed() {return productUsed.get();}
	public void setProductUsed(String product) {this.productUsed.set(product);}
	public StringProperty productUsedProperty() {return productUsed;}
	
	public String getProductionType() {return productionType.get();}
	public void setProductionType(String productionType) {this.productionType.set(productionType);}
	public StringProperty productionTypeProperty() {return productionType;}
	
	public double getMaterialStart() {return materialStart.get();}
	public void setMaterialStart(double material) {this.materialStart.set(material);}
	public DoubleProperty materialStartPropertty() {return materialStart;}
	
	public double getMaterialEnd() {return materialEnd.get();}
	public void setMaterialEnd(double material) {this.materialEnd.set(material);}
	public DoubleProperty materialEndPropertty() {return materialEnd;}
	
	public double getSquareFeetTreated() {return squareFeetTreated.get();}
	public void setSquareFeetTreated(double squareFeet) {this.squareFeetTreated.set(squareFeet);}
	public DoubleProperty squareFeetTreatedProperty() {return squareFeetTreated;}
	
	public double getRate() {return rate.get();}
	public void setRate(double rate) {this.rate.set(rate);}
	public DoubleProperty rateProperty() {return rate;}
	
	public double getMaterialNeeded() {return materialNeeded.get();}
	public void calculateMaterialNeeded()
	{
		double materialNeeded = this.getSquareFeetTreated() * (this.getRate() / RATE_PER_SQ_FT);
		this.materialNeeded.set(materialNeeded);
	}
	public DoubleProperty materialNeededProerpty() {return materialNeeded;}
	
	public double getMaterialUsed() {return materialUsed.get();}
	public void calculateMaterialUsed()
	{
		double materialUsed = this.getMaterialStart() - this.getMaterialEnd();
		this.materialUsed.set(materialUsed);
	}
	public DoubleProperty materialUsedProerpty() {return materialUsed;}
	
	public double getMaterialVariance() {return materialVariance.get();}
	public void calculateMaterialVariance()
	{
		double materialVariance = this.getMaterialUsed() / this.getMaterialNeeded();
		this.materialVariance.set(materialVariance);
	}
	public DoubleProperty materialVarianceProerpty() {return materialVariance;}
	
	public int getMilesDriven() {return milesDriven.get();}
	public void calculateMilesDriven()
	{
		int miles = this.getMilesIn() - this.getMilesOut();
		this.milesDriven.set(miles);
	}
	public IntegerProperty milesDrivenProperty() {return milesDriven;}
	
	public double getProductionPerHour() {return productionPerHour.get();}
	public void calculateProductionPerHour()
	{
		double result = this.getTotalProduction() / this.getHoursWorked();
		
		this.productionPerHour.set(result);
	}
	public DoubleProperty productionPerHourProperty() {return productionPerHour;}
}
