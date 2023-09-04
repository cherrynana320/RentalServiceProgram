package RentalService;

import java.io.*;
import java.util.ArrayList;

public class Manager {
	// private Product[]productList; // ��ǰ �迭
	private ArrayList<Product> productArrayList = new ArrayList<Product>(); // ��ǰ ����Ʈ
	
	// private User[]userList; // �뿩 �迭
	private ArrayList<User> userArrayList = new ArrayList<User>(); // �뿩 ����Ʈ
	
	private Integer revenue = 0; // ���� ���� �Ѿ� ����
	
	// �μ� ���� ������
	Manager() {}
	
	// �μ� �ִ� ������
	Manager (ObjectInputStream ois) throws Exception{
		// ���� read�ϱ�
		// ������ȭ
		try {
			Integer pCount = (Integer)ois.readObject(); // ��ǰ ���� �б�
			for (int i = 0; i < pCount.intValue(); i++) // ��ǰ ������ŭ �ݺ��Ͽ�
	        {
				add(new Product().readProductFile(ois)); // ��ǰ ����Ʈ�� �߰�
	        }
			
			Integer uCount = (Integer)ois.readObject(); // �� �� �б�
			for (int i = 0; i < uCount.intValue(); i++) // �� ����ŭ �ݺ��Ͽ�
	        {
				userArrayList.add(new User().readUserFile(ois)); // ���� ����Ʈ�� �߰�
	        }
			revenue = (Integer)ois.readObject(); // ���� �б�
		}
		catch (FileNotFoundException fnfe) { // rentalData ������ �������� ���� ��
				throw new Exception("������ �������� �ʽ��ϴ�"); 
		}
	}

	
	// ���Ͽ� ������ ���� �Լ�
	// ����ȭ
	public void saveToFile(ObjectOutputStream oos) throws Exception
	{
		// UiŬ�������� oos�� �Ű������� �޾� write �ϰ���� data�� write �Ѵ�.
		try {
			// dos.writeInt(productArrayList.size()); // ��ǰ�� ���� ���Ͽ� ����.
			Integer pCount = (Integer)(productArrayList.size());
			oos.writeObject(pCount);
			for (int i = 0; i < pCount; i++) { // ��ǰ ���� ���� �Լ� �ݺ� ����
				oos.writeObject(productArrayList.get(i));
			}
			
			// dos.writeInt(userArrayList.size()); // ����� �� ���Ͽ� ����.
			Integer uCount = (Integer)(userArrayList.size());
			oos.writeObject(uCount);
			for (int i = 0; i < userArrayList.size(); i++) { // �� ���� ���� �Լ� �ݺ� ����
				oos.writeObject(userArrayList.get(i));
			}
			oos.writeObject(revenue); // ���Ͽ� ����� �ۼ� (������)
		}
		catch(IOException e) { // Exception �߻� �� �ش� ���� ���
			throw new Exception("���Ϸ� ����� �� �����ϴ�");
		}
	}
	


