package dad.javafx.calcuCompleja;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	private TextField PrimerReal;
	private TextField primerImagin;
	private TextField segundoReal;
	private TextField segundoImagin;
	private ComboBox<String> Operador;
	private TextField resultadoReal;
	private TextField resultadoImagin;

	private StringProperty operator = new SimpleStringProperty();
	
	private Complejo respuesta = new Complejo();
	private Complejo primercom = new Complejo();
	private Complejo secondcom = new Complejo();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		PrimerReal = new TextField();
		PrimerReal.setText("0");
		PrimerReal.setMaxWidth(50);
		
		segundoReal = new TextField();
		segundoReal.setText("0");
		segundoReal.setMaxWidth(50);
		
		primerImagin = new TextField();
		primerImagin.setText("0");
		primerImagin.setMaxWidth(50);
		
		segundoImagin = new TextField();
		segundoImagin.setText("0");
		segundoImagin.setMaxWidth(50);
		
		Operador = new ComboBox<String>();
		Operador.getItems().addAll("+","-","*","/");
		Operador.setMaxWidth(60);
		
		resultadoReal = new TextField();
		resultadoReal.setText("0");
		resultadoReal.setMaxWidth(50);
		resultadoReal.setDisable(true);
		
		
		resultadoImagin = new TextField();
		resultadoImagin.setText("0");
		resultadoImagin.setMaxWidth(50);
		resultadoImagin.setDisable(true);
		
		
		
		
		
		Separator separador = new Separator();
	
		
		HBox primerComplejo = new HBox(5,PrimerReal,new Label("+"),primerImagin,new Label("i"));
		HBox segundoComplejo = new HBox(5,segundoReal,new Label("+"),segundoImagin,new Label("i"));			   
		HBox resultComplejo = new HBox(5,resultadoReal,new Label("+"),resultadoImagin,new Label("i"));
		
		VBox operation = new VBox(5,primerComplejo,segundoComplejo,separador,resultComplejo);
		operation.setAlignment(Pos.CENTER);
		operation.setFillWidth(false);
		
		VBox allSentences = new VBox(Operador);
		allSentences.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		HBox root = new HBox(5,allSentences,operation);
		root.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		
		Scene scene = new Scene(root,320,200);

		primaryStage.setTitle("Calculadora compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//bindeos
		
		Bindings.bindBidirectional(PrimerReal.textProperty(),primercom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(primerImagin.textProperty(),primercom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(segundoReal.textProperty(),secondcom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(segundoImagin.textProperty(),secondcom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(resultadoReal.textProperty(), respuesta.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultadoImagin.textProperty(), respuesta.imaginarioProperty(), new NumberStringConverter());
		
		operator.bind(Operador.getSelectionModel().selectedItemProperty());
		
		//listener
		
		operator.addListener((o, ov, nv) -> onOperadorChanged(nv));
		
		Operador.getSelectionModel().selectFirst();
	
	}

	private void onOperadorChanged(String nv) {
		
		switch(nv) {
		case "+":
			respuesta.realProperty().bind(primercom.realProperty().add(secondcom.realProperty()));
			respuesta.imaginarioProperty().bind(primercom.imaginarioProperty().add(secondcom.imaginarioProperty()));
			break;
		case "-":
			respuesta.realProperty().bind(primercom.realProperty().subtract((secondcom.realProperty())));
			respuesta.imaginarioProperty().bind(primercom.imaginarioProperty().subtract((secondcom.imaginarioProperty())));	
			break;
		case "*": 
			respuesta.realProperty().bind(
					primercom.realProperty().multiply(secondcom.realProperty()).subtract(primercom.imaginarioProperty().multiply(secondcom.imaginarioProperty()))
					);
			respuesta.imaginarioProperty().bind(
					primercom.realProperty().multiply(secondcom.imaginarioProperty()).add(primercom.imaginarioProperty().multiply(secondcom.realProperty()))
					);
			break;
		case "/":
			respuesta.realProperty().bind(
					(primercom.realProperty().multiply(secondcom.realProperty()).add(primercom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					);
			respuesta.imaginarioProperty().bind(
					primercom.imaginarioProperty().multiply(secondcom.realProperty()).subtract(primercom.realProperty().multiply(secondcom.imaginarioProperty()))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					);
			break;
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}