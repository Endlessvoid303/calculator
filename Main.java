import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    // * define window size information */
    static short width = 600;
    static short height = 900;

    // * define data about the buttons */
    static Dimension buttonsize = new Dimension((width - 60) / 3, (height - 60) / 7);
    static Font buttonfont = new Font("Arial", Font.PLAIN, 40);

    // * set defaults for calculator data */
    static double oldnum = 0;
    static String operator = "";
    static double newnum = 0;
    static boolean usingdecimal = false;
    static short decimals = 0;

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
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        label.setPreferredSize(new Dimension(width - 60, (height - 60) / 7));

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

        //* add other buttons */
        adddecimalbutton(0, 6);
        addbackspacebutton(1, 6);
        adddeletebutton(2,6);

        // * listen for input */
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Key pressed: " + keyCode);
                if (keyCode == 48 || keyCode == 96) {
                    addnum("0");
                } else if (keyCode == 49 || keyCode == 97) {
                    addnum("1");
                } else if (keyCode == 50 || keyCode == 98) {
                    addnum("2");
                } else if (keyCode == 51 || keyCode == 99) {
                    addnum("3");
                } else if (keyCode == 52 || keyCode == 100) {
                    addnum("4");
                } else if (keyCode == 53 || keyCode == 101) {
                    addnum("5");
                } else if (keyCode == 54 || keyCode == 102) {
                    addnum("6");
                } else if (keyCode == 55 || keyCode == 103) {
                    addnum("7");
                } else if (keyCode == 56 || keyCode == 104) {
                    addnum("8");
                } else if (keyCode == 57 || keyCode == 105) {
                    addnum("9");
                } else if (keyCode == 10) {
                    calculate();
                } else if (keyCode == 106) {
                    setoperator("*");
                } else if (keyCode == 111) {
                    setoperator("/");
                } else if (keyCode == 107) {
                    setoperator("+");
                } else if (keyCode == 109) {
                    setoperator("-");
                } else if (keyCode == 57 || keyCode == 105) {
                    decimal();
                } else if (keyCode == 110) {
                    delete();
                } else if (keyCode == 127) {
                    backspace();;
                }
            }
        });

        // * update screen and show window */
        updatenumdisplay();
        frame.setVisible(true);
    }
    public static void delete() {
        frame.requestFocusInWindow();
        oldnum = 0;
        operator = "";
        newnum = 0;
        usingdecimal = false;
        decimals = 0;
        updatenumdisplay();
    }
    public static void backspace() {
        frame.requestFocusInWindow();
        newnum = 0;
        usingdecimal = false;
        decimals = 0;
        updatenumdisplay();
    }
    public static void decimal() {
        frame.requestFocusInWindow();
        usingdecimal = true;
        updatenumdisplay();
    }

    public static void addnum(String buttonInput) {
        frame.requestFocusInWindow();
        Integer number = Integer.parseInt(buttonInput);
        if (operator != "" || oldnum == 0.0) {
            if (usingdecimal) {
                decimals++;
            } else {
                newnum *= 10;
            }
            newnum += number / Math.pow(10, decimals);
        }
        updatenumdisplay();
    }

    public static void setoperator(String buttonInput) {
        frame.requestFocusInWindow();
        if (oldnum == 0) {
            oldnum = newnum;
            newnum = 0;
        }
        if (newnum != 0) {
            calculate();
        }
        operator = buttonInput;
        updatenumdisplay();
    }

    public static void calculate() {
        frame.requestFocusInWindow();
        System.out.println(String.format("%s %s %s", oldnum, operator, newnum));
        if (operator == "*") {
            oldnum = oldnum * newnum;
        } else if (operator == "/" && newnum != 0) {
            oldnum = oldnum / newnum;
        } else if (operator == "+") {
            oldnum = oldnum + newnum;
        } else if (operator == "-") {
            oldnum = oldnum - newnum;
        } else if (operator == "") {
            oldnum = newnum;
        }
        newnum = 0;
        operator = "";
        System.out.println(String.format("result calculated: %s %s %s", oldnum, operator, newnum));
        updatenumdisplay();
    }

    public static void adddecimalbutton(Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> decimal();
        createbutton(buttonListener, ".", gridx, gridy);
    }
    public static void addbackspacebutton(Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> backspace();;
        createbutton(buttonListener, "<-", gridx, gridy);
    }
    public static void adddeletebutton(Integer gridx, Integer gridy) {
        ActionListener buttonListener = e -> delete();
        createbutton(buttonListener, "x", gridx, gridy);
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

    public static void createbutton(ActionListener buttonListener, String displaytext, Integer gridx, Integer gridy) {
        // * create a new button */
        GridBagConstraints gbc = new GridBagConstraints();
        // * set the display text */
        JButton num = new JButton(displaytext);
        // * set the data */
        num.setPreferredSize(buttonsize);
        num.setFont(buttonfont);
        num.addActionListener(buttonListener);
        // * position the button */
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        // * add the button to the window */
        frame.add(num, gbc);
    }

    public static void updatenumdisplay() {
        String tempoldnum = String.valueOf(oldnum);
        if (tempoldnum == "0.0") {
            tempoldnum = "";
        }
        String tempnewnum = String.valueOf(newnum);
        if (operator == "" && tempoldnum != "") {
            tempnewnum = "";
        }
        label.setText(String.format("%s %s %s", tempoldnum, operator, tempnewnum));
    }
}