	// �ڵ� �ߺ� �˻�
	public void checkCode(Product p) throws Exception {
		// ��ǰ �ڵ尡 �̹� ������
		if (productArrayList.indexOf(p) >= 0)
			// ���� �߻�
			throw new Exception("��ǰ ����� �Ұ��մϴ�.");
	}
	
	
	// ��ǰ �߰�
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // �ڵ� �ߺ� �˻�
			productArrayList.add(p); // ��ǰ �߰�
		}
		catch (Exception e) {
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
	}
	
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		try {
			int number = search(productCode); // ��ǰ �迭���� �˻��ϱ�
			productArrayList.remove(number); // ��ǰ �迭���� �����ϱ�
		}
		catch (Exception e) {
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}
	
	// ��ǰ ��ü �˻�
	// ���� ��ǰ �ڵ�� �´� ��ǰ�� list �ε������� return
	public int search(String productCode) throws Exception {		
		// �μ��� ���� ��ǰ �ڵ�� ���� �ڵ带 ���� ��ǰ�� �ִ��� ã�� 
		int index = productArrayList.indexOf(new Product(productCode));
		
		// �ڵ尡 ��ġ�ϴ� ��ǰ�� ������
		if (index >= 0)
			return index; // �ε��� ��ȯ
		else // ��ǰ�� ������
			throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�."); // �ͼ��� �߻�
	}
	

	// ��ǰ �迭 i��° ����
	public Product productAt(int i) throws Exception {
		try {
			return productArrayList.get(i); // ��ǰ �迭 i��° ��ǰ ��ü return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// productArrayList�� size �� ��ȯ (pCount ���)
	public int getProductCount() {
		return productArrayList.size();
	}
	
	// ��ȭ��ȣ �ߺ� �˻�
	public void checkPhone(User u) throws Exception {
		// ��ȭ��ȣ�� �̹� ������
		if (userArrayList.indexOf(u) >= 0)
			// ���� �߻�
			throw new Exception("�߸��� ����� üũ���Դϴ�.");
	}
	
	// ��� �������� �뿩 ���� ����
	public void subStock(User u) throws Exception {
		// ��� �������� �뿩 ���� �����ϱ�
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			int searchNum;
			try {
				searchNum = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
			} 
			catch (Exception e) {
				throw new Exception("�߸��� ����� üũ���Դϴ�.");
			}
			
			Product p = productArrayList.get(searchNum); // �ش� �ε����� product ��ü get�Լ��� ��������
			
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// �뿩 ����Ʈ�� ���� �߰�
	public void addUser(User u) {
		userArrayList.add(u); 
	}
	
	// üũ��
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // ��ȭ��ȣ �ߺ� �˻�
			subStock(u); // ��� �������� �뿩 ���� ����
			addUser(u); // �뿩 �迭�� �뿩 ���� �ֱ�
		}
		catch(Exception e) {
			throw new Exception("�߸��� ����� üũ���Դϴ�.");
		}
	}
	
	// userCount �� ��ȯ
	public int getUserCount() {
		return userArrayList.size();
	}
	
	// �뿩 �迭 i��° ����
	public User userAt(int i)
	{
		// return userList[i]; // �뿩 �迭 i��° ���� ��ü return
		return userArrayList.get(i); // arrayList�� get�Լ��� i��° ���� ��ü return
	}
	
	// ��ġ�ϴ� ȸ����ȣ �˻�
	public int searchUser(String phone) throws Exception {
		
		for(int i = 0; i < userArrayList.size(); i++)
		{
			// User u = userList[i];
			User u = userArrayList.get(i);
			// ��ġ�ϴ� ������ ������ �ε��� ��ȣ ��ȯ
			if (u.getPhone().equals(phone))
				return i;
		}throw new Exception ("ȸ�������� �����ϴ�."); // ��ġ�ϴ� ������ ������ �ͼ��� �߻�
	}
	
	// ��ǰ ��� �ٽ� �߰�
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			try {
				int number = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
				productAt(number).addNumber(); //�ش� �ε����� product ��ü�� ��� �߰��ϱ�
			}
			catch (Exception e) {
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
	}
	
	// �뿩 �迭�� ���� ����
	public void subUser(int number) {
		userArrayList.remove(number); // arrayList�� remove�Լ��� ���� ����
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			
			int money = userArrayList.get(index).pay(); // �ݾ� ��ȯ�ޱ�
			
			subUser(index); // userList���� ����, �迭 �����ϱ�
			revenue += money; // ���⿡ �߰��ϱ�
		}
		catch(Exception e) {
			throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
		}
	}
	
	// ���� ��ȯ
	public int getRevenue() {
		return revenue;
	}
	
	
	// ++ ��ǰ ����Ʈ�� index��° ��ǰ��ü�� ��� n�� �̻��� ��� ��� n�� ������. n�� ������ ���� exception �߻�
	void substock(int index, int n) throws Exception
	{
		Product p = productArrayList.get(index); // index��° ��ǰ��ü 
		int pn = p.getProductNumber(); // index��° ��ǰ��ü�� ��� ���� �ҷ�����
		try
		{
			if(pn >= n ) // n�� �̻��� ����� ���, n���� ����
			{
				p.setProductNumber(pn-n);
			}
			else // n�� ������ ����� ���, exception �߻�
			{
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
		catch(Exception e)
		{
			System.out.println("n�� ������ ���� exception �߻�");
		}
	}

	
	
	
	
	// ++ ��ǰ ����Ʈ�� index��° ��ǰ��ü�� ��� n�� ������.
	void addstock(int index, int n) {
		Product p = productArrayList.get(index); // index��° ��ǰ��ü 
		int pn = p.getProductNumber();  // index��° ��ǰ��ü�� ��� ���� �ҷ�����
		p.setProductNumber(pn+n); //��� n�� ����
	}

	

	 
	
	
	
}