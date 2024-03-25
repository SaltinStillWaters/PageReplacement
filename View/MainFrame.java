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
    private ImagePanel bgPanel;
    private int y;

    public MainFrame()
    {
        this.setPreferredSize(new Dimension(Config.frameWidth, Config.frameHeight));
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        bgPanel = new ImagePanel();
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, 0, Config.frameWidth, Config.frameHeight);
        bgPanel.setBackgroundImage("/resources/bg.jpg");

        this.add(bgPanel);

        //INIT COMPONENTS
        y = 20;
        int y_pad = 10;
        int x_pad = 20;

        //1st Row
            JLabel job_lbl = new JLabel("Job Queue: ");
            job_lbl.setForeground(Color.WHITE);
            Dimension jobLbl_size = new Dimension(90, 25);

            jobs_txtField = new JTextField();
            Dimension jobsSize = new Dimension((int) (Config.frameWidth * .6), 25);
            
            job_lbl.setBounds((int) (Config.frameWidth - (jobsSize.getWidth() + jobLbl_size.width)) / 2, y, jobLbl_size.width, jobLbl_size.height);
            bgPanel.add(job_lbl);
            
            jobs_txtField.setBounds((int) (Config.frameWidth - jobsSize.getWidth() + jobLbl_size.width) / 2, y, jobsSize.width, jobsSize.height);
            bgPanel.add(jobs_txtField);
        
        y += jobsSize.height + y_pad;

        //2nd Row
            JLabel frame_lbl = new JLabel("Total Frames: ");
            frame_lbl.setForeground(Color.WHITE);
            Dimension frameLbl_size = new Dimension(105, 25);

            totalFrame_txtField = new JTextField();
            Dimension frameSize = new Dimension((int) (Config.frameWidth * .05), 25);
            
            calc_btn = new JButton("Calculate");
            Dimension calcSize = new Dimension(110, 25);
            
            frame_lbl.setBounds((int) (Config.frameWidth - (frameLbl_size.getWidth() + frameSize.width + calcSize.width + x_pad)) / 2, y, frameLbl_size.width, frameLbl_size.height);
            bgPanel.add(frame_lbl);
            
            totalFrame_txtField.setBounds((int) (Config.frameWidth - (frameSize.getWidth() + calcSize.width + x_pad) + frameLbl_size.width) / 2, y, frameSize.width, frameSize.height);
            bgPanel.add(totalFrame_txtField);

            calc_btn.setBounds((int) (Config.frameWidth - calcSize.width + frameSize.getWidth() + frameLbl_size.width) / 2, y, calcSize.width, calcSize.height);
            bgPanel.add(calc_btn);

        y += frameLbl_size.height;


        calc_btn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                generateTable();

            }
        });


        setFontForComponents(this, new Font("Helvetica", Font.BOLD, 14));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private static void centerFrameOnScreen(JFrame frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int centerX = (screenWidth - frameWidth) / 2;
        int centerY = (screenHeight - frameHeight) / 2;

        frame.setLocation(centerX, centerY);
    }

    private void generateTable()
    {
        Model model = Control.initModel(jobs_txtField.getText(), Integer.parseInt(totalFrame_txtField.getText()));
        String[][] data = model.getFrames();
        
        String[] columns = new String[data[0].length];
        Arrays.fill(columns, "");

        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        output_table = new JTable(tableModel);
        output_table.setOpaque(false);
        output_table.setBackground(new Color(173, 216, 230, 100));
        output_table.setDefaultRenderer(Object.class, new CustomRenderer(model.getFramesHistory()));

        output_table.setRowHeight(40);

        TableColumnModel colModel = output_table.getColumnModel();
        int colCount = colModel.getColumnCount();
        int width = 40;
        
        for (int x = 0; x < colCount; ++x)
        {
            colModel.getColumn(x).setPreferredWidth(width);
        }

        Dimension tableSize = output_table.getPreferredSize();

        if (this.scrollPane != null)
        {
            bgPanel.remove(scrollPane);
        }

        scrollPane = new JScrollPane(output_table);
        scrollPane.setBounds((Config.frameWidth - tableSize.width) / 2, y + 50, tableSize.width, (int) tableSize.height + 10);
        scrollPane.setBackground(new Color(173, 216, 230, 100));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        bgPanel.add(scrollPane);

        setFontForComponents(scrollPane, new Font("Helvetica", Font.BOLD, 20));

        this.repaint();
        this.revalidate();
    }

    private static void setFontForComponents(Container container, Font font)
    {
        for (Component component : container.getComponents())
        {
            if (component instanceof Container)
            {
                setFontForComponents((Container) component, font);
            }
            component.setFont(font);
        }
    }
}


/*
 *         jobs_txtField.setBounds(Config.frameWidth/2 - jobs_txtField.getPreferredSize().width/2, 0, jobs_txtField.getPreferredSize().width, jobs_txtField.getPreferredSize().width);

 */
