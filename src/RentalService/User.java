package RentalService;

import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class User implements Serializable {
	private String name; // �̸�
	private String phone; // ��ȭ��ȣ
	private String rentalDay; // �뿩 ��¥
	private String returnDay; // �ݳ� ���� ��¥
	private String[] codeList = new String[3]; // �뿩�� ��ǰ �ڵ� �迭
	private int[] payList = new int[3]; // �뿩 �ݾ� �迭
	private int rentalCount = 0; // �뿩 ��ǰ �ڵ� / �ݾ� �迭 �ε��� ī��Ʈ
	Calendar getToday = Calendar.getInstance(); // ���� ��¥
	
	// �μ� ���� ������
	User() {}
	
	// �μ� �ִ� ������
	User(String name, String phone, String rentalDay, String returnDay)
	{
		this.name = name;
		this.phone = phone;
		this.rentalDay = rentalDay;
		this.returnDay = returnDay;
	}
	
	
	// ++ �� ���� ������ ���ϰ�ü�� write �ϴ� �Լ� ����
	// �Է¹��� �� ������ �о ���Ͽ� ���� �Լ�
//	public void saveUserToFile(ObjectOutputStream oos) throws Exception
//	{
//		// oos ��ü�� User ��ü�� ���Ͽ� ����
//		try {
//			User u = new User(name, phone, rentalDay, returnDay);
//        	oos.writeObject(u);
//			
//			// feedback ++ : dos.writeInt(rentalCount); �뿩�� ��ǰ�� ���ڰ� �����
//			// dos.writeInt(rentalCount);
//        	oos.writeObject(rentalCount);
//			for(int i =0; i < rentalCount ; i++) {
//				oos.writeObject(codeList[i]); // ���Ͽ� �뿩�� ��ǰ �ڵ�� �ۼ� (���ڿ�)
//				oos.writeObject(payList[i]); // ���Ͽ� �뿩�� ��ǰ ���ݵ� �ۼ� (������)
//			}
//		}
//		catch(IOException e) { // Exception �߻� �� �ش� ���� ���
//			throw new Exception("���Ϸ� ����� �� �����ϴ�");
//		}
//	}
	
	
	// ���Ͽ��� �� ���� �о���� �Լ�
	public User readUserFile(ObjectInputStream ois) throws Exception
	{
		// ois ��ü�� User ��ü�� �о
		User u = null;
		try {
			u = (User) ois.readObject();
				
			Integer rentalCount = (Integer) ois.readObject(); // �뿩 ���� �б�
			for (int j = 0; j < rentalCount.intValue(); j++) { // �뿩 ������ŭ �ݺ��Ͽ� ���� �뿩 �迭�� �߰�
				String rentalProductCode = (String)ois.readObject(); //�뿩 ��ǰ�� �ڵ� �б�
				Integer rentalProductPrice = (Integer)ois.readObject(); //�뿩 ��ǰ�� ���� �б�
				u.addProduct(rentalProductCode, rentalProductPrice); //�뿩 ����Ʈ�� �߰�
			}
		}
		catch(FileNotFoundException fnfe) {
			throw new Exception("������ �������� �ʽ��ϴ�.");
		}
		catch(IOException e) { // Exception �߻� �� �ش� ���� ��� 
			throw new Exception("���Ϸ� ����� �� �����ϴ�");
		}
		return u; // ��ȯ
	}
	
		
	// �̸� ��ȯ
	public String getName()
	{
		return name;
	}
	
	// ��ȭ��ȣ ��ȯ
	public String getPhone()
	{
		return phone;
	}
	
	// �뿩 ���� ��ȯ
	public String getRentalDay()
	{
		return rentalDay;
	}
	
	// �ݳ� ���� ���� ��ȯ
	public String getReturnDay()
	{
		return returnDay;
	}
	
	// �뿩 ��ǰ �ڵ� �迭 �ε��� ī��Ʈ ��ȯ
	public int getRentalCount() {
		return rentalCount;
	}
	
	// �뿩 ��ǰ �ڵ�/ �ݾ� �迭�� �ڵ� �߰�
	public void addProduct(String code, int money)
	{
		codeList[rentalCount] = code;
		payList[rentalCount] = money;
		rentalCount++;
	}
	
	// �뿩 ��ǰ �ڵ� �迭 i��° ����
	public String codeAt(int i)
	{
		// �뿩 ��ǰ �ڵ� �迭 i��° ��ǰ ��ü return
		return codeList[i];
	}
	
	// �뿩 �ݾ� �迭 i��° ����
	public int payAt(int i)
	{
		// �뿩 �ݾ� �迭 i��° ��ǰ ��ü return
		return payList[i];
	}
	
	// ���� �Լ�
	public int pay() throws Exception
	{
		// ���� �ݾ� �� ����ϱ�
		int money = 0;
		for(int i = 0; i < rentalCount; i++)
		{
			money += payList[i];
		}
		
		// �ݳ� ���� �Է��ϱ�
		getToday.setTime(new Date());
		
		// �뿩 ���� �ҷ�����
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDay);
		Calendar rentalDate = Calendar.getInstance();
		rentalDate.setTime(date1);
		
		// �ݳ� ���� ���� �ҷ�����
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(returnDay);
		Calendar returnDate = Calendar.getInstance();
		returnDate.setTime(date2);
		
		// �뿩�ϱ�� �� �Ⱓ ����ϱ� (�ݳ� ���� ���� - �뿩 ����)
		// �и��ʸ� ���� ���� �� �� ���� ��ȯ
		int day1 = (int) ((returnDate.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));

		// �뿩 �Ⱓ ����ϱ� (�ݳ� ���� - �뿩 ����)
		// �и��ʸ� ���� ���� �� �� ���� ��ȯ
		int day2 = (int) ((getToday.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		/* �ݾ� ���� ��Ģ ����
		 * ��¥ ���Ѽ� �ݳ� => ��ǰ �ݾ� �� �� x �뿩 �Ⱓ
		 * ���� �ݳ� => ��ǰ �ݾ� �� �� x ���� �뿩 �Ⱓ(���� �Ⱓ�� ���� å�� x)
		 * ���߿� �ݳ� => ��ǰ �ݾ� �� �� x ���� �뿩 �Ⱓ + ��ü��(�Ϸ� �뿩��) */
		
		// �� �ݾ� ����ϱ�
		if (day1 == day2) // �ݳ� ���� ��¥�� �ݳ�
			return money *= (day1 + 1); // ���� �뿩 �ݳ��� 1�Ϸ� ó��
		
		else if (day1 > day2)// �ݳ� ���� ��¥���� ���� �ݳ�
			return money *= (day2 + 1); // ���� �뿩 �ݳ��� 1�Ϸ� ó��
		
		else // �ݳ� ���� ��¥���� �ʰ� �ݳ�
			return money *= (day2 + 2); // ��ü�� (�Ϸ� �뿩��) �߰��Ͽ��� ��ȯ
	}
	
	@Override
	public boolean equals(Object o) {
		// o�� User ��ü�� �ƴϸ�
		if (!(o instanceof User))
			// false ��ȯ
			return false;
		User u = (User) o;
		// ��ȭ��ȣ�� ������ true, �ٸ��� false ��ȯ
		return u.getPhone().equals(this.getPhone());
	}
}