import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.math.BigDecimal;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NonScienctificCalulator extends Application {
    private Block block;
    private Pane root;  
    private TextField numberBox ;
    private Text enterNumber;
    private boolean check = false;
    private boolean check2 = false;
    private boolean check3 = false;
	ArrayList<Block> blocklist = new ArrayList<Block>(){};
	private double storedNum=0;
//	private long int storedNum2=0;
	private char storedOper = '@';

//	@Override
//	public void start(Stage primaryStage)throws Exception{
//		numberBox = new TextField();
//		VBox bigBox = new VBox(10,numberBox);
//		root.getChildren().add(bigBox);
//		Scene scene = new Scene(bigBox,300,200);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Calculator");
//		primaryStage.show();	
//	}
//	public static void main(String[] args) {
//		Application.launch(args);
//	}
	
	   @Override
	    public void start(Stage primaryStage) throws Exception {
	        root = new Pane();
	        int maxLength = 10;
	        numberBox = new TextField();
	        numberBox.setAlignment(Pos.CENTER_RIGHT);
	        numberBox.setFont(Font.font("Arial", 40));
            numberBox.setPrefHeight(62.5);
            numberBox.setPrefWidth(260);

	        
	        String[] arrayText = {"AC", "+/-", "%", "/", "x" , "-", "+"};
	        VBox bigBox = new VBox(10, numberBox);
	        VBox column = new VBox(2);
		     for (int x = 0 ; x <= 3 ; x++) {
		    	 HBox row = new HBox(2);
		    	 for (int y = 10 ; y <= 13 ; y++) {
		             if(x > 0 && y != 13) {
		    	         block = new Block(62.5,62.5,y-(3*x)+"");}
		             else if( x == 0 ) {
		            	 // top row for operator
		            	 block = new Block(62.5,62.5,arrayText[y-10]);
		             }
		             else if( y == 13) {
		                // last colum for operators
		            	 block = new Block(62.5,62.5,arrayText[x+3]);
		             }          
		    	     eachBlockEventHandler med = new eachBlockEventHandler();
		    	     block.setOnMouseClicked(med);
		    		 row.getChildren().add(block);
		    		 blocklist.add(block); 
		    	 }   
		    	 column.getChildren().add(row);
		     }
		     HBox lastRow = new HBox(2);
		     Block block1 = new Block(128,62.5,"0");
		     Block block2 = new Block(62.5,62.5,".");
		     Block block3 = new Block(62.5,62.5,"=");
		     eachBlockEventHandler med = new eachBlockEventHandler();
    	     block1.setOnMouseClicked(med);
    	     block2.setOnMouseClicked(med);
    	     block3.setOnMouseClicked(med);
		     lastRow.getChildren().addAll(block1,block2,block3);
		     column.getChildren().add(lastRow);
		     bigBox.getChildren().add(column);
	        root.getChildren().add(bigBox);
	        Scene scene = new Scene(root); 
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Calculator");
	        primaryStage.show();
	    }
	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    private class eachBlockEventHandler implements EventHandler<MouseEvent>{	
	    @Override
		public void handle(MouseEvent o) {
        	Block block =(Block) o.getSource();
        	String numberOroperator = block.getBlockText();
            if (numberBox.getText().length() >= 9 &&numberOroperator.charAt(0)>= '0'&& numberOroperator.charAt(0) <= '9' && !check3) {
//            	System.out.println(numberBox.getText().length());
            	return;    
            }
            System.out.println(numberOroperator.charAt(0));
        	if(numberOroperator.charAt(0)>= '0' && numberOroperator.charAt(0) <= '9' ) {	
        		if (storedOper == '=') {
        			storedNum=0;
        		}
        		if(check) {
        			
        			numberBox.clear();
        			check = false;
        		}
        		//check2 is to know user click number or not. if not , it keep storing the gettext to stored again. avoid self-compute
        		check2 = true;
        		// check3 is to give a full number (9digits) a chance to give other operator and then can click number agian.
        		check3 = false;
        		System.out.println("hello");
        		numberBox.setText(numberBox.getText()+numberOroperator);
        		System.out.println(numberBox.getText());
        		return;
        	}
        	else if(numberOroperator.equalsIgnoreCase("AC")){
            	storedNum=0;
            	check2 = false;
            	check3 = true;
        	} 
        	else if(numberOroperator.equalsIgnoreCase("+")) {
        		check3 = true;
        		if(!check2) {
         		   storedNum = Double.parseDouble(numberBox.getText()); 
         		   storedOper = '+';
         		   check2 = true;
         		}
        		else { storedNum = calculate(storedNum,Double.parseDouble(numberBox.getText()), storedOper);
    			storedOper = '+';  }
        	}
        	else if(numberOroperator.equalsIgnoreCase("-")) {
        		check3 = true;
        		if(!check2) {
        			 System.out.println(Double.parseDouble(numberBox.getText()));
         		   storedNum = Double.parseDouble(numberBox.getText()); 
         		   storedOper = '-';
         		   check2 = true;
         		}
        		else {	storedNum = calculate(storedNum,Double.parseDouble(numberBox.getText()), storedOper);
        			storedOper = '-'; }
        	}   
        	else if(numberOroperator.equalsIgnoreCase("x")) {
        		check3 = true;
        		if(!check2) {
         		   storedNum = Double.parseDouble(numberBox.getText()); 
         		   storedOper = 'x';
         		   check2 = true;
         		}
        		else { storedNum = calculate(storedNum,Double.parseDouble(numberBox.getText()), storedOper);
    			storedOper = 'x';}
        	} 
        	else if(numberOroperator.equalsIgnoreCase("/")) {
        		check3 = true;
        		if(!check2) {
         		   storedNum = Double.parseDouble(numberBox.getText()); 
         		   storedOper = '/';
         		   check2 = true;
         		}
        		else { storedNum = calculate(storedNum,Double.parseDouble(numberBox.getText()), storedOper);
    			storedOper = '/'; }
        	}
        	else if(numberOroperator.equalsIgnoreCase("=")) {
        		check3 = true;
        		storedNum = calculate(storedNum,Double.parseDouble(numberBox.getText()), storedOper);
        		storedOper = '=';
        		check2 = false;
        	}
        	else if(numberOroperator.equalsIgnoreCase("+/-")) {
        		check3 = true;
        		numberBox.setText("-" + numberBox.getText());
        		storedNum = -1 * storedNum;
        		
        	}
        	else if(numberOroperator.equalsIgnoreCase("%")) {
        		check3 = true;
        		numberBox.setText(""+Integer.parseInt(numberBox.getText())/100);
        		  storedNum = -1 * storedNum;
        	  }
//        	(int)storedNum -storedNum   == 0
        	numberBox.clear();
 		   System.out.println(storedNum);
 		   
 		 
 		
 	  
         BigDecimal bd = new BigDecimal(storedNum);
         String formattedNumber = bd.toPlainString();
        	   if(storedNum%1  == 0) {
        		   System.out.println("hello90"); 
//        		   int storedNumTemp = (int)storedNum;
        		   System.out.println(formattedNumber); 
        		   numberBox.setText(formattedNumber); 
        	   }
        	   else {
        		   String temp = storedNum +"";
        		  if(temp.length() >= 9) {
        			  temp = temp.substring(0, 9);
        		  }
        		  
        	      numberBox.setText(temp); 
        	   }
        	   check = true;
        	   check2 = false;
        	   check3 = true;
        	   System.out.println(check);
        	   
        	o.consume();

	      }
	    }
      
	  private static double calculate(double num1 , double num2 , char operator) {
		  if(operator == '-') {
			  return num1 - num2;
		  }
		  else if(operator == '+') {
			  return num1 + num2;
		  }
		  else if(operator == '/') {
			  return num1 / num2;
		  }
		  else if(operator == 'x') {
			  return num1 * num2;}
		  return num2;
		  
	  }

	  private class Block extends Group{
	    private Rectangle rect;
	   	private Text text;
	   	public Block(double width, double height, String number){
	   		rect = new Rectangle(30,30,width,height);
	   		text = new Text(50,70,number);
    		this.text.setFont(Font.font("Arial" , 30));
    		this.text.setFill(Color.WHITE);
	   		this.getChildren().addAll(rect,text);
    	}	    	

	    	public String getBlockText() {
	    		return this.text.getText();
	    	}
	    }

}
