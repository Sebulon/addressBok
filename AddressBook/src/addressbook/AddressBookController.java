
package addressbook;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat215.lab1.Presenter;


public class AddressBookController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private Button new_button;
    @FXML private Button delete_button;
    @FXML private TextField firstName_input;
    @FXML private TextField lastName_input;
    @FXML private TextField phone_input;
    @FXML private TextField email_input;
    @FXML private TextField address_input;
    @FXML private TextField postCode_input;
    @FXML private TextField city_input;
    @FXML private ListView contact_list;

    private Presenter presenter;



    private class TextFieldListener implements ChangeListener<Boolean>{

        private TextField textField;

        public TextFieldListener(TextField textField){
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            if (t1){
                presenter.textFieldFocusGained(textField);
            }else{
                presenter.textFieldFocusLost(textField);
            }
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        presenter = new Presenter(contact_list, firstName_input, lastName_input, phone_input,
                email_input, address_input, postCode_input, city_input);
        presenter.init();

        contact_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                presenter.contactsListChanged();
            }
        });

        firstName_input.focusedProperty().addListener(new TextFieldListener(firstName_input));
        lastName_input.focusedProperty().addListener(new TextFieldListener(lastName_input));
        phone_input.focusedProperty().addListener(new TextFieldListener(phone_input));
        email_input.focusedProperty().addListener(new TextFieldListener(email_input));
        address_input.focusedProperty().addListener(new TextFieldListener(address_input));
        postCode_input.focusedProperty().addListener(new TextFieldListener(postCode_input));
        city_input.focusedProperty().addListener(new TextFieldListener(city_input));
    }

    @FXML
    protected void newButtonActionPerformed (ActionEvent event){
        presenter.newContact();
    }

    @FXML
    protected void deleteButtonActionPerformed (ActionEvent event){
        presenter.removeCurrentContact();
    }
    
    @FXML 
    protected void openAboutActionPerformed(ActionEvent event) throws IOException{
    
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("addressbook/resources/AddressBook");
        Parent root = FXMLLoader.load(getClass().getResource("address_book_about.fxml"), bundle);
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(root));
        aboutStage.setTitle(bundle.getString("about.title.text"));
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.showAndWait();
    }
    
    @FXML 
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException{
        
        Stage addressBookStage = (Stage) menuBar.getScene().getWindow();
        addressBookStage.hide();
    }    
}
