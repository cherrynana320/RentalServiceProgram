package RentalService;

import java.io.*;
import java.util.ArrayList;

public class Manager {
	// private Product[]productList; // 상품 배열
	private ArrayList<Product> productArrayList = new ArrayList<Product>(); // 상품 리스트
	
	// private User[]userList; // 대여 배열
	private ArrayList<User> userArrayList = new ArrayList<User>(); // 대여 리스트
	
	private Integer revenue = 0; // 일일 매출 총액 변수
	
	// 인수 없는 생성자
	Manager() {}
	
	// 인수 있는 생성자
	Manager (ObjectInputStream ois) throws Exception{
		// 파일 read하기
		// 역직렬화
		try {
			Integer pCount = (Integer)ois.readObject(); // 물품 갯수 읽기
			for (int i = 0; i < pCount.intValue(); i++) // 물품 갯수만큼 반복하여
	        {
				add(new Product().readProductFile(ois)); // 물품 리스트에 추가
	        }
			
			Integer uCount = (Integer)ois.readObject(); // 고객 수 읽기
			for (int i = 0; i < uCount.intValue(); i++) // 고객 수만큼 반복하여
	        {
				userArrayList.add(new User().readUserFile(ois)); // 유저 리스트에 추가
	        }
			revenue = (Integer)ois.readObject(); // 매출 읽기
		}
		catch (FileNotFoundException fnfe) { // rentalData 파일이 존재하지 않을 때
				throw new Exception("파일이 존재하지 않습니다"); 
		}
	}

	
	// 파일에 데이터 저장 함수
	// 직렬화
	public void saveToFile(ObjectOutputStream oos) throws Exception
	{
		// Ui클래스에서 oos를 매개변수로 받아 write 하고싶은 data를 write 한다.
		try {
			// dos.writeInt(productArrayList.size()); // 물품의 갯수 파일에 저장.
			Integer pCount = (Integer)(productArrayList.size());
			oos.writeObject(pCount);
			for (int i = 0; i < pCount; i++) { // 상품 정보 저장 함수 반복 실행
				oos.writeObject(productArrayList.get(i));
			}
			
			// dos.writeInt(userArrayList.size()); // 사용자 수 파일에 저장.
			Integer uCount = (Integer)(userArrayList.size());
			oos.writeObject(uCount);
			for (int i = 0; i < userArrayList.size(); i++) { // 고객 정보 저장 함수 반복 실행
				oos.writeObject(userArrayList.get(i));
			}
			oos.writeObject(revenue); // 파일에 매출액 작성 (정수형)
		}
		catch(IOException e) { // Exception 발생 시 해당 문구 출력
			throw new Exception("파일로 출력할 수 없습니다");
		}
	}
	


	// 코드 중복 검색
	public void checkCode(Product p) throws Exception {
		// 상품 코드가 이미 있으면
		if (productArrayList.indexOf(p) >= 0)
			// 예외 발생
			throw new Exception("상품 등록이 불가합니다.");
	}
	
	
	// 상품 추가
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // 코드 중복 검색
			productArrayList.add(p); // 상품 추가
		}
		catch (Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");
		}
	}
	
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try {
			int number = search(productCode); // 상품 배열에서 검색하기
			productArrayList.remove(number); // 상품 배열에서 삭제하기
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// 상품 객체 검색
	// 받은 상품 코드와 맞는 상품의 list 인덱스값을 return
	public int search(String productCode) throws Exception {		
		// 인수로 받은 상품 코드와 같은 코드를 가진 상품이 있는지 찾기 
		int index = productArrayList.indexOf(new Product(productCode));
		
		// 코드가 일치하는 상품이 있으면
		if (index >= 0)
			return index; // 인덱스 반환
		else // 상품이 없으면
			throw new Exception("일치하는 코드를 찾을 수 없습니다."); // 익셉션 발생
	}
	

	// 상품 배열 i번째 리턴
	public Product productAt(int i) throws Exception {
		try {
			return productArrayList.get(i); // 상품 배열 i번째 상품 객체 return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// productArrayList의 size 값 반환 (pCount 대신)
	public int getProductCount() {
		return productArrayList.size();
	}
	
	// 전화번호 중복 검색
	public void checkPhone(User u) throws Exception {
		// 전화번호가 이미 있으면
		if (userArrayList.indexOf(u) >= 0)
			// 예외 발생
			throw new Exception("잘못된 방법의 체크인입니다.");
	}
	
	// 재고 개수에서 대여 개수 제외
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				searchNum = search(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			
			Product p = productArrayList.get(searchNum); // 해당 인덱스의 product 객체 get함수로 가져오기
			
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 대여 리스트에 원소 추가
	public void addUser(User u) {
		userArrayList.add(u); 
	}
	
	// 체크인
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // 전화번호 중복 검색
			subStock(u); // 재고 개수에서 대여 개수 제외
			addUser(u); // 대여 배열에 대여 정보 넣기
		}
		catch(Exception e) {
			throw new Exception("잘못된 방법의 체크인입니다.");
		}
	}
	
	// userCount 값 반환
	public int getUserCount() {
		return userArrayList.size();
	}
	
	// 대여 배열 i번째 리턴
	public User userAt(int i)
	{
		// return userList[i]; // 대여 배열 i번째 유저 객체 return
		return userArrayList.get(i); // arrayList의 get함수로 i번째 유저 객체 return
	}
	
	// 일치하는 회원번호 검색
	public int searchUser(String phone) throws Exception {
		
		for(int i = 0; i < userArrayList.size(); i++)
		{
			// User u = userList[i];
			User u = userArrayList.get(i);
			// 일치하는 정보가 있으면 인덱스 번호 반환
			if (u.getPhone().equals(phone))
				return i;
		}throw new Exception ("회원정보가 없습니다."); // 일치하는 정보가 없으면 익셉션 발생
	}
	
	// 상품 재고 다시 추가
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				int number = search(code); // productList에서 해당 코드의 인덱스 번호 검색
				productAt(number).addNumber(); //해당 인덱스의 product 객체의 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 대여 배열에 원소 삭제
	public void subUser(int number) {
		userArrayList.remove(number); // arrayList의 remove함수로 원소 삭제
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			
			int money = userArrayList.get(index).pay(); // 금액 반환받기
			
			subUser(index); // userList에서 삭제, 배열 정리하기
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}
	
	// 매출 반환
	public int getRevenue() {
		return revenue;
	}
	
	
	// ++ 상품 리스트의 index번째 상품객체의 재고가 n개 이상일 경우 재고를 n개 제거함. n개 이하의 재고시 exception 발생
	void substock(int index, int n) throws Exception
	{
		Product p = productArrayList.get(index); // index번째 상품객체 
		int pn = p.getProductNumber(); // index번째 상품객체의 재고 갯수 불러오기
		try
		{
			if(pn >= n ) // n개 이상의 재고일 경우, n개를 제거
			{
				p.setProductNumber(pn-n);
			}
			else // n개 이하의 재고일 경우, exception 발생
			{
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
		catch(Exception e)
		{
			System.out.println("n개 이하의 재고시 exception 발생");
		}
	}

	
	
	
	
	// ++ 상품 리스트의 index번째 상품객체의 재고를 n개 증가함.
	void addstock(int index, int n) {
		Product p = productArrayList.get(index); // index번째 상품객체 
		int pn = p.getProductNumber();  // index번째 상품객체의 재고 갯수 불러오기
		p.setProductNumber(pn+n); //재고를 n개 증가
	}

	

	 
	
	
	
}