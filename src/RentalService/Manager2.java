package RentalService;

import java.io.*;
import java.util.ArrayList;


public class Manager2 {
	// private Product[]productList; // 상품 배열
	private ArrayList<Product> productArrayList = new ArrayList<Product>(); // 상품 리스트
	private int pCount = 0; // 상품 배열 인덱스 카운트
	
	// private User[]userList; // 대여 배열
	private ArrayList<User> userArrayList = new ArrayList<User>(); // 대여 리스트
	private int uCount = 0; // 대여 배열 인덱스 카운트
	
	private int revenue = 0; // 일일 매출 총액 변수
	
	// 인수 없는 생성자
	Manager() {}
	
	// 인수 있는 생성자 (상품 배열, 대여 배열 크기 설정)
	// Manager (int maxProductCount, int maxUserCount, String answer) throws Exception{
	Manager (ObjectInputStream in) throws Exception{
		// productList = new Product[maxProductCount]; // 상품 배열 크기 설정
		// userList = new User[maxUserCount]; // 대여 배열 크기 설정
	
		// 파일 read하기
		try {
			int productCount = in.readInt(); // 물품 갯수 읽기
			for (int i = 0; i < productCount; i++) // 물품 갯수만큼 반복하여
	        {
				add(new Product().readProductFile(in)); // 물품 리스트에 추가
	        }
			
			int userCount = in.readInt(); // 고객 수 읽기
			for (int i = 0; i < userCount; i++) // 고객 수만큼 반복하여
	        {
				addUser(new User().readUserFile(in)); // 고객 리스트에 추가
	        }
			revenue = in.readInt(); // 매출 읽기
		} 
		catch (FileNotFoundException fnfe) { // rentalData 파일이 존재하지 않을 때
				throw new Exception("파일이 존재하지 않습니다"); 
		}
	}

	
	// 파일에 데이터 저장 함수
	public void saveToFile(ObjectOutputStream out) throws Exception
	{
		// Ui클래스에서 dos를 매개변수로 받아 write 하고싶은 data를 write 한다.
		try {
			int pc = productArrayList.size();
			out.writeInt(pc); // 물품의 갯수 파일에 저장.
			for (int i = 0; i < pc; i++) { // 상품 정보 저장 함수 반복 실행					
				// productList[i].saveProductToFile(dos);
				productArrayList.get(i).saveProductToFile(out);
			}
			
			int uc = userArrayList.size();
			out.writeInt(uc); // 사용자 수 파일에 저장.
			for (int i = 0; i < uc; i++) { // 고객 정보 저장 함수 반복 실행
				// userList[i].saveUserToFile(dos);
				userArrayList.get(i).saveUserToFile(out);
			}
			out.writeInt(revenue); // 파일에 매출액 작성 (정수형)
		}
		catch(IOException e) { // Exception 발생 시 해당 문구 출력
			throw new Exception("파일로 출력할 수 없습니다");
		}
	}
	

		
	
	// 코드 중복 검색
	public void checkCode(Product p) throws Exception {
		for (int i = 0; i < productArrayList.size(); i++)
		{
			// Product p1 = productList[i];
			Product p1 = productArrayList.get(i);

			// 중복된 키 검색
			if(p1 != null && p1.getCode().equals(p.getCode()))
			{
				throw new Exception ("잘못된 상품 등록입니다.");
			}
		}
	}
	

	// 상품 배열에 원소 추가
	public void addProduct(Product p) {
		// productList[productCount] = p;
		productArrayList.add(p); // add함수 이용해서 상품 원소 추가
		pCount++;
	}
	
	// 상품 추가
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // 코드 중복 검색
			addProduct(p); // 상품 추가
		}
		catch (Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");
		}
	}
	
	// 상품 배열에 number번째 인덱스 원소 삭제
	public void subProduct(int number) {
		// 상품 삭제, 배열 정리하기	
//		for (int i = number; i < productCount; i++)	
//		{		
//			productList[i] = ArrayListproductList[i+1];		
//		}
		productArrayList.remove(number); // arrayList의 remove함수를 사용하여 number번째 원소 삭제

		pCount--;
	}
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try {
			int number = search(productCode); // 상품 배열에서 검색하기
			subProduct(number); // 상품 배열에서 삭제하기
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}
	
	// ++ 물폼 코드를 넘겨주면 그 물품 코드를 가진 객체를 만들어서 IndexOf()함수 사용.
	public void setCode(String pCode)
	{
		
	}
	
	public void searching(String productCode) {
		Product p = new Product();
		// ++ p.setCode(productCode);
		int number = productArrayList.indexOf(p);
		// ++ if(number == -1) throw new Exception("상");
			productArrayList.remove(number);
	}
	// 상품 객체 검색
	// 물품 코드를 넘겨주면 
	
	public int search(String productCode) throws Exception {	
		// 상품 검색
		for(int i = 0; i < productArrayList.size(); i++)
		{
			// Product p1 = productList[i]; // 객체 생성
			Product p1 = productArrayList.get(i); // 
			
			// 원소 코드와 인수 일치
			if((productCode.equals(p1.getCode())))
			{
				return i; // list의 인덱스 반환
			}
		} throw new Exception("일치하는 코드를 찾을 수 없습니다."); // 코드가 일치하지 않으면 익셉션 발생
	}

	// 상품 배열 i번째 리턴
	public Product productAt(int i) throws Exception {
		// return productList[i]; // 상품 배열 i번째 상품 객체 return
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
	
	// productCount 값 반환
	public int getProductCount() {
		return pCount;
	}
	
	// 전화번호 중복 검색
	public void checkPhone(User u) throws Exception {
		for (int i = 0; i < userArrayList.size(); i++)
		{
			// User u1 = userList[i];
			User u1 = userArrayList.get(i);
			
			// 중복된 전화번호 검색
			if(u1 != null && u1.getPhone().equals(u.getPhone()))
			{
				throw new Exception ("잘못된 방법의 체크인입니다."); // 중복된 전화번호일 경우 익셉션 발생
			}
		}
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
			// Product p = productList[searchNum]; //해당 인덱스의 product 객체
			Product p = productArrayList.get(searchNum); // 해당 인덱스의 product 객체 get함수로 가져오기
			
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 대여 리스트에 원소 추가
	public void addUser(User u) {
		// userList[userCount] = u;
		userArrayList.add(u); 

		uCount++;
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
		return uCount;
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
//		for (int i = number; i < userCount; i++)	
//		{		
//			userList[i] = userList[i+1];		
//		}
		userArrayList.remove(number); // arrayList의 remove함수로 원소 삭제
		
		uCount--;
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			
			//int money = userList[index].pay();// 금액 반환받기
			int money = userArrayList.get(index).pay();
			
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
	
		
	
}