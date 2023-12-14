package gui.Windows;

import gui.App;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.MazeState;

public class AskName implements EventHandler{

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;


    public static void AskName(){

        App.menu.setHeight(500);
        App.menu.setWidth(500);
        App.menu.setTitle("Name");

        if(!lancer){
            //La barre qui permet d'ajouter un nom

            GridPane gridPane = new GridPane();
            TextField txtName = new TextField("Random");
            txtName.setPromptText("Nickname");
            txtName.setFocusTraversable(false);
            gridPane.add(txtName,1,0,1,1);

            gridPane.setTranslateX(175);
            gridPane.setTranslateY(150);
            gridPane.setVgap(8);
            gridPane.setHgap(10);
            txtName.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        MazeState.nickname = txtName.getText();
                                        Jeu.Game();
                                    }
                                }
            );
            parent.getChildren().add(gridPane);
        }



        App.menu.setScene(scene);
        App.menu.show();
        lancer = true;
    }


    @Override
    public void handle(Event event) {
        AskName();
    }
}
