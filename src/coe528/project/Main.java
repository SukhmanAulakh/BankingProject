package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

//THE CLASS THAT SATISFIES REQUIREMENT 2 IS SILVER CUSTOMER

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.stage.*;

public class Main extends Application implements EventHandler<ActionEvent> {

    //Set Variables
    private Stage primaryStage;
    private Pane root;
    private Scene scene;

    //Set Images and ImageViews
    private final Image imgLoginBack = new Image("/coe528/project/images/login-background.png");
    private final Image imgManagerBack = new Image("/coe528/project/images/manager-background.png");
    private final Image imgCustomerBack = new Image("/coe528/project/images/customer-background.png");
    private ImageView iviewBack;
    
    //Set Textfields
    private TextField usernameInput;
    private TextField passwordInput;
    private TextField amountInput;
    private TextField newUsernameInput;
    private TextField newPasswordInput;
    
    //Set Buttons
    private Button loginBtn;
    private Button logoutBtn;
    private Button addCustomerBtn;
    private Button remCustomerBtn;
    private Button purchaseBtn;
    private Button depositBtn;
    private Button withdrawBtn;
    private Button balanceBtn;
    
    //Instantiating User Object
    Manager manager;
    Customer customer;

    //Start Stage
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Banking System | Login");
            root = new Pane();
            
            //Go to login Screen
            loginScreen();
            
            //Set the stage
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

