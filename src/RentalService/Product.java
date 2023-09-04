package RentalService;

import java.io.*;

public class Product implements Serializable {
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	// 인수 없는 생성자
	Product() {}
	
	// 인수 있는 생성자1 (상품 코드만 대입)
	Product(String productCode)
	{
		this.productCode = productCode;
	}
	
	// 인수 있는 생성자2
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
	
	
	// ++ 상품 정보를 화일객체에 write 하는 함수
	// 입력받은 상품 정보를 읽어서 파일에 적는 함수
//	public void saveProductToFile(ObjectOutputStream oos)
//	{
//		// oos 객체로 Product 객체를 파일에 저장
//		try {
//        	Product p = new Product(productName, productCode, productNumber, price); 
//        	oos.writeObject(p);
//		}
//		catch(IOException e) { // Exception 발생 시 해당 문구 출력 
//			System.out.println("파일로 출력할 수 없습니다");
//		}
//	}
	
	
	// 파일에서 물품 정보를 읽어오는 함수
	public Product readProductFile(ObjectInputStream ois) throws Exception
	{
		Product p = null;
		try {
			// ois 객체로 Product 객체를 읽어서
			p = (Product)ois.readObject();
		}
		catch(FileNotFoundException fnfe) {
			throw new Exception("파일이 존재하지 않습니다.");
		}
		catch(IOException e) { // Exception 발생 시 해당 문구 출력 
			throw new Exception("파일로 출력할 수 없습니다");
		}
		return p; // 반환
	}
	
	
	
	// 상품 이름 반환
	public String getName()
	{
		return productName;
	}
	
	// 상품 코드 반환
	public String getCode()
	{
		return productCode;
	}
	
	// 상품 개수 반환
	public int getNumber()
	{
		return productNumber;
	}
	
	// 상품 개수 추가
	public void addNumber()
	{
		productNumber++;
	}
	
	// 상품 대여 가능한지 확인 후 상품 개수 1개 삭제
	public void subNumber() throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("잘못된 방법의 체크인입니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber--; // 재고 수 1개 감소
	}
	
	// 상품 가격 반환
	public int getPrice()
	{
		return price;
	}
	
	// 재고 검색 함수 (재고가 있으면 true, 아니면 false 반환)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	@Override
	   public boolean equals(Object o) {
	      // o가 Product 객체가 아니면
	      if (!(o instanceof Product))
	         // false 반환
	         return false;
	      Product p = (Product) o;
	      // 전화번호가 같으면 true, 다르면 false 반환
	      return p.getCode().equals(this.getCode());
	   }
}