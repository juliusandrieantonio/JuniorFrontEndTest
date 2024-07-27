package org.example.frondendtest.ui.Main;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.example.frondendtest.ui.card.CardController;
import org.example.frondendtest.utils.models.MainModel;
import org.example.frondendtest.utils.models.RandomDataModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TextField searchTF;
    public GridPane dataHolder;
    public ProgressIndicator progressIndicator;

    public void changeView(ArrayList<RandomDataModel> temp) {
        dataHolder.getChildren().clear();
        dataHolder.getRowConstraints().clear();
        // for setting the rows constraint and fill the cell's height
        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(VBox.USE_COMPUTED_SIZE);
            rowConstraints.setMaxHeight(VBox.USE_COMPUTED_SIZE);
            dataHolder.getRowConstraints().add(i, rowConstraints);
        }
        int size = (temp.size() / 3) + (temp.size() % 3);
        for (int column = 0; column < size; column++) {
            for (int row = 0; row < 3; row++) {
                if ((column * 3) + row < temp.size()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/frondendtest/FXML/card.fxml"));
                    try {
                        Parent parent = loader.load();
                        CardController encoderCardController = loader.getController();
                        encoderCardController.setData(temp.get((column * 3) + row));
                        dataHolder.add(parent, row, column);

                        GridPane.setValignment(parent, VPos.TOP);
                        GridPane.setVgrow(parent, Priority.ALWAYS);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    break;
                }
            }
        }
        progressIndicator.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainModel mainModel = new MainModel();
        try {
            changeView(mainModel.getData());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}