
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
	JTextField text;
	JPanel a1;
	static Box vertical = Box.createVerticalBox();
	static JFrame t = new JFrame();
	static DataOutputStream dout;
	
	Server()
	{
		t.setLayout(null);
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,138,94));
		p1.setBounds(0,0,450,68);
		p1.setLayout(null);
		t.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/s3.png"));
		Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5,20,25,25); 
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter()
				{
			public void mouseClicked(MouseEvent ae)
			{
				System.exit(0);
			}
				});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("img/Naem.jpeg"));
		Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(30,3,70,70);
		p1.add(profile);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("img/vd.png"));
		Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel video = new JLabel(i9);
		video.setBounds(270,20,25,25);
		p1.add(video);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("img/vd2.png"));
		Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(330,20,25,25);
		p1.add(phone);
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("img/t3.png"));
		Image i14 = i13.getImage().getScaledInstance(10,20,Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel morevert = new JLabel(i15);
		morevert.setBounds(380,22,10,20);
		p1.add(morevert);
		
		JLabel name = new JLabel("Naem_Akhter");
		name.setBounds(110, 15, 100,22);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
		p1.add(name);
		
		JLabel status = new JLabel("Active now");
		status.setBounds(110, 35, 100,20);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
		p1.add(status);
		
		a1 = new JPanel();
		a1.setBounds(5,75,440,500);
		t.add(a1);
		
		text = new JTextField();
		text.setBounds(5,600,310,40);
		text.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
		t.add(text);
		
		JButton send = new JButton("send");
		send.setBounds(320,600,120,40);
		send.setBackground(new Color(7,138,94));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
		send.addActionListener(this);
		t.add(send);
		
		
		
		t.setSize(450,650);
		t.setLocation(200,50);
		t.setUndecorated(true);
		t.getContentPane().setBackground(Color.WHITE);
		t.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try {
			String out = text.getText();
			
			JPanel p2 = formatLabel(out);
			
			a1.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			a1.add(vertical,BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			
			text.setText("");
			
			t.repaint();
			t.invalidate();
			t.validate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static JPanel formatLabel(String out) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 150px\">" +out+ "</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN,16));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}

	public static void main(String[] args) {
		new Server();
		
		try {
			
			ServerSocket skt = new ServerSocket(6001);
			
			while(true)
			{
				Socket s = skt.accept();
				
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while(true)
				{
					String msg = din.readUTF();
					JPanel panel = formatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel,BorderLayout.LINE_START);
					vertical.add(left);
					t.validate();
					
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}

	}

}

