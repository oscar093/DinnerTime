package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		taList.setEditable(false);
	}
	
	@FXML
	private void search(){
		if(rbTitle.isSelected()){
			client.sendToServer("titleSearch" + tfSearch.getText());
		}
		if(rbCountry.isSelected()){
			client.sendToServer("countrySearch" + tfSearch.getText());
		}
		if(rbAuthor.isSelected()){
			client.sendToServer("authorSearch" + tfSearch.getText());
		}	
	}
	
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
	
	@FXML
	private void back(){
		try {
			main.showClientView(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void setClient(Client client){
		this.client = client;
		client.setSearchViewController(this);
	}
}