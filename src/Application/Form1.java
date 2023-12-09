package Application;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
public class Form1 extends JFrame {
    GaussMethod sample;
    static JButton buttonFill, buttonResolve, buttonSave, buttonShowGraph, buttonRandFill, buttonOpen;
    static JLabel lb1, lb2, lb3, imageLabel;
    static JTextField mSize, nSize;
    static JTable table;
    static DefaultTableModel tableModel;
    static JTextPane solution;
    Checked check = new Checked();
    public Form1() {
        super("Решение СЛАУ методом Гаусса");
        buttonFill = new JButton("Заполнить матрицу");
        buttonResolve = new JButton("Решить систему");
        buttonSave = new JButton("Сохранить в файл");
        buttonShowGraph = new JButton("Показать график");
        buttonRandFill = new JButton("Заполнить случайно");
        buttonOpen = new JButton("Открыть из файла");

        nSize= new JTextField("");
        mSize= new JTextField("");
        lb1 = new JLabel("Введите количество уравнений:");
        lb2 = new JLabel("Введите количество переменных:");
        lb3 = new JLabel("Решение СЛАУ:");
        solution = new JTextPane();
        imageLabel = new JLabel(new ImageIcon("C:\\Users\\higheroffpropane\\Desktop\\3 КУРС\\КУРСАЧ\\coursework\\line.png"));
        imageLabel.setVisible(true);
        imageLabel.setBounds(10,120,540,90);
        setSize(600,800);
        buttonFill.setBounds( 240, 20,150,20);
        buttonResolve.setBounds(240,80, 150, 20);
        buttonRandFill.setBounds(240,50, 150, 20);
        buttonShowGraph.setBounds(400,20, 150, 20);
        buttonOpen.setBounds(400,50, 150, 20);
        buttonSave.setBounds(400,80, 150, 20);
        mSize.setBounds(10,20,200,30);
        nSize.setBounds(10,70,200,30);
        lb1.setBounds(mSize.getX(), mSize.getY() - 20, 300, 20 );
        lb2.setBounds(nSize.getX(), nSize.getY() - 20, 300, 20 );
        add(buttonFill);
        add(buttonResolve);
        add(buttonSave);
        add(buttonShowGraph);
        add(buttonRandFill);
        add(buttonOpen);
        add(mSize);
        add(lb1);
        add(nSize);
        add(lb2);
        add(lb3);
        add(solution);
        add(imageLabel);
        setLayout(null);
        setVisible(true);
        buttonFill.addActionListener(check);
        buttonResolve.addActionListener(check);
        buttonSave.addActionListener(check);
        buttonShowGraph.addActionListener(check);
        buttonRandFill.addActionListener(check);
        buttonOpen.addActionListener(check);
        table = new JTable(tableModel);
    }
    private class Checked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == buttonFill) { // событие произошло у кнопки
                remove(table);
                int n = Integer.parseInt(nSize.getText());
                int m = Integer.parseInt(mSize.getText());
                if (n > 20 | m > 20) {
                    JOptionPane.showMessageDialog(null, "Слишком большое число"," ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                sample = new GaussMethod(m,n);
                table = new JTable(m, n + 1);
                table.setBorder(new LineBorder(new Color(41, 101, 222)));
                table.setSelectionBackground(Color.LIGHT_GRAY);
                //table.setVisible(true);
                table.setLocation(10,150);
                table.setSize(540, m*20);
                table.setRowHeight(16);
                table.setAutoResizeMode(4);
                add(table);
                setVisible(true);
                lb3.setBounds(10, 150 + m * 20 + 20, 300, 20 );
                solution.setBounds(10, 150 + m * 20 + 40, 540, 200);
                revalidate();
            }
            if(event.getSource() == buttonResolve) {
                Double val = null;
                String str;
                for(int i = 0; i <
                        Integer.parseInt(mSize.getText()); i++) {
                    for(int j = 0; j <
                            Integer.parseInt(nSize.getText())+1; j++) {
                        try {
                            str = table.getModel().getValueAt(i, j).toString();
                            val = Double.valueOf(str);
                            sample.set(i, j, val);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Таблица заполнена некорректно");
                            return;
                        }
                    }
                }
                System.out.println(sample);
                sample.rightGaussianStroke();
                sample.backGaussianStroke();
                StyledDocument doc = solution.getStyledDocument();
                SimpleAttributeSet keyWord = new SimpleAttributeSet();
                try
                {
                    doc.remove(0, doc.getLength());
                    doc.insertString(0, sample.answer(),null );
                }
                catch(Exception e) { System.out.println(e);
                }
                System.out.println(sample.answer());
            }
            if(event.getSource() == buttonSave) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                        fw.write("");
                        sample.saveToFile(fc.getSelectedFile());
                    }
                    catch (IOException e ) {
                        System.out.println("Всё погибло!");
                    }
                }
            }
            if(event.getSource() == buttonShowGraph) {
                Icon gr = new ImageIcon("C:\\Users\\higheroffpropane\\Desktop\\3 КУРС\\КУРСАЧ\\coursework\\graph.png");
                JOptionPane.showMessageDialog(null, "","График зависимости времени решения системы от количества уравнений", JOptionPane.WARNING_MESSAGE, gr);
            }
            if(event.getSource() == buttonRandFill) {
                for(int i = 0; i < Integer.parseInt(mSize.getText()); i++) {
                    for(int j = 0; j < Integer.parseInt(nSize.getText())+1; j++) {
                        try {
                            double rand = (int)(Math.random() * 50);
                            table.getModel().setValueAt(rand, i, j);
                            //sample.set(i, j, rand);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Таблица заполнена некорректно");
                            return;
                        }
                    }
                }
            }
            if(event.getSource() == buttonOpen) {
                Scanner sc = null;
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    try {
                        sc = new Scanner(new BufferedReader(new FileReader(selectedFile.getAbsolutePath())));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                int rows;
                int columns;
                int count = 0;
                assert sc != null;
                String[] lineIn = sc.nextLine().trim().split(" ");
                rows = Integer.parseInt(lineIn[0]);
                columns = Integer.parseInt(lineIn[1]) + 1;
                sample = new GaussMethod(rows,columns-1);
                while(sc.hasNextLine() && (count < rows)) {
                    for (int i = 0; i < rows; i++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int j = 0; j < line.length; j++) {
                            try {
                                sample.set(i,j,Float.parseFloat(line[j]));
                            }
                            catch (InputMismatchException | NumberFormatException ex) {
                                System.out.println(ex.getMessage());
                                JOptionPane.showMessageDialog(null, "Файл заполнен некорректно");
                                return;
                            }
                        }
                        count += 1;
                    }
                }
                tableModel = new DefaultTableModel(rows, columns);
                table = new JTable(tableModel);
                table.setBorder(new LineBorder(new Color(41, 101, 222)));
                table.setSelectionBackground(Color.LIGHT_GRAY);
                table.setVisible(true);
                table.setLocation(10,340);
                table.setSize(580, rows * 20);
                table.setRowHeight(16);
                table.setAutoResizeMode(4);
                add(table);
                lb3.setBounds(10, 340 + rows * 20 + 20, 300, 20 );
                solution.setBounds(10, 340 + rows * 20 + 40, 580, 200);
                revalidate();
                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < columns; j++) {
                        try {
                            table.getModel().setValueAt(sample.get(i,j), i, j);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Таблица заполнена некорректно");
                            return;
                        }
                    }
                }
            }
        }
    }
}