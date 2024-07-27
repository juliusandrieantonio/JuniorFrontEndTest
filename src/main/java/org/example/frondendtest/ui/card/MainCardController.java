package org.example.frondendtest.ui.card;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.frondendtest.utils.constants.SingletonInstance;
import org.example.frondendtest.utils.models.RandomDataModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainCardController implements Initializable {
    public Label title;
    public ImageView more;
    public Label availability;
    public Label type;
    public Label participant;
    public Label price;
    public Label accessibility;
    public Label duration;
    public Label kidFriendly;
    public Label link;
    public Label key;
    public VBox moreOptions;
    public Label print;
    public Label dlCSV;
    public Label dlJSON;
    private boolean isVisible = false;
    private SingletonInstance singletonInstance;
    private ObjectMapper objectMapper = null;

    // setting the objects data
    public void setData(RandomDataModel randomDataModel) {
        title.setText(randomDataModel.getActivity());
        availability.setText(String.valueOf(randomDataModel.getAvailability()));
        type.setText(randomDataModel.getType());
        participant.setText(String.valueOf(randomDataModel.getParticipants()));
        price.setText(String.valueOf(randomDataModel.getPrice()));
        accessibility.setText(randomDataModel.getAccessibility());
        duration.setText(randomDataModel.getDuration());
        kidFriendly.setText(String.valueOf(randomDataModel.getKidFriendly()));
        link.setText((randomDataModel.getLink().isBlank() ? "NA": randomDataModel.getLink()));
        key.setText(randomDataModel.getKey());

        // clicking the toggle option
        // showing the more option
        more.addEventHandler(MouseEvent.MOUSE_PRESSED, _ -> {
            isVisible = !isVisible;
            moreOptions.setVisible(isVisible);
        });

        // for the console button event
        print.addEventHandler(MouseEvent.MOUSE_PRESSED, _ -> {
            // straight forward approach. Print the object to the consoel
            System.out.println(randomDataModel);
            isVisible = !isVisible;
            moreOptions.setVisible(false);
        });

        // for the download csv event
        dlCSV.addEventHandler(MouseEvent.MOUSE_PRESSED, _ -> {
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();

            JsonNode jsonTree = objectMapper.valueToTree(randomDataModel);

            jsonTree.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            CsvMapper csvMapper = new CsvMapper();

            String fileName = randomDataModel.getActivity() + ".csv";
            File csvFile = new File(fileName);
            int tempCounter = 0;
            while(csvFile.exists()) {
                tempCounter++;
                fileName = randomDataModel.getActivity() + "(" + tempCounter + ").csv";
                csvFile = new File(fileName);
            }

            try {
                csvMapper.writerFor(JsonNode.class)
                        .with(csvSchema)
                        .writeValue(csvFile, jsonTree);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("CSV file downloaded in: " + csvFile.getAbsoluteFile());
            isVisible = !isVisible;
            moreOptions.setVisible(false);
        });

        // for the download as json button event
        dlJSON.addEventHandler(MouseEvent.MOUSE_PRESSED, _ -> {
            String fileName =  randomDataModel.getActivity() + ".json";
            File jsonFile = new File(fileName);
            int tempCounter = 0;
            while(jsonFile.exists()) {
                tempCounter++;
                fileName = randomDataModel.getActivity() + "(" + tempCounter + ").json";
                jsonFile = new File(fileName);
            }

            try {
                objectMapper.writeValue(jsonFile, randomDataModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("JSON file downloaded in: " + jsonFile.getAbsoluteFile());
            isVisible = !isVisible;
            moreOptions.setVisible(false);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moreOptions.managedProperty().bind(moreOptions.visibleProperty());

        // create/get the instance of the mapper
        singletonInstance = SingletonInstance.getInstance();
        objectMapper = singletonInstance.getObjectMapper();
    }
}
