package RentalService;

import java.io.*;
import java.util.ArrayList;


public class Manager2 {
	// private Product[]productList; // ��ǰ �迭
	private ArrayList<Product> productArrayList = new ArrayList<Product>(); // ��ǰ ����Ʈ
	private int pCount = 0; // ��ǰ �迭 �ε��� ī��Ʈ
	
	// private User[]userList; // �뿩 �迭
	private ArrayList<User> userArrayList = new ArrayList<User>(); // �뿩 ����Ʈ
	private int uCount = 0; // �뿩 �迭 �ε��� ī��Ʈ
	
	private int revenue = 0; // ���� ���� �Ѿ� ����
	
	// �μ� ���� ������
	Manager() {}
	
	// �μ� �ִ� ������ (��ǰ �迭, �뿩 �迭 ũ�� ����)
	// Manager (int maxProductCount, int maxUserCount, String answer) throws Exception{
	Manager (ObjectInputStream in) throws Exception{
		// productList = new Product[maxProductCount]; // ��ǰ �迭 ũ�� ����
		// userList = new User[maxUserCount]; // �뿩 �迭 ũ�� ����
	
		// ���� read�ϱ�
		try {
			int productCount = in.readInt(); // ��ǰ ���� �б�
			for (int i = 0; i < productCount; i++) // ��ǰ ������ŭ �ݺ��Ͽ�
	        {
				add(new Product().readProductFile(in)); // ��ǰ ����Ʈ�� �߰�
	        }
			
			int userCount = in.readInt(); // �� �� �б�
			for (int i = 0; i < userCount; i++) // �� ����ŭ �ݺ��Ͽ�
	        {
				addUser(new User().readUserFile(in)); // �� ����Ʈ�� �߰�
	        }
			revenue = in.readInt(); // ���� �б�
		} 
		catch (FileNotFoundException fnfe) { // rentalData ������ �������� ���� ��
				throw new Exception("������ �������� �ʽ��ϴ�"); 
		}
	}

	
	// ���Ͽ� ������ ���� �Լ�
	public void saveToFile(ObjectOutputStream out) throws Exception
	{
		// UiŬ�������� dos�� �Ű������� �޾� write �ϰ���� data�� write �Ѵ�.
		try {
			int pc = productArrayList.size();
			out.writeInt(pc); // ��ǰ�� ���� ���Ͽ� ����.
			for (int i = 0; i < pc; i++) { // ��ǰ ���� ���� �Լ� �ݺ� ����					
				// productList[i].saveProductToFile(dos);
				productArrayList.get(i).saveProductToFile(out);
			}
			
			int uc = userArrayList.size();
			out.writeInt(uc); // ����� �� ���Ͽ� ����.
			for (int i = 0; i < uc; i++) { // �� ���� ���� �Լ� �ݺ� ����
				// userList[i].saveUserToFile(dos);
				userArrayList.get(i).saveUserToFile(out);
			}
			out.writeInt(revenue); // ���Ͽ� ����� �ۼ� (������)
		}
		catch(IOException e) { // Exception �߻� �� �ش� ���� ���
			throw new Exception("���Ϸ� ����� �� �����ϴ�");
		}
	}
	

		
	
	// �ڵ� �ߺ� �˻�
	public void checkCode(Product p) throws Exception {
		for (int i = 0; i < productArrayList.size(); i++)
		{
			// Product p1 = productList[i];
			Product p1 = productArrayList.get(i);

			// �ߺ��� Ű �˻�
			if(p1 != null && p1.getCode().equals(p.getCode()))
			{
				throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
			}
		}
	}
	

	// ��ǰ �迭�� ���� �߰�
	public void addProduct(Product p) {
		// productList[productCount] = p;
		productArrayList.add(p); // add�Լ� �̿��ؼ� ��ǰ ���� �߰�
		pCount++;
	}
	
	// ��ǰ �߰�
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // �ڵ� �ߺ� �˻�
			addProduct(p); // ��ǰ �߰�
		}
		catch (Exception e) {
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
	}
	
	// ��ǰ �迭�� number��° �ε��� ���� ����
	public void subProduct(int number) {
		// ��ǰ ����, �迭 �����ϱ�	
//		for (int i = number; i < productCount; i++)	
//		{		
//			productList[i] = ArrayListproductList[i+1];		
//		}
		productArrayList.remove(number); // arrayList�� remove�Լ��� ����Ͽ� number��° ���� ����

		pCount--;
	}
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		try {
			int number = search(productCode); // ��ǰ �迭���� �˻��ϱ�
			subProduct(number); // ��ǰ �迭���� �����ϱ�
		}
		catch (Exception e) {
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}
	
	// ++ ���� �ڵ带 �Ѱ��ָ� �� ��ǰ �ڵ带 ���� ��ü�� ���� IndexOf()�Լ� ���.
	public void setCode(String pCode)
	{
		
	}
	
	public void searching(String productCode) {
		Product p = new Product();
		// ++ p.setCode(productCode);
		int number = productArrayList.indexOf(p);
		// ++ if(number == -1) throw new Exception("��");
			productArrayList.remove(number);
	}
	// ��ǰ ��ü �˻�
	// ��ǰ �ڵ带 �Ѱ��ָ� 
	
	public int search(String productCode) throws Exception {	
		// ��ǰ �˻�
		for(int i = 0; i < productArrayList.size(); i++)
		{
			// Product p1 = productList[i]; // ��ü ����
			Product p1 = productArrayList.get(i); // 
			
			// ���� �ڵ�� �μ� ��ġ
			if((productCode.equals(p1.getCode())))
			{
				return i; // list�� �ε��� ��ȯ
			}
		} throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�."); // �ڵ尡 ��ġ���� ������ �ͼ��� �߻�
	}

	// ��ǰ �迭 i��° ����
	public Product productAt(int i) throws Exception {
		// return productList[i]; // ��ǰ �迭 i��° ��ǰ ��ü return
//		return productArrayList.get(i);
		
		// ++
		try {
			return productArrayList.get(i);
		}
		catch(IndexOutOfBoundsException iobe)
		{
			return null;
		}
	}
	
	// productCount �� ��ȯ
	public int getProductCount() {
		return pCount;
	}
	
	// ��ȭ��ȣ �ߺ� �˻�
	public void checkPhone(User u) throws Exception {
		for (int i = 0; i < userArrayList.size(); i++)
		{
			// User u1 = userList[i];
			User u1 = userArrayList.get(i);
			
			// �ߺ��� ��ȭ��ȣ �˻�
			if(u1 != null && u1.getPhone().equals(u.getPhone()))
			{
				throw new Exception ("�߸��� ����� üũ���Դϴ�."); // �ߺ��� ��ȭ��ȣ�� ��� �ͼ��� �߻�
			}
		}
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
			// Product p = productList[searchNum]; //�ش� �ε����� product ��ü
			Product p = productArrayList.get(searchNum); // �ش� �ε����� product ��ü get�Լ��� ��������
			
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// �뿩 ����Ʈ�� ���� �߰�
	public void addUser(User u) {
		// userList[userCount] = u;
		userArrayList.add(u); 

		uCount++;
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
		return uCount;
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
//		for (int i = number; i < userCount; i++)	
//		{		
//			userList[i] = userList[i+1];		
//		}
		userArrayList.remove(number); // arrayList�� remove�Լ��� ���� ����
		
		uCount--;
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			
			//int money = userList[index].pay();// �ݾ� ��ȯ�ޱ�
			int money = userArrayList.get(index).pay();
			
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
	
		
	
}