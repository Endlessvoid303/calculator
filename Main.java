import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// TODO:make a better way to add numbers after eachother using numbers /
public class Main {
    // * define window size information */
    static short width = 600;
    static short height = 900;

    // * define data about the buttons */
    static Dimension buttonsize = new Dimension((width - 60) / 3, (height - 60) / 6);
    static Font buttonfont = new Font("Arial", Font.PLAIN, 40);

    // * set defaults for calculator data */
    static String oldnum = "";
    static String operator = "";
    static String newnum = "";

    // * create a window */
    static JFrame frame = new JFrame("rekenmachine");

    // * create the screen */
    static JLabel label = new JLabel("testing 123");

    public static void main(String[] args) {
        // * make the window have a grid layout */
        frame.setLayout(new GridBagLayout());

        // * position the window */
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // * position the screen */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        label.setPreferredSize(new Dimension(width - 60, (height - 60) / 6));

        // * style the screen */
        label.setFont(buttonfont);
        label.setOpaque(true);
        label.setBackground(new Color(100, 200, 255));

        // * add the screen to the window */
        frame.add(label, gbc);

        // * add the numbers to the screen */
        addnumbutton(1, 0, 1);
        addnumbutton(2, 1, 1);
        addnumbutton(3, 2, 1);
        addnumbutton(4, 0, 2);
        addnumbutton(5, 1, 2);
        addnumbutton(6, 2, 2);
        addnumbutton(7, 0, 3);
        addnumbutton(8, 1, 3);
        addnumbutton(9, 2, 3);
        addnumbutton(0, 0, 4);

        // * add the operators to the screen */
        addoperatorbutton("+", 1, 4);
        addoperatorbutton("*", 1, 5);
        addoperatorbutton("/", 2, 5);
        addoperatorbutton("-", 2, 4);

        // * add the calculate button to the screen */
        addcalculatebutton(0, 5);

        //* update screen and show window */
        updatenumdisplay();
        frame.setVisible(true);
    }

    public static void addnum(String buttonInput) {
        if (operator != "" || oldnum == "") {
            newnum += buttonInput;
        }
        updatenumdisplay();
    }

    public static Integer getnumber(String number) {
        if (number == "") {
            return 0;
        } else {
            return Integer.parseInt(number);
        }
    }

    public static void setoperator(String buttonInput) {
        if (newnum != "") {
            calculate();
        }
        operator = buttonInput;
        updatenumdisplay();
    }

    public static void calculate() {
        System.out.println(String.format("%s %s %s", oldnum, operator, newnum));
        if (operator == "*") {
            oldnum = String.valueOf(getnumber(oldnum) * getnumber(newnum));
        } else if (operator == "/" && getnumber(newnum) != 0) {
            oldnum = String.valueOf(getnumber(oldnum) / getnumber(newnum));
        } else if (operator == "+") {
            oldnum = String.valueOf(getnumber(oldnum) + getnumber(newnum));
        } else if (operator == "-") {
            oldnum = String.valueOf(getnumber(oldnum) - getnumber(newnum));
        } else if (operator == "" && oldnum == "") {
            oldnum = newnum;
        }
        newnum = "";
        operator = "";
        System.out.println(String.format("result calculated: %s %s %s", oldnum, operator, newnum));
        updatenumdisplay();
    }

    public static void addnumbutton(Integer nummer, Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> addnum(((JButton) e.getSource()).getText());
        createbutton(buttonListener, String.valueOf(nummer), gridx, gridy);
    }

    public static void addoperatorbutton(String operator, Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> setoperator(((JButton) e.getSource()).getText());
        createbutton(buttonListener, operator, gridx, gridy);
    }

    public static void addcalculatebutton(Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> calculate();
        createbutton(buttonListener, "=", gridx, gridy);
    }
    public static void createbutton(ActionListener buttonListener,String displaytext , Integer gridx, Integer gridy) {
        //* create a new button */
        GridBagConstraints gbc = new GridBagConstraints();
        //* set the display text */
        JButton num = new JButton(displaytext);
        //* set the data */
        num.setPreferredSize(buttonsize);
        num.setFont(buttonfont);
        num.addActionListener(buttonListener);
        //* position the button */
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        //* add the button */
        frame.add(num, gbc);
    }

    public static void updatenumdisplay() {
        label.setText(String.format("%s %s %s", oldnum, operator, newnum));
    }
}