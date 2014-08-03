package flyser;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.io.*;
import javax.swing.SwingConstants;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
public class flyser extends JFrame {
 
    public void analyse(String a) throws IOException
{
    JFrame frame = new JFrame();
    frame.setLayout(new FlowLayout(10,70,35));
    frame.setTitle("File analyser V2");
    frame.setSize(550,400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(Color.orange);
    JLabel label = new JLabel();
    JLabel label2 = new JLabel();
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label2.setHorizontalAlignment(SwingConstants.RIGHT);
    File theFile = new File(a);
    String basename = theFile.getName();
    String[] ext = basename.split("\\.(?=[^\\.]+$)");
    JLabel label3 = new JLabel();
    Path path = Paths.get(a);
    Map f  = Files.readAttributes(path, "*");
    String attr = f.toString();
    String out[] = attr.split(",");
    out[0]= out[0].replace("{"," ");
    out[8]= out[8].replace("}"," ");
    Object boiler[] = {"File Attributes"};
    DefaultTableModel model = new DefaultTableModel(boiler,0);
    for (int i=0;i<9; i++)
    {
        Vector<String> vector = new Vector<String>(Arrays.asList(out[i]));
        model.addRow(vector);
    }
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    JTable jTable1 = new JTable();
    JScrollPane spTable = new JScrollPane(jTable1);
    jTable1.setBackground(Color.pink);
    jTable1.setPreferredSize(new Dimension(300, 145));
    spTable.setPreferredSize(new Dimension(350, 168));
    jTable1.setModel(model);
    panel.add(spTable);
    		try 
			{	
				char c;
				String enc="Cp1252";
				BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(a), enc));
				String str = new String (in.readLine());
				String chk[] = {"‰PNG","MZ","FLV","%PDF","PK","7z","BZh","ÐÏ","Êþ"};
                             if (str == null) 
				{
				label.setText("~~>file error<--");
				}
				else if (str.startsWith(chk[0]))
				{
				label.setText("~~> its a portable network graphics image");
				}
				else if (str.startsWith(chk[1]))
				{
				label.setText("~~> its a windows application file");
				}
				else if (str.startsWith(chk[2]))
				{
				label.setText("~~> its a flash video FLV file");
				}
                                else if ((str.matches("(.*)mp4(.*)" )) || (str.matches("(.*)isom(.*)"))|| (str.matches("(.*)3gp4(.*)")))
				{
				label.setText("~~> its a mp4 video file");
				}
                                else if (str.startsWith(chk[3]))
                                {
                                    label.setText("~~> its a PDF document");
                                }
                                
                                else if (str.startsWith(chk[4]))
                                {
                                        if (str.matches("(.*)AndroidManifest(.*)"))
                                            {
                                            label.setText("~~> its a android app package (APK) file");
                                            }
                                        else
                                        {
                                                label.setText("~~> its a Archive file");
                                        }
                                }
                                 else if (str.startsWith(chk[5]))
                                {
                                     label.setText("~~> its a 7z compressed file");
                                }
                                 else if (str.startsWith(chk[6]))
                                {
                                     label.setText("~~> its a tarball bzip2 compressed file");
                                }
                                 else if (str.startsWith(chk[7]))
                                {
                                     label.setText("~~> its a Microsoft document .doc file");
                                }
                                
                                 else if (str.startsWith(chk[8]))
                                {
                                     label.setText("~~> its a java class file");
                                }
                                
				else 
				{
					label.setText("unknown file type ");
				}
			}
                  catch (IOException e) 
	    {
			label.setText(e.getMessage());
	    }
     
    label2.setText("File name: "+ext[0]);
    label3.setText("Extension: " +ext[1]);
    frame.add(label2);
    frame.add(label3);
    frame.add(label);
    frame.getContentPane().add(panel);
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
   }  
   public static void main(String args[]) 
	{
		final String dir=args[0];	
    SwingUtilities.invokeLater(new Runnable() 
    {
        @Override
        public void run() 
        {
                flyser analyse = new flyser();
            try 
            {
                analyse.analyse(dir);
            } catch (IOException ex)
            {
                Logger.getLogger(flyser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
  }
}
