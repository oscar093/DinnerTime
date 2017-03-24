package demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ClientGUI{
	private JFrame newFrame = new JFrame("DinnerTimeDemo");
	private JPanel btnPanel = new JPanel();
	private JTextArea ta = new JTextArea();	
	private JButton readRecipe = new JButton("Läs in recept");
	private JButton writeRecipe = new JButton("Skriv in recept");
	private JTextField tfIngredent = new JTextField("ingridiens");
	private JButton writeIngident = new JButton("Lägg till ingridiens");
	private JTextField tfCook = new JTextField("Receptmakare");
	private JTextField tfCookTime = new JTextField("Tillagningstid");
	
	private JTextField recipeName = new JTextField();
	private JTextField tfSearch = new JTextField();
	private JButton btnSearch = new JButton("Sök");
	
	private ButtonListener bl = new ButtonListener();
	private Recipe recipe = new Recipe();
	private DemoDatabase ddb = new DemoDatabase();
	
	
	public ClientGUI(){ 
        
        ta.setVisible(true);
        ta.setEditable(false);
        ta.setColumns(40);
        ta.setRows(30);
                
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setViewportView(ta);
        
        tfIngredent.setPreferredSize(new Dimension(10,50));
        btnPanel.setLayout(new GridLayout(2,2));
       
//        btnPanel.add(readRecipe);
//        btnPanel.add(writeRecipe);
//        btnPanel.add(tfIngredent);
//        btnPanel.add(writeIngident);
//        btnPanel.add(tfCook);
//        btnPanel.add(tfCookTime);
        btnPanel.add(tfSearch);
        btnPanel.add(btnSearch);
        
        readRecipe.addActionListener(bl);
        this.writeRecipe.addActionListener(bl);
        this.writeIngident.addActionListener(bl);
        btnSearch.addActionListener(bl);
        
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setVisible(true);
        newFrame.setLayout(new FlowLayout());
        newFrame.add(scroll);
        newFrame.add(btnPanel);
        newFrame.pack();
        
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == readRecipe) {
				recipe.setTillag(Integer.parseInt(tfCookTime.getText()));
				recipe.setName(tfCook.getText());
				ta.append(recipe.toString());
			} else if (e.getSource() == writeRecipe) {
				ta.append("skriv Recept\n");
			} else if (e.getSource() == writeIngident) {
				recipe.addIngredient(tfIngredent.getText());
			} else if (e.getSource() == btnSearch) {

				Recipe[] result = ddb.searchRecipeByCountry(tfSearch.getText());
				if (result.length != 0) {
					for(Recipe r : result){
						ta.append(r.toString());
					}
					
				} else {
					ta.append("Recept inte funnet.. \n");
				}
			}

		}

	}
	
	public static void main(String[] args){
		ClientGUI cg = new ClientGUI();
	}
	
	
}
