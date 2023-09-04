package RentalService;

import java.io.*;

public class Product implements Serializable {
	private String productName; // ��ǰ �̸�
	private String productCode; // ��ǰ �ڵ�
	private int productNumber; // ��ǰ ����
	private int price; // ��ǰ ����
	
	// �μ� ���� ������
	Product() {}
	
	// �μ� �ִ� ������1 (��ǰ �ڵ常 ����)
	Product(String productCode)
	{
		this.productCode = productCode;
	}
	
	// �μ� �ִ� ������2
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	//++ getter, setter
	int getProductNumber(){return productNumber;}
	void setProductNumber(int n){productNumber = n;}
	
	
	// ++ ��ǰ ������ ȭ�ϰ�ü�� write �ϴ� �Լ�
	// �Է¹��� ��ǰ ������ �о ���Ͽ� ���� �Լ�
//	public void saveProductToFile(ObjectOutputStream oos)
//	{
//		// oos ��ü�� Product ��ü�� ���Ͽ� ����
//		try {
//        	Product p = new Product(productName, productCode, productNumber, price); 
//        	oos.writeObject(p);
//		}
//		catch(IOException e) { // Exception �߻� �� �ش� ���� ��� 
//			System.out.println("���Ϸ� ����� �� �����ϴ�");
//		}
//	}
	
	
	// ���Ͽ��� ��ǰ ������ �о���� �Լ�
	public Product readProductFile(ObjectInputStream ois) throws Exception
	{
		Product p = null;
		try {
			// ois ��ü�� Product ��ü�� �о
			p = (Product)ois.readObject();
		}
		catch(FileNotFoundException fnfe) {
			throw new Exception("������ �������� �ʽ��ϴ�.");
		}
		catch(IOException e) { // Exception �߻� �� �ش� ���� ��� 
			throw new Exception("���Ϸ� ����� �� �����ϴ�");
		}
		return p; // ��ȯ
	}
	
	
	
	// ��ǰ �̸� ��ȯ
	public String getName()
	{
		return productName;
	}
	
	// ��ǰ �ڵ� ��ȯ
	public String getCode()
	{
		return productCode;
	}
	
	// ��ǰ ���� ��ȯ
	public int getNumber()
	{
		return productNumber;
	}
	
	// ��ǰ ���� �߰�
	public void addNumber()
	{
		productNumber++;
	}
	
	// ��ǰ �뿩 �������� Ȯ�� �� ��ǰ ���� 1�� ����
	public void subNumber() throws Exception{
		if(productNumber < 1) // ��� ������ 1���� ���� ��� 
			throw new Exception("�߸��� ����� üũ���Դϴ�."); // �ͼ��� �߻�
		else // ��� ���� ���ڰ� 1�̻��� ���
			productNumber--; // ��� �� 1�� ����
	}
	
	// ��ǰ ���� ��ȯ
	public int getPrice()
	{
		return price;
	}
	
	// ��� �˻� �Լ� (��� ������ true, �ƴϸ� false ��ȯ)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	@Override
	   public boolean equals(Object o) {
	      // o�� Product ��ü�� �ƴϸ�
	      if (!(o instanceof Product))
	         // false ��ȯ
	         return false;
	      Product p = (Product) o;
	      // ��ȭ��ȣ�� ������ true, �ٸ��� false ��ȯ
	      return p.getCode().equals(this.getCode());
	   }
}