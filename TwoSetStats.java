//fix the algorithm

//importing needed packages and classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

//class declared as default access
class TwoSetStats implements ActionListener{

    //declaring and initializing (some) global objects and arrays
    JFrame frame;
    JPanel[] inp, out;
    JLabel[][] units;
    JTextField[][] set, ans;
    GridLayout forInp, forOut, forFrame;
    String[] theUnits = {"Mean", "Median", "Mode", "Min", "Max"};
    double[][] numSet = new double[2][12];
    Font theFont = new Font("defaultFont", Font.BOLD, 16);
    JButton calc;
    ArrayList<Double> sortedList = new ArrayList<Double>();
    ArrayList<ArrayList<Double>> mode = new ArrayList<ArrayList<Double>>();

    //beginning of constructor method
    TwoSetStats(){

        //initializing & configuring input panel with input fields
        inp = new JPanel[2];
        forInp = new GridLayout(1,12);
        forInp.setHgap(10);
        set = new JTextField[2][12];
        for(int i=0; i<inp.length; i++){
            inp[i] = new JPanel();
            inp[i].setLayout(forInp);
            for(int j=0; j<set[0].length; j++){
                set[i][j] = new JTextField();
                set[i][j].setFont(theFont);
                inp[i].add(set[i][j]);
            }
        }

        //initializing & configuring output panel with output labels
        //and fields
        out = new JPanel[2];
        forOut = new GridLayout(1,10);
        forOut.setHgap(5);
        ans = new JTextField[2][5];
        units = new JLabel[2][5];
        for(int i=0; i<out.length; i++){
            int k = 0;
            int l = 0;
            out[i] = new JPanel();
            out[i].setLayout(forOut);
            for(int j=0; j<10; j++){
                if(j%2 != 0){
                    ans[i][k] = new JTextField();
                    ans[i][k].setFont(theFont);
                    ans[i][k].setEditable(false);
                    ans[i][k].setBackground(Color.WHITE);
                    out[i].add(ans[i][k]);
                    k++;
                }
                else{
                    units[i][l] = new JLabel(theUnits[l], SwingConstants.RIGHT);
                    units[i][l].setFont(theFont);
                    out[i].add(units[i][l]);
                    l++;
                }
            }
            
        }

        //initializing the calculate button
        calc = new JButton("Calculate");
        calc.setFocusable(false);
        calc.addActionListener(this);
        
        //initializing and configuring the overall JFrame
        frame = new JFrame("Roa: Two Set Stats");
        forFrame = new GridLayout(7,1);
        forFrame.setVgap(3);
        frame.setLayout(forFrame);
        for(int i=0; i<out.length; i++){
            frame.add(new JLabel("Set ".concat(String.valueOf(i+1)), SwingConstants.CENTER));
            frame.add(inp[i]);
            frame.add(out[i]);
        }
        frame.add(calc);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700,400);
    }

    //main method calling the constructor
    public static void main(String[] args){
        new TwoSetStats();
    }

    // This section comprises of methods for the proper calculations
    double mean(int pos){
        double sum = 0.0;
        for(int i=0; i<numSet[pos].length; i++)
            sum+=numSet[pos][i];

        double average = sum/numSet[pos].length;
        return (double) Math.round(average*100)/100;
    }

    double median(int pos){
        sortedList.clear();
        int placement = numSet[pos].length/2;
        for(double el: numSet[pos])
            sortedList.add(el);
        Collections.sort(sortedList);

        double medOnList = sortedList.get(placement);
        if(sortedList.size()%2!=0)
            return medOnList;
        return (sortedList.get(placement-1)+sortedList.get(placement))/2;
    }

    void mode(int pos){

        int appearance = 0;

        mode.add(new ArrayList<Double>());
        for(double el: numSet[pos]){
            int inAppear = 0;
            for(double inEl: numSet[pos]){
                if(el==inEl)
                    inAppear++;
                if(inAppear>appearance){
                    mode.get(pos).clear();
                    appearance = inAppear;
                    mode.get(pos).add(el);
                }
                else if(inAppear == appearance && !(mode.get(pos).contains(el))){
                    mode.get(pos).add(el);
                }
            }

        }

        for(double i: mode.get(pos)){
            ans[pos][2].setText(ans[pos][2].getText().concat(
                    String.valueOf(i).concat(" ")));
        }

    }

    double min(int pos){
        sortedList.clear();
        for(double el: numSet[pos])
            sortedList.add(el);
        Collections.sort(sortedList);

        double firstOnList = sortedList.get(0);

        return firstOnList;
    }

    double max(int pos){
        sortedList.clear();
        for(double el: numSet[pos])
            sortedList.add(el);
        Collections.sort(sortedList);

        double lastOnList = sortedList.get(sortedList.size()-1);

        return lastOnList;
    }

    //End of section. The following method calls the methods above
    //when button is pressed
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == calc){
            for(int i=0; i<set.length; i++){
                for(int j=0; j<set[0].length; j++)
                    numSet[i][j] = Double.parseDouble(set[i][j].getText());
            }

            for(int i=0; i<2; i++){
                ans[i][0].setText(String.valueOf(mean(i)));
                ans[i][1].setText(String.valueOf(median(i)));
                ans[i][3].setText(String.valueOf(min(i)));
                ans[i][4].setText(String.valueOf(max(i)));
                mode(i);
            }
        }
    }

}
