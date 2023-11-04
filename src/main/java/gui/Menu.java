package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu implements EventHandler<ActionEvent> {

        public static void affichageMenu()  {
        //Initialisation du nouvel ecran
        VBox parent = new VBox();
        Stage ecran = new Stage();
        Label lab = new Label();
        Scene scene = new Scene(lab);
        ecran.setHeight(500);
        ecran.setWidth(500);
        ecran.setScene(scene);
        parent.setStyle("-fx-background-color: #000000");

        // Ajouter un nom a la page
        ecran.setTitle("Menu");
        // Ajouter une icone a la page`
        Image icon = new Image("pacman.png");
        ecran.getIcons().add(icon);
        // Ne plus pouvoir aggrandir/retrecir la page
        ecran.setResizable(false);


        //affichage du mesage Menu
        Text go = new Text();
        go.setText("Menu");
        go.setTranslateX(190);
        go.setTranslateY(40);
        go.setFont(Font.font(50));
        go.setFill(Color.YELLOW);
        parent.getChildren().add(go);

        //bouton pour jouer
        Button bouton = new Button(" Play "); //Le nom du bouton
        bouton.setTranslateX(210);//Les coordonnées du bouton
        bouton.setTranslateY(150);
        bouton.setFont(Font.font(20));
        bouton.setOnAction(new Jeu());
        parent.getChildren().add(bouton);

        //bouton pour les modes
        Button menubouton = new Button("Mode");
        menubouton.setTranslateX(210);
        menubouton.setTranslateY(170);
        menubouton.setFont(Font.font(20));
        //menubouton.setOnAction();
        parent.getChildren().add(menubouton);

        //bouton pour quitter tout lle programme
        Button exitbouton = new Button(" Exit  ");
        exitbouton.setTranslateX(210);
        exitbouton.setTranslateY(190);
        exitbouton.setFont(Font.font(20));
        exitbouton.setOnAction(new ExitButon());
        parent.getChildren().add(exitbouton);

        ecran.setScene(new Scene(parent));
        ecran.show();

    }


        @Override
        public void handle(ActionEvent event) {
                affichageMenu();
        }
}
