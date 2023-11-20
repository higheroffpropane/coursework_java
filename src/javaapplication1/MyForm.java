package javaapplication1;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
public class MyForm extends JFrame{
    GaussMethod sample;
    static JButton button, button2, button3, button4, button5, button6;
    static JLabel lb1, lb2, imageLabel, lb3;
    static JTextField mSize, nSize;
    static JTable table;
    static DefaultTableModel tableModel;
    static JTextPane solution;
    Checked check = new Checked();
    public MyForm() {
        super("Решение СЛАУ методом Гаусса");
        button = new JButton("Заполнить матрицу");
        button2 = new JButton("Решить систему");
        button3 = new JButton("Сохранить в файл");
        mSize= new JTextField("0");
        lb1 = new JLabel("Введите количество уравнений:");
        nSize= new JTextField("0");
        lb2 = new JLabel("Введите количество переменных:");
        lb3 = new JLabel("Решение СЛАУ:");
        button4 = new JButton("Показать график");
        button5 = new JButton("Заполнить случайно");
        button6 = new JButton("Открыть из файла");
        solution = new JTextPane();
        imageLabel = new JLabel(new
                ImageIcon("C:\\Users\\higheroffpropane\\Desktop\\3 КУРС\\КУРСАЧ\\coursework\\ea702cc03837dab3bbed8d5c64266130.png"));
                imageLabel.setVisible(true);
                imageLabel.setBackground(Color.BLACK);
        imageLabel.setBounds(400,200,150,150);
        setSize(600,800);
        button.setBounds( 400, 20,150,20);
        button2.setBounds( 400, 50,150,20);
        button4.setBounds(400,80, 150, 20);
        button5.setBounds(400,110, 150, 20);
        button6.setBounds(400,140, 150, 20);
        button3.setBounds( 400, 170,150,20);
        mSize.setBounds(10,20,200,30);
        nSize.setBounds(10,70,200,30);
        lb1.setBounds(mSize.getX(), mSize.getY() - 20, 300,
                20 );
        lb2.setBounds(nSize.getX(), nSize.getY() - 20, 300,
                20 );
//        lb3.setBounds(10, 510, 300, 20 );
//        solution.setBounds(10, 530, 580, 200);
//table = new JTable(1,0);
        add(button);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
        add(mSize);
        add(lb1);
        add(nSize);
        add(lb2);
        add(lb3);
        add(solution);
        add(imageLabel);
        setLayout(null);
        setVisible(true);
        button.addActionListener(check);
        button2.addActionListener(check);
        button3.addActionListener(check);
        button4.addActionListener(check);
        button5.addActionListener(check);
        button6.addActionListener(check);
        table = new JTable(tableModel);
    }
    private class Checked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == button) { // событие произошло у кнопки
                remove(table);
                int n = Integer.parseInt(nSize.getText());
                int m = Integer.parseInt(mSize.getText());
                sample = new GaussMethod(m,n);
                tableModel = new DefaultTableModel(m,n + 1);
//System.out.println(m+"\n");
                table = new JTable(tableModel);
                table.setBorder(new LineBorder(new Color(41, 101, 222)));
                table.setSelectionBackground(Color.LIGHT_GRAY);
                table.setVisible(true);
                table.setBounds(10, 340, m * 65, n * 20 - 20);
//table.setVisible(true);
//table.getModel().setValueAt(12,0, 0);
                add(table);
                lb3.setBounds(10, 340 + n * 20 + 20, 300, 20 );
                solution.setBounds(10, 340 + n * 20 + 40, 580, 200);
                revalidate();
            }
            if(event.getSource() == button2) {
                Double val = null;
                String str;
                for(int i = 0; i <
                        Integer.parseInt(mSize.getText()); i++) {
                    for(int j = 0; j <
                            Integer.parseInt(nSize.getText())+1; j++) {
                        try {
                            str = table.getModel().getValueAt(i, j).toString();
//System.out.print("STR:" + str + " ");
                            val = Double.valueOf(str);
//System.out.println("DOUBLE:" + val + " ");
                            sample.set(i, j, val);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Таблица заполнена некорректно");
                            return;
                        }
//System.out.print(table.getModel().getValueAt(i,j) + " ");
                    }
//System.out.println();
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
// solution.setText(sample.answer());
                System.out.println(sample.answer());
            }
            if(event.getSource() == button3) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(null) ==
                        JFileChooser.APPROVE_OPTION) {
                    try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                        fw.write("");
                        sample.saveToFile(fc.getSelectedFile());
                    }
                    catch (IOException e ) {
                        System.out.println("Всё погибло!");
                    }
                }
// sample.saveToFile();
            }
            if(event.getSource() == button4) {
                Icon gr = new
                        ImageIcon("C:\\Users\\higheroffpropane\\Desktop\\3 КУРС\\КУРСАЧ\\coursework\\graph.pngа");
                        JOptionPane.showMessageDialog(null, "","График зависимости времени решения системы от количества уравнений", JOptionPane.WARNING_MESSAGE, gr);
            }
            if(event.getSource() == button5) {
                for(int i = 0; i < Integer.parseInt(mSize.getText()); i++) {
                    for(int j = 0; j < Integer.parseInt(nSize.getText())+1; j++) {
                        try {
                            double rand = (int)(Math.random() * 50);
                            table.getModel().setValueAt(rand, i, j);
// val = Double.valueOf(str);
                            sample.set(i, j, rand);
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Таблица заполнена некорректно");
                            return;
                        }
//System.out.print(table.getModel().getValueAt(i,j) + " ");
                    }
//System.out.println();
                }
            }
            if(event.getSource() == button6) {
                Scanner sc = null;
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
//System.out.println(selectedFile.getAbsolutePath());
                    try {
                        sc = new Scanner(new BufferedReader(new FileReader(selectedFile.getAbsolutePath())));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                int rows;
                int columns;
                assert sc != null;
                String[] lineIn = sc.nextLine().trim().split(" ");
                rows = Integer.parseInt(lineIn[0]);
                columns = Integer.parseInt(lineIn[1]) + 1;
                sample = new GaussMethod(rows,columns-1);
//int [][] myArray = new int[rows][columns];
                while(sc.hasNextLine()) {
                    for (int i = 0; i < rows; i++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int j = 0; j < line.length; j++) {
                            sample.set(i,j,Integer.parseInt(line[j]));
//myArray[i][j] = Integer.parseInt(line[j]);
                        }
                    }
                }
                tableModel = new DefaultTableModel(rows, columns);
//System.out.println(m+"\n");
                table = new JTable(tableModel);
                table.setBorder(new LineBorder(new Color(41, 101, 222)));
                table.setSelectionBackground(Color.LIGHT_GRAY);
                table.setVisible(true);
                table.setBounds(10, 140, rows * 125, columns * 20);
                add(table);
                revalidate();
//System.out.println(Arrays.deepToString(myArray));
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
//System.out.print(table.getModel().getValueAt(i,j) + " ");
                    }
//System.out.println();
                }
            }
        }
    }
}