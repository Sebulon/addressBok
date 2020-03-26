
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat215.lab1.Presenter;

public class AddressBookController implements Initializable {
    
    @FXML private MenuBar menuBar;
    @FXML private Button newButton;
    @FXML private Button deleteButton;
    @FXML private MenuItem newContactMenu;
    @FXML private MenuItem deleteContactMenu;
    @FXML private ListView contactList;
    @FXML private TextField firstNameText;
    @FXML private TextField lastNameText;
    @FXML private TextField phoneText;
    @FXML private TextField emailText;
    @FXML private TextField addressText;
    @FXML private TextField postcodeText;
    @FXML private TextField cityText;
    private Presenter presenter;

    private class TextFieldListener implements ChangeListener<Boolean>{

        private TextField textField;

        public TextFieldListener(TextField textField){
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                presenter.textFieldFocusGained(textField);

            }
            else{
                presenter.textFieldFocusLost(textField);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        presenter = new Presenter(
                contactList,
                firstNameText,
                lastNameText,
                phoneText,
                emailText,
                addressText,
                postcodeText,
                cityText);

        presenter.init();

        contactList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                presenter.contactsListChanged();
            }

        });

        firstNameText.focusedProperty().addListener(new TextFieldListener(firstNameText));
        lastNameText.focusedProperty().addListener(new TextFieldListener(lastNameText));
        phoneText.focusedProperty().addListener(new TextFieldListener(phoneText));
        emailText.focusedProperty().addListener(new TextFieldListener(emailText));
        addressText.focusedProperty().addListener(new TextFieldListener(addressText));
        postcodeText.focusedProperty().addListener(new TextFieldListener(postcodeText));
        cityText.focusedProperty().addListener(new TextFieldListener(cityText));
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

    @FXML
    protected void newButtonActionPerformed (ActionEvent event){
        presenter.newContact();
    }

    @FXML
    protected void deleteButtonActionPerformed (ActionEvent event){
        presenter.removeCurrentContact();
    }

    @FXML
    protected void textFieldActionPerformed (ActionEvent event){
        presenter.textFieldActionPerformed(event);
    }
}
