package machine;

import java.util.Scanner;

public class CoffeeMachine {
    
    private static Coffee coffeeMachine = new Coffee(400, 540, 120, 9, 550);
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        //coffeeMachine.printStatus();
        coffeeMachine.actionPrompt();
        
        while(coffeeMachine.getState() != State.SHUTDOWN){
            coffeeMachine.action(input.nextLine());
        }
    }
    
}

class Coffee {
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;
    private int fillCount;
    
    private State state = State.MAIN_MENU;
    
    public Coffee(int w, int m, int co, int cu, int mo){
        this.water = w;
        this.milk = m;
        this.coffee = co;
        this.cups = cu;
        this.money = mo;
    }
    
    public State getState(){
        return state;
    }
    
    public void actionPrompt(){
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }
    
    public void printStatus(){
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffee + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println("$" + money + " of money");
    }
    
    public void action(String action){
        switch(state){
            case MAIN_MENU: // options are buy fill take remaining exit
                switch(action){
                    case "buy" :
                        state = State.BUY;
                        System.out.println();
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                        break;
                    case "fill":
                        state = State.FILL;
                        fillCount = 0;
                        System.out.println("\nWrite how many ml of water do you want to add:");
                        break;
                    case "take":
                        System.out.println("I gave you $" + money);
                        money = 0;
                        break;
                    case "remaining":
                        System.out.println();
                        printStatus();
                        actionPrompt();
                        break;
                    case "exit" :
                        state = State.SHUTDOWN;
                        break;
                }
                
                break;
            case FILL: // water milk coffee cups
                switch(fillCount){
                    case 0 :
                        water += Integer.parseInt(action);
                        fillCount++;
                        System.out.println("Write how many ml of milk do you want to add:");
                        break;
                    case 1 :
                        milk += Integer.parseInt(action);
                        fillCount++;
                        System.out.println("Write how many grams of coffee beans do you want to add:");
                        break;
                    case 2 :
                        coffee += Integer.parseInt(action);
                        fillCount++;
                        System.out.println("Write how many disposable cups of coffee do you want to add:");
                        break;
                    case 3 :
                        cups += Integer.parseInt(action);
                        state = State.MAIN_MENU;
                        actionPrompt();
                        break;
                }
                break;
            case BUY: // 1 espresso 2 latte 3 cappucino back
                switch(action){
                    case "1": // espresso
                        makeCoffee(250, 0, 16, 4);
                        break;
                    case "2": // latte
                        makeCoffee(350, 75, 20, 7);
                        break;
                    case "3": // cappucino
                        makeCoffee(200, 100, 12, 6);
                        break;
                    case "back": // back to main menu
                        state = State.MAIN_MENU;
                        actionPrompt();
                        break;
                    default: // none of the above return to main menu
                        System.out.println("Command Not Recognised");
                        state = State.MAIN_MENU;
                        actionPrompt();
                        break;
                        
                }
                
                if(action.equalsIgnoreCase("back")){
                    state = State.MAIN_MENU;
                }
                break;
            default:
                
                break;
            
        }
    }
    
    public void makeCoffee(int w, int m, int c, int p){
        if(water < w){
            System.out.println("Sorry, not enough water!");
            state = State.MAIN_MENU;
            actionPrompt();
            return;
        }
        
        if(milk < m){
            System.out.println("Sorry, not enough milk!");
            state = State.MAIN_MENU;
            actionPrompt();
            return;
        }
        
        if(coffee < c){
            System.out.println("Sorry, not enough coffee!");
            state = State.MAIN_MENU;
            actionPrompt();
            return;
        }
        
        if(cups < 1){
            System.out.println("Sorry, not enough cups!");
            state = State.MAIN_MENU;
            actionPrompt();
            return;
        }
        
        water -= w;
        milk -= m;
        coffee -= c;
        cups--;
        money += p;
        System.out.println("I have enough resources, making you a coffee!");
        state = State.MAIN_MENU; // coffee has been bought back to main menu
        actionPrompt(); // prompt user for action
    }
}

enum State {
    MAIN_MENU, BUY, FILL, SHUTDOWN;
}
