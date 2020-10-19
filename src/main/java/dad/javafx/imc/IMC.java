package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class IMC extends Application {
	
	
	private Label pesoLabel;
	private TextField pesoText;
	private Label kgLabel;
	
	private Label alturaLabel;
	private TextField alturaText;
	private Label cmLabel;
	
	private Label imcLabel;
	
	private Label resLabel;

	
	
	
	
	//PROPERTIES
	private StringProperty pesoStringProperty = new SimpleStringProperty();
	private DoubleProperty pesoProperty = new SimpleDoubleProperty();
	
	private StringProperty alturaStringProperty = new SimpleStringProperty();
	private DoubleProperty alturaProperty = new SimpleDoubleProperty();
	
	private StringProperty imcStringProperty = new SimpleStringProperty();
	private DoubleProperty imcProperty = new SimpleDoubleProperty();
	
	private StringProperty resProperty= new SimpleStringProperty();
	

	
	



	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 pesoLabel = new Label("Peso:");
		 alturaLabel = new Label("Altura:");
		 kgLabel = new Label("kg");
		 cmLabel = new Label("cm");
		
		 imcLabel = new Label("IMC: (peso * altura^ 2)");
		 resLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		
		pesoText = new TextField();
		pesoText.setPrefColumnCount(3);
		pesoText.setMaxWidth(100);
		pesoText.setAlignment(Pos.CENTER);
		
		 alturaText = new TextField();
		alturaText.setPrefColumnCount(3);
		alturaText.setMaxWidth(100);
		alturaText.setAlignment(Pos.CENTER);		
		
		// BINDEOS
		
		pesoStringProperty.bindBidirectional(pesoText.textProperty());
		Bindings.bindBidirectional(pesoStringProperty, pesoProperty, new NumberStringConverter());
		pesoProperty.addListener((o, ov, nv) -> onCalculoIndice());
		
		alturaStringProperty.bindBidirectional(alturaText.textProperty());
		Bindings.bindBidirectional(alturaStringProperty, alturaProperty, new NumberStringConverter());
		alturaProperty.addListener((o, ov, nv) -> onCalculoIndice());
		
		imcStringProperty.bindBidirectional(imcLabel.textProperty());
		resProperty.bindBidirectional(resLabel.textProperty());
		imcProperty.addListener((o, ov, nv) -> onCalculoRes());
	
		//CONTENEDORES Y ESCENA
		HBox pesoBox = new HBox();
		pesoBox.setSpacing(5);
		pesoBox.setAlignment(Pos.CENTER);
		pesoBox.getChildren().addAll(pesoLabel, pesoText, kgLabel);
		
		HBox alturaBox = new HBox();
		alturaBox.setSpacing(5);
		alturaBox.setAlignment(Pos.CENTER);
		alturaBox.getChildren().addAll(alturaLabel, alturaText, cmLabel);
		
		HBox imcBox = new HBox();
		imcBox.setSpacing(1);
		imcBox.setAlignment(Pos.CENTER);
		
		
		
		VBox rootBox= new VBox(5,pesoBox,alturaBox,imcLabel,resLabel);
		rootBox.setAlignment(Pos.CENTER);
		
		Scene scene= new Scene(rootBox,300,200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("IMC");
		primaryStage.show();
		

		
		
		
		
		

	}
	
	//METODOS DE LOS LISTENERS 
	
	public void onCalculoRes() {
	
		
		if (imcProperty.get() == 0) {
			imcStringProperty.set("IMC: (peso * altura^ 2)");
			resProperty.set("Bajo peso | Normal | Sobrepeso | Obeso");
		} else {
			imcStringProperty.set("IMC: " + imcProperty.get());
			if (imcProperty.get() < 18.5)
				resProperty.set("Bajo peso");
			else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25.0 )
				resProperty.set("Normal");
			else if (imcProperty.get() >= 25.0 && imcProperty.get() < 30.0)
				resProperty.set("Sobrepeso");
			else
				resProperty.set("Obeso");
		}
	}
	
	private void onCalculoIndice() {
		if ((pesoProperty.get() == 0) || (alturaProperty.get() == 0))
			imcProperty.set(0);
		else {
	
			Double imc = (pesoProperty.get() / (Math.pow(alturaProperty.get(), 2)));
			imcProperty.set(imc*10000);

	}
	}
	


	public static void main(String[] args) {
		launch(args);
	}

}