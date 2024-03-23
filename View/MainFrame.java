package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Controller.Control;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import Model.Config;
import Model.Model;

public class MainFrame extends JFrame
{
    private JTextField jobs_txtField;
    private JTextField totalFrame_txtField;
    private JButton calc_btn;
    private JTable output_table;
    private JScrollPane scrollPane;
    private int y;
    public MainFrame()
    {
        this.setPreferredSize(new Dimension(Config.frameWidth, Config.frameHeight));
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        //INIT COMPONENTS
        y = 2;
        int y_pad = 10;
        int x_pad = 20;

        //1st Row
            JLabel job_lbl = new JLabel("Job Queue: ");
            Dimension jobLbl_size = new Dimension(70, 25);

            jobs_txtField = new JTextField();
            Dimension jobsSize = new Dimension((int) (Config.frameWidth * .6), 25);
            
            job_lbl.setBounds((int) (Config.frameWidth - (jobsSize.getWidth() + jobLbl_size.width)) / 2, y, jobLbl_size.width, jobLbl_size.height);
            this.add(job_lbl);
            
            jobs_txtField.setBounds((int) (Config.frameWidth - jobsSize.getWidth() + jobLbl_size.width) / 2, y, jobsSize.width, jobsSize.height);
            this.add(jobs_txtField);
        
        y += jobsSize.height + y_pad;

        //2nd Row
            JLabel frame_lbl = new JLabel("Total Frames: ");
            Dimension frameLbl_size = new Dimension(80, 25);

            totalFrame_txtField = new JTextField();
            Dimension frameSize = new Dimension((int) (Config.frameWidth * .05), 25);
            
            calc_btn = new JButton("Calculate");
            Dimension calcSize = new Dimension(89, 25);
            
            frame_lbl.setBounds((int) (Config.frameWidth - (frameLbl_size.getWidth() + frameSize.width + calcSize.width + x_pad)) / 2, y, frameLbl_size.width, frameLbl_size.height);
            this.add(frame_lbl);
            
            totalFrame_txtField.setBounds((int) (Config.frameWidth - (frameSize.getWidth() + calcSize.width + x_pad) + frameLbl_size.width) / 2, y, frameSize.width, frameSize.height);
            this.add(totalFrame_txtField);

            calc_btn.setBounds((int) (Config.frameWidth - calcSize.width + frameSize.getWidth() + frameLbl_size.width) / 2, y, calcSize.width, calcSize.height);
            this.add(calc_btn);

        y += frameLbl_size.height;


        calc_btn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                generateTable();
            }
        });


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void generateTable()
    {
        Model model = Control.initModel(jobs_txtField.getText(), Integer.parseInt(totalFrame_txtField.getText()));
        String[][] data = model.getFrames();
        
        String[] columns = new String[data[0].length];
        Arrays.fill(columns, "");

        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        output_table = new JTable(tableModel);
        
        TableColumnModel colModel = output_table.getColumnModel();
        int colCount = colModel.getColumnCount();
        int width = 25;

        for (int x = 0; x < colCount; ++x)
        {
            colModel.getColumn(x).setPreferredWidth(width);
        }

        Dimension tableSize = output_table.getPreferredSize();

        if (this.scrollPane != null)
        {
            this.remove(scrollPane);
        }

        scrollPane = new JScrollPane(output_table);
        scrollPane.setBounds((Config.frameWidth - tableSize.width) / 2, y + 50, tableSize.width, (int) tableSize.height + 10);

        this.add(scrollPane);

        this.repaint();
        this.revalidate();
    }
}


/*
 *         jobs_txtField.setBounds(Config.frameWidth/2 - jobs_txtField.getPreferredSize().width/2, 0, jobs_txtField.getPreferredSize().width, jobs_txtField.getPreferredSize().width);

 */