            //What to do if user wants to close window
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                public void handle(WindowEvent e) {
                    //create an alert asking if user really wants to close the program
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    if(customer!=null||manager!=null)
                    {
                        alert.setContentText("Changes wont be saved unless logged out.\nDo you want to exit?");
                    }
                    else
                    {
                        alert.setContentText("Do you want to exit?");   
                    }
                    alert.setHeaderText(null);
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> result = alert.showAndWait();

                    //what to do if user does want to close
                    if (result.get() == ButtonType.YES) {
                        System.out.println("Quit");
                    } //what to do if they do not want to
                    else {
                        e.consume();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    //What to do on button presses
    public void handle(ActionEvent e) {
        //What to do if login button is pressed
        if(e.getSource()==loginBtn)
        {
            if(checkTextInput(passwordInput)&&checkTextInput(usernameInput))
            {
                System.out.println("trying log in as..."+usernameInput.getText());
                if(usernameInput.getText().equals("admin")&&passwordInput.getText().equals("admin"))
                {
                    manager = new Manager();
                    
                    //Changing the scene
                    this.primaryStage = primaryStage;
                    primaryStage.setTitle("Banking System | Manager");
                    root = new Pane();

                    //Go to login Screen
                    managerScreen();

                    //Set the stage
                    this.primaryStage.setScene(scene);
                    this.primaryStage.show();
                }
                else
                {
                    //Will return null or password depending on if username is found
                    if((customer = findCustomer(usernameInput.getText()))!=null)
                    {
                        //Check the customer's Level and assign it accordingly
                        customer = checkLevel(customer.getUsername(),customer.getBalance());
                        
                        //Changing the scene
                        this.primaryStage = primaryStage;
                        primaryStage.setTitle("Banking System | Customer:  "+customer.getUsername());
                        root = new Pane();

                        //Go to Customer Screen
                        customerScreen();

                        //Set the stage
                        this.primaryStage.setScene(scene);
                        this.primaryStage.show();
                    }
                    else
                    {
                        //Show Alert indicating unsuccessful login
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("FAILURE,  Login Attempt Unsuccessful!");
                        alert.setTitle("Login");
                        //Show the alert
                        alert.showAndWait();

                    }
                }
            }
        }
        else if(e.getSource()==logoutBtn)
        {
            if(customer!=null)
            {
                System.out.println("SAVING");
                saveToCustomerFile(customer);
            }
            
            customer = null;
            manager = null;
            
            root = new Pane();
            
            //Go to login Screen
            loginScreen();
            
            //Set the stage
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        }
        else if(e.getSource()==addCustomerBtn)
        {
            if(checkTextInput(newUsernameInput)&&checkTextInput(newPasswordInput))
            {
                if(manager.addCustomer(newUsernameInput.getText(), newPasswordInput.getText()))
                {
                    //Show Alert Showing successfull deposit
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success,  Account : "+newUsernameInput.getText()+" Created");
                    alert.setTitle("Manager");
                    //Show the alert
                    alert.showAndWait();
                }
                else
                {
                    //Show Alert indicating unsuccessful deposit
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("FAILURE,  Unable To Add Customer!");
                    alert.setTitle("Manager");
                    //Show the alert
                    alert.showAndWait();
                }
            }
        }
        else if(e.getSource()==remCustomerBtn)
        {
            if(checkTextInput(newUsernameInput)&&checkTextInput(newPasswordInput))
            {
                if(manager.removeCustomer(newUsernameInput.getText(), newPasswordInput.getText()))
                {
                    //Show Alert Showing successfull deposit
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success,  Account : "+newUsernameInput.getText()+" Removed");
                    alert.setTitle("Manager");
                    //Show the alert
                    alert.showAndWait();
                }
                else
                {
                    //Show Alert indicating unsuccessful deposit
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("FAILURE,  Unable to Remove Customer");
                    alert.setTitle("Manager");
                    //Show the alert
                    alert.showAndWait();
                }
            }
        }
        else if(e.getSource()==purchaseBtn)
        {
            if(checkAmountInput(amountInput))
            {
                if(customer.makePurchase(Long.parseLong(amountInput.getText())))
                {
                    //Show Alert Showing successfull deposit
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success,  Made A Purchase Of (W/Fee):   "+(Long.parseLong(amountInput.getText()))+"\nNew Balance:   "+customer.getBalance());
                    alert.setTitle("Purchase");
                    //Show the alert
                    alert.showAndWait();
                    
                    //Check the customer's Level and assign it accordingly
                    customer = checkLevel(customer.getUsername(),customer.getBalance());
                }
                else
                {
                    //Show Alert indicating unsuccessful purchase
                    Alert alert = new Alert(AlertType.ERROR);
                    //Check if error is because amount is greater than the balance
                    if(customer.getBalance()<Long.parseLong(amountInput.getText()))
                    {
                        alert.setHeaderText("FAILURE,  Purchase Error!! :"+Long.parseLong(amountInput.getText())+"\nIncompatible with Balance:   "+customer.getBalance());
                    }
                    else
                    {
                        alert.setHeaderText("FAILURE,  Purchase Error!! :"+Long.parseLong(amountInput.getText())+"\nMinimum Purchase for "+customer.toString()+" is : "+(50+customer.getFEE()));
                    }
                    alert.setTitle("Purchase");
                    //Show the alert
                    alert.showAndWait();
                }
            }
        }
        else if(e.getSource()==depositBtn)
        {
            if(checkAmountInput(amountInput))
            {
                if(customer.deposit(Long.parseLong(amountInput.getText())))
                {
                    //Show Alert Showing successfull deposit
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success,  Deposited:   "+Long.parseLong(amountInput.getText())+"\nNew Balance:   "+customer.getBalance());
                    alert.setTitle("Deposit");
                    //Show the alert
                    alert.showAndWait();
                    
                    //Check the customer's Level and assign it accordingly
                    customer = checkLevel(customer.getUsername(),customer.getBalance());
                }
                else
                {
                    //Show Alert indicating unsuccessful deposit
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("FAILURE,  Insufficient Amount!! :"+Long.parseLong(amountInput.getText())+"\nAmount Must be greater than 0.");
                    alert.setTitle("Deposit");
                    //Show the alert
                    alert.showAndWait();
                }
            }
        }
        else if(e.getSource()==withdrawBtn)
        {
            if(checkAmountInput(amountInput))
            {
                if(customer.withdraw(Long.parseLong(amountInput.getText())))
                {
                    //Show Alert Showing successfull withdrawal
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Success,  Withdrew:   "+Long.parseLong(amountInput.getText())+"\nNew Balance:   "+customer.getBalance());
                    alert.setTitle("Withdrawal");
                    //Show the alert
                    alert.showAndWait();
                    
                    //Check the customer's Level and assign it accordingly
                    customer = checkLevel(customer.getUsername(),customer.getBalance());
                }
                else
                {
                    //Show Alert indicating unsuccessful withdrawal
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("FAILURE,  Insufficient Funds!! Withdrawal :"+Long.parseLong(amountInput.getText())+"\nIncompatible with Balance:   "+customer.getBalance());
                    alert.setTitle("Withdrawal");
                    //Show the alert
                    alert.showAndWait();
                }
            }
        }
        else if(e.getSource()==balanceBtn)
        {
            //Check the customer's Level and assign it accordingly
            customer = checkLevel(customer.getUsername(),customer.getBalance());
                        
            //Show Alert Telling Bank Balance
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Balance:   "+customer.getBalance());
            alert.setContentText("User:    "+customer.getUsername()+"\nCustomer Level:   "+customer.toString());
            alert.setTitle("Balance");
            //Show the alert
            alert.showAndWait();
        }
    }

    //Home Screen Stage Allows user to click button to login
    private void loginScreen() {
        //Set the background to the Login Image
        iviewBack = new ImageView(imgLoginBack);
        iviewBack.setFitHeight(540);
        iviewBack.setFitWidth(960);
        iviewBack.setPreserveRatio(false);

        //Set the login scene to match the background image
        scene = new Scene(root, iviewBack.getFitWidth(), iviewBack.getFitHeight());

        //Create TextField For User to type login info into
        usernameInput = new TextField();
        passwordInput = new TextField();

        //Create Custom Font for Username and Password
        Font inputFont = Font.font("Arial", FontWeight.BOLD, 20);
        
        //ALLIGN AND CREATE TEXTFIELDS FOR PASSWORD AND USERNAME
        usernameInput.setAlignment(Pos.CENTER_LEFT);
        usernameInput.setPrefSize(400, 30);
        usernameInput.setFont(inputFont);
        usernameInput.setStyle("-fx-text-fill: black;");
        usernameInput.setBackground(null);
        usernameInput.setLayoutX(scene.getWidth()/2 - usernameInput.getPrefWidth()/2+35);
        usernameInput.setLayoutY(scene.getHeight()/2 - usernameInput.getPrefHeight()/2 - 52*3);
        usernameInput.setOnAction(e -> checkTextInput(usernameInput));
        
        passwordInput.setAlignment(Pos.CENTER_LEFT);
        passwordInput.setPrefSize(400, 30);
        passwordInput.setFont(inputFont);
        passwordInput.setStyle("-fx-text-fill: black;");
        passwordInput.setBackground(null);
        passwordInput.setLayoutX(scene.getWidth()/2 - passwordInput.getPrefWidth()/2+35);
        passwordInput.setLayoutY(scene.getHeight()/2 - passwordInput.getPrefHeight()/2 - 56*1);
        passwordInput.setOnAction(e -> checkTextInput(passwordInput));
        
        //Create Button To Login
        loginBtn = new Button();
        loginBtn.setText("");
        loginBtn.setPrefSize(170,45);
        loginBtn.setBackground(null);
        loginBtn.setLayoutX(scene.getWidth()/2-loginBtn.getPrefWidth()/2-2);
        loginBtn.setLayoutY(scene.getHeight()/2+47);
        loginBtn.setOnAction(e ->handle(e));

        //Add Elements to the scene
        root.getChildren().addAll(iviewBack,usernameInput,passwordInput,loginBtn);
    }

    //Manager Screen Stage Allows for creation and deletion of customer account and logging out(logging out assumed to be going to login screen)
    private void managerScreen()
    {
        //Set the background to the Manager Image
        iviewBack = new ImageView(imgManagerBack);
        iviewBack.setFitHeight(540);
        iviewBack.setFitWidth(960);
        iviewBack.setPreserveRatio(false);

        //Set the manager scene to match the background image
        scene = new Scene(root, iviewBack.getFitWidth(), iviewBack.getFitHeight());

        //Create TextField For User to type login info into
        newUsernameInput = new TextField();
        newPasswordInput = new TextField();

        //Create Custom Font for Username and Password
        Font inputFont = Font.font("Arial", FontWeight.BOLD, 20);
        
        //ALLIGN AND CREATE TEXTFIELDS FOR PASSWORD AND USERNAME
        newUsernameInput.setAlignment(Pos.CENTER_LEFT);
        newUsernameInput.setPrefSize(400, 30);
        newUsernameInput.setFont(inputFont);
        newUsernameInput.setStyle("-fx-text-fill: black;");
        newUsernameInput.setBackground(null);
        newUsernameInput.setLayoutX(scene.getWidth()/2 - newUsernameInput.getPrefWidth()/2+53);
        newUsernameInput.setLayoutY(scene.getHeight()/2 - newUsernameInput.getPrefHeight()/2 - 47*2);
        newUsernameInput.setOnAction(e -> checkTextInput(newUsernameInput));
        
        newPasswordInput.setAlignment(Pos.CENTER_LEFT);
        newPasswordInput.setPrefSize(400, 30);
        newPasswordInput.setFont(inputFont);
        newPasswordInput.setStyle("-fx-text-fill: black;");
        newPasswordInput.setBackground(null);
        newPasswordInput.setLayoutX(scene.getWidth()/2 - newPasswordInput.getPrefWidth()/2+53);
        newPasswordInput.setLayoutY(scene.getHeight()/2 - newPasswordInput.getPrefHeight()/2+5);
        newPasswordInput.setOnAction(e -> checkTextInput(newPasswordInput));
        
        //Create Button To Logout
        logoutBtn = new Button();
        logoutBtn.setText("");
        logoutBtn.setPrefSize(168,52);
        logoutBtn.setBackground(null);
        logoutBtn.setLayoutX(scene.getWidth()/2-logoutBtn.getPrefWidth()/3-18);
        logoutBtn.setLayoutY(scene.getHeight()/2+logoutBtn.getPrefHeight()*2.8);
        logoutBtn.setOnAction(e ->handle(e));
        
        //Create Button To Add Customer
        addCustomerBtn = new Button();
        addCustomerBtn.setText("");
        addCustomerBtn.setPrefSize(160,65);
        addCustomerBtn.setBackground(null);
        addCustomerBtn.setLayoutX(scene.getWidth()/2-addCustomerBtn.getPrefWidth()-3);
        addCustomerBtn.setLayoutY(scene.getHeight()/2+addCustomerBtn.getPrefHeight()-16);
        addCustomerBtn.setOnAction(e ->handle(e));
        
        //Create Button To Remove Customer
        remCustomerBtn = new Button();
        remCustomerBtn.setText("");
        remCustomerBtn.setPrefSize(165,65);
        remCustomerBtn.setBackground(null);
        remCustomerBtn.setLayoutX(scene.getWidth()/2+remCustomerBtn.getPrefWidth()/3-15);
        remCustomerBtn.setLayoutY(scene.getHeight()/2+remCustomerBtn.getPrefHeight()-16);
        remCustomerBtn.setOnAction(e ->handle(e));
        
        //Add Elements to the scene
        root.getChildren().addAll(iviewBack,newUsernameInput,newPasswordInput,logoutBtn,addCustomerBtn,remCustomerBtn);
    }
    
    //Customer Screen Stage Allows for deposit,withdrawal,balance checking, and online purchases(logging out assumed to be going to login screen)
    private void customerScreen()
    {
        //Set the background to the customer Image
        iviewBack = new ImageView(imgCustomerBack);
        iviewBack.setFitHeight(540);
        iviewBack.setFitWidth(960);
        iviewBack.setPreserveRatio(false);

        //Set the customer scene to match the background image
        scene = new Scene(root, iviewBack.getFitWidth(), iviewBack.getFitHeight());

        //Create TextField For User to type login info into
        amountInput = new TextField();

        //Create Custom Font for Username and Password
        Font inputFont = Font.font("Arial", FontWeight.BOLD, 20);
        
        //ALLIGN AND CREATE TEXTFIELDS FOR PASSWORD AND USERNAME
        amountInput.setAlignment(Pos.CENTER_LEFT);
        amountInput.setPrefSize(400, 30);
        amountInput.setFont(inputFont);
        amountInput.setStyle("-fx-text-fill: black;");
        amountInput.setBackground(null);
        amountInput.setLayoutX(scene.getWidth()/2 - amountInput.getPrefWidth()/2);
        amountInput.setLayoutY(scene.getHeight()/2 - amountInput.getPrefHeight()/2 - 26*5);
        amountInput.setOnAction(e -> checkTextInput(amountInput));
        
        //Create Button To Logout
        logoutBtn = new Button();
        logoutBtn.setText("");
        logoutBtn.setPrefSize(168,52);
        logoutBtn.setBackground(null);
        logoutBtn.setLayoutX(scene.getWidth()/2-logoutBtn.getPrefWidth()/2-4);
        logoutBtn.setLayoutY(scene.getHeight()/2+logoutBtn.getPrefHeight()/2);
        logoutBtn.setOnAction(e ->handle(e));
        
        //Create Button To Make Purchase
        purchaseBtn = new Button();
        purchaseBtn.setText("");
        purchaseBtn.setPrefSize(130,55);
        purchaseBtn.setBackground(null);
        purchaseBtn.setLayoutX(scene.getWidth()/2-purchaseBtn.getPrefWidth()*2-28);
        purchaseBtn.setLayoutY(scene.getHeight()/2-73);
        purchaseBtn.setOnAction(e ->handle(e));
        
        //Create Button to Deposit
        depositBtn = new Button();
        depositBtn.setText("");
        depositBtn.setPrefSize(130,55);
        depositBtn.setBackground(null);
        depositBtn.setLayoutX(scene.getWidth()/2-depositBtn.getPrefWidth()-3);
        depositBtn.setLayoutY(scene.getHeight()/2-73);
        depositBtn.setOnAction(e ->handle(e));
        
        //Create Button to Withdraw
        withdrawBtn = new Button();
        withdrawBtn.setText("");
        withdrawBtn.setPrefSize(120,55);
        withdrawBtn.setBackground(null);
        withdrawBtn.setLayoutX(scene.getWidth()/2+15);
        withdrawBtn.setLayoutY(scene.getHeight()/2-73);
        withdrawBtn.setOnAction(e ->handle(e));
        
        //Create Button to check balance
        balanceBtn = new Button();
        balanceBtn.setText("");
        balanceBtn.setPrefSize(120,55);
        balanceBtn.setBackground(null);
        balanceBtn.setLayoutX(scene.getWidth()/2+balanceBtn.getPrefWidth()+36);
        balanceBtn.setLayoutY(scene.getHeight()/2-73);
        balanceBtn.setOnAction(e ->handle(e));

        //Add Elements to the scene
        root.getChildren().addAll(iviewBack,amountInput,logoutBtn,purchaseBtn,depositBtn,withdrawBtn,balanceBtn);
    }
    
    //get the text from any (String) textfield and check if it is a valid input
    private boolean checkTextInput(TextField textInput) {
        //Check if the text is valid
        if (textInput.getText().equals("") || textInput.getText().length() > 30) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No Text Detected Or Text is Greater than 30 Characters.");
            alert.setTitle("Error!");
            textInput.setText("");
            //Show the alert
            alert.showAndWait();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    //Check if the amount is a valid input
    private boolean checkAmountInput(TextField amountInput)
    {
        try
        {
            if(amountInput.getText()==""||amountInput.getText()==""||Long.parseLong(amountInput.getText())<0)
            {
                //Throw alert if invalid input
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Invalid Amount Detected.\nMust be Greater than 0");
                alert.setTitle("Error!");
                amountInput.setText("");
                //Show the alert
                alert.showAndWait();
                return false;
            }
            else
            {
                return true;
            }
        }catch(Exception e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Enter A Number (Ex/ 1; 2; 100; 1000).\nMust be Greater than 0");
            alert.setTitle("Error!");
            amountInput.setText("");
            //Show the alert
            alert.showAndWait();
            return false;
        }
    }
    
    //Find Customer from Textfiles
    private Customer findCustomer(String username)
    {
        try
        {
           BufferedReader customerFile = new BufferedReader(new FileReader("./src/coe528/project/customers/"+username+".txt"));
           String password = customerFile.readLine();
           if(password.equals(passwordInput.getText()))
           {
               long balance = Long.parseLong(customerFile.readLine());
               Customer tmpCustomer = new Customer(username,balance);
               customerFile.close();
               return tmpCustomer;
           }
           else
           {
               customerFile.close();
               return null;
           }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    //State Machine that controls the customer level
    private Customer checkLevel(String username,long balance)
    {
        if(balance>=20000)
        {
            return new PlatinumCustomer(username,balance);
        }
        else if(balance>=10000)
        {
            return new GoldCustomer(username,balance);
        }
        else if(balance>=0)
        {
            return new SilverCustomer(username,balance);
        }
        else
        {
            return customer;
        }
    }
    
    //Save When Logging Out
    private void saveToCustomerFile(Customer customer)
    {
        try {
            //Get Exsisting Password
            BufferedReader customerFile = new BufferedReader(new FileReader("./src/coe528/project/customers/"+customer.getUsername()+".txt"));
            String password = customerFile.readLine();
            customerFile.close();
            
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("./src/coe528/project/customers/"+customer.getUsername()+".txt"));
            fileWriter.write(password);
            fileWriter.newLine();
            fileWriter.write(""+customer.getBalance());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("ERROR IN SAVE TO CUSTOMER FILE METHOD");
        }
    } 
}