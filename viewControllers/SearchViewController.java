package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/** 
 * Controller class for the search view.
 * 
 * @author Olof
 */

public class SearchViewController implements Initializable  {
	private static Stage primaryStage;
	@FXML
	private RadioButton rbTitle, rbCountry, rbAuthor;
	@FXML
	private TextField tfSearch;
	@FXML
	private TextArea taList;
	@FXML
	private Button btnBack;
	@FXML
	private Main main;
	private Client client;
	
	/**
	 * Adds all found recipes to this textarea.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		taList.setEditable(false);	//textarean ska vara read-only
	}
	
	/**
	 * Different Strings are sent to the client/server depending on what
	 * the user has chosen to search for.
	 */
	@FXML
	private void search(){
		if(rbTitle.isSelected()){
			client.sendToServer("titleSearch_" + tfSearch.getText());
		}
		if(rbCountry.isSelected()){
			client.sendToServer("countrySearch_" + tfSearch.getText().toLowerCase());
		}
		if(rbAuthor.isSelected()){
			client.sendToServer("authorSearch_" + tfSearch.getText());
		}	
	}
	
	/**
	 * Only one radioButton can be chosen at a time.
	 */
	@FXML
	private void setSelect(){
		if(rbTitle.isSelected()){
			rbCountry.setSelected(false);
			rbAuthor.setSelected(false);
		}
		if(rbCountry.isSelected()){
			rbTitle.setSelected(false);
			rbAuthor.setSelected(false);
		}
		if(rbAuthor.isSelected()){
			rbCountry.setSelected(false);
			rbTitle.setSelected(false);
		}
	}
	
	/**
	 * The user is taken to the ClientView if the button "back" is pressed.
	 */
	
	@FXML
	private void back(){
		try {
			main.showClientView(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the result of the search
	 * the array is split up and listed in taList.
	 * 
	 * @param response : the result of the search
	 */
	public void showResponse(String[] response){
		String list = "";
		for(String s : response){
			String[] split = s.split("_");
			list += split[0].toUpperCase() + ":\n";
			for(int i = 1; i < split.length; i++){
				list += split[i]+ "\n";
			}
		}
		taList.setText(list);
	}

	/**
	 * Set which client to use.
	 * @param client
	 */
	public void setClient(Client client){
		this.client = client;
		client.setSearchViewController(this);
	}
}