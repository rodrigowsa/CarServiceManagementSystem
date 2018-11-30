package tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tables.VehiclesTable;
import tables.WorkordersTable;

import java.sql.*;

/**
 *
 * Statistics is a tab that contains the GUI for viewing service statistics
 * It is a singleton class
 *
 * @author James DiNovo
 * @date 29.10.2018
 * @version 1.0
 *
 */
public class StatisticsTab extends Tab {

	/**
	 * @Author Dorian Harusha
	 * @Date 11.25.2018
	 */

	private static StatisticsTab tab;
	public static BorderPane pane;
	public static PieChart chart = new PieChart();

	static NumberAxis xAxis = new NumberAxis();
	static CategoryAxis yAxis = new CategoryAxis();
	static XYChart.Series series1 = new XYChart.Series();
	private static BarChart<String, Number> bchart = new BarChart<>(yAxis, xAxis);

	/**
	 *
	 * Statistics private constructor
	 *
	 * @author James DiNovo
	 * @date 29.10.2018
	 * @version 1.0
	 *
	 */
	private StatisticsTab() {
		/**
		 * @Author Dorian Harusha
		 * @Date 11.28.2018
		 */
		this.setText("Statistics");

		final Label prompt = new Label();
		prompt.setText("Choose a chart: ");
		prompt.setPadding(new Insets(5, 5, 0, 0));

		final ComboBox chartComboBox = new ComboBox();
		chartComboBox.getItems().addAll(
				"Pie Chart",
				"Bar Graph"
		);
		chartComboBox.setValue("Pie Chart");


		chartComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				if (chartComboBox.getValue() == "Pie Chart") {
					pane.setCenter(generateChart());
					bchart.setVisible(false);
					chart.setVisible(true);
				} else if (chartComboBox.getValue() == "Bar Graph") {
					pane.setCenter(generateBarChart());
					chart.setVisible(false);
					bchart.setVisible(true);
				}
			}
		});

		// Fixes duplication issue *read below for more info*
		bchart.getData().addAll(series1);

		HBox hBox = new HBox(prompt, chartComboBox);
		hBox.setPadding(new Insets(15, 12, 15, 12));

		pane = new BorderPane();
		pane.setTop(hBox);
		pane.setCenter(generateChart());
		this.setContent(pane);
		this.setOnClosed(e-> {
			tab = null;
		});
	}
	
	/**
	 *
	 * Statistics getInstance method
	 * 
	 * @author James DiNovo
	 * @date 29.10.2018
	 * @version 1.0
	 * @return StatisticsTab()
	 *
	 */
	public static StatisticsTab getInstance() {
		if(tab == null) {
			tab = new StatisticsTab();
		}
		return tab;
	}

	/**
	 * @Author Dorian Harusha
	 * @Date 11.25.2018
	 *
	 * Pie chart to display open workorders vs closed workorders percentage
	 *
	 * @return PieChart
	 */
	public static PieChart generateChart() {
		WorkordersTable table = new WorkordersTable();

		int closed = table.getClosedWorkordersCount();
		int open = table.getOpenWorkordersCount();

		chart.setTitle("Service Charts Statistics");
		chart.setLabelsVisible(true);
		chart.setLabelLineLength(10);

		ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
				new PieChart.Data("Open Workorders:" + open, open),
				new PieChart.Data("Closed Workorders:" + closed, closed)
		);
		chart.setData(data);

		chart.setLegendSide(Side.LEFT);
		return chart;
	}

	/**
	 * @Author Dorian Harusha
	 * @Date 11.29.2018
	 * @return BarChart
	 */
	public static BarChart generateBarChart() {
		VehiclesTable vehiclesTable = new VehiclesTable();

		bchart.setTitle("Vehicle Brands Worked On");
		xAxis.setLabel("# Worked on");
		xAxis.setTickLabelRotation(10);
		yAxis.setTickLabelRotation(10);
		yAxis.setLabel("Brands");

		series1.setName("2018");

		series1.getData().add(new XYChart.Data("BMW", vehiclesTable.getVehiclesWorkedOnCount("BMW")));
		series1.getData().add(new XYChart.Data("TOYOTA"  , vehiclesTable.getVehiclesWorkedOnCount("TOYOTA")));
		series1.getData().add(new XYChart.Data("NISSAN"  , vehiclesTable.getVehiclesWorkedOnCount("NISSAN")));
		series1.getData().add(new XYChart.Data("CHRYSLER", vehiclesTable.getVehiclesWorkedOnCount("CHRYSLER")));
		series1.getData().add(new XYChart.Data("JEEP", vehiclesTable.getVehiclesWorkedOnCount("JEEP")));
		series1.getData().add(new XYChart.Data("AUDI"  , vehiclesTable.getVehiclesWorkedOnCount("AUDI")));
		series1.getData().add(new XYChart.Data("VOLKSWAGEN"  , vehiclesTable.getVehiclesWorkedOnCount("VOLKSWAGEN")));
		series1.getData().add(new XYChart.Data("PORCHE", vehiclesTable.getVehiclesWorkedOnCount("PORCHE")));
		series1.getData().add(new XYChart.Data("SUBARU", vehiclesTable.getVehiclesWorkedOnCount("SUBARU")));
		series1.getData().add(new XYChart.Data("DODGE", vehiclesTable.getVehiclesWorkedOnCount("DODGE")));
		series1.getData().add(new XYChart.Data("FORD"  , vehiclesTable.getVehiclesWorkedOnCount("FORD")));
		series1.getData().add(new XYChart.Data("HONDA"  , vehiclesTable.getVehiclesWorkedOnCount("HONDA")));
		series1.getData().add(new XYChart.Data("CHEVROLET", vehiclesTable.getVehiclesWorkedOnCount("CHEVROLET")));

		return bchart;
	}
}

