package RentalService;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

public class Ui {
	public static void main(String[] args) throws Exception{
		Scanner scan = new Scanner(System.in);
		Calendar getToday = Calendar.getInstance();
		Manager act = null;
		ObjectInputStream ois = null; //DataInputStream ��ü ���� �� null������ �ʱ�ȭ
		
		
		try { // ������ ������ ��� ���α׷� ����
			ois = new ObjectInputStream(new FileInputStream("RentalData.dat")); //RentalData.dat���� ������ �����ϰ� ���� ����1
			act = new Manager(ois);
			System.out.println("����� ������ �ҷ����� �Ϸ�!");
		}
			
		catch(FileNotFoundException fnfe) { // ������ �������� ���� ���
			while(true) {
				System.out.println("������ ã�� �� �����ϴ�. �ش� ���α׷��� ����غ� ���� ��������? (������ Y/y�� �ƴϸ� N/n�� �Է��ϼ���.) : ");
				String ans = scan.nextLine();
				System.out.println(" ");
			
				// ����� ������ ���(���α׷� ����)
				if (ans.equals("Y") || ans.equals("y"))
				{ 
					System.out.println("������ ã�� �� ���� ���α׷��� �ʱ�ȭ ���·� �����մϴ�.");
					act = new Manager();
					break;
				}
				// ����� ���� ���(�Ŵ��� ��ü �Ϲ� ����)
				else if (ans.equals("N") || ans.equals("n"))
				{
					// ������ ���� ���� ��ǰ �迭�� ���� �迭 ����
					act = new Manager();
					break;
				}
				// �߸��� ����� ���
				else
				{
					System.out.println("�߸��� �Է��Դϴ�.");
					System.out.println(" ");
				}
			}
		}
		finally { // ���� ���� �� ���� �ݱ�
			try {
				ois.close();
			}
			catch(Exception e) {	
			}
		}
		
			
		
		while(true)
		{
			Menu.menu(); // �޴� �����ֱ�
			int menu = scan.nextInt(); // ��ȣ �Է¹ޱ�
			
			// ��ȣ�� ���� �޼ҵ� ����
			switch(menu)
			{
			case 1: // ��ǰ ���
				try {
					// ��ǰ �̸� �Է¹ޱ�
					scan.nextLine();
					System.out.printf("����� ��ǰ�� �̸��� �Է��ϼ��� : "); 
					String addName = scan.nextLine();
					
					// ��ǰ �ڵ� �Է¹ޱ�
					System.out.printf("����� ��ǰ�� �ڵ带 �Է��ϼ��� : ");
					String addCode = scan.nextLine();
				
					// ��ǰ ���� �Է¹ޱ�
					System.out.printf("����� ��ǰ�� ������ �Է��ϼ��� : ");
					int addNumber = scan.nextInt();
				
					// ��ǰ ���� �Է¹ޱ�
					System.out.printf("����� ��ǰ�� ������ �Է��ϼ��� : ");
					int addPrice = scan.nextInt();
					
					// �� ��ǰ ��ü ����
					Product p1;
					p1 = new Product(addName, addCode, addNumber, addPrice);
					
					// ��ǰ �߰��ϱ�
					act.add(p1);
					System.out.println(" ");
					System.out.println("��ǰ�� ����Ͽ����ϴ�.");
					System.out.println(" ");
				}
				
				catch (Exception e) { // ���� �߻�
					System.out.println(" ");
					System.out.println("�߸��� ��ǰ ����Դϴ�.");
					System.out.println(" ");
				}
				
				break; // switch�� ����
				
				
				
				
			case 2: // ��ǰ ����
				try {
					// ��ǰ �ڵ� �Է¹ޱ�
					scan.nextLine();
					System.out.printf("������ ��ǰ�� �ڵ带 �Է��ϼ��� : ");
					String delCode = scan.nextLine();
					
					// ��ǰ �����ϱ�
					act.delete(delCode);
					System.out.println(" ");
					System.out.println("��ǰ�� �����Ǿ����ϴ�.");
					System.out.println(" ");
				}
				
				catch(Exception e){ // ���� �߻�
					System.out.println(" ");
					System.out.println("�������� �ʴ� ��ǰ�Դϴ�.");
					System.out.println(" ");
				}
				break; // switch�� ����
				
				
				
				
			case 3: // ��ü ��ǰ �����ֱ�
				// ��ǰ ���
				System.out.println(" ");
//				if (act.productAt(0) == null) { // ���Ұ� ���� ���
//						System.out.println("��ǰ�� �����ϴ�.");
//				}
				
				if (act.getProductCount() == 0) { // ���Ұ� ���� ���
					System.out.println("��ǰ�� �����ϴ�.");
				}
				
				else { // ���Ұ� ���� ���
					System.out.println("��ü ��ǰ�� ����մϴ�."); // ��� �ȳ� ���� ���
					
					for(int i = 0; i < act.getProductCount(); i++)
					{
						// product ��ü���� �ʿ��� ���� ����
						String name = act.productAt(i).getName();
						String code = act.productAt(i).getCode();
						int number = act.productAt(i).getNumber();
						int price = act.productAt(i).getPrice();
						
						// ���
						System.out.printf("��ǰ��: %s, �ڵ�: %s, ����: %d, ����: %d", name, code, number, price);
						System.out.println(" ");
					}
				}
				
				System.out.println(" ");
				break; // switch�� ����
				
				
				
				
			case 4: // ��� ��ǰ �����ֱ�
				// ��� ��ǰ ���
				System.out.println(" ");
				
				if (act.productAt(0) == null) // ���Ұ� ���� ���
					System.out.println("��� ��ǰ�� �����ϴ�.");
			
				else {
					// productCount : ��ǰ �迭 �ε��� ī��Ʈ
					for(int i = 0; i < act.getProductCount(); i++)
					{
						if(act.productAt(i).isEmpty() == false) // ��� 0�� ���
						{
							if (i == 0) { // i�� ù��° index�� ���
								for (int k = 0; k < act.getProductCount(); k++)
								{
									if(act.productAt(k).isEmpty()) { // ��� �ϳ��� �ִ� ���
										System.out.println("��� ��ǰ�� ����մϴ�."); // ��� �ȳ� ���� ���
										break; // �ݺ��� ����
									}
									
									else // i�� ù��°���� ������ �ε����� ���
									{
										if (k == act.getProductCount() - 1) // ��� ��ǰ�� �ϳ��� ���� ���
											System.out.println("��� ��ǰ�� �����ϴ�.");
									}
								}
							}							
							continue;
						}
						
						else { // ��� 0�� �ƴ� ���
							if (i == 0)
								System.out.println("��� ��ǰ�� ����մϴ�."); // ��� �ȳ� ���� ���
							
							// product ��ü���� �ʿ��� ���� ����
							String name = act.productAt(i).getName();
							String code = act.productAt(i).getCode();
							int number = act.productAt(i).getNumber();
							int price = act.productAt(i).getPrice();
						
							// ���
							System.out.printf("��ǰ��: %s, �ڵ�: %s, ����: %d, ����: %d", name, code, number, price);
							System.out.println(" ");
						}
					}
				}
				
				System.out.println(" ");
				break; // switch�� ����
				
				
				
				
			case 5: // üũ�� �ϱ�
				try {
					// �̸� �Է¹ޱ�
					scan.nextLine();
					System.out.printf("üũ�� �Ͻ� ���� �̸��� �����ּ��� : ");
					String name = scan.nextLine();
					
					// ��ȭ��ȣ �Է¹ޱ�
					System.out.printf("üũ�� �Ͻ� ���� ��ȭ��ȣ�� �����ּ��� : ");
					String phone = scan.nextLine();
					
					// �뿩 ���� �Է��ϱ�
					String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime());
					
					// �ݳ� ���� ���� �Է¹ޱ�
					System.out.printf("�ݳ� ���� ���ڸ� �����ּ���(yyyy-MM-dd �������� ����) : ");
					String returnDay = scan.nextLine();
					
					// User ��ü ����
					User u1;
					u1 = new User(name, phone, rentalDay, returnDay);
					
					
					// �뿩�� ��ǰ ���� �Է� �ޱ�
					System.out.printf("�뿩�� ��ǰ ������ �����ּ���(�ִ� 3��) : ");
					int rentalNum = scan.nextInt();
					
					// �뿩�� ��ǰ ������ 4�̻��϶� �ͼ��� �߻�
					if(rentalNum >= 4)
						throw new Exception("�߸��� ����� üũ���Դϴ�.");
					
					scan.nextLine();
					// �뿩�� ��ǰ �ڵ� �Է� �ޱ�
					for(int i = 0; i < rentalNum; i++)
					{
						System.out.printf("�뿩�� ��ǰ �ڵ带 �����ּ��� : ");
						String rentalCode = scan.nextLine();
						int index = act.search(rentalCode); // ** ��ǰ �ڵ忡 �´� ��ǰ�� list �ε�����
						int amount = act.productAt(index).getPrice(); // index��° ��ǰ�� ���ݰ� ��������
						u1.addProduct(rentalCode, amount); // �뿩 ��ǰ �ڵ� �迭�� �ڵ� �߰�
						
						act.substock(index, amount);
						act.addstock(index, amount);
						
					}
					
					// üũ�� �ϱ�
					act.checkIn(u1);
					System.out.println(" ");
					System.out.println("�뿩�� �Ϸ�Ǿ����ϴ�.");
				}
				catch (Exception e) {
					System.out.println(" ");
					System.out.println("�߸��� ����� üũ���Դϴ�.");
				}
				System.out.println(" ");
				break; // switch�� ����
				
				
				
				
			case 6: // üũ�� ���� �����ֱ�
				System.out.println(" ");
//				if (act.userAt(0) == null) // ���Ұ� ���� ���
//					System.out.println("üũ�� ������ �����ϴ�.");
				
				if (act.getUserCount() == 0) // ���Ұ� ���� ���
					System.out.println("üũ�� ������ �����ϴ�.");
				
				else { // ���Ұ� �ִ� ���
					System.out.println("üũ�� ������ ����մϴ�."); // ��� �ȳ� ���� ���
					
					for(int i = 0; i < act.getUserCount(); i++)
					{
						// user ��ü���� �ʿ��� ���� ����
						String name =act.userAt(i).getName();
						String phone =act.userAt(i).getPhone();
						String rentalDay = act.userAt(i).getRentalDay();
						String returnDay = act.userAt(i).getReturnDay();
						
						// ���
						System.out.printf("�̸�: %s, ��ȭ��ȣ: %s, �뿩 ����: %s, �ݳ� ���� ����: %s", name, phone, rentalDay, returnDay);
						for(int k = 0; k < act.userAt(i).getRentalCount(); k++)
						{
							String code = act.userAt(i).codeAt(k);
							System.out.printf(", %d��° �뿩 ��ǰ �ڵ� : %s", k+1, code);
						}
						System.out.println(" ");
					}
				}
				
				System.out.println(" ");
				break; // switch�� ����
				
			case 7: // üũ�ƿ� �ϱ�
				try {
					scan.nextLine();
					System.out.println(" ");
					System.out.printf("�뿩�Ͻ� ���� ��ȭ��ȣ�� �Է��ϼ��� : ");
					String phone = scan.nextLine();
					
					// ��ġ�ϴ� ��ȭ��ȣ Ȯ��
					int index = act.searchUser(phone);
					
					// �ٽ��ѹ� üũ�ƿ� Ȯ���ϱ�
					System.out.printf("üũ�ƿ� �Ͻðڽ��ϱ�? (������ Y/y�� �ƴϸ� N/n�� �Է��ϼ���.) : ");
					String answer1 = scan.nextLine();
					System.out.println(" ");
					
					// ����� ������ ��� (üũ�ƿ� ����)
					if (answer1.equals("Y") || answer1.equals("y"))
					{
						// �� �ݾ� ����ϱ�
						int money = act.userAt(index).pay();
						System.out.printf("�����Ͻ� �ݾ��� %d�� �Դϴ�.", money);
						
						// �� ���ҿ��� �����
						System.out.println(" ");
						System.out.printf("���� �����Ͻðڽ��ϱ�? (������ Y/y�� �ƴϸ� N/n�� �Է��ϼ���.) : ");
						String answer2 = scan.nextLine();
						System.out.println(" ");
						
						// ����� ������ ��� (üũ�ƿ� ����)
						if (answer2.equals("Y") || answer2.equals("y")) {
							
							act.checkOut(index);
							
							// ++ ȭ�� ��ü�� ����� ���� 
							
							System.out.println("üũ�ƿ��� �Ϸ�Ǿ����ϴ�.");
							
						}
						
						// ����� ���� ��� (üũ�ƿ� ������)
						else if (answer2.equals("N") || answer2.equals("n"))
							break;
						
						// �߸� ������� ���
						else
							throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
					}
					
					// ����� ���� ��� (üũ�ƿ� ������)
					else if (answer1.equals("N") || answer1.equals("n"))
						break;
					
					// �߸� ������� ���
					else
						throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
				}
				catch(Exception e) {
					System.out.println(" ");
					System.out.println("�߸��� ����� üũ�ƿ��Դϴ�.");
				}
				System.out.println(" ");
				break; // switch�� ����
				
				
				
			case 8: // ���� ���� ��� ����
				System.out.println(" ");
				System.out.println("���� ������ ����մϴ�."); // ���� ���� ��� �ȳ� ��Ʈ
				System.out.printf("���� ������ %d���Դϴ�.", act.getRevenue()); // ���� ���� ���
				System.out.println(" ");
				break; // switch�� ����
				
				
			
			case 9: // ������ ����
				// ** RentalService���α׷��� �����ص� �����Ͱ� ��� ����� �� �ֵ��� ���Ͽ� ����
				ObjectOutputStream oos = null; //DataOutputStream ��ü ���� �� null������ �ʱ�ȭ
				try 
				{
					oos = new ObjectOutputStream(new FileOutputStream("RentalData.dat")); //RentalData.dat���� ������ �����ϰ� ���� ����
					act.saveToFile(oos); // ���� ���� �Լ� ����
				}
				catch(IOException ioe) { // Exception �߻� �� �ش� ���� ���
					System.out.println("���Ϸ� ����� �� �����ϴ�");
				}
				finally {
					try { //Exception�� ���ٸ� ������ ����
						oos.close();
						System.out.println("���α׷��� �����͸� �����մϴ�.");
					}
					catch(Exception e) {
					}
				}
				
				System.out.println(" ");
				break; // switch�� ����
				
				
				
			case 10:// ����
				System.out.println(" ");
				System.out.println("���α׷��� �����մϴ�");
				break; // switch�� ����
				
				
				
			default: // �� �� �Է��� ���
				System.out.println(" ");
				System.out.println("�ٽ� �Է��ϼ���.");
				System.out.println(" ");
				break; // switch�� ����
			}
			
			
			if(menu == 10)
				break; // �ݺ��� ����
		}
		
		scan.close(); // ��ĵ ����
	}
}